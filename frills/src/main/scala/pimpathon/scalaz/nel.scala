package pimpathon.scalaz

import scala.collection.{mutable ⇒ M, GenTraversable, GenTraversableLike}
import scalaz.{NonEmptyList, Order}

import pimpathon.{CanBuildNonEmpty, genTraversableLike}

import pimpathon.list._
import pimpathon.tuple._


object nel extends genTraversableLike[NonEmptyList] {
  implicit def canBuildNonEmpty[A]: CanBuildNonEmpty[A, NonEmptyList[A]] = new CanBuildNonEmpty[A, NonEmptyList[A]] {
    def builder(head: A): M.Builder[A, NonEmptyList[A]] = List.newBuilder[A].mapResult(NonEmptyList.nel(head, _))
  }

  implicit def nelFrills[A](nel: NonEmptyList[A]): NelFrills[A] = new NelFrills[A](nel)

  class NelFrills[A](nel: NonEmptyList[A]) {
    def distinct: NonEmptyList[A] = lift(_.distinct)
    def distinctBy[B](f: A ⇒ B): NonEmptyList[A] = lift(_.distinctBy(f))
    def max(implicit o: Order[A]): A = nel.list.max(o.toScalaOrdering)
    def min(implicit o: Order[A]): A = nel.list.min(o.toScalaOrdering)

    private def lift(f: List[A] ⇒ List[A]): NonEmptyList[A] = f(nel.list).headTail.calc(NonEmptyList.nel)
  }

  protected def toGTL[A](nel: NonEmptyList[A]): GenTraversableLike[A, GenTraversable[A]] = nel.list
}