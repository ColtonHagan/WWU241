import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
public class Airplane {
   private String flightNum;
   private String from;
   private String to;
   private String time;
   private int numPassengers;
   private int inputPrio;
   private boolean requested;
   public Airplane(String flightNum, String from, String to, String time, int numPassengers, int inputPrio) {
      this.flightNum = flightNum;
      this.from = from;
      this.to = to;
      this.time = time;
      this.numPassengers = numPassengers;
      this.inputPrio = inputPrio;
      this.requested = false;
   }
   public boolean getRequested() {
      return requested;
   }
   public void changeRequestToTrue() {
      this.requested = true;
   }
   public String getFlightNum() {
      return flightNum;
   }
   public String getFrom() {
      return from;
   }
   public String getTo() {
      return to;
   }
   public void addTime(int minutes) {
      DateTimeFormatter timeFormator = DateTimeFormatter.ofPattern("hh:mm");
      LocalTime updatedTime = LocalTime.parse(time);
      time = timeFormator.format(updatedTime.plusMinutes(minutes));
   }
   public int timeInMinutes() {
      if(time == "filler") {
         return Integer.MAX_VALUE;
      }
      int minutes = Integer.parseInt(time.substring(0,2)) * 60 + Integer.parseInt(time.substring(3,5));
      int hour = Integer.parseInt(time.substring(0,2)) * 60;
      return minutes;
   }
   public String getTime() {
      return time;
   }
   
   public void changeTime(String newTime) {
      time = newTime;
   }
   public int getNumPassengers() {
      return numPassengers;
   }
   public int getInputPrio() {
      return inputPrio;
   }
   public void printFlight() {
      System.out.println(flightNum + " " + from + " " + to + " " + time + " " + numPassengers);
   }
}