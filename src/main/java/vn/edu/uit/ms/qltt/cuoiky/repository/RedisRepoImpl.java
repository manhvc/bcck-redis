package vn.edu.uit.ms.qltt.cuoiky.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import vn.edu.uit.ms.qltt.cuoiky.exception.DataException;

import java.util.List;
import java.util.Set;

@Repository
public class RedisRepoImpl implements IRedisRepo {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRepoImpl.class);

    private static JedisPool pool = null;

    public RedisRepoImpl() {
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(32);
            poolConfig.setMaxIdle(16);
            poolConfig.setMinIdle(4);
            poolConfig.setTestOnBorrow(true);
            pool = new JedisPool(poolConfig, "localhost", 6379);
            LOGGER.info("Connect redis successful");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error on connect to redis cause by {}", e.getMessage());
            throw new DataException.ExecuteException("Connect to redis failed");
        }
    }


    @Override
    public void save(String key, String value, Long expireTime) {
        try (Jedis client = pool.getResource()) {
            client.setex(key, Math.toIntExact(expireTime), value);
            LOGGER.info("Set expires success");
            LOGGER.debug("Set key {} value {} expires after {}", key, value, expireTime);
        } catch (Exception e) {
            LOGGER.error("Redis save error cause by {}", e.getMessage());
            throw new DataException.ExecuteException("Save key to redis failed");
        }
    }

    @Override
    public void save(String key, String value) {
        try (Jedis client = pool.getResource()) {
            client.set(key, value);
            LOGGER.info("Set expires success");
            LOGGER.debug("Set key {} value {} ", key, value);
        } catch (Exception e) {
            LOGGER.error("Redis save error cause by {}", e.getMessage());
            throw new DataException.ExecuteException("Save key to redis failed");
        }
    }

    @Override
    public void delKey(String key) {
        try (Jedis client = pool.getResource()) {
            try {
                LOGGER.info("Ready to delete key in redis");
                LOGGER.debug("Key {}", key);
                client.del(key);
                LOGGER.info("Delete key {} success", key);
            } catch (Exception e) {
                LOGGER.info("Delete key {} failed", key);
            }
        } catch (Exception e) {
            throw new DataException.ExecuteException("Delete pattern to redis failed");
        }
    }

    @Override
    public void delKeys(String pattern) {
        try (Jedis client = pool.getResource()) {
            LOGGER.info("Begin to get value with pattern in redis");
            LOGGER.debug("Pattern {}", pattern);
            Set<String> set = client.keys(pattern);
            LOGGER.info("Found keys");
            Transaction transaction = client.multi();
            for (String item : set) {
                transaction.del(item);
            }
            transaction.exec();
            LOGGER.info("Delete pattern {} success", pattern);
        } catch (Exception e) {
            LOGGER.error("Error on delete pattern cause by {}", e.getMessage());
            throw new DataException.ExecuteException("Delete pattern to redis failed");
        }
    }

    @Override
    public String getValue(String key) throws DataException.NotFoundException {
        try (Jedis client = pool.getResource()) {
            LOGGER.info("Begin get value with key in redis");
            LOGGER.debug("Key {}", key);
            String value = client.get(key);
            if (value == null) {
                throw new DataException.NotFoundException("Not found data in redis");
            } else {
                LOGGER.info("Get value with key success");
                return value;
            }
        } catch (DataException.NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error on get value with key cause by {}", e.getMessage());
            throw new DataException.ExecuteException("Get values failed. result is fail");
        }
    }

    @Override
    public List<String> getValues(String pattern) throws DataException.NotFoundException {
        return null;
    }
}
