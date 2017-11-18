package Cache

import java.util.concurrent.{Callable, TimeUnit}

import com.google.common.cache.{Cache, CacheBuilder, CacheLoader, ForwardingCache}

/**
  * \* Created with IntelliJ IDEA.
  * \* User: alan
  * \* Date: 2017/11/18
  * \* Time: 下午2:12
  * \* To change this template use File | Settings | File Templates.
  * \* Description: 
  * \*/
class GuavaLocalCache[K <: Object,V <: Object](flashCache: FlashCache[K,V],outTime:Long) extends FlashCache[K,V]{

  private val guavaCache:Cache[K,V] = buildGuavaLocalCache(flashCache,outTime)

  override def getKey(key: K,value:K=>V): V = {
    guavaCache.get(key, if(value == null) null else new Callable[V] {
      override def call(): V = {
        value(key)
      }
    })
  }

  override def setKey(key: K, value: V): Unit = {
    guavaCache.put(key,value)
    flashCache.setKey(key,value)
  }

  override def clearKey(key: K): Unit = {

  }


  /**
    * 构建本地 Guava 缓存
    *
    * @param flash
    * @param outTime
    * @tparam K
    * @tparam V
    * @return
    */
  private def buildGuavaLocalCache[K <:Object,V <: Object](flash:FlashCache[K,V],outTime:Long): Cache[K,V] ={

    val cache = {
      val guavaCache = CacheBuilder.newBuilder.
        maximumSize(10000).
        expireAfterWrite(outTime, TimeUnit.MINUTES)

      if( null == flash ){
        guavaCache.build[K,V]

      }else{

        val localLoader = new CacheLoader[K,V] {
          override def load(keys: K): V = {
            flash.getKey(keys)
          }
        }

        val localCache = guavaCache.build[K,V](localLoader)

        /**
          * 加载策略的实现
          *   本地没有对应 key 的 value 时候，从远程缓存中去取,返回出去，并且设置到本地缓存中
          *   本地有对应的 key 的 value 时候，直接从本地去取
          *
          * 使用一个代理类来实现该策略
          */

        new ForwardingCache.SimpleForwardingCache[K,V](localCache) {
          override def get(key: K, valueLoader: Callable[_ <: V]): V = {
            /**
              * valueLoader 是单独的线程去获取对应的 value，作用类似于 localLoader
              * 这里可以通过设置 valueLoader 来改变获取 value 的方式
              */
            if( valueLoader != null)
              super.get(key, valueLoader)
            else{
              var value = super.getIfPresent(key)

              if( null ==  value){
                value = flash.getKey(key)
                super.put(key,value)
              }

              value
            }
          }
        }
      }
    }

    cache
  }

}

object GuavaLocalCache{

  import java.util.concurrent.TimeUnit
  import com.google.common.cache.CacheBuilder

  // cache
  val cache = {
    CacheBuilder.newBuilder.
      maximumSize(10000).
      expireAfterWrite(10, TimeUnit.MINUTES)


  }

}
