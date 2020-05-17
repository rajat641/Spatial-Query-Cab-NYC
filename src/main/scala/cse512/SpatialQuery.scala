package cse512

import org.apache.spark.sql.SparkSession

object SpatialQuery extends App{

  def contains(queryRectangle:String, pointString:String):Boolean = {
    try {
      var rectangle:Array[String] = new Array[String](4)
      rectangle = queryRectangle.split(',')
      var r_x1 = rectangle(0).trim.toDouble
      var r_y1 = rectangle(1).trim.toDouble
      var r_x2 = rectangle(2).trim.toDouble
      var r_y2 = rectangle(3).trim.toDouble

      var point:Array[String] = new Array[String](2)
      point = pointString.split(',')
      var p_x = point(0).trim.toDouble
      var p_y = point(1).trim.toDouble

      var range_x:Array[Double] = new Array[Double](2)
      range_x(0) = math.min(r_x1, r_x2)
      range_x(1) = math.max(r_x1, r_x2)
      var range_y:Array[Double] = new Array[Double](2)
      range_y(0) = math.min(r_y1, r_y2)
      range_y(1) = math.max(r_y1, r_y2)

      if(p_x > range_x(1) || p_x < range_x(0) || p_y > range_y(1) || p_y < range_y(0))
        return false
      else
        return true

    }
    catch {
      case _: Throwable => return false
    }
  }

  def within(pointString1:String, pointString2:String, distance:Double):Boolean={
    try{
      var p1 = new Array[String](2)
      p1 = pointString1.split(',')
      var p1_x = p1(0).trim.toDouble
      var p1_y = p1(1).trim.toDouble

      var p2 = new Array[String](2)
      p2 = pointString2.split(',')
      var p2_x = p2(0).trim.toDouble
      var p2_y = p2(1).trim.toDouble

      var dist = math.sqrt(math.pow((p1_x - p2_x), 2) + math.pow((p1_y - p2_y), 2))

      if(dist <= distance)
        return true
      else
        return false

    }
    catch{
      case _: Throwable => return false
    }
  }

  def runRangeQuery(spark: SparkSession, arg1: String, arg2: String): Long = {

    val pointDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg1);
    pointDf.createOrReplaceTempView("point")

    // YOU NEED TO FILL IN THIS USER DEFINED FUNCTION
    spark.udf.register("ST_Contains",(queryRectangle:String, pointString:String)=>((contains(queryRectangle, pointString))))

    val resultDf = spark.sql("select * from point where ST_Contains('"+arg2+"',point._c0)")
    //resultDf.show()

    print(resultDf.count()+" ")

    return resultDf.count()
  }

  def runRangeJoinQuery(spark: SparkSession, arg1: String, arg2: String): Long = {

    val pointDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg1);
    pointDf.createOrReplaceTempView("point")

    val rectangleDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg2);
    rectangleDf.createOrReplaceTempView("rectangle")

    // YOU NEED TO FILL IN THIS USER DEFINED FUNCTION
    spark.udf.register("ST_Contains",(queryRectangle:String, pointString:String)=>((contains(queryRectangle, pointString))))

    val resultDf = spark.sql("select * from rectangle,point where ST_Contains(rectangle._c0,point._c0)")
    // resultDf.show()

    print(resultDf.count()+" ")

    return resultDf.count()
  }

  def runDistanceQuery(spark: SparkSession, arg1: String, arg2: String, arg3: String): Long = {

    val pointDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg1);
    pointDf.createOrReplaceTempView("point")

    // YOU NEED TO FILL IN THIS USER DEFINED FUNCTION
    spark.udf.register("ST_Within",(pointString1:String, pointString2:String, distance:Double)=>((within(pointString1, pointString2, distance))))

    val resultDf = spark.sql("select * from point where ST_Within(point._c0,'"+arg2+"',"+arg3+")")
    // resultDf.show()

    print(resultDf.count()+" ")

    return resultDf.count()
  }

  def runDistanceJoinQuery(spark: SparkSession, arg1: String, arg2: String, arg3: String): Long = {

    val pointDf = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg1);
    pointDf.createOrReplaceTempView("point1")

    val pointDf2 = spark.read.format("com.databricks.spark.csv").option("delimiter","\t").option("header","false").load(arg2);
    pointDf2.createOrReplaceTempView("point2")

    // YOU NEED TO FILL IN THIS USER DEFINED FUNCTION
    spark.udf.register("ST_Within",(pointString1:String, pointString2:String, distance:Double)=>((within(pointString1, pointString2, distance))))
    val resultDf = spark.sql("select * from point1 p1, point2 p2 where ST_Within(p1._c0, p2._c0, "+arg3+")")
    // resultDf.show()

    print(resultDf.count()+" ")

    return resultDf.count()
  }
}