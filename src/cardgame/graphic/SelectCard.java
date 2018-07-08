package cardgame.graphic;

import cardgame.Card;
import cardgame.CardGame;
import cardgame.Creature;
import cardgame.Effect;
import cardgame.Player;
import cardgame.graphic.images.Images;
import java.util.List;
import javax.swing.JLabel;

public class SelectCard  {
     
    boolean choosen;
    boolean haveToChoose;
    boolean isHand;
    boolean pass;
    JLabel last;
    Images i;

    SelectCard(Images images) {
        i = images;
    }
    
    /**
     * Play choosen effect or pass the turn.
     * @param p
     * @param isMain
     * @return true if Effect is played, false is player pass the turn.
     * @throws InterruptedException 
     */
    public boolean choose(Player p, boolean isMain) throws InterruptedException {
        choosen = false;
        last = null;
        haveToChoose = true;
        CardGame.instance.mainF.field.activeFieldToChoose();
        pass = false;
        
        CardGame.instance.mainF.updateButton("Play", true, false);
        
        
        if (!isMain) {
            boolean obscured[] = new boolean[p.getHand().size()];
            for (int i = 0; i < p.getHand().size(); i++)
                if (!p.getHand().get(i).isInstant())
                    obscured[i] = true;
            CardGame.instance.mainF.hand.obscure(p, obscured);
        }
             
        CardGame.instance.mainF.interaction.waitPressed();
        
        if (!isMain)
            CardGame.instance.mainF.hand.redoObscure();
        
        haveToChoose = false;
        CardGame.instance.mainF.field.disactiveFieldToChoose();
                 
        if (choosen) {
            if (isHand) {
                for (Card c : p.getHand()) 
                    if (c.name().equals(last.getName())) {
                        if (!isMain && !c.isInstant()) {                           
                            resetLabel();
                            return choose(p, isMain);
                        }
                        c.getEffect(p).play();
                        CardGame.instance.mainF.updateHand(p);
                        break;
                    }
            }
            else {
                for (Creature c : p.getCreatures()) 
                    if (c.name().equals(last.getName())) {
                        for (Effect e : c.avaliableEffects())
                            e.play();
                        CardGame.instance.mainF.updateField(p);
                        break;
                    }
            }
            CardGame.instance.mainF.left.unlock();
            return true;
        }
        if (pass) {
            CardGame.instance.mainF.left.unlock();
            return false;
        }
        return choose(p, isMain);
    }
    
    /**
     * Make label's icon not more bright.
     */
    public void resetLabel() {
        CardGame.instance.mainF.left.unlock();
        if (i.images.containsKey(last.getName()))
            last.setIcon(i.images.get(last.getName()));
    }
    
    /**
     * Called by Hand or Field.
     * Make label's card bright.
     * @param l is the label
     * @param isHand is true if the card is from player's hand.
     */
    public void lightCard(JLabel l, boolean isHand) {
        if (haveToChoose) {
            CardGame.instance.mainF.zoomCard(l.getName());
            CardGame.instance.mainF.left.lock();
            if (l == last) {
                choosen = false;
                resetLabel();
                last = null;
            }
            else {
                if (last != null)
                    resetLabel();
                last = l;
                choosen = true;
                this.isHand = isHand;

                if (i.images.containsKey("Sel" + l.getName()))
                    l.setIcon(i.images.get("Sel" + l.getName()));
            }
        }
        else {
            if (CardGame.instance.mainF.getField().isFieldActived()) {
                CardGame.instance.mainF.zoomCard(l.getName());
                if (l.getIcon() != i.images.get("Dark" + l.getName())) {
                    if (l.getIcon() != i.images.get(l.getName()))
                        l.setIcon(i.images.get(l.getName()));
                    else
                        l.setIcon(i.images.get("Sel" + l.getName()));
                }
            }
        }
    }
    
    /**
     * Called by Interaction.
     * Pass the turn.
     */
    public void pass() {
        CardGame.instance.mainF.left.unlock();
        pass = true;
        if (last != null) 
            resetLabel();
        last = null;
        choosen = false;
    }
    
    public boolean[] selectFromField() throws InterruptedException {
        CardGame.instance.mainF.field.activeFieldToChoose();
        List<JLabel> inserted = CardGame.instance.mainF.getField().getInserted();
        boolean choosen[] = new boolean[inserted.size()]; 
        int i = 0;
        
        CardGame.instance.mainF.interaction.waitPressed();
        
        for (JLabel j : inserted) {
            if (j.getIcon() == this.i.images.get("Sel" + j.getName()))
                choosen[i] = true;
            i++;
        }
        CardGame.instance.mainF.field.disactiveFieldToChoose();
        return choosen;
    }
    
    public boolean choose_discard(Player p, boolean isMain) throws InterruptedException {
        choosen = false;
        last = null;
        haveToChoose = true;
        pass = false;

        CardGame.instance.mainF.interaction.waitPressed();
        haveToChoose = false;

        if (choosen) {
            if (isHand) {
                for (Card c : p.getHand()) 
                    if (c.name().equals(last.getName())) {
                        p.getHand().remove(c);
                        break;
                    }
            }
            return true;
        }
        if (pass)
            return false;
        return choose(p, isMain);
    }
    
}