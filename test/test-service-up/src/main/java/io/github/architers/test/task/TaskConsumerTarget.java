package io.github.architers.test.task;


import lombok.Data;

import java.lang.reflect.Method;

/**
 * 任务消费目标对象
 *
 * @author luyi
 */
@Data
public class TaskConsumerTarget {

    /**
     * 任务组
     */
    private String group;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务对应的类
     */
    private Object taskBean;

    /**
     * 任务对应的方法
     */
    private Method taskMethod;

    /**
     * 优先级
     */
    private int priority;

    /**
     * 可靠性
     */
    private boolean reliable;

    /**
     * 最大任务
     */
    private int maxTask = -1;

    /**
     * 任务参数
     */
    private Object[] args;


}
