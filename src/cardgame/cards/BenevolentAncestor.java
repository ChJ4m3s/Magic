package cardgame.cards;

import cardgame.*;
import cardgame.graphic.ChooseFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class BenevolentAncestor extends AbstractCard {
    static private final String cardName = "Benevolent Ancestor";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new BenevolentAncestor(); }
                } );
    
    private class BenevolentAncestorEffect extends AbstractCreatureCardEffect {
        public BenevolentAncestorEffect(Player p, Card c) { super(p,c); }
        
        protected Creature createCreature() { return new BenevolentAncestorCreature(owner); }
    }
    public Effect getEffect(Player p) { return new BenevolentAncestorEffect(p,this); }
    
    
    private class BenevolentAncestorCreature extends AbstractCreature {
        
        BenevolentAncestorCreature(Player owner) {  super(owner); }
        
        public String name() { return cardName; }
        
        public boolean CanAttack() { return false; }
        public int power() { return 0; }
        public int toughness() { return 4; }

        public List<Effect> effects() 
        { 
            ArrayList<Effect> effects= new ArrayList<>();
            effects.add(new ProtectionEffect(owner));
            return effects; 
        }
        public List<Effect> avaliableEffects() {
            ArrayList<Effect> effects= new ArrayList<>();
            if (!topDecorator.isTapped()) effects.add(new ProtectionEffect(owner));
            return effects;
        }
        
        
        
        private class ProtectionEffect extends AbstractEffect implements TargetingEffect {
            private Player owner;
            private Damageable target;

            public ProtectionEffect(Player p) { owner=p; }

            public String name() { return "prevent 1 damage"; }

            public String toString() {
                if (target==null) {
                    return "prevent the next 1 damage to target creature or player this turn";
                } else if (target.isRemoved() ) {
                    return "prevent the next 1 damage to (removed target)";
                } else return "prevent the next 1 damage to " + target.name(); 
            }

            public boolean play() {
                if (isTapped) return false;
                topDecorator.tap();
                pickTarget();
                return super.play();
            }

            @Override
            public void pickTarget() {

                ArrayList<Damageable> targets = new ArrayList<>();
                List<String> names = new ArrayList<String>();

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
                    Logger.getLogger(BenevolentAncestor.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (idx < 0 || idx >= targets.size()) 
                    target=null;
                else 
                    target=targets.get(idx);
            }
            
            public void resolve() { 
                if (target==null) {
                    System.out.println(cardName + ": damage prevention effect has no target");
                    return;    
                }

                target.accept( new GameEntityVisitor() {
                   
                    public void visit(Player p) {
                        final BenevolentAncestorDamageStrategy st = new BenevolentAncestorDamageStrategy();
                        final Player ptarget=p;

                        ptarget.addDamageStrategy(st);
                        CardGame.instance.getTriggers().register(Triggers.END_FILTER, 
                                new TriggerAction() {
                                    public void execute() {
                                        System.out.println("Removing " + cardName + ": damage prevention effect");
                                        ptarget.removeDamageStrategy(st);
                                        CardGame.instance.getTriggers().remove(this);
                                    }
                                }
                                );
                    
                    }
                    
                    public void visit(Card c) {} //should not happen
                    
                    public void visit(Effect e) {}//should not happen
                    
                    public void visit(DecoratedCreature c) {
                        final DecoratedCreature ctarget=c;
                        if (ctarget.isRemoved()) {
                            System.out.println(cardName + ": damage prevention effect targets a removed creature");
                            return;
                        }
                        final BenevolentAncestorCreatureDecorator cr = new BenevolentAncestorCreatureDecorator();

                        ctarget.addDecorator(cr);
                        CardGame.instance.getTriggers().register(Triggers.END_FILTER, 
                                new TriggerAction() {
                                    public void execute() {
                                        System.out.println("Removing " + cardName + ": damage prevention effect");
                                        ctarget.removeDecorator(cr);
                                        CardGame.instance.getTriggers().remove(this);
                                    }
                                }
                                );
                    }
                    
                }); //end accept

            }
        }
        
    }
    
    
    
    
    private class BenevolentAncestorDamageStrategy extends AbstractDamageStrategyDecorator {
        int prevention=1;
        public void inflictDamage(int dmg) {
            System.out.println(cardName + " preventing 1 damage to player");
            if (dmg<=prevention) prevention-=dmg;
            else {
                decorated.inflictDamage(dmg-prevention);
                prevention=0;
            }
        }
    } 
    
    private class BenevolentAncestorCreatureDecorator extends AbstractCreatureDecorator {
        int prevention=1;
        public void inflictDamage(int dmg) {
            System.out.println(cardName + " preventing 1 damage to player");
            if (dmg<=prevention) prevention-=dmg;
            else {
                decorated.inflictDamage(dmg-prevention);
                prevention=0;
            }
        }
    }
    
    public String name() { return cardName; }
    public String type() { return "Creature"; }
    public String ruleText() { 
        return "Put in play a creature " + cardName + "(0/4) with tap: prevent the next 1 damage that would be delt to target creature or player this turn"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }

}