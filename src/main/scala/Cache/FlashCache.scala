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

  def getKey(key:K,value:K=>V = null):V

  def setKey(key:K,value:V)

  def clearKey(key:K)
}
