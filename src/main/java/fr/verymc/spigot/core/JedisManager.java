package main.java.fr.verymc.spigot.core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisManager {

    public static JedisManager instance;
    JedisPool pool;
    private String REDIS_PASSWORD = "1234";
    private String REDIS_HOST = "127.0.0.1";

    public JedisManager() {
        instance = this;
        String tmpHost = System.getenv("REDIS_HOST");
        String tmpPass = System.getenv("REDIS_PASSWORD");
        if (tmpHost != null && tmpPass != null) {
            REDIS_HOST = tmpHost;
            REDIS_PASSWORD = tmpPass;
            pool = new JedisPool(REDIS_HOST, 6379);
            return;
        }
        pool = new JedisPool(REDIS_HOST, 6379);
    }

    public void sendToRedis(String arg0, String data) {
        try (Jedis jedis = pool.getResource()) {
            //jedis.auth(""); if password is set
            jedis.auth(REDIS_PASSWORD);
            jedis.set(arg0, data);
        }
    }

    public String getFromRedis(String arg0) {
        try (Jedis jedis = pool.getResource()) {
            //jedis.auth(""); if password is set
            jedis.auth(REDIS_PASSWORD);
            return jedis.get(arg0);
        }
    }

}
