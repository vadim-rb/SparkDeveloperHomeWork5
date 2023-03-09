import Main.{popularArea, popularTime, processTaxiData, readCSV, readParquet}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DoubleType, LongType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.contain
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class SimpleUnitTest extends AnyFlatSpec {


  implicit val spark: SparkSession = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Test №1 for Big Data Application")
    .getOrCreate()

  it should "Test processTaxiData" in {
    val taxiZone = readCSV("src/main/resources/data/taxi_zones.csv")
    val taxi = readParquet("src/main/resources/data/yellow_taxi_jan_25_2018")

    val result = processTaxiData(taxi, taxiZone)

    val shemaForTest = StructType(Array(
      StructField("Borough",StringType,nullable = true),
      StructField("total_trips",LongType,nullable = false),
      StructField("min_distance",DoubleType,nullable = true),
      StructField("mean_distance",DoubleType,nullable = true),
      StructField("max_distance",DoubleType,nullable = true)))

    assert(result.schema ==  shemaForTest)
    assert(result.count() == 7)

  }

  it should "popularArea test" in {
    val taxiZone = readCSV("src/main/resources/data/taxi_zones.csv")
    val taxi = readParquet("src/main/resources/data/yellow_taxi_jan_25_2018")

    val actualDistribution = popularArea(taxi, taxiZone)
      .collectAsList()
      .get(0)

    assert(actualDistribution.get(0) != "East Chelsea")
    assert(actualDistribution.get(1) == 15945)
  }

  it should "popularTime test" in {
    val taxi = readParquet("src/main/resources/data/yellow_taxi_jan_25_2018")

    val result = popularTime(taxi)
    val hourColumn = result.select("hour").collect.map(_.getInt(0))
    hourColumn should contain theSameElementsAs Seq(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)

  }

}