package cardgame.graphic;

import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Player;
import cardgame.graphic.images.Images;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FieldPanel extends JPanel {
        
    private class CardMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (activeChoose) {
                JLabel l = (JLabel) e.getSource();
                CardGame.instance.mainF.getSelector().lightCard(l, false);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel l = (JLabel) e.getSource();
            CardGame.instance.mainF.zoomCard(l.getName());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            CardGame.instance.mainF.relaseZoom();
        }
        
    }
    
    List<JLabel> inserted;
    Images i;
    boolean activeChoose;
    boolean obscured[];
    
    FieldPanel(Images image) {
        i = image;
        initComponents();
    }

    private void initComponents() {
        /*
        Set JPanel proprieties.
        */
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.GRAY);
        /*
        Init component.
        */
        activeChoose = false;
        inserted = new ArrayList();
        /*
        Add a vertical space at the panel.
        */
        this.add(Box.createVerticalStrut(116));
    }
    
    /**
     * Called by setField.
     * Add a card at the field.
     * @param s is the card's name.
     */
    void addCard(String s, boolean obscured) {
        JLabel aux = null;
        if (i.images.containsKey(s)) {
            if (!obscured)
                aux = new JLabel(i.images.get(s));
            else
                aux = new JLabel(i.images.get("Dark" + s));
            aux.setName(s);
            aux.addMouseListener(new CardMouseListener());
            inserted.add(aux);
            this.add(aux);
        }
    }
    
    /**
     * Called by Field.
     * Update the field.
     * @param p is player p's field.
     */
    void setField(Player p) {
        this.removeAll();
        this.inserted = new ArrayList<JLabel>();
        for (Creature c : p.getCreatures()) 
            if (!c.isTapped())
                addCard(c.name(), false);
            else
                addCard(c.name(), true);
    }
    
    /**
     * Called by Field.
     * Obscure not playable cards.
     * @param obscured 
     */
    public void obscure(boolean[] obscured) {
        this.obscured = obscured;
        for (int i = 0; i < obscured.length; i++)
            if (obscured[i]) {
                JLabel l = inserted.get(i);
                l.setIcon(this.i.images.get("Dark" + l.getName()));
            }
    }
    
    
}
