
package cardgame;

import java.util.List;


public class DecoratedCreature implements Damageable, Creature{
    private Creature decorated;
    private Player owner;
    
    public DecoratedCreature(Player o, Creature c) {
        owner=o;
        decorated=c;
        decorated.setTopDecorator(this);
    }
    
    public void addDecorator(CreatureDecorator d) {
        decorated=d.decorate(decorated);
        if (getDamage()>=toughness()) remove();
    }
    

    public void remove() { 
        owner.getCreatures().remove(this); 
        System.out.println("removing " + name() + " from field");
        onRemove();
        CardGame.instance.getTriggers().trigger(Triggers.EXIT_CREATURE_FILTER);
    }
    public void onRemove() { 
        decorated.onRemove(); 
        decorated=null;
    }
    public boolean isRemoved() { return decorated==null; }
    public void accept(GameEntityVisitor v) { v.visit(this); }
    
    public String name() { return decorated.name(); }
    public int power() { return Math.max(0,decorated.power()); }
    public int toughness() { return Math.max(0,decorated.toughness()); }
    
    public void tap() { decorated.tap(); }
    public void untap() { decorated.untap(); }
    public boolean isTapped() { return decorated.isTapped(); }
    
    public boolean canAttack() { return decorated.canAttack(); }
    public void attack() { decorated.attack(); }
    public boolean canDefend() { return decorated.canDefend(); }
    public void defend() { decorated.defend(); }
    public void inflictDamage(int dmg) { owner.inflictDamage(decorated, dmg); }
    public int getDamage() { return decorated.getDamage(); }
    public void resetDamage() { decorated.resetDamage(); }
    public void destroy() { decorated.destroy(); }
    
    public List<Effect> effects() { return decorated.effects(); }
    
    public List<Effect> avaliableEffects() { return decorated.avaliableEffects(); }
    

    public void removeDecorator(CreatureDecorator d) { 
        if (decorated instanceof CreatureDecorator) {
            decorated=((CreatureDecorator)decorated).removeDecorator(d);  
            if (getDamage() >= toughness())
                destroy();
        }      
    }

    public void setTopDecorator(DecoratedCreature c) {}
    public DecoratedCreature getTopDecorator() { return this; }
    
    public String toString() { return decorated.toString(); }
    
}
