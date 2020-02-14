/*
  Name: Colton Hagan
  Date: 2/13/20
  Class: 241
  Description: Node of BST tree
*/
public class Node{
   private int value;
   Node left, right;
   public Node(int value){
      this.value = value;
   }
   //Prints out value of current node
   public int getValue(){
      return value;
   }
   //Takes in value in inserts it on bst tree in the correct left/right position
   public void insert(int value){
      //Inserts left/right node
      if (left == null && value <= getValue()){
         left = new Node(value);
      } else if (right == null && value >= getValue()) {
         right = new Node(value);
      //moves to the left/right to find place to insert node
      } else if (value <= getValue()) {
         left.insert(value);
      } else {
         right.insert(value);
      }
   }
}