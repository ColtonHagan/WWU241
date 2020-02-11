/*
Author: Colton hagan
Class: CS 241
Date: 2/1/2020
Purpose: Takes in via command line arguements a file, source person, and target/targets.
         Program reads in the file, performs dijkstra on the file, and 
         outputs the found names that find the target range, or the distance of the given name.
*/

//for reading in the file
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//for the array list
import java.util.ArrayList;
public class findUsers {
   //graph to be printed
   private static int[][] graph;
   //vertexes on graph
   private static ArrayList<String> vertexNames;
   //number of vertices on ints
   private static int numVertices;
   public static void main(String args[]) throws IOException {
      String sourcePerson = args[1];
      ArrayList<String> namesAndWeight = readFile(args[0]);
      createVertex(namesAndWeight);
      createGraph(namesAndWeight);
      int[] dijkstraDistances = dijkstra(sourcePerson);
      findCorrectOutput(dijkstraDistances, sourcePerson, args[2]);
   }
   
   //Takes in the name of a file and reads the file in and converts it into a array list
   public static ArrayList<String> readFile(String fileName) throws IOException {
      BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
      String currLine;
      ArrayList<String> namesAndWeight = new ArrayList<String>();
      //Reads the lines into a arrayList then outputs it
      while ((currLine = fileReader.readLine()) != null) {
         String[] currWords = currLine.split(" ");
         namesAndWeight.add(currWords[0]);
         namesAndWeight.add(currWords[1]);
         namesAndWeight.add(currWords[2]);
      }
      fileReader.close();
      return namesAndWeight;
   }
   
   /*Takes in the found distances for the vertexs, the source person, and your target
     (targetPerson, lt, gt, or eq) */
   public static void findCorrectOutput(int[] dijkstraDistances, String sourcePerson,
                                        String targetIdea) {
      //Saves into a arrayList to allow fenceposting
      ArrayList<String> output = new ArrayList<String>();
      String targetNumber = targetIdea.replaceAll("[^0-9]", "");
      /*Uses series of if statements with redundency due to import rule not allowing BiPredicate
        Saves if target idea is eq#*/
      if(targetIdea.matches(".*eq\\d.*")) {
         for(int i = 0; i < numVertices; i++) { 
            if(dijkstraDistances[i] == Integer.parseInt(targetNumber) &&
               //makes sure to not add self to the output output
               i != getVertexID(sourcePerson)) {
               output.add(vertexNames.get(i));
            }
         }
      //Saves if target idea is lt#
      } else if (targetIdea.matches(".*lt\\d.*")) {
         for(int i = 0; i < numVertices; i++) {
            if(dijkstraDistances[i] < Integer.parseInt(targetNumber) &&
               i != getVertexID(sourcePerson)) {
               output.add(vertexNames.get(i));
            }
         }
      //Saves if target idea is gt#
      } else if (targetIdea.matches(".*gt\\d.*")) {
         for(int i = 0; i < numVertices; i++) {
            if(dijkstraDistances[i] > Integer.parseInt(targetNumber) &&
               i != getVertexID(sourcePerson)) {
               output.add(vertexNames.get(i));
            }
         }
      //Saves if target idea is a name
      } else {
         for(int i = 0; i < numVertices; i++) {
            if(vertexNames.get(i).equals(targetIdea)) {
               output.add("" + dijkstraDistances[i]);
            }
         }
      }
      printOutput(output);
   }
   
   //Takes in a array list and outputs the contents, unles it is empty, it then prints none
   public static void printOutput(ArrayList<String> output) {
      if (output.size() != 0) {
         System.out.print(output.get(0));
         for(int i = 1; i < output.size(); i++) {
            System.out.print(" " + output.get(i));
         }
      } else {
         System.out.print("none");
      }
   }
   
