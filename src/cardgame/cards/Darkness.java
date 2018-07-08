package cardgame.cards;

import cardgame.*;

public class Darkness extends AbstractCard {
    static private final String cardName = "Darkness";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Darkness(); }
                } );
    
    private class DarknessEffect extends AbstractCardEffect {
        public DarknessEffect(Player p, Card c) { super(p,c); }
        public void resolve() {
            final DarknessDamageStrategy p0 = new DarknessDamageStrategy();
            final DarknessDamageStrategy p1 = new DarknessDamageStrategy();
            
            CardGame.instance.getPlayer(0).addDamageStrategy(p0);
            CardGame.instance.getPlayer(1).addDamageStrategy(p1);
            
            CardGame.instance.getTriggers().register(Triggers.END_FILTER, 
                    new TriggerAction() {
                        public void execute() {
                            System.out.println("Remove " +  cardName);
                            CardGame.instance.getPlayer(0).removeDamageStrategy(p0);
                            CardGame.instance.getPlayer(1).removeDamageStrategy(p1);
                            CardGame.instance.getTriggers().remove(this);
                        }
                    }
                );
        }
    }
    
    private class DarknessDamageStrategy extends AbstractDamageStrategyDecorator {
        public void inflictDamage(int dmg) {
        System.out.println(cardName + " preventing damage to player");
        }
        public void inflictDamage(Creature c, int dmg) {
        System.out.println(cardName + " preventing damage to " + c.name());
        }
    } 
    

    public Effect getEffect(Player owner) { 
        return new DarknessEffect(owner, this); 
    }
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
    //should have been "prevent all combat damage", but dealing with this would require 
    //the game to keep track of different types of damages just for this card
    public String ruleText() { return "Prevents all damage that would be delt this turn"; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
}
