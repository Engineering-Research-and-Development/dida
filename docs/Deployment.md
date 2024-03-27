# DIDA Platform 

Following docker configuration can be used to run algorithms in DIDA platform.

 
## Deploy DIDA Platform

### Start Main Docker

Navigate into extracted folder and from terminal execute:

```
docker compose up
```

This will start main docker.

### Start Superset Docker

After starting DIDA platform, you can start Superset by executing command

```
docker compose -f docker-compose-superset.yml -d
```

After Superset is started, it will require to create user, which is done by following:

```
docker compose exec superset superset-init 
```

Answer the provided questions and create admin user for Superset.

####  Create the Superset Database

Open browser and navigate to

```
http://localhost:8888/login/

```

Data -> Database -> + Database
Connect a database
Choose from a list of other databases we support
Apache Druid

SQLAlchemy URI - should point to Druid Broker service

```
druid://broker:8082/druid/v2/sql
```

#### Create a  Dataset

Open browser and navigate to Druid console:

```
http://localhost:9888/unified-console.html#
```

Here you can create dataSet needed by Superset to read data from and display data in graphs.

Dataset -> + Dataset

Database - Apache Druid
Schema - druid
See table schema - dataSource we created in Druid

Click on newly created dataset

Additional remark for Superset - it will start 6 Superset related containers, each about 2,5Gb so you might run into problem that your machine might run out of resources and that Superset will not work correct.

### Configuration 

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
```
docker exec -it sparkmasterdemo bash
```

Following example runs python algorithm with one input parameter - test_data.csv.
Both files needs to be uploaded to HDFS before executing command. How to do that, please check section above.

```
docker exec -it sparkmasterdemo bash
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

Please, refer to [CAPRI](https://github.com/Engineering-Research-and-Development/capri_cap_blueprints) or [S-X-AIPI](https://github.com/Engineering-Research-and-Development/s-X-AIPI-Autonomic-Manager/) implementation to check existing customizations
