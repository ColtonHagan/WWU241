import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import java.lang.Math;
public class findUsers {
   //graph to be printed
   private static int[][] graph;
   //vertexes on graph
   private static ArrayList<String> vertexNames;
   public static void main(String args[]) throws IOException {
      String fileName = args[0];
      String sourcePerson = args[1];
      String targetPerson = args[2];
      BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
      String currLine;
      ArrayList<String> words = new ArrayList<String>();
      while ((currLine = fileReader.readLine()) != null) {
         String[] currWords = currLine.split(" ");
         words.add(currWords[0]);
         words.add(currWords[1]);
         words.add(currWords[2]);
      }
      createVertex(words);
      createGraph(words);
      //printGraph();
      int[] dijkstraDistances = Dijkstra(sourcePerson);
      findCorrectOutput(dijkstraDistances, sourcePerson, targetPerson);
   }
   public static void findCorrectOutput(int[] dijkstraDistances, String sourcePerson, String targetPerson) {
      //Saves into a arrayList to allow fenceposting
      ArrayList<String> output = new ArrayList<String>();
      if(targetPerson.matches(".*eq\\d.*")) {
         int targetNumber = Integer.parseInt(targetPerson.replaceAll("[^0-9]", ""));
         for(int i = 0; i < dijkstraDistances.length; i++) {
            if(dijkstraDistances[i] == targetNumber && i != getVertexID(sourcePerson, vertexNames)) {
               output.add(vertexNames.get(i));
            }
         }
      } else if (targetPerson.matches(".*lt\\d.*")) {
         int targetNumber = Integer.parseInt(targetPerson.replaceAll("[^0-9]", ""));
         for(int i = 0; i < dijkstraDistances.length; i++) {
            if(dijkstraDistances[i] < targetNumber && i != getVertexID(sourcePerson, vertexNames)) {
               output.add(vertexNames.get(i));
            }
         }
      } else if (targetPerson.matches(".*gt\\d.*")) {
         int targetNumber = Integer.parseInt(targetPerson.replaceAll("[^0-9]", ""));
         for(int i = 0; i < dijkstraDistances.length; i++) {
            if(dijkstraDistances[i] > targetNumber && i != getVertexID(sourcePerson, vertexNames)) {
               output.add(vertexNames.get(i));
            }
         }
      } else {
         for(int i = 0; i < dijkstraDistances.length; i++) {
            if(vertexNames.get(i).equals(targetPerson)) {
               output.add("" + dijkstraDistances[i]);
            }
         }
      }
      printOutput(output);
   }
   public static void printOutput(ArrayList<String> output) {
      if (output.size() != 0) {
         System.out.print(output.get(0));
         for(int i = 1; i < output.size(); i++) {
            System.out.print(" " + output.get(i));
         }
      } else {
         System.out.println("none");
      }
   }
   public static int[] Dijkstra(String sourcePerson) {
      int[] distances = new int[vertexNames.size()];
      for(int i = 0; i < distances.length; i++) {
         distances[i] = 2147483647;
      }
      ArrayList<String> known = new ArrayList<String>();
      known.add(sourcePerson);
      ArrayList<String> unknown = new ArrayList<String>(vertexNames);
      unknown.remove(sourcePerson);
      distances[getVertexID(sourcePerson, vertexNames)] = 0;
      updateDistances(distances, sourcePerson, 0);
      int newPersonWeight = 0;
      while(!unknown.isEmpty() && newPersonWeight != 2147483647) {
         String newPerson = unknown.get(0);
         newPersonWeight = 2147483647;
         for(int i = 0; i < vertexNames.size(); i++) {
            if(unknown.contains(vertexNames.get(i))) {
               if(distances[i] < newPersonWeight) {
                  newPersonWeight = distances[i];
                  newPerson = vertexNames.get(i);
               }
            }
         }
         known.add(newPerson);
         unknown.remove(newPerson);
         updateDistances(distances, newPerson, newPersonWeight);
      }
      return distances;
   }
  public static void updateDistances(int[] distances, String currentPerson, int prevousWeight) {
      for(int i = 0; i < vertexNames.size(); i++) {
         int graphWeight = graph[getVertexID(currentPerson, vertexNames)][i];
         int weight = graphWeight + prevousWeight;
         if(weight < distances[i] && graphWeight != 0 && prevousWeight != 2147483647) {
            distances[i] = weight;
         }
      }
   }
   public static void createVertex(ArrayList<String> words) {
      vertexNames = new ArrayList<String>();
      for(int i = 0; i < words.size(); i+=3) {
         if(!vertexNames.contains(words.get(i))) {
            vertexNames.add(words.get(i));
         }
         if(!vertexNames.contains(words.get(i+1))) {
            vertexNames.add(words.get(i+1));
         }
      }
   }
   public static void createGraph(ArrayList<String> array) {
      graph = new int[vertexNames.size()][vertexNames.size()];
      for(int i = 0; i < array.size(); i += 3) {
         graph[getVertexID(array.get(i), vertexNames)][getVertexID(array.get(i+1), vertexNames)] = Integer.parseInt(array.get(i+2));
      }
   }
   public static int getVertexID(String vertexName, ArrayList<String> array) {
      for (int i = 0; i < array.size(); i++) {
         if(vertexName.equals(array.get(i))) {
            return i;
         }  
      }
      return -1;
   }
   private static void printGraph() {
      int numVertices = vertexNames.size();
      for(int i = 0; i < numVertices; i++) {
            String spaces = "                   ";
            //Uses substring to keep spacing consistant
            spaces = spaces.substring(0, spaces.length() - vertexNames.get(i).length());
            String output = vertexNames.get(i) + spaces;
            System.out.print(output);
            //Fenceposting
            System.out.print("| " + graph[i][0] + " |");
         for(int j = 1; j < numVertices; j++) {
            System.out.print(" " + graph[i][j] + " |");
         }
         System.out.println("");
      }
   }

}