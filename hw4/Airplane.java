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
   //How many times has a time hit midnight (for series of large number of passengers)
   private int hitMidnight;
   //Insializes values
   public Airplane(String flightNum, String time, int numPassengers) {
      this.flightNum = flightNum;
      this.time = time;
      //Adds a 0 to add 1-9 for calculations
      if(time.length() == 4) {
         this.time = "0" + time;
      }
      //Calculates time in minutes
      timeInMinutes();
      //At first no takeoff times have passed/ are up next
      requested = false;
      this.numPassengers = numPassengers;
   }
   //Converts and returns time in file to minutes
   private void timeInMinutes() {
      //If index 0, put to max value so it will be put down on the heap as low as possible
      if(time == "filler") {
         timeInMinutes = Integer.MAX_VALUE;
      //Coverts time from hh:mm to just minutes
      } else {
         timeInMinutes = Integer.parseInt(time.substring(0,2)) * 60 
                         + Integer.parseInt(time.substring(3,5));
         //fixes 12pm < 1pm problem
         if(!time.substring(0,2).equals("12")) {
            timeInMinutes += 720;
         }
         //adds time if it has hit midnight
         timeInMinutes += 720*hitMidnight;
      }
   }
   //Adds given time(in minutes) to requested takeoff time
   public void addTime(int minutes) {
      DateTimeFormatter timeFormator = DateTimeFormatter.ofPattern("hh:mm");
      LocalTime updatedTime = LocalTime.parse(time);
      //Checks with swapping from pm to am
      String oldTime = time;
      time = timeFormator.format(updatedTime.plusMinutes(minutes));
      //Swaps pm to pm
      if(oldTime.substring(0,2).equals("11") && !time.substring(0,2).equals("11")) {
         hitMidnight += 1;
      } 
      timeInMinutes();
   }
   //Prints out flight in diffrent ways, depending on a given way to do so
   public void printFlight(int method) {
      System.out.print(flightNum);
      if(method == 2) {
         System.out.print(" " + numPassengers);
      } else if(method == 3) {
         //Removes the extra 0 I used for calculations
         if(time.substring(0,1).equals("0")) {
            time = time.substring(1,5);
         }
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
      requested = true;
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
      timeInMinutes();
   }
   //If called changes the time from pm to am
   public void passedMidnight(int daysPassed) {
      hitMidnight = daysPassed;
   }
   //Returns if the time has passed midnight (gone from pm to am)
   public int getHitMidnight() {
      return hitMidnight;
   }
   //Returns number of passengers on flight
   public int getNumPassengers() {
      return numPassengers;
   }
}
