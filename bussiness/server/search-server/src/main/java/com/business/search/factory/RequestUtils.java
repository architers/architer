package com.business.search.factory;

import com.core.es.model.doc.DocumentRequest;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * request工具类
 *
 * @author luyi
 */
@Component
public class RequestUtils {

    private List<FactorySupport<DocWriteRequest>> factorySupportList;


    public DocWriteRequest getDocWriteRequest(DocumentRequest documentRequest) {
        // DocWriteRequest生产-抽象工厂模式/适配器
        for (FactorySupport factorySupport : factorySupportList) {
            if (factorySupport.support(documentRequest.getRequestType())) {
                return factorySupport.get(documentRequest);
            }
        }
        return null;
    }

    public BulkRequest getBulkRequest(DocumentRequest documentRequest) {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(getDocWriteRequest(documentRequest));
        return bulkRequest;
    }


    public BulkRequest getBulkRequest(List<DocumentRequest> documentRequests) {
        BulkRequest bulkRequest = new BulkRequest();
        documentRequests.forEach(documentRequest -> {
            bulkRequest.add(getDocWriteRequest(documentRequest));
        });
        return bulkRequest;
    }

    @Autowired
    public void setFactorySupportList(List<FactorySupport<DocWriteRequest>> factorySupportList) {
        this.factorySupportList = factorySupportList;
    }
}
