package Pi;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PiEstimator{
//the following code is just to jog your memory about how labels and buttons work!
//implement your Pi Estimator as described in the project. You may do it all in main below or you 
//may implement additional functions if you feel it necessary.
//An easy application for our project would be something like this inside your run() method for your thread:


	
public static void main(String[] args) {  
	    JFrame f=new JFrame("Button Example");  
	    JButton button=new JButton("Start");  
	    JLabel example = new JLabel(Double.toString(Math.PI));
		calcThread something = new calcThread();
		something.start();
		JLabel mine = new JLabel("Estimae: ");
		JLabel trial = new JLabel("Trials: ");
	    f.add(example);
	    f.add(mine);
	    f.add(trial);
	   
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(button.getText().equals("Start")) {
					button.setText("Stop");
					synchronized(something) {
						something.running = true;
						something.notify();
					
					}
				}
				else {
					something.running = false;
					button.setText("Start");
				}
			}
		}); 
		 f.add(button);  
	    f.setSize(300,300);  
	    f.setLayout(new GridLayout(4, 1));  
	    f.setVisible(true);  
	
		while(true) {
	
				mine.setText("Estimate: "+Double.toString(something.estimate));
				trial.setText("Trials: "+Double.toString(something.total));
			
		}
	  
}
}
