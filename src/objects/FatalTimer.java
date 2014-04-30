package objects;

import javax.swing.Timer;

/**
 * Counts down the seconds left in a match until the time is up.
 * 
 * @author Marcos Davila
 *
 */
public class FatalTimer {
  
  private Timer timer;
  private int DELAY = 1000;
  private int secondsInMatch = 60;

  public FatalTimer(){
     timer = new Timer(DELAY, e -> {
    	 System.out.println(secondsInMatch);
         
         secondsInMatch--;

         if(isTimeToStop()){
            stop();
         }
     });
  }

  public void start(){
     timer.start();
  }

  public void stop(){
     timer.stop();
  }

  public boolean isTimeToStop(){
     return (secondsInMatch == 0) ? true : false;
  }
}