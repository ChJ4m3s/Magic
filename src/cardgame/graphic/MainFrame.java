package cardgame.graphic;

import cardgame.CardGame;
import cardgame.Player;
import cardgame.graphic.images.Images;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
    
    Field field;
    Title titleGame;
    Hand hand;
    InteractionPanel interaction;
    LeftPanel left;
    SelectCard selector;
    
    Images images;
    
    public MainFrame() {
        initComponents();
    }
    
    private void initComponents() {
        /* 
        setting of the JFrame proprieties.
        */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle("~ MAGIC ~");
        this.setLocation(230, 100);
        this.setSize(new Dimension(820, 520));
        this.setResizable(false);    
        /*
        initializing components.
        */
        images = new Images();
        field = new Field(images);
        titleGame = new Title();
        hand = new Hand(images);
        interaction = new InteractionPanel();
        left = new LeftPanel(images);
        selector = new SelectCard(images);
        /*
        adding components.
        */
        this.add(titleGame, BorderLayout.NORTH);
        this.add(field, BorderLayout.CENTER);
        this.add(hand, BorderLayout.SOUTH);
        this.add(interaction, BorderLayout.EAST);
        this.add(left, BorderLayout.WEST);
        /*
        set the JFrame visible
        */
        this.setVisible(true);
    }
    
    
    /**
     * Update frame title.
     * @param player is the name of the current player.
     * @param phase is the name of the current phase.
     */
    public void updateTitle(String player, String phase) {
        titleGame.setPlayer(player);
        titleGame.setPhase(phase);
    }
    
    /**
     * Update the text of the main botton
     * @param s is the name to give to the button.
     * @param pass is true if we need 'paass' button.
     * @param wait is true if the game has to wait the button to be clicked.
     * @throws InterruptedException
     */
    public void updateButton(String s, boolean pass, boolean wait) throws InterruptedException {
        interaction.updateButton(s, pass);
        if (wait)
            interaction.waitPressed();
    }
    
    /**
     * Display the hand of player p.
     * @param p
     */
    public void updateHand(Player p) {
        hand.setHand(p);
    }
    
    /**
     * Display in the left panel the card which name is s.
     * @param s
     */
    public void zoomCard(String s) {
        left.addCard(s);
    }
    
    /**
     * Delete card from left panel.
     */
    public void relaseZoom() {
        left.removeCard();
    }
    
    /**
     *
     * @return input selector.
     */
    public SelectCard getSelector() {
        return selector;
    }
    
    /**
     * Update the cards on the field.
     * @param p is current player.
     */
    public void updateField(Player p) {
        field.updateField(p);
    }
    
    /**
     * Update player p life in the titles of the game.
     * @param p 
     */
    public void updateLife(Player p) {
        int player = (p.equals(CardGame.instance.getPlayer(0)))? 0 : 1;
        titleGame.updateLife(player);
    }
    
    /**
     * Update the field and show the hand of player p.
     * @param p is current player.
     */
    public void updateAll(Player p) {
        updateField(p);
        updateHand(p);
    }
    
    public Field getField() {
        return field;
    }
    
    public Hand getHand() {
        return hand;
    }
    
}
