package com.ne4j.demo


import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.neo4j.spark.Neo4j
import org.apache.spark.rdd.RDD


object DemoPageRank {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "C:\\hadoop\\")
    val conf = new SparkConf().setAppName("Simple ConnectedComponent Application")
      .setMaster("local")
      
    val sc = new SparkContext(conf)
    
    // Load the graph 
    val graph = GraphLoader.edgeListFile(sc,"C:\\result\\followerEdgeRDD\\tag_follower.txt")
    
	// Run PageRank
    val ranks = graph.pageRank(0.08).vertices
    
	// Join the connected components with the usernames
    val users = sc.textFile("C:\\result\\user.txt").map { line =>
      val fields = line.split(",")
      (fields(0).toLong, fields(1))
    }
    val ccByUsername = users.join(ranks).map { 
      case (id, (username, rank)) => (username, rank)
    }
    // Print the result
   ccByUsername.coalesce(1).saveAsTextFile("C:\\result\\pageRankResult")
   
  }
}

