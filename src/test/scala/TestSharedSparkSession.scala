import Main.{popularArea, popularTime, processTaxiData, readCSV, readParquet}
import org.apache.spark.sql.QueryTest.checkAnswer
import org.apache.spark.sql.test.SharedSparkSession
import org.apache.spark.sql.{QueryTest, Row}
import org.apache.spark.sql.functions.col



 class TestSharedSparkSession extends QueryTest with SharedSparkSession {

  test("popularTime") {

    //val taxi = readParquet("./src/main/resources/data/yellow_taxi_jan_25_2018")
    //val actualDistribution = popularTime(taxi)

    val actualDistribution = readParquet("./output/popularTimeParquetSample")
    actualDistribution.show
    checkAnswer(
      actualDistribution.filter(col("hour")===0),
      Row(0, 14652) :: Nil
    )

  }

}