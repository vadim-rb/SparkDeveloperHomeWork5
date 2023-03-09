import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession, functions}
import org.apache.spark.sql.functions.{col, count, hour, mean, round}

object Main {
  def readParquet(path: String)(implicit spark: SparkSession): DataFrame = spark.read.load(path)

  def readCSV(path: String)(implicit spark: SparkSession): DataFrame =
    spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(path)

  def popularArea(taxi: DataFrame, taxiZone: DataFrame): DataFrame = {
    val _sorted = taxi.groupBy("PULocationID").count().orderBy(col("count").desc)
    _sorted.as("sort")
      .join(taxiZone.as("zones"), col("sort.PULocationID") === col("zones.LocationID"))
      .select(col("zones.Zone"), col("sort.count"))
      .orderBy(col("sort.count").desc)
  }

  def popularTime(taxi: DataFrame): DataFrame = {
    taxi
      .withColumn("hour", hour(col("tpep_pickup_datetime")))
      .groupBy(col("hour"))
      .count()
      .orderBy(col("hour"))
  }

  def processTaxiData(taxiDF: DataFrame, taxiZonesDF: DataFrame): DataFrame = {
    taxiDF
      .join(taxiZonesDF, col("PULocationID") === col("LocationID"))
      .groupBy("Borough")
      .agg(
        count("*").as("total_trips"),
        functions.min("trip_distance").as("min_distance"),
        round(mean("trip_distance"), 2).as("mean_distance"),
        functions.max("trip_distance").as("max_distance")
      )
      .orderBy(col("total_trips").desc)
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("HomeWork5")
      .config("spark.master", "local")
      .config("spark.eventLog.enabled", "true")
      .config("spark.eventLog.dir", "file:///home/vadim/MyExp/spark-logs/event")
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")

    val taxi = spark.read.parquet("./src/main/resources/data/yellow_taxi_jan_25_2018/")
    taxi.cache()
    val taxiZone = spark.read.option("header", value = true).option("inferSchema", value = true).csv("./src/main/resources/data/taxi_zones.csv")
    taxiZone.cache()



    popularArea(taxi, taxiZone).show
    popularTime(taxi).write.parquet("./output/popularTimeParquetSample")
    processTaxiData(taxi, taxiZone).show
    //processTaxiData(taxi, taxiZone).coalesce(1).write.parquet("./output/processTaxiDataParquetSample")



  }
}