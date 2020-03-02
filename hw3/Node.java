/*
  Name: Colton Hagan
  Date: 3/1/20
  Class: 241
  Description: Node of BST tree
*/
import java.util.ArrayList;
public class Node {
   private String value;
   Node left, right;
   private ArrayList<Integer> appearances;
   public Node(String value, int index) {
      this.value = value;
      appearances = new ArrayList<Integer>();
      appearances.add(index);
   }
   //Returns value of node
   public String getValue(){
      return value;
   }
   //Returns appearances
   public ArrayList<Integer> getAppearances() {
      return appearances;  
   }
   //Takes in a index and adds it to appearances
   public void anotherAppearance(int index) {
      appearances.add(index);
   }
   //Inserts a value on the left/right (based on dictionary sort) with a index
   public void insert(String value, int index) {
      //inserts left/right node
      if (left == null && (value.compareTo(getValue()) <= 0)) {
         left = new Node(value, index);
      } else if (right == null && (value.compareTo(getValue()) > 0)) {
         right = new Node(value, index);
      //moves to the left/right to find place to insert node
      } else if (value.compareTo(getValue()) <= 0) {
         left.insert(value, index);
      } else {
         right.insert(value, index);
      }
   }
}