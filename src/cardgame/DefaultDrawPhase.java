package cardgame;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DefaultDrawPhase implements Phase {
    
    
    
    public void execute() {
        Player current_player = CardGame.instance.getCurrentPlayer();  
        /*
        update title.
        */
        CardGame.instance.mainF.updateTitle(current_player.name(), "Draw Phase");
        CardGame.instance.mainF.updateAll(current_player);
        
        try {
            CardGame.instance.mainF.updateButton("Draw", false, true);
        } catch (InterruptedException ex) {
            Logger.getLogger(DefaultDrawPhase.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(current_player.name() + ": draw phase");
        
        CardGame.instance.getTriggers().trigger(Triggers.DRAW_FILTER);
        
        current_player.draw();
        
        
        while(current_player.getHand().size() > current_player.getMaxHandSize()) {
            try {
                current_player.selectDiscard();
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultDrawPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
