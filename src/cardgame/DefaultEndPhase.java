package cardgame;


public class DefaultEndPhase implements Phase {
    
    public void execute() {
        Player current_player = CardGame.instance.getCurrentPlayer();
        CardGame.instance.mainF.updateAll(current_player);
        
        CardGame.instance.mainF.updateTitle(current_player.name(), "End Phase");
       
        for(Creature c:current_player.getCreatures()) {
            
            c.resetDamage();
        }
        
        for(Creature c:CardGame.instance.getCurrentAdversary().getCreatures()) {
           
            c.resetDamage();
        }
        
        CardGame.instance.getTriggers().trigger(Triggers.END_FILTER);
    }
    
}
