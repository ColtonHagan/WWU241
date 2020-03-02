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
   public String getValue(){
      return value;
   }
  
   public ArrayList<Integer> getAppearances() {
      return appearances;  
   }
   
   public void anotherAppearance(int index) {
      appearances.add(index);
   }
   public void insert(String value, int index) {
      if (left == null && (value.compareTo(getValue()) <= 0)) {
         left = new Node(value, index);
      } else if (right == null && (value.compareTo(getValue()) > 0)) {
         right = new Node(value, index);
      } else if (value.compareTo(getValue()) <= 0) {
         left.insert(value, index);
      } else {
         right.insert(value, index);
      }
   }
}