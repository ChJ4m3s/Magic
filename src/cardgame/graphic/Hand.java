package cardgame.graphic;

import cardgame.Card;
import cardgame.CardGame;
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

public class Hand extends JPanel {
    
    private class CardMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel j = (JLabel) e.getSource();
            int i = 0;
            for (JLabel s : cards) {
                if (j == s) {
                    if (!obscured[i])
                        CardGame.instance.mainF.getSelector().lightCard(j, true);
                    break;
                }
                i++;
            } 
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel j = (JLabel) e.getSource();
            CardGame.instance.mainF.zoomCard(j.getName());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            CardGame.instance.mainF.relaseZoom();
        }
        
    }
    
    static List<JLabel> cards;
    Images i;
    boolean obscured[];    
    
    public Hand(Images images) {
        i = images;
        initComponents(); 
    }
    
    private void initComponents() {
        /*
        Set JPanel proprieties.
        */
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.lightGray);
        this.add(Box.createVerticalStrut(116));
        /*
        Init component.
        */
        cards = new ArrayList();
    }
    
    /**
     * Called by MainFrame.
     * Show player's p hand.
     * @param p is current player.
     */
    public void setHand(Player p) {      
        for (JLabel l : cards) 
            this.remove(l);
        cards = new ArrayList();
        List<Card> currCards = p.getHand();
        for(Card c : currCards) {
            if (i.images.containsKey(c.name())) {
                JLabel aux = new JLabel(i.images.get(c.name()));
                aux.setName(c.name());
                aux.addMouseListener(new CardMouseListener());
                cards.add(aux);
            }
        }
        for (JLabel l : cards) 
            this.add(l);
        obscured = new boolean[cards.size()];
        for (boolean b : obscured)
            b = false;
        this.repaint();
    }

    /**
     * Called by setHand.
     * Add a card name to the list.
     * @param s is card's name.
     */
    private void addList(String s) {
        if (i.images.containsKey(s))
            cards.add(new JLabel(i.images.get(s)));
    }
    
    
    /**
     * Obscure non instant cards.
     */
    public void obscure(Player p, boolean obscure[]) {
        for (int i = 0; i < obscure.length; i++) {
            if (obscure[i]) {
                JLabel l = cards.get(i);
                l.setIcon(this.i.images.get("Dark" + l.getName()));
                obscured[i] = true;
            }
        }
    }
    
    /**
     * Undo obscuration.
     */
    public void redoObscure() {
        int i = 0;
        for (JLabel j : cards) {
            j.setIcon(this.i.images.get(j.getName()));
            obscured[i] = false;
        }
    }
    
}