   //Takes in a person and finds the shortest path to all other names using dijkstra 
   public static int[] dijkstra(String sourcePerson) {
      int[] distances = new int[vertexNames.size()];
      /*Sets all the distances to infite/integer max value.
        Uses number representation due to import guildlines */
      for(int i = 0; i < numVertices; i++) {
         distances[i] = 2147483647;
      }
      /*Creates array lists of all known and unknown names and then adds/subtracts
        the source person from them*/
      ArrayList<String> known = new ArrayList<String>();
      known.add(sourcePerson);
      ArrayList<String> unknown = new ArrayList<String>(vertexNames);
      unknown.remove(sourcePerson);
      distances[getVertexID(sourcePerson)] = 0;
      updateDistances(distances, sourcePerson, 0);
      int newPersonWeight = 0;
      /*Runs dijkstra finding the shortest possible path based on current name until
        there is no unkown names left or until there is only names with infinite values left*/
      while(!unknown.isEmpty() && newPersonWeight != 2147483647) {
         //Uses the first name with infinite value in case of tie
         String newPerson = unknown.get(0);
         newPersonWeight = 2147483647;
         //Finds the smallest distance left of the unknown names
         for(int i = 0; i < numVertices; i++) {
            if(unknown.contains(vertexNames.get(i))) {
               if(distances[i] < newPersonWeight) {
                  newPersonWeight = distances[i];
                  newPerson = vertexNames.get(i);
               }
            }
         }
         //Updates the known, unknown, and distances variables
         known.add(newPerson);
         unknown.remove(newPerson);
         updateDistances(distances, newPerson, newPersonWeight);
      }
      return distances;
   }
   
   /*Takes in the current shortest distances, current person, and the prevous weight
     traversed to get to that person and updates the distance array with new values from that 
     current person*/
   public static void updateDistances(int[] distances, String currentPerson, int prevousWeight) {
      for(int i = 0; i < numVertices; i++) {
         //Current weight at that edge
         int graphWeight = graph[getVertexID(currentPerson)][i];
         int weight = graphWeight + prevousWeight;
         /*Sets distance equal to weight if and only if it is less then old value, 
           not equal to zero (there is no edge), and if the old weight is not equal to infitity
           (there are no edges that are touching it)*/
         if(weight < distances[i] && graphWeight != 0 && prevousWeight != 2147483647) {
            distances[i] = weight;
         }
      }
   }
   
   /*Is given a array list of names and weights and finds and filters all the names
     into a non-repeating array list.*/
   public static void createVertex(ArrayList<String> namesAndWeight) {
      vertexNames = new ArrayList<String>();
      for(int i = 0; i < namesAndWeight.size(); i+=3) {
         //Uses two if statements to skip third weight part of array list.
         if(!vertexNames.contains(namesAndWeight.get(i))) {
            vertexNames.add(namesAndWeight.get(i));
         }
         if(!vertexNames.contains(namesAndWeight.get(i+1))) {
            vertexNames.add(namesAndWeight.get(i+1));
         }
      }
      //set variable of number of vertices
      numVertices = vertexNames.size();

   }
   
   /*Takes in a array list of names and weights and creates a matrix representation of a directed,
     weighted graph*/
   public static void createGraph(ArrayList<String> namesAndWeight) {
      graph = new int[vertexNames.size()][vertexNames.size()];
      for(int i = 0; i < namesAndWeight.size(); i += 3) {
         //Saves weight into vararable to shorten lines
         int weight = Integer.parseInt(namesAndWeight.get(i+2));
         graph[getVertexID(namesAndWeight.get(i))][getVertexID(namesAndWeight.get(i+1))] = weight;
      }
   }
   /*Takes in a vertex name and returns its ID in vertexNames array list,
     returning -1 if there is no such name in the array list*/
   public static int getVertexID(String vertexName) {
      for (int i = 0; i < numVertices; i++) {
         if(vertexName.equals(vertexNames.get(i))) {
            return i;
         }  
      }
      //Returns if no match found
      return -1;
   }
}