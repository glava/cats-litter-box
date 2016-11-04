package com.feynmanliang.logo.aggregation

import cats.free.Free

sealed trait Instruction[A]

trait Metric extends Serializable {
  /**
    * Unique identify the metric
    *
    * @return String  unique identifier for the metric
    */
  def name = toString

  /**
    * The target view to save the final aggregation result into
    *
    * @return the name of the view to save the aggregation result into (MySQL table name and the HDFS directory name)
    */
  def target = toString

  /**
    * Deduplication strategy used to drop duplicated records.
    * https://github.com/SponsorPay/deduper/blob/develop/src/main/scala/com/fyber/deduper/Deduplication.scala
    *
    * @return the name of Kafka topic that could be used to deduplicate records.
    */
  def deduplicationStrategy = toString
}

object Session extends Metric {
  override def toString = "session"
}

class Dataframe(val name: String)

case class Filter(dataFrame: Dataframe, metric: Metric) extends Instruction[Dataframe]
case class Deduplication(dataFrame: Dataframe, metric: Metric) extends Instruction[Dataframe]
case class Aggregation(dataFrame: Dataframe, metric: Metric) extends Instruction[Dataframe]
case class ShowPosition(dataFrame: Dataframe, metric: Metric) extends Instruction[Unit]

object dsl {
  def filter(dataframe: Dataframe, m: Metric) = Free.liftF(Filter(dataframe, m))
  def deduplication(dataframe: Dataframe, m: Metric) = Free.liftF(Deduplication(dataframe, m))
  def aggregation(dataframe: Dataframe, m: Metric) = Free.liftF(Aggregation(dataframe, m))
  def showPosition(dataframe: Dataframe, m: Metric): Free[Instruction, Unit] = Free.liftF(ShowPosition(dataframe, m))
}