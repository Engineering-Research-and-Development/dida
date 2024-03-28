# How to Run Algorithms through the DIDA Platform

The following section is meant to explain how to plug-and-play algorithms in DIDA platform.

To run the algorithm, follow each solution guide provided in each solution's folder.
Generally speaking, there are two kind of algorithms:
- **Batch algorithms** usually runned on livy docker container with the help of Draco
  - Usually they need to install library dependencies on livy container (dependencies expressed in solution's guide)
- **Real time algorithms** usually runned on sparkmaster container
  - They should have every dependency already installed on sparkmaster container, however further modifications may need other libraries

In both case is reccomended to check if the execution starts correctly and wait for the first results to be displayed on screen. <br/>



## Configure 

```
docker exec -it  sparkmasterdemo bash

# make folders in hdfs
hdfs dfs -mkdir /user/
hdfs dfs -mkdir /user/hdfs/
hdfs dfs -mkdir /user/hdfs/jobs/
hdfs dfs -mkdir /user/hdfs/jobs/dida


# copy files to HDFS file system
# make changes for all files that needs to be copied
hdfs dfs -copyFromLocal /data/jobs/py/{filename} /user/hdfs/jobs/dida

```

## Launch through Spark

From inside docker container 
```
docker exec -it sparkmasterdemo bash
```

Following example runs python algorithm with one input parameter - test_data.csv.
Both files needs to be uploaded to HDFS before executing command. How to do that, please check section above.

```
spark-submit --master yarn 
hdfs://master:9000/user/hdfs/jobs/A3_EXE_CPS2_20220204/algorithm.py hdfs://master:9000/user/hdfs/jobs/A3_EXE_CPS2_20220204/test_data.csv
```
