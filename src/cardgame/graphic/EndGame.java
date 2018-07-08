package cardgame.graphic;

import cardgame.CardGame;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EndGame extends JFrame{
    
    private class MouseListenerButton implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.exit(0);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        
    }
    
    JButton button;
    JLabel label;
    
    public EndGame(String s) {
        CardGame.instance.mainF.setVisible(false);
        initComponents(s);
    }
    
    private void initComponents(String s) {
        this.setLayout(new FlowLayout());
        this.setLocation(480, 200);
        
        button = new JButton("New Game!");
        label = new JLabel(s);
        button.addMouseListener(new MouseListenerButton());
        
        this.add(label);
        this.add(button);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    
}
