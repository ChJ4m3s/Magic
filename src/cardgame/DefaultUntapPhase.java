package cardgame;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultUntapPhase implements Phase {
    
    @Override
    public void execute() {
        Player current_player = CardGame.instance.getCurrentPlayer();
        
        CardGame.instance.mainF.updateAll(current_player);
        
        //System.out.println(current_player.name() + ": untap phase");
        CardGame.instance.mainF.updateTitle(current_player.name(), "Untap Phase");
        
        CardGame.instance.getTriggers().trigger(Triggers.UNTAP_FILTER);
        
        if (current_player.getCreatures().isEmpty()) {
            //System.out.println("...no creatures to untap");
            CardGame.instance.mainF.updateTitle(current_player.name() + ": no creatures to untap", "Untap Phase");
            try {
                CardGame.instance.mainF.updateButton("pass", false, true);
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultUntapPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else {
            try {
                CardGame.instance.mainF.updateButton("untap", false, true);
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultUntapPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(Creature c:current_player.getCreatures()) {
            c.untap();
        }
    }
}