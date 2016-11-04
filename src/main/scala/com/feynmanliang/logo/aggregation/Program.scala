package com.feynmanliang.logo.aggregation

import cats.free.Free
/**
  * Created by gojkic on 01/11/16.
  */
object Program {
  import dsl._

  val program2: (Dataframe => Free[Instruction, Dataframe]) = {
    s: Dataframe =>
      for {
        p1 <- deduplication(s, Session)
        p1 <- filter(s, Session)
        p1 <- aggregation(s, Session)
      } yield (p1)
  }

  val program3: ((Dataframe, Dataframe) => Free[Instruction, Dataframe]) = {
    (s: Dataframe, b: Dataframe) =>
      for {
        p1 <- program2(s)
        p2 <- program2(b)
      } yield (p1)
  }

  def main(args: Array[String]):Unit = {
    val startPosition = new Dataframe("test")
    val interpreter = AggInterpreter

    program3(new Dataframe("1"), new Dataframe("2")).foldMap(interpreter) // foldMap does trampolining
  }
}
