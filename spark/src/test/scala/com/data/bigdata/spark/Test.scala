package com.data.bigdata.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Test {
  def main(args: Array[String]): Unit = {
    
     val spark = SparkSession.builder().appName("Spark-SQL").master("local[2]").getOrCreate()
     val df = spark.read.json("E:\\student.json")
     df.show()
     //df.show(100)

     // 建议在进行 spark SQL 编程前导入下面的隐式转换，因为 DataFrames 和 dataSets 中很多操作都依赖了隐式转换
     import spark.implicits._
  }
}