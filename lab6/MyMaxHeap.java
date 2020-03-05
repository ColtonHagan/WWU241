/*
  Name: Colton Hagan
  Date: 3/4/20
  Class: 241
  Description: Prompts the user for up to 15 integers and creates and prints a heap of them
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class MyMaxHeap{
   public static void main(String[] args) throws IOException { 
   HeapMax heap= new HeapMax(15);
      BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
      int size = 0;
      String userInput = "";
      System.out.println("Type integers to insert into your max heap. Press enter after each one.");
      System.out.println("A maximum of 15 elements are allowd. Type 'end' when done.");
      //Reads in up to 15 user submititions or into user inputs "end".
      while (size < 15 && !userInput.equals("end")) {
         size++;
         userInput = reader.readLine();
         if(!userInput.equals("end")) {
            heap.insert(Integer.parseInt(userInput));
         }
      }
      heap.printMaxHeap();
   }
}