package cardgame.graphic;

import java.awt.FlowLayout;
import javax.swing.JFrame;

public class StartScreen extends JFrame {
    
    StartPanel start;
    
    
    public StartScreen() throws InterruptedException {
        initComponents();
    }
    
    final void initComponents() throws InterruptedException {
        /*
        setting JFrame proprieties.
        */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("Magic start screen");   
        //this.setResizable(false);
        this.setLocation(480, 200);
        /*
        initi panel and adding to the JFrame.
        */
        start = new StartPanel();
        this.add(start);
        /*
        setting JFrame visible.
        */
        this.pack();
        this.setVisible(true);
        
        start.waitButton();
    }
    
}
