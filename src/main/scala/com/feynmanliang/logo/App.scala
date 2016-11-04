package com.feynmanliang.logo

import cats.{Id,~>}
import cats.implicits._
import cats.free.Free

import Logo._
import Logo.dsl._

object App {
  def program(start: Position)(implicit M: Moves[LogoApp], P: PencilActions[LogoApp]): Free[LogoApp, Unit] = {
    import M._, P._
    for {
      p1 <- forward(start, 10)
      p2 <- right(p1, Degree(90))
      _ <- pencilUp(p2)
      p3 <- forward(p2, 10)
      _ <- pencilDown(p3)
      p4 <- backward(p3, 20)
      _ <- showPosition(p4)
    } yield ()
  }

  val program2: (Position => Free[Instruction, Unit]) = {
    import ss._
    s: Position =>
      for {
        p1 <- forward(s, 10)
        p2 <- right(p1, Degree(45))
        p3 <- forward(p2, 10)
        p4 <- backward(p3, 20)//Here the computation stops, because result will be None
        _ <- showPosition(p4)
      } yield ()
  }


  def main(args: Array[String]):Unit = {
    val startPosition = Position(0.0, 0.0, Degree(0))
    val interpreter = InterpreterId

    program2(startPosition).foldMap(interpreter) // foldMap does trampolining
  }
}
