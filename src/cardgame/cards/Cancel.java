package cardgame.cards;

import cardgame.*;
import cardgame.graphic.ChooseFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cancel extends AbstractCard {
    static private final String cardName = "Cancel";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Cancel(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
    public String ruleText() { return "Counter target spell"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
    private class CancelEffect extends AbstractCardEffect implements TargetingEffect {
        Effect target;
        
        public CancelEffect(Player p, Card c) { super(p,c); }
        public boolean play() {
            pickTarget();
            return super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [Counter " + target.name() + "]";
        }
        
        public void pickTarget() {
            List<String> names = new ArrayList<String>();
            CardStack stack = CardGame.instance.getStack();

            for (Effect e:stack) 
                names.add(e.name());
            
            ChooseFrame f = new ChooseFrame(names);
            
            int idx = 0;
            try {
                idx = f.selected();
            } catch (InterruptedException ex) {
                Logger.getLogger(Cancel.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (idx<0 || idx>=stack.size()) target=null;
            else target=stack.get(idx);
        }
        
        public void resolve() {
            if (target==null) {
                System.out.println(cardName + " has no target");
            } else if (CardGame.instance.getStack().indexOf(target)==-1) {
                System.out.println(cardName + " target is not on the stack anymore");
            } else {
                System.out.println(cardName + " removing " + target.name() + " from stack");
                CardGame.instance.getStack().remove(target);
            }
        }
    }

    public Effect getEffect(Player owner) { 
        return new CancelEffect(owner, this); 
    }
}

