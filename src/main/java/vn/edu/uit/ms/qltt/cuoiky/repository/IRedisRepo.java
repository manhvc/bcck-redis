package vn.edu.uit.ms.qltt.cuoiky.repository;

import vn.edu.uit.ms.qltt.cuoiky.exception.DataException;

import java.io.OptionalDataException;
import java.util.List;

public interface IRedisRepo {
    static IRedisRepo create() {
        return new RedisRepoImpl();
    }

    /**
     * Save value to redis with key and expire time
     *
     * @param key
     * @param value
     * @param expireTime second
     */
    void save(String key, String value, Long expireTime);

    /**
     * Save value to redis with key without expire time
     *
     * @param key
     * @param value
     */
    void save(String key, String value);

    /**
     * Delete value with key input synchronous
     *
     * @param key key to delete
     */
    void delKey(String key);

    /**
     * Delete all key with pattern in redis synchronous
     *
     * @param pattern string pattern like: "user_*"
     */
    void delKeys(String pattern);


    /**
     * Get single value with key in redis response synchronous
     *
     * @param key key to get
     */
    String getValue(String key) throws DataException.NotFoundException;

    /**
     * Get all values with pattern of key in redis response synchronous
     *
     * @param pattern pattern to get key like "user_*"
     */
    List<String> getValues(String pattern) throws DataException.NotFoundException;
}
