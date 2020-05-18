### 4. Run your code on Apache Spark using "spark-submit"

If you are using the Scala template, note that:

1. You **only have to replace the logic** (currently is "true") in all User Defined Function.
2. The main function in this template takes **dynamic length of parameters** as follows:
	* Output file path (**Mandatory**): ```/Users/ubuntu/Downloads/output```
	* Range query data file path, query window: ```rangequery /Users/ubuntu/Downloads/arealm.csv -155.940114,19.081331,-155.618917,19.5307```
	* Range join query data file path, range join query window data file path: ```rangejoinquery /Users/ubuntu/Downloads/arealm.csv /Users/ubuntu/Downloads/zcta510.csv```
	* Distance query data file path, query point, distance: ```distancequery /Users/ubuntu/Downloads/arealm.csv -88.331492,32.324142 10```
	* Distance join query data A file path, distance join query data B file path, distance: ```distancejoinquery /Users/ubuntu/Downloads/arealm.csv /Users/ubuntu/Downloads/arealm.csv 10```
3. The number of queries and the order of queries in the input **do not matter**. The code template will detect the corresponding query and call it!
4. Two example datasets are put in "src/resources" folder. arealm10000 is a point dataset and zcta10000 is a rectangle dataset. You can can use them to test your code but eventually you must run your code on NYC taxi trip dataset. Our auto-grading system will also run your code on many different datasets.
5. Here is an example that tells you how to submit your jar using "spark-submit"
```
./bin/spark-submit CSE512-Project-Phase2-Template-assembly-0.1.0.jar result/output rangequery src/resources/arealm10000.csv -93.63173,33.0183,-93.359203,33.219456 rangejoinquery src/resources/arealm10000.csv src/resources/zcta10000.csv distancequery src/resources/arealm10000.csv -88.331492,32.324142 1 distancejoinquery src/resources/arealm10000.csv src/resources/arealm10000.csv 0.1
```
6. A test case file is given: ``exampleinput``. A correct answer is given: ``exampleanswer``

## Installation

1. Use IntelliJ Idea with Scala plug-in or any other Scala IDE.
2. Replace the logic of User Defined Functions ST\_Contains and ST\_Within in SparkSQLExample.scala.
3. Append ```.master("local[*]")``` after ```.config("spark.some.config.option", "some-value")``` to tell IDE the master IP is localhost.
3. In some cases, you may need to go to "build.sbt" file and change ```% "provided"``` to ```% "compile"``` in order to debug your code in IDE
4. Run your code in IDE
5. If you want to run the project jar file on a  Spark Cluster use command "./bin/spark-submit <jar file name>".
To know more on this, refer this (link)[https://spark.apache.org/docs/latest/spark-standalone.html]
