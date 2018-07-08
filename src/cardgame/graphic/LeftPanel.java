package cardgame.graphic;

import cardgame.graphic.images.Images;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeftPanel extends JPanel {
    
    JLabel card;
    Images i;
    boolean locked;
    ImageIcon last;
    
    public LeftPanel(Images images) {
        i = images;
        initComponents();
    }
    
    private void initComponents() {
        /*
        Set JPanel proprieties.
        */
        this.setLayout(new FlowLayout());
        this.setBackground(Color.LIGHT_GRAY);
        /*
        Init components.
        */
        locked = false;
        if (i.images.containsKey("BigNull"))
            card = new JLabel(i.images.get("BigNull"));
        /*
        Add NullCard.
        */
        this.add(card);
    }
    
    /**
     * Show the card.
     * @param s is the name of the card.
     */
    public void addCard(String s) {
        if (!locked)
            last = i.images.get("Big" + s);
        if (i.images.containsKey("Big" + s)) {
            card.setIcon(i.images.get("Big" + s));
        }
    }
 
    /**
     * Delete previous card and add NullCard.
     */
    public void removeCard() {
        if (locked)
            card.setIcon(last);
        else {
            if (i.images.containsKey("BigNull")) {
                card.setIcon(i.images.get("BigNull"));
            }
        }
    }
    
    /**
     * Set the card showed locked.
     */
    public void lock() {
        locked = true;
    }
    
    /**
     * Unlock showed card.
     */
    public void unlock() {
        locked = false;
        removeCard();
    }
    
}
