/*
Author: Colton Hagan
Class: CS 241
Date: 3/1/2020
Purpose: Takes in via command line arguements a file, and then reads that file into a bst tree
         ignoring all non numbers and increasing appearance value of repeated words
         (but not adding them to the tree), also takes in other arguements, if the other arguement
         is ANALYZE returns 3 biggest (by frequency of apperances) nodes/words of tree. If other
         arguement is words returns there frequency of appearances, along with were they appeared
         in the file 
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class AnalyzeTextStream {
   private static Node overallRoot;
   //How many of the biggest nodes do you want to return when ANALYZE is called
   private static final int biggestSize = 3;
   public static void main(String args[]) throws IOException {
      readAndCreate(args[0]);
      //If the arguement passed outside of file name is ANALYZE command
      if(args[1].equals("ANALYZE")) {
         Node[] biggest = new Node[biggestSize];
         for(int i = 0; i < biggestSize; i++) {
            //Fills all with filler values with a appearance of 0
            biggest[i] = new Node("filler", 0);
         }
         findBiggestNodes(overallRoot, biggest);
         for(Node node : biggest) {
            printNodeInfo(node);
            System.out.print(" ");
         }
      //If it is words, not ANALYZE
      } else {
         for(int i = 1; i < args.length; i++) {
            Node node = inTree(args[i], overallRoot);
            if (node != null) {
               printNodeInfo(node);
            } else {
               System.out.print(args[i]+":0");
            }
            //Is redudent with ANALYZE case, but needed to deal with null possibility
            System.out.print(" ");
         }
      }
   }
   //Takes in a node and prints the value of the node, and the frequency of which it occured
   public static void printNodeInfo(Node node) {
      ArrayList<Integer> appearances = node.getAppearances();
      //Uses fenceposting
      System.out.print(node.getValue()+":"+appearances.size()+":"+appearances.get(0));
      for(int i = 1; i < appearances.size(); i++) {
         System.out.print(","+appearances.get(i));
      }  
   }
   /*Peforms a in-order traversal of tree, 
     finding the 3 biggest values (values that appeared with the most frequency)*/
   public static void findBiggestNodes(Node node, Node[] biggest) {
      if(node != null) {
         findBiggestNodes(node.left, biggest);
         /*Uses to overwrite to avoid unnessarly overwriting early parts of the
           biggest array while ignoring latter indexes*/ 
         int smallestIndex = findSmallestValuesIndex(biggest);
         //Checks nodes size against all nodes in biggest
         if((node.getAppearances().size() > biggest[smallestIndex].getAppearances().size())) {
            biggest[smallestIndex] = node;
         }
         findBiggestNodes(node.right, biggest);
      }
   }
   /*Takes in a node of arrays and returns the index of the node in the array
     with the smallest apperance frequency*/
   public static int findSmallestValuesIndex(Node[] nodeArray) {
      int smallestIndex = -1;
      int minValue = Integer.MAX_VALUE;
      for(int i = 0; i < nodeArray.length; i++) {
         if(nodeArray[i].getAppearances().size() < minValue) {
            minValue = nodeArray[i].getAppearances().size();
            smallestIndex = i;
         }
      }
      return smallestIndex;
   }
   /*Takes in a fileName and reads the file attached to it, constructing a bst of it word by word
     ignoring non-letters, with words that are node values of the bst tree being non-repetitive*/
   public static void readAndCreate(String fileName) throws IOException {
      BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
      String currLine;
      int currIndex = 0;
      while((currLine = fileReader.readLine()) != null) {
         //looks at word differentiated by a dash or a space
         for(String word : currLine.split(" |\\-")) {
            //gets ride of non-letters
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            //Runs if the word is not a number
            if(!word.equals("")) {
               currIndex++;
               //checks if node is already in tree
               Node node = inTree(word, overallRoot);   
               //create root if it does not exist
               if(overallRoot == null) {
                  overallRoot = new Node(word, currIndex);
               //if word already exists add to the indexing
               } else if (node != null) {
                  node.anotherAppearance(currIndex);
               //if word does not exist add it to the tree
               } else {
                  overallRoot.insert(word, currIndex);
               }
            }
         }
      }
   }
   /*Takes in a value and a node, and checks if value is in bst of node (making the node the root)
     returns the node that contains the value if it is in the tree else returns null*/
   public static Node inTree(String value, Node node) {
      if(node != null) {
         //returns node if matches 
         if(value.equals(node.getValue())) {
            return node;
         //reruns on node's left if less then or equals
         } else if (value.compareTo(node.getValue()) <= 0) {
            return inTree(value, node.left);
         //reruns on node's right if greater then
         } else {
            return inTree(value, node.right);
         }
      }
      //returns null if no matach is found
      return null;
   }
}