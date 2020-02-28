/*
  Name: Colton Hagan
  Date: 2/27/20
  Class: 241
  Description: Takes in user input to form a bst that has In order traversal and
               right rotation functionality, runs unit tests those functions and prints results.
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class BST {
   private static Node overallRoot;
   private static String inOrderTraverseStr = "";
   //Sets root to null
   public BST(){  
      overallRoot = null;
   } 
   //Returns the node that contains the original root
   public static Node getRoot(){
      return overallRoot;
   }
   //Takes in a node and makes it the root
   public static void updateRoot(Node node) {
      overallRoot = node;
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
   /*Prints out a modified in order traversal that also
     prints out children to help verify try shape with n seperating nodes */
   public static void inOrderTraverse(Node node){
      //end function here if node is null
      if (node == null){
         return;
      }
      // recursive call to left child
      inOrderTraverse(node.left);
      Node leftNode = node.getLeftChild();
      //prints left child if it exists
      if(leftNode == null ) {
         inOrderTraverseStr += "n";
      } else {
         inOrderTraverseStr += leftNode.getValue();
      }
      //prints current nodes value
      inOrderTraverseStr += node.getValue();
      Node rightNode = node.getRightChild();
      //prints right child if it exists
      if(rightNode == null ) {
         inOrderTraverseStr += "n";
      } else {
         inOrderTraverseStr += rightNode.getValue();
      }
      // recursive call to the right child
      inOrderTraverse(node.right);
   }
   //Unit tests in Order Traversal and prints results
   private static void treeTest_1() {
      inOrderTraverseStr = "";
      BST testTree = createTestTree();
      System.out.println("==========");
      System.out.println("treeTest_1");
      System.out.println("==========");
      System.out.print("In order: ");
      printInOrder(testTree.getRoot());
      System.out.println();
      inOrderTraverse(testTree.getRoot());
      System.out.println("nodeChildrenTrav: " + inOrderTraverseStr);
      System.out.print("treeTest_1");
      //checks if it passed
      if (inOrderTraverseStr.equals("n1n123n3n249n8n89n")){
         System.out.print(" passed\n");
      } else {
         System.out.print(" FAILED!\n");
      }    
   }
   //Unit tests right rotation and prints results
   private static void rightRotateTest_1(){
      BST testTree = createTestTree();
      inOrderTraverseStr = "";
      System.out.println("==========");
      System.out.println("rightRotateTest_1");
      System.out.println("==========");
      System.out.print("In order: ");
      printInOrder(testTree.getRoot());
      System.out.println();
      inOrderTraverse(testTree.getRoot());
      System.out.println("preRotation: " + inOrderTraverseStr);
      testTree.updateRoot(rightRotate(testTree.getRoot()));
      inOrderTraverseStr = "";
      inOrderTraverse(testTree.getRoot());
      System.out.println("preRotation: " + inOrderTraverseStr);
      System.out.print("rightRotateTest_1");
      //checks if it pasted
      if (inOrderTraverseStr.equals("n1n124n3n349n8n89n")){
         System.out.print(" passed.\n");
      } else {
         System.out.print(" FAILED!\n");
      }
   }
   //Rotates BST right around pivot point 
   public static Node rightRotate(Node pivot) {
      Node aNode = pivot.getLeftChild(); 
      Node leftChild = aNode.getLeftChild(); 
      Node rightChild = aNode.getRightChild(); 
      
      aNode.right = pivot;
      pivot.left = rightChild;
      return aNode;
   }
   //Creates and returns a test BST tree for unit tests to use
   public static BST createTestTree() {
      BST tester = new BST();
      tester.overallRoot = new Node(4);
      tester.insert(2);
      tester.insert(1);
      tester.insert(3);
      tester.insert(9);
      tester.insert(8);
      return tester;
   }
   public static void main(String[] args) throws IOException {
      BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
      System.out.println("Type integers, press enter after each one");
      System.out.println("Type end when done");
      String userInput = "";
      BST bst = new BST();
      //As long as the user is not inputing end it inserts nodes of bst tree
      while(!userInput.equals("end")) {
         userInput = reader.readLine();
         if(!userInput.equals("end")) {
            //creates overall root if it does not exist
            if(bst.overallRoot == null) {
               bst.overallRoot = new Node(Integer.parseInt(userInput));
            } else {
               bst.insert(Integer.parseInt(userInput));
            }
         }
      }
      //Prints out in Order Traversal
      inOrderTraverse(bst.getRoot());
      System.out.println("nodeChildrenTrav: " + inOrderTraverseStr);
      
      //Runs unit tests
      treeTest_1();
      System.out.println();
      rightRotateTest_1();
      System.out.println();
   }
}