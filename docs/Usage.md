# How to Run Algorithms through the DIDA Platform

The following section is meant to explain how to plug-and-play algorithms in DIDA platform.

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
