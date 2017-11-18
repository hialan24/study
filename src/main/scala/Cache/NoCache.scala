package Cache

/**
  * \* Created with IntelliJ IDEA.
  * \* User: chen
  * \* Date: 2017/11/18
  * \* Time: 下午4:06
  * \* To change this template use File | Settings | File Templates.
  * \* Description: 
  * \*/
class NoCache[K,V] extends FlashCache[K,V]{

  override def getKey(key: K, value: (K) => V): V = {
    return value(key)
  }

  override def setKey(key: K, value: V): Unit = {

  }

  override def clearKey(key: K): Unit = {

  }
}
