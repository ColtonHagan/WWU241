/*
  Name: Colton Hagan
  Date: 2/13/20
  Class: 241
  Description: Takes in user input to form a bst and then prints varous transversal methods
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class BST {
   private static Node overallRoot;
   //Why do I need this constructor -- ask teacher
   public BST(){  
   } 
   //Returns the node that contains the original root
   public static Node getRoot(){
      return overallRoot;
   }
   //Inserts a new value off of the original root
   public static void insert(int value){
      getRoot().insert(value);
   }
   //Takes in a node and prints its in order traversal
   public static void printInOrder(Node node){
      if(node != null) {
         printInOrder(node.left);
         System.out.print(node.getValue() + " ");
         printInOrder(node.right);
      }
   }
   //Takes in a node and prints its pre order traversal
   public static void printPreOrder(Node node){
      if(node != null) {
         System.out.print(node.getValue() + " ");
         printPreOrder(node.left);
         printPreOrder(node.right);
      }
   }
   //Takes in a node and prints its post order traversal
   public static void printPostOrder(Node node){
      if(node != null) {
         printPostOrder(node.left);
         printPostOrder(node.right);
         System.out.print(node.getValue() + " ");
      }
   }
   public static void main(String[] args) throws IOException {
      BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
      System.out.println("Type integers, press enter after each one");
      System.out.println("Type end when done");
      String userInput = reader.readLine();
      //Creates the root if the user does not start with end
      if(!userInput.equals("end")) {
         overallRoot = new Node(Integer.parseInt(userInput));
      }
      //As long as the user is not inputing end it inserts nodes of bst tree
      while(!userInput.equals("end")) {
         userInput = reader.readLine();
         if(!userInput.equals("end")) {
            insert(Integer.parseInt(userInput));
         }
      }
      //Prints out in order/pre order/post order transversals
      System.out.print("In order: ");
      printInOrder(getRoot());
      System.out.println();
      System.out.print("Pre order: ");
      printPreOrder(getRoot());
      System.out.println();
      System.out.print("Post order: ");
      printPostOrder(getRoot());
      System.out.println();
   }
}