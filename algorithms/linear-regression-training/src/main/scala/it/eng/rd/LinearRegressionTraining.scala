package it.eng.rd

import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.regression.LinearRegressionModel
import org.apache.spark.sql.SparkSession

object LinearRegressionTraining {
  
  def main(args : Array[String]) {
/*
==================================================================================================================================================================================		
Phase 1
- save the model
- prediction based on test data
- save model and prediction on hdfs

Nifi variables
--------------

demo_file 							hdfs://master:9000/user/hdfs/jobs/linear-regression-training-0.0.1-SNAPSHOT.jar 
demo_className 					it.eng.rd.LinearRegressionTraining
demo_master 						yarn
demo_model 							hdfs://master:9000/user/hdfs/sparkoutput/model/nifi/demo/
demo_prediction 				hdfs://master:9000/user/hdfs/sparkoutput/predition/nifi/demo/
demo_training_dataset 	hdfs://master:9000/user/hdfs/sparkinput/training.csv 
demo_training_results 	hdfs://master:9000/user/hdfs/sparkoutput/training/result/nifi/demo
==================================================================================================================================================================================		
*/
    
    val dataset_file = args(0)
    val model_file = args(1)
    val preditions_file = args(2)
    val master = args(3)
      
    println("------------------------------------------------------")
    println("dataset file: " + dataset_file)
    println("model file: " + model_file)
    println("preditions file: " + preditions_file)
    println("master: " + master)
    println("------------------------------------------------------")  
   
     val spark = SparkSession
    .builder
    .appName("Clustering")
    .master(master)//"local[*]")
    .getOrCreate()
        
    var sc = spark.sparkContext
   
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
 
    import sqlContext.implicits._

	val customSchema = StructType(Array( 
	  StructField("_c0", DoubleType, true),
	  StructField("_c1", DoubleType, true),
	  StructField("_c2", DoubleType, true),
	  StructField("_c3", DoubleType, true),
	  StructField("_c4", DoubleType, true),
	  StructField("_c5", DoubleType, true),
	  StructField("_c6", DoubleType, true),
	  StructField("_c7", DoubleType, true),
	  StructField("_c8", DoubleType, true),
	  StructField("_c9", DoubleType, true),
	  StructField("_c10", DoubleType, true),
	  StructField("_c11", DoubleType, true),
	  StructField("_c12", DoubleType, true),
	  StructField("_c13", DoubleType, true))
	)
	
	val data = spark.read.format("csv")
	  .option("header", "true")
	  .schema(customSchema)
	  .load(dataset_file)
	
	val assembler = new VectorAssembler().setInputCols(Array("_c0","_c1","_c2","_c3","_c4","_c5","_c6","_c7","_c8","_c9","_c10","_c11","_c12")).setOutputCol("features")
	 
	val features = assembler.transform(data)
	features.select("features").show(false)
	
	val dfSplit = features.randomSplit(Array(0.7, 0.3))
	val trainingDF = dfSplit(0)
	val testDF = dfSplit(1)
	
	val lr = new LinearRegression()
	  .setLabelCol("_c13")
	  .setFeaturesCol("features")
	
	val lrModel = lr.fit(trainingDF)
//  lrModel.save(model_file) - it doesn't work
  
// Optional
// val evaluation_summary = lrModel.evaluate(testDF)
	val predictions = lrModel.transform(testDF)
	predictions.select("_c13", "features", "prediction").show(false)

	predictions.select("prediction").write.format("com.databricks.spark.csv").option("header","true").save(preditions_file)
	
	sc.parallelize(Seq(lrModel), 1).saveAsObjectFile(model_file+"linReg.model")
	
  }
}

