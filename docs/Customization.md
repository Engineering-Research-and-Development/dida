### Customization


If your requirements does not need some of the components, they can be removed from provided docker compose file, by simply editing the file and commenting out, or deleting components that are not needed.

Next to DIDA docker-compose.yml file, you can find Superset docker compose docker-compose-superset.yml. This docker file requires environment file, located in same directory and has dependency on DIDA docker compose, in sense that it connects to spark_net and must be run after DIDA docker compose.

Additional remark for Superset - it will start 6 Superset related containers, each about 2,5Gb so you might run into problem that your machine might run out of resources and that Superset will not work correct.
