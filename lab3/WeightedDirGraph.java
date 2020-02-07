/*
  Name: Colton Hagan
  Date: 2/6/20
  Class: 241
  Description: Takes in user input to form a graph using linked lists
*/

import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class WeightedDirGraph {

  //Edge class with 3 properies
  static class Edge{
    int from;
    int to;
    int cost;
    //Takes in from, to, and cost and updates them
    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  //Graph class that conains the graph 
  static class Graph {
    int vertices;
    LinkedList<Edge> [] adjList;
    /*takes in a number of Vertices. 
      Fills in array of linked List with a new linked list of edge type */
    public Graph(int numVertices){
      vertices = numVertices;
      adjList = new LinkedList[numVertices];
      for(int i = 0; i < numVertices; i++){ 
         adjList[i] = new LinkedList<Edge>(); 
      } 
    }
    /*Takes in from, to, and cost int and creates a new edge with those values
      puts that in a linked list, then puts the linked list in array */
    public void addEdge(int from, int to, int cost){
      Edge edge = new Edge(from, to, cost);
      adjList[from].addFirst(edge);
    }
    //prints out graph
    public void printGraph(){
      for(int i = 0; i < vertices; i++) {
         for(Edge currEdge: adjList[i]) {
            System.out.println("Vertex " + currEdge.from + " connected to " +
                               currEdge.to + " at cost " + currEdge.cost);
         }
      }  
    }
  }

  public static void main(String[] args) throws IOException {
    //takes in user input
    BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
    System.out.print("How many vertices? ");
    int verices = Integer.parseInt(reader.readLine());
    Graph graph = new Graph(verices);
    String fromInput = "";
    //runs while the first input is not equal to "end"
    while(!fromInput.equals("end")) {
      System.out.print("From vertex ");
      fromInput = reader.readLine();
      //checks is first input equals end if not contues to ask for user input.
      if(!fromInput.equals("end")) {
        int from = Integer.parseInt(fromInput);
        System.out.print("To vertex ");
        int to = Integer.parseInt(reader.readLine());
        System.out.print("Cost ");
        int cost = Integer.parseInt(reader.readLine());
        graph.addEdge(from, to, cost);
      }
    }
    graph.printGraph();
  }

}
