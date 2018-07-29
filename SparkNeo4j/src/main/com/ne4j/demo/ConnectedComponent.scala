package com.ne4j.demo

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.neo4j.spark.Neo4j
import org.apache.spark.rdd.RDD

object ConnectedComponent {

  def main(args: Array[String]) {
  
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\")
    val conf = new SparkConf().setAppName("Simple ConnectedComponent Application")
      .setMaster("local")
      .set("spark.neo4j.bolt.url", "bolt://localhost")
      .set("spark.neo4j.bolt.user", "neo4j")
      .set("spark.neo4j.bolt.password", "qwerty")

    val sc = new SparkContext(conf)

    val neo = Neo4j(sc)

    println("Opening neo4J connection.......")

    
    val followers_relation = "MATCH (t:Tags)<-[tr:HAS_TAG]-(q:Questions)-[p:HAS_TAG]->(tq:Tags) WITH t,tq RETURN t.tagID,tq.tagID"
    
    println(followers_relation)

    val users_relation = "MATCH (t:Tags)<-[tr:HAS_TAG]-(q:Questions) WITH t RETURN DISTINCT t.tagID,t.tagName"

    case class edgeList(sorceId: Int, targetId: Int)

    val userVertexRDD = neo.cypher(users_relation).loadRowRdd.map(x => (x.getString(0).toLong, x.get(1).toString()))

	
	val followerEdgeRDD = neo.cypher(followers_relation).loadRowRdd.map(x =>(x.getString(0).toLong, x.getString(1).toLong))
    
    userVertexRDD.coalesce(1).saveAsTextFile("C:\\result\\userVertexRDD")
    followerEdgeRDD.coalesce(1).saveAsTextFile("C:\\result\\followerEdgeRDD")
    
	// Load the graph
    val graph = GraphLoader.edgeListFile(sc, "C:\\result\\followerEdgeRDD\\tag_follower.txt")
    
	// Find the connected components
    val cc = graph.connectedComponents().vertices
    
	// Join the connected components with the usernames
    val users = sc.textFile("C:\\result\\userVertexRDD\\tag_user.txt").map { line =>
      val fields = line.split(",")
      (fields(0).toLong, fields(1))
    }
	
    val ccByUsername = users.join(cc).map {
      case (id, (username, cc)) => (username, cc)
    }
    
	// Print the result
   ccByUsername.coalesce(1).saveAsTextFile("C:\\result\\tagFileResult")

  }
}