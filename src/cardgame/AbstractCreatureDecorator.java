package cardgame;

import java.util.List;


public abstract class AbstractCreatureDecorator implements CreatureDecorator {

    protected Creature decorated;

    public CreatureDecorator decorate(Creature c) { 
        decorated = c; 
        return this;
    }
    public Creature removeDecorator(CreatureDecorator d) {
            Creature result = this;
            if (d==this) {
                result = decorated;
                decorated = null;
                onRemove();
            }
            else if (decorated instanceof CreatureDecorator)
                decorated=((CreatureDecorator)decorated).removeDecorator(d);
            return result;
        }

    public String name() { 
        return decorated.name(); 
    }

    public int power() { 
        return decorated.power(); 
    }
    public int toughness() { 
        return decorated.toughness(); 
    }
    public void tap() { 
        decorated.tap(); 
    }
    public void untap() { 
        decorated.untap(); 
    }
    public boolean isTapped(){ 
        return decorated.isTapped(); 
    }

    public boolean canAttack() { 
        return decorated.canAttack(); 
    }
    public void attack() { 
        decorated.attack(); 
    }
    public boolean canDefend() { 
        return decorated.canDefend(); 
    }
    public void defend() { 
        decorated.defend(); 
    }
    
    public void inflictDamage(int dmg) { 
        decorated.inflictDamage(dmg); 
    }
    public int getDamage() { 
        return decorated.getDamage(); 
    }
    public void resetDamage()  { 
        decorated.resetDamage(); 
    }
    public void destroy()  { 
        decorated.destroy(); 
    }
    public void onRemove() { 
        if (decorated!=null)
            decorated.onRemove(); 
    }
    
    public List<Effect> effects()  { 
        return decorated.effects(); 
    }
    public List<Effect> avaliableEffects() { 
        return decorated.avaliableEffects(); 
    }
    public void setTopDecorator(DecoratedCreature c) { 
        decorated.setTopDecorator(c); 
    }
    
    public DecoratedCreature getTopDecorator() { 
        return decorated.getTopDecorator(); 
    }

    public String toString() { 
        return decorated.toString(); 
    }
    
}
