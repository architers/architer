package com.core.es.model;

/**
 * @author luyi
 * 数据类型
 */
public enum MappingType {
    /**
     * 数值型
     */
    LONG("long"),
    DOUBLE("double"),
    /**
     * boolean
     */
    BOOLEAN("boolean"),
    /**
     * 结构化内容，例如 ID、电子邮件地址、主机名、状态代码、邮政编码或标签
     */
    KEYWORD("keyword"),
    /**
     * 对于始终包含相同值的关键字字段
     */
    CONSTANT_KEYWORD("constant_keyword"),
    /**
     * 通配符字段类型
     */
    WILDCARD("wildcard");
    private final String type;

    MappingType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
