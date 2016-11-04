package com.feynmanliang.logo.aggregation



object Meat {

  val agg: Map[Metric, (Dataframe) => Dataframe] = Map(
    Session -> aggSession _)

  val filtering: Map[Metric, (Dataframe) => Dataframe] = Map(
    Session -> aggSession _)

  val deduplication: Map[Metric, (Dataframe) => Dataframe] = Map(
    Session -> aggSession _)

  def aggSession(in: Dataframe): Dataframe = {
    println(s"agg session ${in.name}")
    in
  }

}
