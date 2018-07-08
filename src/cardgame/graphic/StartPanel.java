package cardgame.graphic;

import cardgame.CardGame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class StartPanel extends JPanel {
    
    private class NameListener implements MouseListener {

        @Override
        public synchronized void mouseClicked(MouseEvent e) {
            String namePlayer1 = text1.getText();
            String namePlayer2 = text2.getText();
            if (!namePlayer1.equals("") && !namePlayer2.equals("")) {
                CardGame.instance.getPlayer(0).setName(namePlayer1);
                CardGame.instance.getPlayer(1).setName(namePlayer2);  
                change();
            }
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
    
    private class DeckListener implements MouseListener {

        @Override
        public synchronized void mouseClicked(MouseEvent e) {
            if (insertedDeck1  && insertedDeck2) {
                CardGame.instance.setDeck(deck1, deck2);
                inserted();
            }
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
    
    private class ChooseDefault implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == autom1) {
                deck1 = null;
                insertedDeck1 = true;
            }
            
                
            if (e.getSource() == autom2) {
                deck2 = null;
                insertedDeck2 = true;
            }
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
    
    private class ChooseListener implements MouseListener {
        
        @Override
        public void mouseClicked(MouseEvent e) {
            frame = new JFrame();
            
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setVisible(true);

            int choosen = chooser.showOpenDialog(frame);  
            if (choosen == JFileChooser.APPROVE_OPTION) {
                if (e.getSource() == choose1) {
                    insertedDeck1 = true;
                    deck1 = chooser.getSelectedFile();
                }
                else {
                    insertedDeck2 = true;
                    deck2 = chooser.getSelectedFile();
                }
            }
                
            frame.setVisible(false);
            
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
    
    public StartPanel() throws InterruptedException {
        initComponents();
    }
    
    JLabel title;
    JLabel player1;
    JLabel player2;
    JTextField text1;
    JTextField text2;
    JButton confirm;
    JPanel center;
    JPanel row1;
    JPanel row2;
    JPanel bottom;
    NameListener l1;
    JButton choose1;
    JButton choose2;
    JButton autom1;
    JButton autom2;
    JFileChooser chooser;
    JFrame frame;
    File deck1;
    File deck2;
    boolean insertedDeck1;
    boolean insertedDeck2;
    
    private void initComponents() throws InterruptedException {
        /*
        setting JFrame proprieties.
        */
        this.setLayout(new BorderLayout());
        /*
        setting components.
        */
        title = new JLabel("~ Magic ~", SwingConstants.CENTER);
        title.setFont(new Font("Lucida Console", Font.BOLD, 40));
        
        player1 = new JLabel("Player1:");
        player1.setFont(new Font("Lucida Console", Font.BOLD, 15));
        
        player2 = new JLabel("Player2:");
        player2.setFont(new Font("Lucida Console", Font.BOLD, 15));
        
        text1 = new JTextField();
        text1.setText("");
        text1.setColumns(15);
        
        text2 = new JTextField();
        text2.setText("");
        text2.setColumns(15);
        
        center = new JPanel();
        center.setLayout(new GridLayout(4, 0));
        
        row1 = new JPanel();
        row1.setLayout(new FlowLayout());
        row1.add(Box.createHorizontalStrut(5));
        row1.add(player1);
        row1.add(Box.createHorizontalStrut(10));
        row1.add(text1);
        row1.add(Box.createHorizontalStrut(5));
        
        row2 = new JPanel();
        row2.setLayout(new FlowLayout());
        row2.add(Box.createHorizontalStrut(5));
        row2.add(player2);
        row2.add(Box.createHorizontalStrut(10));
        row2.add(text2);
        row2.add(Box.createHorizontalStrut(5));
        
        l1 = new NameListener();
        confirm = new JButton("confirm");
        confirm.addMouseListener(l1);
        
        center.add(Box.createVerticalStrut(5));
        center.add(row1);
        center.add(row2);
        
        bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.add(confirm);
        
        /*
        adding components
        */
        this.add(title, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH, SwingConstants.CENTER);
    }
    
    boolean inserted = false;
    
    synchronized void waitButton() throws InterruptedException {
        while (!inserted)
            wait();
    }
    
    void change() {
        chooser = new JFileChooser();
        inserted = false;
        title.setText("Select deck");
        
        autom1 = new JButton("default");
        autom1.addMouseListener(new ChooseDefault());
        choose1 = new JButton("choose file");
        choose1.addMouseListener(new ChooseListener());
        row1.remove(text1);
        row1.add(choose1);
        row1.add(autom1);
        
        autom2 = new JButton("default");
        autom2.addMouseListener(new ChooseDefault());
        choose2 = new JButton("choose file");
        choose2.addMouseListener(new ChooseListener());
        row2.remove(text2);
        row2.add(choose2);
        row2.add(autom2);
        
        confirm.removeMouseListener(l1);
        confirm.addMouseListener(new DeckListener());  
    }
    
    synchronized void inserted() {
        inserted = true;
        notifyAll();
    }
    
}