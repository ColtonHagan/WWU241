/*
Author: Colton Hagan
Class: CS 241
Date: 3/12/2020
Purpose: Airplane object that keeps take of flight number, takeoff request time,
         and number if passengers. Made for heap.
*/
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
public class Airplane {
   private String flightNum;
   private String time;
   private int timeInMinutes;
   private int numPassengers;
   //If time has passed requested takeoff tiem
   private boolean requested;
   //Insializes values
   public Airplane(String flightNum, String time, int numPassengers) {
      this.flightNum = flightNum;
      this.time = time;
      this.numPassengers = numPassengers;
      //Calculates time in minutes
      timeInMinutes();
      //At first no takeoff times have passed/ are up next
      requested = false;
   }
   //Converts and returns time in file to minutes
   private void timeInMinutes() {
      //If index 0, put to max value so it will be put down on the heap as low as possible
      if(time == "filler") {
         timeInMinutes = Integer.MAX_VALUE;
      //Coverts time from hh:mm to just minutes
      } else {
         String fixedTime = hourFixer();
         //fixes 12pm < 1pm problem
         if(!fixedTime.substring(0,2).equals("12")) {
            timeInMinutes += 720;
         }
         int inMinutes = Integer.parseInt(fixedTime.substring(0,2)) * 60 
                         + Integer.parseInt(fixedTime.substring(3,5));
         timeInMinutes += inMinutes;
      }
   }
   
   /*"fixes" time by adding a 0 in front if hour is 1 through 9 to allow localTime 
     and timeInMinutes to correctly parses*/
   private String hourFixer() {
      String fixedTime = time;
      if(time.length() == 4) {
         fixedTime = "0" + fixedTime;
      }
      return fixedTime;
   }
   //Adds given time(in minutes) to requested takeoff time
   public void addTime(int minutes) {
      DateTimeFormatter timeFormator = DateTimeFormatter.ofPattern("hh:mm");
      LocalTime updatedTime = LocalTime.parse(hourFixer());
      String oldTime = time;
      String newTime = timeFormator.format(updatedTime.plusMinutes(minutes));
      //Deals with swapping from pm to am
      if(oldTime.substring(0,2).equals("11") && !newTime.substring(0,2).equals("11")) {
         timeInMinutes += 720;
      }
      time = newTime;
      //Removes hourfixer fix
      if(time.substring(0,1).equals("0")) {
          time = time.substring(1,5);
      }
   }
   //Prints out flight in diffrent ways, depending on a given way to do so
   public void printFlight(int method) {
      System.out.print(flightNum);
      if(method == 2) {
         System.out.print(" " + numPassengers);
      } else if(method == 3) {
         System.out.print(" departed at " + time);
      }
      System.out.println();
   }
   //Returns if the plane has passed its requested takeoff time
   public boolean getRequested() {
      return requested;
   }
   //Changes if it has passed requested takeoff time
   public void changeRequestToTrue() {
      this.requested = true;
   }
   //Returns flightNum of plane
   public String getFlightNum() {
      return flightNum;
   }
   //Returns time in requested takeoff time minutes of plane
   public int getTimeInMinutes() {
      return timeInMinutes;
   }
   //Returns requested takeoff time in hh:mm of plane
   public String getTime() {
      return time;
   }
   //Changes time to a given string
   public void changeTime(String newTime) {
      time = newTime;
   }
   //Returns number of passengers on flight
   public int getNumPassengers() {
      return numPassengers;
   }
}