# DIDA Architecture

## DIDA Architecture in-the-large
![DIDA in-the-large](images/DIDA_in_the_large_v2.png)

### The Reference Architecture layering

The resulting three-tier RA is depicted in Figure *DIDA Architecture in-the-large* and defines several functional macro-components:

*Smart Field* represents the physical layer and contains industrial devices, machines, actuators, sensors, wearable devices, robots, etc. that are spread in the shop floor, and supports the most common industrial and, more in general, IoT protocols such as OPC UA, MQTT, etc. Standards interfaces and protocols must be used, in order to represent the information collected from the plant and to connect and integrate actuators for implementing the sensing and control mechanisms. Data will be collected typically as Data in Motion (DiM) since data coming from IIoT systems are dynamic and should be ingested and processed in real time.

*External Systems* component contains all internal and IT systems for supporting industrial processes (ERPs, PLMs, Supply Chain Mgmt, customized, etc.). It represents static information that comes from Legacy Systems and can be collected as Data at Rest (DaR). Custom interfaces and system wrappers are a crucial part of the component, aiming to share data using smart data models for representing information.

*Smart Data Management and Integration* is the core of the architecture since it contains the brokering, the storage and the data processing capabilities, including cognitive process analytics and simulation systems. Data in Motion (DiM), Data at Rest (DaR) and Situational Data are represented using standard information models and made available using standard APIs. Thought the service layer, data can be collected and persisted supporting a wide range of database (i.e., relational, nosql, time-series). 

*Data Ingestion* sublayer provides a bridge between the physical layer and the data brokering, where the data from the devices are shared in a standardized structure with the broker, putting the information at the disposal of the tools will analyse them. FIWARE IDAS Agent Generic Enabler is the IoT component that translates IoT-specific protocols into the NGSI-LD context information protocol, which is the FIWARE standard data exchange model. [IoT Agent for OPC UA](https://iotagent-opcua.readthedocs.io/en/latest/), [IoT Agent for JSON](https://fiware-iotagent-json.readthedocs.io/en/latest/), [IoT Agent for Ultralight](https://fiware-iotagent-ul.readthedocs.io/en/latest/) are some IDAS Agents in FIWARE Catalogue.

The *Data Brokering* sublayer role is to manage the persistence and processing phase, where the main actors areis the [Orion-LD Context Broker](https://github.com/FIWARE/context.Orion-LD), able to manage the entire lifecycle of context information including updates, queries, registrations, and subscriptions and [Apache Kafka](https://kafka.apache.org/) for high-performance data pipelines, streaming analytics, data integration, and mission-critical applications. 

The *Data Persistence and Processing* sublayer is composed of various FIWARE ([Cygnus](https://fiware-cygnus.readthedocs.io/en/latest/), [Quantum Leap](https://quantumleap.readthedocs.io/en/latest/). [Draco](https://fiware-draco.readthedocs.io/en/latest/), [Cosmos](https://fiware-cosmos.readthedocs.io/en/latest/) ) and Apache ([Livy](https://livy.apache.org/), [Spark](https://spark.apache.org/), [StreamPipes](https://streampipes.apache.org/), [Hadoop](https://hadoop.apache.org/)) components and is devoted to storing the data collected and process them.  Cygnus, Quantum Leap and Draco are in charge to support the data storage (and pre-processing) acting as a data sink for the persistence vertical. Cosmos is oriented to big data analysis of Streaming and Batch processing over context data, while Spark is a parallel processing framework for running largescale, both batch and real-time, data analytics applications across clustered computers. Data flows can be defined with Draco running Spark jobs through Apache Livy. StreamPipes is an Industrial IoT toolbox to enable non-technical users to connect, analyze, and explore IoT data streams. Its runtime layer supports the addition of pipeline elements through a built-in SDK in the form of microservices. 

The *Data Visualization* gives a clear understanding of resulting data giving it visual context through maps or graphs. There are specific components, compliant with the most data source that fit different scenarios: [Wirecloud](https://wirecloud.readthedocs.io/en/stable/) enable the quick creation of web applications and dashboards/cockpits, while [Grafana](https://grafana.com/) supports the analytics and interactive visualization, more oriented to complex monitoring dashboards. [Knowage](https://www.knowage-suite.com/site/) offers complete set of tools for analytics, paying attention in particular at the data visualization for the most common data sources and big data, covering different topics like Smart Intelligence, Enterprise Reporting, Location Intelligence, Performance Management, Predictive Analysis. Finally, [Apache Superset](https://superset.apache.org/) is fast, lightweight, intuitive, and loaded with options that make it easy for users of all skill sets to explore and visualize their data, from simple line charts to highly detailed geospatial charts.


## DIDA Architecture in-the-small
![DIDA in-the-small](images/DIDA_in_the_small_v2.png)

## Modularity and edge-cloud balancing
The described architecture has been conceived with modularity as a main principle: 

Components in every layer can be combined according with a Lego-like approach, fulfilling the exposed data schema, making the architecture flexible and adaptable to the specific needs of the various application domains in process industry.

At the same time the modularity makes possible to approach a microservices design of the application that produces smaller software code, to be organizer as docker containers, so they could be run on smaller processing elements and restricted resources, as we can find in current plants, thus making easier the reuse of existing computing equipments.

In this respect, the DIDA Reference Architecture allows the implementation on both cloud and edge, managing the edge-cloud continuum Figure *DIDA on the Edge* is the edge version of the RA, that can be run on virtualized computing resources nearer to where multiple streams of data are created, thus addressing system latency, privacy, cost and resiliency challenges that a pure cloud computing approach cannot address, and make a big difference in process industry. The edge implementation smoothly integrates with the cloud version, to enable data collection, storing, processing and presentation directly from the plant. Most of the short-term processing, including some data analytics, artificial intelligence and cognitive tasks could be managed at the edge, while cloud resources can be devoted to non-mission critical - massive processing of data.

![DIDA on the Edge](images/DIDA_Edge.png)


## Customization

The reference architecture is built in a modular fashion, making the DIDA Platform extensible through the use of other open-sources components and connectors. 
Please, refer to [CAPRI](https://github.com/Engineering-Research-and-Development/capri_cap_blueprints) or [S-X-AIPI](https://github.com/Engineering-Research-and-Development/s-X-AIPI-Autonomic-Manager/) implementation examples to evaluate existing customizations and extension to the reference architecture.
