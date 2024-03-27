# Digital Industry Data Analytics (DIDA) Platform




### Tool version

DIDA is a [FIWARE](https://www.fiware.org/catalogue/) based platform for Smart Industry 

v0.0.3
	
	- FIWARE Orion v2.0.0
	- FIWARE Draco v7.8.1
	- Apache Spark v2.4.5
	- Apache Hadoop 
	- Apache Livy v0.0.7
	- Apache Kafka v2.4.1

### Customization

If your requirements does not need some of the components, they can be removed from provided docker compose file, by simply editing the file and commenting out, or deleting components that are not needed. 

Next to DIDA docker-compose.yml file, you can find Superset docker compose *docker-compose-superset.yml*. This docker file requires environment file, located in same directory and has dependency on DIDA docker compose, in sense that it connects to spark_net and must be run after DIDA docker compose. 

Additional remark for Superset - it will start 6 Superset related containers, each about 2,5Gb so you might run into problem that your machine might run out of resources and that Superset will not work correct.

### Deploy DIDA platform

Execute the command into project root folder

```
docker compose up -d
```

### Superset

After starting DIDA platform, you can start Superset by executing command

```
docker compose -f docker-compose-superset.yml -d
```

After Superset is started, it will require to create user, which is done by following:

```
docker compose exec superset superset-init 
```

Answer the provided questions and create admin user for Superset.

### Superset - creating database

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

### Create Dataset

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

### License
The DIDA Platform is licensed under the GNU Affero General Public License v3.0

DIDA Platform has received funding from the European Union's Horizon 2020 research and innovation programme under grant agreements No 952003 [AI REGIO](https://www.airegio-project.eu/) and 870062 [CAPRI](https://www.capri-project.com/).
