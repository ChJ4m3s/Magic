package cardgame.cards;

import java.util.ArrayList;

import cardgame.*;
import cardgame.graphic.ChooseFrame;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Deflection extends AbstractCard {
    static private final String cardName = "Deflection";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Deflection(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
    public String ruleText() { return "Change the target of target spell with a single target"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
    private class DeflectionEffect extends AbstractCardEffect implements TargetingEffect {
        TargetingEffect target;
        
        public DeflectionEffect(Player p, Card c) { super(p,c); }
        public boolean play() {
            pickTarget();
            return super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [Change the target of " + target.name() + " with a single target]";
        }
        
        public void pickTarget() {
            CardStack stack = CardGame.instance.getStack();
            ArrayList<TargetingEffect> effects=new ArrayList<>();
            List<String> names = new ArrayList();
            
            for (Effect e:stack) {
                if (e instanceof TargetingEffect)
                {
                    TargetingEffect te=(TargetingEffect)e;
                    effects.add(te);
                    names.add(e.name());
                }
            }
            
            ChooseFrame f = new ChooseFrame(names);
            
            int idx = 0;
            try {
                idx = f.selected();
            } catch (InterruptedException ex) {
                Logger.getLogger(Deflection.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (idx<0 || idx>=effects.size()) 
                target = null;
            else 
                target = effects.get(idx);
        }
        
        public void resolve() {
            if (target==null) {
                System.out.println(cardName + " has no target");
            } else if (CardGame.instance.getStack().indexOf(target)==-1) {
                System.out.println(cardName + " target is not on the stack anymore");
            } else {
                target.pickTarget();
            }
        }
    }

    public Effect getEffect(Player owner) { 
        return new DeflectionEffect(owner, this); 
    }
}

