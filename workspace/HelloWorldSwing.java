import javax.swing.*;    
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow; 
 

public class HelloWorldSwing {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        JButton button = new JButton("Press Me");
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(label);
        frame.getContentPane().add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
               label.setText("You Did It!");
            }
        
        });

        //  Container pane = gui.getContentPane();
        //  pane.setLayout(new FlowLayout(FlowLayout.RIGHT,30,30));
        //     pane.add(new JButton("1"));
        //     pane.add(new JButton("2"));
        //     pane.add(new JButton("3"));
        //     for(int i = 0; i < 20; i++) {
        //         pane.add(new JButton(Integer.toString(i)));
        //     }

        JPanel something = new JPanel();

        frame.getContentPane().removeAll();
        frame.getContentPane().add(something);
        JButton button2 = new JButton("Meh");
        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
               label.setText("why");
            }
        
        });
        JButton button3 = new JButton("Ye");
        button3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
               label.setText("Lie");
            }
        
        });
        JButton button4 = new JButton("Hi");
        button4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               
               label.setText("Bye");
            }
        
        });
        //Display the window.
        
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
