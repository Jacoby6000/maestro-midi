package com.github.jacoby6000.maestro.midi

object util {
  implicit class EitherExtensions[A, B](val either: Either[A, B]) extends AnyVal {
    def map[C](f: B => C): Either[A, C] =
      either match {
        case Left(l) => Left(l)
        case Right(r) => Right(f(r))
      }
  }
}
