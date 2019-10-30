# custom-cache

### custom-cache介绍
custome-cache是自定义本地缓存

### custom-cache使用
- 创建LocalCache对象
  - 姿势一
    ```
    LocalCache<Integer, Integer> localCache = new LocalCache<>(4, null);
    ```
    第一个参数缓存的大小，允许存放缓存的数量
    第二个参数定期删除对象，如果为`null`，使用默认的定期删除对象【执行周期、执行时间、执行次数都为默认值】
  - 姿势二  
    ```
    RegularExpireStrategy<Integer, Integer> expireStrategy = new RegularExpireStrategy<>();
    expireStrategy.setExecuteRate(1); //每隔1分钟执行一次
    LocalCache<Integer, Integer> localCache = new LocalCache<>(4, expireStrategy);
    ```
    传入自定义的定期删除对象
- 存入缓存
  ```
  for (int i = 0; i < 16; i++) {
    localCache.putValue(i, i);
  }
  ```
- 存入缓存并设置失效时间
  ```
   localCache.putValue(i, i,1000);
  ```
- 从缓存中读取值
  ```
  localCache.getValue(i)
  ```
- 设置已有缓存中数据的过期时间
  ```
  localCache.setExpireKey(i, 1000)
  ```
- 获取缓存的大小
  ```
  localCache.getLocalCacheSize()
  ```
- 删除缓存
  ```
  localCache.removeKey(i)
  ```

