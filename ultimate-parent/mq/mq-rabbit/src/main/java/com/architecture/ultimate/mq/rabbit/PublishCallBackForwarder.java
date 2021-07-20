package com.architecture.ultimate.mq.rabbit;


import com.architecture.ultimate.mq.rabbit.CallBackMessage;
import com.architecture.ultimate.mq.rabbit.ConfirmCallbackHandler;
import com.architecture.ultimate.mq.rabbit.ReturnCallbackHandler;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author luyi
 * 发布确认回调转发器
 * 1.对消息没有投递到broker处理
 * 2.对消息没有投递到队列处理
 */
public class PublishCallBackForwarder implements ApplicationContextAware {
    private RabbitTemplate rabbitTemplate;

    private RabbitProperties rabbitProperties;

    private Map<String, ConfirmCallbackHandler> confirmCallbackHandlerMap;

    private Map<String, ReturnCallbackHandler> returnCallbackHandlerMap;


    public PublishCallBackForwarder(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired(required = false)
    public void setRabbitProperties(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    @PostConstruct
    public void startInitPublishCallBack() {
        this.initConfirmCallBack();
        this.initReturnCallBack();
    }

    /**
     * 消息没有投递到队列处理
     */
    private void initReturnCallBack() {
        if (returnCallbackHandlerMap == null) {
            return;
        }
        if (rabbitProperties != null && rabbitProperties.isPublisherReturns()) {
            rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
                if (!(message instanceof CallBackMessage)) {
                    return;
                }
                CallBackMessage callBackMessage = (CallBackMessage) message;
                String returnKey = callBackMessage.getReturnKey();
                ReturnCallbackHandler returnCallbackHandler = returnCallbackHandlerMap.get(returnKey);
                if (returnCallbackHandler != null) {
                    returnCallbackHandler.returnedMessage(message, replyCode, replyText, exchange, routingKey);
                }
            });
        }
    }

    /**
     * 对消息没有投递到broker处理
     */
    private void initConfirmCallBack() {
        if (confirmCallbackHandlerMap == null) {
            return;
        }
        if (rabbitProperties != null && !CachingConnectionFactory.ConfirmType.NONE.equals(rabbitProperties.getPublisherConfirmType())) {
            rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                if (!(correlationData instanceof CallbackCorrelationData)) {
                    return;
                }
                CallbackCorrelationData confirmCorrelationData = (CallbackCorrelationData) correlationData;
                String confirmKey = confirmCorrelationData.getConfirmKey();
                if (StringUtils.isEmpty(confirmKey)) {
                    return;
                }
                ConfirmCallbackHandler confirmCallbackHandler = confirmCallbackHandlerMap.get(confirmKey);
                if (confirmCallbackHandler == null) {
                    throw new IllegalArgumentException(confirmKey + "not exist");
                }
                confirmCallbackHandler.confirm(correlationData, ack, cause);
            });

        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initConfirmCallbackHandler(applicationContext);
        this.initReturnCallBackHandler(applicationContext);
    }

    private void initReturnCallBackHandler(ApplicationContext applicationContext) {
        Map<String, ReturnCallbackHandler> returnCallbackMap = applicationContext.getBeansOfType(ReturnCallbackHandler.class);

        if (CollectionUtils.isEmpty(returnCallbackMap)) {
            return;
        }
        Collection<ReturnCallbackHandler> returnCallbackHandlers = returnCallbackMap.values();
        returnCallbackHandlerMap = new HashMap<>(returnCallbackHandlers.size());
        for (ReturnCallbackHandler returnCallbackHandler : returnCallbackHandlers) {
            String callbackKey = returnCallbackHandler.getReturnCallbackKey();
            if (StringUtils.isEmpty(callbackKey)) {
                continue;
            }
            if (returnCallbackHandlerMap.containsKey(callbackKey)) {
                throw new RuntimeException(callbackKey + "已经存在");
            }
            returnCallbackHandlerMap.putIfAbsent(callbackKey, returnCallbackHandler);
        }
    }

    private void initConfirmCallbackHandler(ApplicationContext applicationContext) {
        Map<String, ConfirmCallbackHandler> confirmCallbackMap = applicationContext.getBeansOfType(ConfirmCallbackHandler.class);

        if (CollectionUtils.isEmpty(confirmCallbackMap)) {
            return;
        }
        Collection<ConfirmCallbackHandler> confirmCallbackHandlers = confirmCallbackMap.values();
        confirmCallbackHandlerMap = new HashMap<>(confirmCallbackHandlers.size());
        for (ConfirmCallbackHandler confirmCallbackHandler : confirmCallbackHandlers) {
            String confirmKey = confirmCallbackHandler.getConfirmCallbackKey();
            if (StringUtils.isEmpty(confirmKey)) {
                continue;
            }
            if (confirmCallbackHandlerMap.containsKey(confirmKey)) {
                throw new RuntimeException(confirmKey + "已经存在");
            }
            confirmCallbackHandlerMap.putIfAbsent(confirmKey, confirmCallbackHandler);
        }
    }
}
