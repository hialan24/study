package Cache

import org.junit.Assert

/**
  * \* Created with IntelliJ IDEA.
  * \* User: alan
  * \* Date: 2017/11/18
  * \* Time: 下午4:06
  * \* To change this template use File | Settings | File Templates.
  * \* Description:
  *       使用 NoCache 提供 key 的默认 value
  *       防止 guava 因为 put 空 (null) value 报错
  * \*/
class NoCache[K,V](valGetter:K=>V) extends FlashCache[K,V]{

  override def getKey(key: K, getter: (K) => V): V = {
    Assert.assertNotNull("valueGetter cannot be null",valGetter)

    if(getter != null)
      getter(key)
    else
      valGetter(key)     // 默认的 value
  }

  override def setKey(key: K, value: V): Unit = {
    throw new UnsupportedOperationException
  }

  override def clearKey(key: K): Unit = {
    throw new UnsupportedOperationException
  }
}
