package cardgame.graphic;

import cardgame.CardGame;
import cardgame.Player;
import cardgame.graphic.images.Images;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Field extends JPanel {   
    
    FieldPanel view[];
    JScrollPane scroll[];
    Images i;
    Player p1;
    Player p2;
    
    public Field(Images images) {
        i = images;
        initComponents();
    }
 
    private void initComponents() {
        /*
        Set JPanel proprieties.
        */
        this.setLayout(new GridLayout(2, 0));
        /*
        Init components.
        */
        view = new FieldPanel[2];
        view[0] = new FieldPanel(i);
        view[1] = new FieldPanel(i);
        scroll = new JScrollPane[2];
        scroll[0] = new JScrollPane(view[0]);
        scroll[0].setBackground(Color.BLACK);
        scroll[1] = new JScrollPane(view[1]);
        scroll[1].setBackground(Color.BLACK);
        
        /*
        Add components to the panel.
        */
        this.add(scroll[0]);
        this.add(scroll[1]);
    }
    
    /**
     * Called by MainFrame.
     * Udate the Field.
     * @param p is current player.
     */
    public void updateField(Player p) {
        p1 = p;
        p2 = (CardGame.instance.getPlayer(0).equals(p))? CardGame.instance.getPlayer(1) 
                : CardGame.instance.getPlayer(0);
        view[0].setField(p2);
        view[0].repaint();
        view[1].setField(p1);
        view[1].repaint();
    }
    
    /**
     * User can choose a card from active plyaer field.
     */
    public void activeFieldToChoose() {
        view[1].activeChoose = true;
    }
    
    /**
     * User now can't choose from Field.
     */
    public void disactiveFieldToChoose() {
        view[1].activeChoose = false;
    }
    
    public boolean isFieldActived() {
        return view[1].activeChoose;
    }
    
    /**
     * Obscure creatures in the selected field.
     * @param obscured
     * @param i is 1 for current player, 0 for the other one.
     */
    public void obscure(boolean obscured[], int i) {
        view[i].obscure(obscured);
    }
    
    public List<JLabel> getInserted() {
        return view[1].inserted;
    }
    
}
