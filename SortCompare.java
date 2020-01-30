/*
Author: Colton hagan
Class: CS 241
Date: 1/26/20
Purpose: Generate a area of n size of numbers between -n and n where n is given by user input
         the try out merge, insertion, quick, and or radix sort on the aformentioned array.
*/

import java.io.*; 
import java.util.*;
public class  SortCompare {
	public static void main(String[] args) {
      /* Gets user input for size then creates an array of that size
         with random numbers (negetive and positive of that size */
      Scanner user_input = new Scanner(System.in);		
      System.out.println("Input Params");
      System.out.println("============");
      System.out.print("How many entries? ");
      int size = user_input.nextInt();
      int[] unsorted = new int[size];
      for (int i = 0; i < size; i++) {
         unsorted[i] = (int)(Math.random() *((size * 2) + 1)) + -size;
      }
      //Takes user input of type of sort and then runs that sort
      System.out.print("Which sort [m,i,q,r,all]? ");
      String sort_type = user_input.next();
      System.out.println("");
      if(sort_type.equals("m")) {
         mergeSort(unsorted);
      } else if (sort_type.equals("q")) {
         quickSort(unsorted);
      } else if (sort_type.equals("i")) {
         insertionSort(unsorted);
      } else if (sort_type.equals("r")) {
         radixSort(unsorted);
      } else {
         //copies arrays to avoid overlap
         mergeSort(unsorted.clone());
         quickSort(unsorted.clone());
         insertionSort(unsorted.clone());
         radixSort(unsorted);
      } 
	}
   /* Takes in an array and then passes it on to merge sort function with additional
       information and collects information you will need to print */
   public static void mergeSort(int[] array) {
      int[] comparisons = new int[1];
      int[] unsorted = array.clone();
      MergeHelper(array, 0, array.length - 1, comparisons);
      printStats("merge", unsorted, comparisons[0], array);
   }
   
   /* Takes in an unsorted array. first int of that array, last int of that array
      and number of current comparisons that have been. 
      Calculates mid and splits array in halves*/
   public static void MergeHelper(int[] unsortedArray, int first, int last, int[] comparisons) {
      if (first < last) {
         //finds middle to sort halves
         int mid = (first + last) / 2;
         MergeHelper(unsortedArray, first, mid, comparisons);
         MergeHelper(unsortedArray,mid+1,last, comparisons);
         Merge(unsortedArray,first,mid,last, comparisons);
      }
   }
   
   /* Takes in an unsorted array. first int of that array, last int of that array
      and number of current comparisons that have been made and then creates temporary arrays
      for each half of original array and then sorts the arrays */
   public static void Merge(int [] unsortedArray, int first, int mid,
                            int last, int[] comparisons) {
      //creates a temporary left and right array with last element being int's max value
      int leftSize = mid - first + 1;
      int rightSize = last - mid;
      int leftArray[] = new int [leftSize + 1]; 
      int rightArray[] = new int [rightSize + 1]; 
      for (int i = 0; i < leftSize; i++) {
         leftArray[i] = unsortedArray[first + i];
      }
      for (int i = 0; i < rightSize; i++) {
         rightArray[i] = unsortedArray[mid + 1 + i];
      }
      //uses ints max value in place of infinity into last spot of arrays
      leftArray[leftSize] = Integer.MAX_VALUE; 
      rightArray[rightSize] = Integer.MAX_VALUE;
      int i = 0;
      int j = 0;
      //sorts temporary arrays
      for (int k = first; k <= last; k++) {
         comparisons[0]++;
         if (leftArray[i] <= rightArray[j]) {
            unsortedArray[k] = leftArray[i];
            i++;
         } else {
            unsortedArray[k] = rightArray[j];
            j++;
         }
      }   
   }
   
   /* Takes in an array and then passes it on to quick sort function with additional
      information and collects information you will need to print */
   public static void quickSort(int[] array) {
      int[] comparisons = new int[1];
      int[] unsorted = array.clone();
      quickHelper(array, 0, array.length - 1, comparisons); 
      printStats("quick", unsorted, comparisons[0], array);
   }
   
   /* Takes in an unsorted array. first int of that array, last int of that array
      and number of current comparisons that have been made and breaks the array
      in parts to sort. */
   public static void quickHelper(int[] unsortedArray, int first, int last, int[] comparisons) {
      if(first < last) {
         int index = partition(unsortedArray, first, last, comparisons);
         quickHelper(unsortedArray,first, index - 1, comparisons);
         quickHelper(unsortedArray, index + 1, last, comparisons);
      }
   }
   
