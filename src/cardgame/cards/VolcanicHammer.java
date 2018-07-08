package cardgame.cards;

import cardgame.*;
import cardgame.graphic.ChooseFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VolcanicHammer extends AbstractCard {
    static private final String cardName = "Volcanic Hammer";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new VolcanicHammer(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Deal 3 damage to target creature or player"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private class VolcanicHammerEffect extends AbstractCardEffect implements TargetingEffect {
        Damageable target;
        
        public VolcanicHammerEffect(Player p, Card c) { super(p,c); }
        public boolean play() {
            pickTarget();
            return super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [Deal 3 damage to " + target.name() + "]";
        }
        
        public void pickTarget() {
            List<String> names = new ArrayList<String>();
            ArrayList<Damageable> targets = new ArrayList<>();
            
            Player curPlayer = CardGame.instance.getPlayer(0);
            names.add("Player: " + curPlayer.name());
            targets.add(curPlayer);

            for (DecoratedCreature c:curPlayer.getCreatures()) {
                names.add(curPlayer.name() + ": " + c.name());
                targets.add(c);
            }
            
            curPlayer = CardGame.instance.getPlayer(1);
            names.add("Player: " + curPlayer.name());
            targets.add(curPlayer);

            for (DecoratedCreature c:curPlayer.getCreatures()) {
                names.add(curPlayer.name() + ": " + c.name());
                targets.add(c);
            }
            
            ChooseFrame f = new ChooseFrame(names);
            
            int idx = 0;
            try {
                idx = f.selected();
            } catch (InterruptedException ex) {
                Logger.getLogger(VolcanicHammer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (idx<0 || idx>=targets.size()) target=null;
            else target=targets.get(idx);
        }
        
        public void resolve() {
            if (target==null) {
                System.out.println(cardName + " has no target");
            } else if (target.isRemoved() ) {
                System.out.println(cardName + " target not in play anymore");
            } else {
                target.inflictDamage(3);
            }
        }
    }

    public Effect getEffect(Player owner) { 
        return new VolcanicHammerEffect(owner, this); 
    }
}
