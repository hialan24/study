package CacheTest

import Cache.{GuavaLocalCache, NoCache}
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
  * \* Created with IntelliJ IDEA.
  * \* User: alan
  * \* Date: 2017/11/18
  * \* Time: 下午3:48
  * \* To change this template use File | Settings | File Templates.
  * \* Description: 
  * \*/
@RunWith(classOf[SpringJUnit4ClassRunner])
class TestFlashCache {



  @Test
  def testGuavaLocalCache(): Unit ={
    val noCache = new NoCache[String,String]
    val guavaCache = new GuavaLocalCache[String,String](noCache,10)
    val value = (k:String) => {
      k
    }

    guavaCache.getKey("hello",null)

  }
}
