package cardgame;

// utility clas implementing common defaul behavior and fields for creatures

import java.util.List;
import java.util.ArrayList;

// creatures with differenf behavior from the default nee not extend it
public abstract class AbstractCreature implements Creature {
    protected Player owner;
    protected boolean isTapped=false;
    protected int damage = 0;
    protected DecoratedCreature topDecorator;
        
    protected AbstractCreature(Player owner) { 
        this.owner = owner; 
    }

    public void tap() { 
        if (isTapped) {
            System.out.println("creature " + topDecorator.name() + " already tapped");
        } else {
            System.out.println("tapping creature " + topDecorator.name());
            isTapped=true; 
        }
    }

    public void untap() { 
        if (!isTapped) {
            System.out.println("creature " + topDecorator.name() + " not tapped");
        } else {
            System.out.println("untapping creature " + topDecorator.name());
            isTapped=false; 
        }
    }

    public boolean isTapped() { 
        return isTapped; 
    }
    public boolean canAttack() { 
        return !isTapped; 
    }
    public void attack() { 
        topDecorator.tap(); 
    } 
    public boolean canDefend() { 
        return !isTapped; 
    }
    public void defend() {} 

    public void inflictDamage(int dmg) { 
        damage += dmg; 
        if (damage >= topDecorator.toughness())
            topDecorator.destroy();        
    }
    public int getDamage() { 
        return damage; 
    }


    public void destroy() { 
        topDecorator.remove();
    }
    public void onRemove() {
        System.out.println("Removing " + name());
    }

    public void resetDamage() { 
        damage = 0; 
    }

    public void setTopDecorator(DecoratedCreature c) {
        topDecorator = c;
        if (damage >= topDecorator.toughness())
            topDecorator.destroy();
    }
    public DecoratedCreature getTopDecorator() { 
        return topDecorator; 
    }


    public String toString() {
        return topDecorator.name() + "(" + topDecorator.power() + "/" + topDecorator.toughness() + ")";
    }
    
    public boolean isRemoved() { 
        return topDecorator.isRemoved(); 
    }
    
    
    public List<Effect> effects() { return new ArrayList<>(); }
    public List<Effect> avaliableEffects() { return new ArrayList<>(); }
        
}
