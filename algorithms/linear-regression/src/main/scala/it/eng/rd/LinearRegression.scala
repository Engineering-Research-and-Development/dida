package it.eng.rd

import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.regression.LinearRegressionModel
import org.apache.spark.sql.SparkSession

object LinearRegression {
  
  def main(args : Array[String]) {

/*
==================================================================================================================================================================================		
Phase 2
- load the model which is saved in phase 1
- prediction based on a generic dataset
- save prediction on hdfs

Nifi variables
--------------

demo_file 							hdfs://master:9000/user/hdfs/jobs/linear-regression-0.0.1-SNAPSHOT.jar 
demo_className 					it.eng.rd.LinearRegression
demo_master 						yarn
demo_model 							hdfs://master:9000/user/hdfs/sparkoutput/model/nifi/demo/
demo_prediction 				hdfs://master:9000/user/hdfs/sparkoutput/predition/nifi/demo/
demo_generic_dataset 			hdfs://master:9000/user/hdfs/sparkinput/generic.csv

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
    .master(master)
    .getOrCreate()
        
    var sc = spark.sparkContext
   
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
 
    import sqlContext.implicits._
    
//	val linerModel = LinearRegressionModel.load(model_file) - it doesn't work
    
  val linerModel = sc.objectFile[LinearRegressionModel](model_file+"linReg.model").first()
	println("Loaded model ")
	
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
	  StructField("_c12", DoubleType, true))
	)
	
	val generic_data = spark.read.format("csv")
	  .option("header", "true")
	  .schema(customSchema)
	  .load(dataset_file)
	  
	val generic_assembler = new VectorAssembler().setInputCols(Array("_c0","_c1","_c2","_c3","_c4","_c5","_c6","_c7","_c8","_c9","_c10","_c11","_c12")).setOutputCol("features")
	 
	val generic_features = generic_assembler.transform(generic_data)
	
	val dfSplit = generic_features.randomSplit(Array(1.0, 0.0))
	val generic_DF = dfSplit(0)
	
	val generic_lr = new LinearRegression()
	  .setLabelCol("_y")
	  .setFeaturesCol("features")
	
	val predictions = linerModel.transform(generic_DF)
	predictions.select("features", "prediction").show(false)
	
  predictions.select("prediction").write.format("com.databricks.spark.csv").option("header", "true").save(preditions_file)
  }
}
