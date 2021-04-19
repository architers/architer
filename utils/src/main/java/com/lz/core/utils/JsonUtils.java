package com.lz.core.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Json工具类
 * <p>
 * 创建这个类的目的是:
 * 1.调用这无需关心Json序列化的api是什么，如果更换序列化工具，无需更改大量的序列化
 * 2.ObjectMapper是线程安全的，可以减少对象频繁的创建
 *
 * @author luyi
 * @TODO 对于其他的后续再不断增加，考虑将这个工具类抽出来，不放到缓存中（因为其他的地方也要用）
 */
public class JsonUtils {
    /**
     * 防止被new
     */
    private JsonUtils() {

    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Object转成Json字符串
     *
     * @return json字符串
     */
    public static String toJsonString(Object object) {
        try {
            if (object instanceof String) {
                return object.toString();
            }
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化失败", e);
        }
    }

    /**
     * 将json转为对象
     *
     * @param value json字符串
     * @param clazz 反序列化的class
     * @param <T>   实体类型
     * @return 反序列化的实体
     */
    public static <T> T readValue(String value, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("反序列化失败", e);
        }
    }

    /**
     * 将Json字符串反序列化成list
     *
     * @param value 需要反序列化的字符串
     * @param clazz list中的实体
     * @param <T>   实体类型
     * @return 反序列化后的list
     */
    public static <T> Object readValue(String value, Class<?> typeClass, Class<T> clazz) {
        JavaType listType = OBJECT_MAPPER.getTypeFactory().constructParametricType(typeClass, clazz);
        try {
            return OBJECT_MAPPER.readValue(value, listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("反序列化失败", e);
        }
    }

    /**
     * 将Json字符串反序列化成list
     *
     * @param value 需要反序列化的字符串
     * @param clazz list中的实体
     * @param <T>   实体类型
     * @return 反序列化后的list
     */
    public static <T> List<T> readListValue(String value, Class<T> clazz) {
        JavaType listType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return OBJECT_MAPPER.readValue(value, listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("反序列化失败", e);
        }
    }
}
