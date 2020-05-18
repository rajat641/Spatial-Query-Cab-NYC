# Spatial Query on NYC Cab Data
This repo provides scripts to run multiple spatial queries on the large database that contains geographic data as well as real-time location data of the customer for-hire vehicle (Uber, Lyft, etc.).Most of the raw data comes from the NYC Taxi & Limousine Commission from 2009.

#### Statistics through December 31, 2019:

1. 2.63 billion total trips
2. 1.69 billion taxi
3. 935 million for-hire vehicle
4. 291 GB of raw data
Database takes up 391 GB on disk with minimal indexes

#### Spatial Query 
A spatial query is a special type of query supported by geodatabases and spatial databases. The queries differ from traditional SQL queries in that they allow for the use of points, lines, and polygons. The spatial queries also consider the relationship between these geometries. 




## Installation

1. Use IntelliJ Idea with Scala plug-in or any other Scala IDE.
2. Replace the logic of User Defined Functions ST\_Contains and ST\_Within in SparkSQLExample.scala.
3. Append ```.master("local[*]")``` after ```.config("spark.some.config.option", "some-value")``` to tell IDE the master IP is localhost.
3. In some cases, you may need to go to "build.sbt" file and change ```% "provided"``` to ```% "compile"``` in order to debug your code in IDE
4. Run your code in IDE
5. If you want to run the project jar file on a  Spark Cluster use command "./bin/spark-submit <jar file name>".
To know more on this, refer this - [Link]( https://spark.apache.org/docs/latest/spark-standalone.html ) .
