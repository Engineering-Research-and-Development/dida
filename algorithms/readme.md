docker exec -it sparkmasterdemo bash

hdfs dfs -mkdir /user/
hdfs dfs -mkdir /user/hdfs/
hdfs dfs -mkdir /user/hdfs/sparkinput/
hdfs dfs -mkdir /user/hdfs/sparkoutput/
hdfs dfs -mkdir /user/hdfs/jobs/

hdfs dfs -copyFromLocal /data/jobs/linear-regression-0.0.1-SNAPSHOT.jar /user/hdfs/jobs

hdfs dfs -copyFromLocal /data/jobs/linear-regression-training-0.0.1-SNAPSHOT.jar /user/hdfs/jobs/

hdfs dfs -copyFromLocal /data/dataset/generic.csv /user/hdfs/sparkinput/

hdfs dfs -copyFromLocal /data/dataset/training.csv /user/hdfs/sparkinput/



./spark-submit --class it.eng.rd.LinearRegressionTraining /data/jobs/linear-regression-training-0.0.1-SNAPSHOT.jar /user/hdfs/sparkinput/training.csv /user/hdfs/sparkoutput/model/ /user/hdfs/sparkoutput/results1.csv local[*] > /data/output1.log

./spark-submit --class it.eng.rd.LinearRegression /data/jobs/linear-regression-0.0.1-SNAPSHOT.jar /user/hdfs/sparkinput/training.csv /user/hdfs/sparkoutput/model/ /user/hdfs/sparkoutput/results2.csv local[*] > /data/output2.log

{"file": "${demo_file}", "className": "${demo_className}", "args":["${demo_training_dataset}", "${demo_generic_dataset}", "${demo_model}", "${demo_training_results}", "${demo_prediction}", "${demo_master}"]}

{"file": "hdfs://master:9000/user/hdfs/jobs/linear-regression-training-0.0.1-SNAPSHOT.jar", "className": "it.eng.rd.LinearRegressionTraining", "args":["hdfs://master:9000/user/hdfs/sparkinput/training.csv", "hdfs://master:9000/user/hdfs/sparkoutput/model/nifi/demo/", "hdfs://master:9000/user/hdfs/sparkoutput/training/result/nifi/demo", "yarn"]}



{"file": "${demo_file}", "className": "${demo_className}", "args":["${demo_generic_dataset}", "${demo_model}", "${demo_prediction}", "${demo_master}"]}

{"file": "hdfs://master:9000/user/hdfs/jobs/linear-regression-0.0.1-SNAPSHOT.jar", "className": "it.eng.rd.LinearRegression", "args":["hdfs://master:9000/user/hdfs/sparkinput/generic.csv", "hdfs://master:9000/user/hdfs/sparkoutput/model/nifi/demo/", "hdfs://master:9000/user/hdfs/sparkoutput/predition/nifi/demo/", "yarn"]}

