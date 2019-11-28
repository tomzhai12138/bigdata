package com.data.bigdata.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Test1 {
  def main(args: Array[String]): Unit = {
    
    val spark=SparkSession.builder().appName("Spark-Shell").master("local[2]").getOrCreate()
    
    val file=spark.sparkContext.textFile("E:\\wc.txt")
    
    val wordCounts = file.flatMap(line => line.split(",")).map((word => (word, 1))).reduceByKey(_ + _)
    
    wordCounts.collect().mkString
    
    // 建议在进行 spark SQL 编程前导入下面的隐式转换，因为 DataFrames 和 dataSets 中很多操作都依赖了隐式转换
    import spark.implicits._
  }
}