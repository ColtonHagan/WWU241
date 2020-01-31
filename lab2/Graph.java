/*
Author: Colton hagan
Class: CS 241
Date: 1/30/20
Purpose: To ask the user to input edges of a graph and then print out the graph
         in matrix form containing them.
*/

import java.io.*;
public class Graph{
      //graph to be printed
      private static int[][] graph;
      //vertexes on graph
      private static String[] vertexNames;
      //number of vertixes
      private static final int numVertices = 5;
      
   public static void main(String args[]) throws IOException {
      createGraph();
      createGraphVertexNames();
      printVertexNames();
      promptEdgeInput();
      printGraph();
   }
   
   //Creates square graph of number of Vertices size
   private static void createGraph() {
      System.out.println("Graph created.");
      graph = new int[numVertices][numVertices];
   }
   
   //Prints out the name of all vertices
   private static void printVertexNames() {
      System.out.println("The vertices are:");
      for(int i = 0; i < numVertices; i++) {
         System.out.println(vertexNames[i]);
      }
      System.out.println("");
   }
   
   //Put vertices into array and prints them
   private static void createGraphVertexNames() {
      System.out.println("Vertices added to graph.");
      vertexNames = new String[numVertices];
      //These are the given vertices to add
      vertexNames[0] = "apple";
      vertexNames[1] = "grape";
      vertexNames[2] = "banana";
      vertexNames[3] = "orange";
      vertexNames[4] = "pineaple";
   }
   
   //Takes in a vertex name and returns it indexing in vertexName arrayif -1 if there is not one
   private static int getVertexID(String vertexName) {
      for (int i = 0; i < numVertices; i++) {
         if(vertexName.equals(vertexNames[i])) {
            return i;
         }  
      }
      return -1;
   }
   
   //Prints out the graph next to the vertices
   private static void printGraph() {
      for(int i = 0; i < numVertices; i++) {
            String spaces = "                   ";
            //Uses substring to keep spacing consistant
            spaces = spaces.substring(0, spaces.length() - vertexNames[i].length());
            String output = vertexNames[i] + spaces;
            System.out.print(output);
            //Fenceposting
            System.out.print("| " + graph[i][0] + " |");
         for(int j = 1; j < numVertices; j++) {
            System.out.print(" " + graph[i][j] + " |");
         }
         System.out.println("");
      }
   }
   
   /* Takes in two vertices and creates an edge in the graph showing they are connect
      prints out a message showing success or failure */
   private static void addEdge(String vertex1, String vertex2) {
      int vertex1Index = getVertexID(vertex1);
      int vertex2Index = getVertexID(vertex2);
      String edge = vertex1 + "-" + vertex2;
      if (vertex1Index == -1 || vertex2Index == -1) {
         System.out.println("Cannot add " + edge + 
                            " to graph because one of those vertices do not exist.");
      } else {
         graph[vertex1Index][vertex2Index] = 1;
         graph[vertex2Index][vertex1Index] = 1;
         System.out.println("Added " + edge + " to graph.");
      }
   }
   
   /* Continously takes in user input of two vertices and moves to create a edge
      between them if they are valid untill the user inpuuts done */
   private static void promptEdgeInput() throws IOException {
      BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
      String vertex1 = "";
      String vertex2 = "";
      //Asks for user input until done is inputed
      while (!vertex1.equals("done")&&!vertex2.equals("done")) {
         System.out.print("Enter first vertex : ");
         vertex1 = reader.readLine();
         System.out.print("Enter second vertex : ");
         vertex2 = reader.readLine();
         if(!vertex1.equals("done")&&!vertex2.equals("done")) {
            addEdge(vertex1, vertex2);
         }
         System.out.println("");
      }
   }
}