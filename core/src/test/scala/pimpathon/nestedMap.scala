package pimpathon

import org.junit.Test

import org.junit.Assert._
import pimpathon.nestedMap._


class NestedMapTest {
  @Test def nestedMap_mapValuesEagerly(): Unit = assertEquals(
    Map(1 → Map(2 → 6, 3 → 8), 2 → Map(3 → 8, 4 → 10)),
    Map(1 → Map(2 → 3, 3 → 4), 2 → Map(3 → 4, 4 → 5)).nestedMap.mapValuesEagerly(_ * 2)
  )

  @Test def nestedMap_mapKeysEagerly(): Unit = assertEquals(
    Map(1 → Map(4 → 3, 6 → 4), 2 → Map(6 → 4, 8 → 5)),
    Map(1 → Map(2 → 3, 3 → 4), 2 → Map(3 → 4, 4 → 5)).nestedMap.mapKeysEagerly(_ * 2)
  )
}