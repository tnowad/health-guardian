package com.example.health_guardian_server.services;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisService<K, F, V> {

  V get(K key);

  void set(K key, V value);

  V hashGet(K key, F field);

  void hashSet(K key, F field, V value);

  Boolean exists(K key);

  Boolean delete(K key);

  Long hashDelete(K key, F... fields);

  Long increment(K key, F field, long delta);

  Map<F, V> hashEntries(K key);

  void setWithExpiration(K key, V value, long timeout, TimeUnit unit);

  Boolean setExpire(K key, long timeout, TimeUnit unit);

  void setTimeToLive(K key, long timeoutInDays);
}
