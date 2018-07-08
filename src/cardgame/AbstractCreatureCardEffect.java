package cardgame;

// utility class implementing common behavior for effects linked with 
// creature cards
public abstract class AbstractCreatureCardEffect extends AbstractCardEffect {
    protected AbstractCreatureCardEffect( Player p, Card c) { 
        super(p,c);
    }
    
    // deferred method that creates the creature upon resolution
    protected abstract Creature createCreature();
    
    public void resolve() {
        owner.getCreatures().add(new DecoratedCreature(owner,createCreature()) );
        CardGame.instance.getTriggers().trigger(Triggers.ENTER_CREATURE_FILTER);
    }
    
}
