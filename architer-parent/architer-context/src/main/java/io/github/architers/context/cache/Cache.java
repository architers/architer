package io.github.architers.context.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 缓存接口类
 * @version 1.0.0
 */
public interface Cache {
    /**
     * 得到缓存名称
     *
     * @return 缓存名称
     */
    String getCacheName();

    /**
     * 描述:放入值:永不过期
     *
     * @param key   缓存的key
     * @param value 缓存的值
     */
    void set(Object key, Object value);


    default Future<Boolean> multiSet(Object values) {
        throw new IllegalArgumentException();
    }

    /**
     * 批量设置
     *
     * @param expire   过期时间
     * @param timeUnit 单位
     * @param values   批量缓存的数据
     */
    Future<Boolean> multiSet(Object values, long expire, TimeUnit timeUnit);

    /**
     * 描述:向缓存中存放值，并设置过期时间
     *
     * @param expire   过期时间
     * @param timeUnit 单位
     * @param key      缓存的key
     * @param value    缓存的值
     */
    void set(Object key, Object value, long expire, TimeUnit timeUnit);


    /**
     * 描述:如果不存在，就向缓存中设置值(设置的值不过期)
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return true为设置成功
     */
    boolean setIfAbsent(Object key, Object value);

    /**
     * 描述:如果不存在，就向缓存中设置值
     *
     * @param expire   过期的时间
     * @param key      缓存的key
     * @param value    缓存的值
     * @param timeUnit 单位
     * @return 是否设置成功：true成功
     */
    boolean setIfAbsent(Object key, Object value, long expire, TimeUnit timeUnit);

    /**
     * 描述:如果不存在，就向缓存中设置值
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return 是否设置成功：true成功
     */
    boolean setIfPresent(Object key, Object value);

    /**
     * 描述:如果存在，就向缓存中设置值
     *
     * @param expire   过期的时间
     * @param key      缓存的key
     * @param value    缓存的值
     * @param timeUnit 单位
     * @return 是否成功
     */
    boolean setIfPresent(Object key, Object value, long expire, TimeUnit timeUnit);

    //*************************************get**************************************************/

    /**
     * 得到以前的值，并设置新的值
     *
     * @param key   缓存的key
     * @param value 缓存的值
     * @return 原来的缓存值
     */
    Object getAndSet(Object key, Object value);

    /**
     * 描述:得到缓存值
     *
     * @param key 缓存的key
     * @return 缓存的值
     */
    Object get(Object key);

    /**
     * 批量获取
     *
     * @param keys 缓存key集合
     * @return kes对应的缓存值
     */
    Map<?, Object> multiGet(Set<Object> keys) throws ExecutionException, InterruptedException;

    /**
     * 只适合指定类型的值
     *
     * @param key   缓存的key
     * @param clazz 值的类型
     * @return 缓存后T类型的数据
     */
    <T> T get(Object key, Class<T> clazz);

    /**
     * 描述:删除缓存
     *
     * @param key 缓存的key
     * @return 是否删除成功
     */
    boolean delete(Object key);

    /**
     * 批量删除
     *
     * @param keys 需要删除key
     * @return 删除的数量
     */
    long multiDelete(Collection<Object> keys);

    /**
     * 获取所有的缓存信息
     *
     * @return 对应的所有的返回值
     */
    default Object getAll() {
        throw new IllegalArgumentException();
    }

    /**
     * 删除所有的值
     */
    default void clearAll() {
        throw new IllegalArgumentException();
    }
}
