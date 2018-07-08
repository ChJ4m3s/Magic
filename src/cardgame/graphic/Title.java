package cardgame.graphic;

import cardgame.CardGame;
import cardgame.Player;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Title extends JPanel {
    
    JLabel phase;
    JLabel player;
    JPanel pointLife;
    JLabel lifePlayer[];
    
    public Title() {
        initComponents();
    }
    
    private void initComponents() {
        /*
        Create new Fonts.
        */
        Font playerFont = new Font("Lucida Console", Font.BOLD, 15);
        Font phaseFont = new Font("Lucida Console", Font.BOLD, 20);
        /*
        Setting JPanel proprieties.
        */
        this.setLayout(new GridLayout(3, 0));
        this.setBackground(Color.lightGray);
        /*
        Initialing components.
        */
        phase = new JLabel("");
        phase.setForeground(Color.DARK_GRAY);
        phase.setFont(phaseFont);
        
        player = new JLabel("");
        player.setForeground(Color.DARK_GRAY);
        player.setFont(playerFont);
        
        lifePlayer = new JLabel[2];
        lifePlayer[0] = new JLabel();
        lifePlayer[0].setText(CardGame.instance.getPlayer(0).name() + ": " + 
                CardGame.instance.getPlayer(0).getLife());
        lifePlayer[0].setForeground(Color.DARK_GRAY);
        lifePlayer[1] = new JLabel();
        lifePlayer[1].setText(CardGame.instance.getPlayer(1).name() + ": " + 
                CardGame.instance.getPlayer(1).getLife());
        lifePlayer[1].setForeground(Color.DARK_GRAY);
        
        pointLife = new JPanel();
        pointLife.setLayout(new FlowLayout());
        pointLife.add(lifePlayer[0]);
        pointLife.add(Box.createHorizontalStrut(10));
        pointLife.add(lifePlayer[1]);
        pointLife.setBackground(Color.lightGray);
        /*
        Adding components.
        */
        this.add(phase);
        this.add(player);
        this.add(pointLife);
    }

    /**
     * Called by MainFrame.
     * Set player name.
     * @param s is current player.
     */
    void setPlayer(String s) {
        player.setText(s);
    }

    /**
     * Called by MainFrame.
     * Set phase name.
     * @param s is current phase.
     */
    void setPhase(String s) {
        phase.setText(s);
    }
    
    /**
     * Called by MainFrame.
     * Update player's p life showed.
     * @param player is the index of the player.
     */
    void updateLife(int player) {
        Player p = CardGame.instance.getPlayer(player);
        lifePlayer[player].setText(p.name() + ": " + p.getLife());
    }
    
}