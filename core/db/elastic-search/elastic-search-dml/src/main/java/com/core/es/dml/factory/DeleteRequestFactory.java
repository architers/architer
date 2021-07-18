package com.core.es.dml.factory;

import com.core.es.model.RequestType;
import com.core.es.model.doc.DocumentRequest;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.springframework.stereotype.Component;

/**
 * 得到删除request
 *
 * @author luyi
 */
@Component
public class DeleteRequestFactory implements FactorySupport<DeleteRequest> {
    @Override
    public DocWriteRequest<DeleteRequest> get(DocumentRequest documentRequest) {
        return new DeleteRequest(documentRequest.getIndex(), documentRequest.getId());
    }

    @Override
    public boolean support(String requestType) {
        return RequestType.DELETE.name().equals(requestType);
    }
}