   /* Takes in an unsorted array. first int of that array, last int of that array
      and number of current comparisons that have been made and  then moves through
      the area swapping if current spot is less then the pivot*/
   public static int partition(int[] unsortedArray, int first, int last, int[] comparisons) {
        int pivot = unsortedArray[last];
        int temp;
        int i = (first - 1);
        for (int j=first; j<last; j++) {
            //if current spot is smaller then pivot swap
            if (unsortedArray[j] < pivot) { 
                comparisons[0]++;
                i++; 
                temp = unsortedArray[j]; 
                unsortedArray[j] = unsortedArray[i]; 
                unsortedArray[i] = temp; 
            } 
        }
        //swap pivot
        temp = unsortedArray[last]; 
        unsortedArray[last] = unsortedArray[i + 1]; 
        unsortedArray[i+1] = temp;
        return i + 1;
   }
   
   // Takes in unsorted array and sorts it using insertion sort
   public static void insertionSort(int[] array) {
      int[] unsorted = array.clone();
      int len = array.length;
      int comparisons = 0;
      for(int i = 1; i < len; i++) {
         int j = i;;
         //swaps elements n with prevous element until all elements before n are sorted
         while ((j > 0) && (array[j - 1] > array[j])) {
            int temp = array[j - 1];
            array[j-1] = array[j];
            array[j] = temp;
            j--;
            comparisons++;
         }
      }
      printStats("insertion", unsorted, comparisons, array);
   }

   /* Takes in unsorted array and find how many digits the biggest number has
      and then runs counting sort from LSD to MSD */
   public static void radixSort(int[] array) {
      int[] unsorted = array.clone();
      int max = getMax(array);
      //runs counting sort on digits for each digit/decimal place  
      for (int digit = 1; max / digit > 0; digit = digit * 10)  {
         countingSort(array, digit);
      }
            //gets how many digits bigest number has
      printStats("radix", unsorted, 0, array);
    }
    
    //takes in unsorted array and current digit and then sorts
    public static void countingSort(int[] unsortedArray, int digit) {
      int length = unsortedArray.length; 
      //The max possible digit is 9 + 10 (to cover negetives) + 1 (due to 0-indexing);
      int[] count = new int[20];
      
      //count number of occurances of number
      for (int i = 0; i < length; i++) {
         count[((unsortedArray[i] / digit) % 10) + 10]++;
      }
      for (int i = 1; i < count.length; i++) {
         count[i] += count[i - 1];
      }
      //create sorted array
      int[] sortedArray = new int[length];
      for (int i = length - 1; i >= 0; i--) {
         sortedArray[count[(unsortedArray[i]/digit) % 10 + 10] - 1] = unsortedArray[i];
         count[((unsortedArray[i] / digit) % 10) + 10]--;
      }
      
      //Save into original array (.clone() did not work here for some reason)
      System.arraycopy(sortedArray, 0, unsortedArray, 0, sortedArray.length);
   }
   
   //Prints out array and returns it in string form 
   //Note: I used this over Array.toString due to my prefence in appearance
   public static String arrayPrint(int[] array) {
      String output = "" + array[0];
      for(int i = 1; i < array.length; i++) {
         output += " " + array[i];
      }
      return output;
   }
   
   //Takes in a array and finds biggest number in it and retyrns it 
   public static int getMax(int[] array) {
      int max = array[0];
      for (int i  = 1; i < array.length; i++) {
         if(array[i] > max) {
            max = array[i];
         }
      } 
      return max;
   }
   /* Takes name of sort, unsorted array (pre-sort), sorted array(post-sort),
      and number of comparisons name and prints them out to present to the user */
   public static void printStats(String sortName, int[] unsorted, int comparisons, int[] sorted) {
      System.out.println(sortName + " sort");
      System.out.println("============");
      //checks to see if length of array is under 20, prints array if true
      if(unsorted.length < 20) {
         System.out.println("Unsorted array: " + arrayPrint(unsorted));
         System.out.println("Sorted array: " + arrayPrint(sorted));
      }
      System.out.println("Num Comparisons: " + comparisons);
      System.out.println("");
   }
   
}
      
