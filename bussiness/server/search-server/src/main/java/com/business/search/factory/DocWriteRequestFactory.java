package com.business.search.factory;


import com.core.es.model.doc.DocumentRequest;
import org.elasticsearch.action.DocWriteRequest;

/**
 * 工厂
 *
 * @author luyi
 */
public interface DocWriteRequestFactory<T> {
    /**
     * 生产DocWriteRequest
     *
     * @param documentRequest 文档操作请求
     * @return 具体的DocWriteRequest的文档操作类型
     */
    DocWriteRequest<T> get(DocumentRequest documentRequest);

}
