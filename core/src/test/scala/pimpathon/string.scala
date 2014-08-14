package pimpathon

import org.junit.Test

import org.junit.Assert._
import pimpathon.string._


class StringTest {
  @Test def sharedPrefix {
    assertEquals(("", "", ""), "".sharedPrefix(""))
    assertEquals(("1", "", ""), "1".sharedPrefix("1"))

    assertEquals(("12", "34", "43"), "1234".sharedPrefix("1243"))
  }
}