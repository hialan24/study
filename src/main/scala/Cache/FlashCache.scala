package Cache

/**
  * \* Created with IntelliJ IDEA.
  * \* User: alan
  * \* Date: 2017/11/18
  * \* Time: 下午2:37
  * \* To change this template use File | Settings | File Templates.
  * \* Description: 
  * \*/
trait FlashCache[K,V] {

  /**
    *
    * 获取 key 对应的 value
    *
    * @param key
    * @param getter  // 生成 value 的方式
    * @return
    */
  def getKey(key:K,getter:K=>V = null):V

  def setKey(key:K,value:V)

  def clearKey(key:K)
}
