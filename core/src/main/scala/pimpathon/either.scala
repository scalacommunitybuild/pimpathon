package pimpathon

import scala.collection.generic.{Growable, Shrinkable}

import pimpathon.function._


object either {
  implicit def eitherPimps[L, R](either: Either[L, R]): EitherPimps[L, R] = new EitherPimps[L, R](either)

  class EitherPimps[L, R](either: Either[L, R]) {
    def leftMap[M](f: L ⇒ M): Either[M, R]  = bimap[M, R](f, identity[R])
    def rightMap[S](f: R ⇒ S): Either[L, S] = bimap[L, S](identity[L], f)

    def valueOr(lr: L ⇒ R): R = rightOr(lr)
    def valueOr(pf: PartialFunction[L, R]): Either[L, R] = rescue(pf)

    def rescue(lr: L ⇒ R): R = rightOr(lr)
    def rescue(pf: PartialFunction[L, R]): Either[L, R] = leftFlatMap(pf.either)

    def leftOr(rl: R ⇒ L): L  = either.fold(identity, rl)
    def rightOr(lr: L ⇒ R): R = either.fold(lr, identity)

    def bimap[M, S](lf: L ⇒ M, rf: R ⇒ S): Either[M, S] = either.fold(l ⇒ Left[M, S](lf(l)), r ⇒ Right[M, S](rf(r)))

    def leftFlatMap(f: L ⇒ Either[L, R]): Either[L, R]  = either.fold(f, Right(_))
    def rightFlatMap(f: R ⇒ Either[L, R]): Either[L, R] = either.fold(Left(_), f)

    def addTo(ls: Growable[L], rs: Growable[R]): Either[L, R] = tap(ls += _, rs += _)
    def removeFrom(ls: Shrinkable[L], rs: Shrinkable[R]): Either[L, R] = tap(ls -= _, rs -= _)

    def tapLeft[Discarded](l: L ⇒ Discarded):  Either[L, R] = tap(l, _ ⇒ {})
    def tapRight[Discarded](r: R ⇒ Discarded): Either[L, R] = tap(_ ⇒ {}, r)
    def tap[Discarded](l: L ⇒ Discarded, r: R ⇒ Discarded): Either[L, R] = { either.fold(l, r); either }

    def getMessage(implicit ev: L <:< Throwable): Option[String] = either.fold(t ⇒ Some(t.getMessage), _ ⇒ None)
  }

  implicit def eitherToRightProjection[L, R](either: Either[L, R]): Either.RightProjection[L, R] = either.right
  implicit def rightProjectionToEither[L, R](rp: Either.RightProjection[L, R]): Either[L, R] = rp.e
}