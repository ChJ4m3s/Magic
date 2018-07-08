package cardgame.cards;

import cardgame.*;
import cardgame.graphic.ChooseFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class AggressiveUrge extends AbstractCard {
    
    static private final String cardName = "Aggressive Urge";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new AggressiveUrge(); }
                } );
    
    private class AggressiveUrgeEffect extends AbstractCardEffect implements TargetingEffect {
        private DecoratedCreature target;
        public AggressiveUrgeEffect(Player p, Card c) { super(p,c); }
        public boolean play() {
            pickTarget();
            return super.play();
        }
        public void resolve() {
            if (target==null) return;
            
            if (target.isRemoved()) {
                System.out.println("Attaching " + cardName + " to removed creature");
                return;
            }
            
            final AggressiveUrgeDecorator decorator=new AggressiveUrgeDecorator();
            TriggerAction action = new TriggerAction() {
                    public void execute() {
                        if (!target.isRemoved()){
                            System.out.println("Triggered removal of " + cardName + " from " + target);
                            target.removeDecorator(decorator);
                        } else {
                            System.out.println("Triggered dangling removal of " + cardName + " from removed target. Odd should have been invalidated!" );
                        }
                    }
                };
            System.out.println("Ataching " + cardName + " to " + target.name() + " and registering end of turn trigger");
            CardGame.instance.getTriggers().register(Triggers.END_FILTER, action);
                        
            decorator.setRemoveAction(action);
            target.addDecorator(decorator);
        }
        
        public void pickTarget() {   
            ArrayList<DecoratedCreature> creatures = new ArrayList<>();
            List<String> names = new ArrayList<String>();
            
            Player player1 = CardGame.instance.getPlayer(0);
            Player player2 = CardGame.instance.getPlayer(1);
            
            for (DecoratedCreature c: player1.getCreatures()) {
                creatures.add(c);
                names.add(player1.name() + ": " + c.name());
            }
            for (DecoratedCreature c: player2.getCreatures()) {
                creatures.add(c);
                names.add(player2.name() + ": " + c.name());
            }
            
            ChooseFrame f = new ChooseFrame(names);
            
            int idx = 0;
            try {
                idx = f.selected();
            } catch (InterruptedException ex) {
                Logger.getLogger(AggressiveUrge.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (idx < 0 || idx >= creatures.size())
                target=null;
            else 
                target=creatures.get(idx);
        }
        
        public String toString() {
            if (target==null ) return super.toString() + " (no target)";
            else if (target.isRemoved()) return super.toString() + " (removed creature)";
            else return cardName + " [" + target.name() + " gets +1/+1 until end of turn]";
        }
                
    }

    public Effect getEffect(Player owner) { 
        return new AggressiveUrgeEffect(owner, this); 
    }
    
    class AggressiveUrgeDecorator extends AbstractCreatureDecorator {
        TriggerAction action;
        public void setRemoveAction(TriggerAction a) { action=a; }
        public void onRemove() {
            System.out.println("Removing " + cardName + " and deregistering end of turn trigger");
            if (action!=null) 
                CardGame.instance.getTriggers().remove(action); 
            super.onRemove();
        }
        
        public int power() { return decorated.power()+1; }
        public int toughness() { return decorated.toughness()+1; }
    }
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
    public String ruleText() { return "Target creature gets +1/+1 until end of turn"; }
    public String toString() { return cardName + "[" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
}
