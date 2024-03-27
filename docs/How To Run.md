# DIDA Platform 

Following docker configuration can be used to run algorithms in DIDA platform.
Before starting the platform, copy algorithm in **data\jobs\py\** folder and all necessary files (csv or other)
 
## Start main docker

Navigate into extracted folder and from terminal execute:

```
docker compose up
```

This will start main docker.

## Configuration ##

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

## Run algorithm from HDFS

From inside docker container 

Following example runs python algorithm with one input parameter - test_data.csv.
Both files needs to be uploaded to HDFS before executing command. How to do that, please check section above.

```
spark-submit --master yarn 
hdfs://master:9000/user/hdfs/jobs/A3_EXE_CPS2_20220204/algorithm.py hdfs://master:9000/user/hdfs/jobs/A3_EXE_CPS2_20220204/test_data.csv
```


### Submit job to Livy using postman

Same logic occurs when Draco executes Submit REST processor

```
curl --location --request POST 'http://localhost:8998/batches' \
--header 'Content-Type: application/json' \
--data-raw '{
    "file" : "hdfs://master:9000/user/hdfs/jobs/dida/algorithm.py",
    "conf": {
        "spark.jars.packages": "org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.5"
        },
    "args" : ["hdfs://master:9000/user/hdfs/jobs/A3_EXE_CPS2_20220204/test_data.csv"]
}'
```


## Customization


If your requirements does not need some of the components, they can be removed from provided docker compose file, by simply editing the file and commenting out, or deleting components that are not needed.

Next to DIDA docker-compose.yml file, you can find Superset docker compose docker-compose-superset.yml. This docker file requires environment file, located in same directory and has dependency on DIDA docker compose, in sense that it connects to spark_net and must be run after DIDA docker compose.

Additional remark for Superset - it will start 6 Superset related containers, each about 2,5Gb so you might run into problem that your machine might run out of resources and that Superset will not work correct.

Please, refer to [CAPRI](https://github.com/Engineering-Research-and-Development/capri_cap_blueprints) or [S-X-AIPI](https://github.com/Engineering-Research-and-Development/s-X-AIPI-Autonomic-Manager/) implementation to check existing customizations
