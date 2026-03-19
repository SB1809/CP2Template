package Pi;

public class calcThread extends Thread{


double inside = 0.0;
double total = 0.0;
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
            double x = Math.random();
            double y = Math.random();
            
            if(x*x + y*y <= 1) {
            	inside++;
            }
            total++;
            double estimate = (double) 4 * (inside / total);
            System.out.println("Current estimate of pi: " + estimate);
    }

        

	}
}
