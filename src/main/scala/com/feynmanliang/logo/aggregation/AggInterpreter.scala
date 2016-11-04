package com.feynmanliang.logo.aggregation

import cats._

object AggInterpreter extends (Instruction ~> Id) {

  override def apply[A](fa: Instruction[A]): Id[A] = fa match {
    case Filter(d, m) => Meat.filtering(m)(d)
    case Deduplication(d, m) => Meat.deduplication(m)(d)
    case Aggregation(d, m) => Meat.agg(m)(d)
    case ShowPosition(p, m) => println(s"showing position $p")
  }
}
