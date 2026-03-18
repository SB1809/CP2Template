package Pi;

public class calcThread extends Thread{
    
public boolean running = false; 
public void run() {
	while (true) {
        synchronized(this) {
        	while ( !running ) { // wait for running to be true
                try {
                    System.out.println("got here and paused");
                    wait();
                }
            	catch (InterruptedException e) {
                e.printStackTrace();}
            	}
			}
             System.out.println("Thread is running");
        }
       
		//rest of your code actually doing stuff here

	}
}
