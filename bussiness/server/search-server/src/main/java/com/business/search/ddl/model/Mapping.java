package com.business.search.ddl.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 */
public class Mapping implements Serializable {
    /**
     * index项
     */
    private String index;

    /**
     * mapping定义
     */
    private List<MappingItem> mappingItems;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<MappingItem> getMappingItems() {
        return mappingItems;
    }

    public void setMappingItems(List<MappingItem> mappingItems) {
        this.mappingItems = mappingItems;
    }
}
