package cardgame;

import java.util.List;

public interface Creature {
    
    String name();
    
    int power();
    int toughness();
    
    void tap();
    void untap();
    boolean isTapped();
    
    boolean canAttack();
    void attack();
    boolean canDefend();
    void defend();
    
    void inflictDamage(int dmg);
    int getDamage();
    void resetDamage();
    void destroy();
    void onRemove();
    
    // returns all the effects associated to this creature
    List<Effect> effects();
    
    // returns only the effects that can be played currently
    // depending on state, e.g., tapped/untapped
    List<Effect> avaliableEffects();
    
    
    void setTopDecorator(DecoratedCreature c);
    DecoratedCreature getTopDecorator();
}
