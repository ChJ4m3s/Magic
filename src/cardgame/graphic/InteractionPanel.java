package cardgame.graphic;

import cardgame.Card;
import cardgame.CardGame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class InteractionPanel extends JPanel {
    
    private class InteractionMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            relase();
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
    
    private class PassMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (CardGame.instance.mainF.getSelector().haveToChoose) 
                CardGame.instance.mainF.getSelector().pass();
            relase();
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
    
    JButton interaction;
    JButton pass;
    boolean pressed = false;
    Card choosen;
    
    public InteractionPanel() {
        initComponents();
    }
    
    private void initComponents() { 
        /*
        Set JPanel proprieties.
        */
        this.setLayout(new GridLayout(2, 0));
        this.setBackground(Color.lightGray);
        /*
        Init components.
        */
        interaction = new JButton();    
        pass = new JButton("pass");
        pass.setVisible(false);
        interaction.addMouseListener(new InteractionMouseListener());
        pass.addMouseListener(new PassMouseListener());
        choosen = null;
        /*
        Add components.
        */
        this.add(interaction);   
        this.add(pass);
    }
    
    /**
     * Called by MainFrame.
     * Set button's name.
     * @param s is button's name.
     * @param pass is true if the pass button has to be shown.
     */
    public void updateButton(String s, boolean pass) {
        interaction.setText(s);
        this.pass.setVisible(pass);
    }
    
    /**
     * Notify that a button is clicked.
     */
    synchronized private void relase() {
        pressed = true;
        pass.setVisible(false);
        notifyAll();
    }
    
    /**
     * Wait that a button is clicked.
     * @throws InterruptedException 
     */
    synchronized public void waitPressed() throws InterruptedException {
        while (!pressed)
            wait();
        pressed = false;
    }
}