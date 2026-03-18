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
	    JButton b=new JButton("Click Here");  
	    JLabel example = new JLabel(Double.toString(Math.PI));
	    f.add(example);
	    f.add(b);  
	    f.setSize(300,300);  
	    f.setLayout(new GridLayout(4, 1));  
	    f.setVisible(true);    
		calcThread something = new calcThread();
		something.start();
		something.running = true;
		something.notify();
	}  
}
