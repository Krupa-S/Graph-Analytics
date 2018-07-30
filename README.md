# Graph-Analytics using Neo4j and Spark's GraphX API

The aim of this project is to develop end-to-end graph analytics module for big data. As a part of implementation, Stack Overflow Questions & Answers dataset, Neo4j Graph database, Spark's GraphX API, Scala programming and Amazon's EC2 cloud instance for hosting database for used. 

The demonstration includes:
* Graph Cypher queries for the following use cases - 
  * Finding trends of a technology in the data set
  * Identify top answerers for javascript questions
  * Fetch all the answers for each Java questions based on the scores
  * Lists where Else Were the Top Answerers of Java also Active?
  * Find users posting most Javascript questions
* Extended Graph Analytics using Scala based implementation for Spark's GraphX API for - 
  * Evaluate an expert's rank for a programming language based on ranking using Page Rank Algorithm
  * Identifying the connected and non-connected tags from the dataset using Connected Components Algorithm.  

### Experimental Setup

* The experimental setup included: 
  *	Programming Language: Scala – Scala SDK – 4.7.0
  *	Dependencies: Spark-core_2.11, Spark-sql_2.11, spark-graphx_2.11
  *	Neo4j Database – Neo4j 3.2.4 version

* Cloud Implmentation for Neo4j Database: Neo4j Graph Database Community Edition was deployed on AWS EC2 instance and graph implementation for Stack overflow dataset. The Neo4j database edition deployed is limited to the single machine. 

* Locally Neo4j Community Edition can be downloaded from http://neo4j.com/download/ and server should be started after installation. 
Default graphdb folder should be replaced with unzipped ![Graph DB](/databases/neo4j/default.graphdb(2)) folder. Consequently, restart the services. 

* Clone the SparkNeo4j locally, build the Gradle and Run the project to execute
### References

[GraphX](http://spark.apache.org/docs/1.6.0/graphx-programming-guide.html)



