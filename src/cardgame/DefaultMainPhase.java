package cardgame;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultMainPhase implements Phase {

    
    @Override
    public void execute() {
        Player current_player = CardGame.instance.getCurrentPlayer();
        int response_player_idx = (CardGame.instance.getPlayer(0) == current_player)? 1 : 0;
        
        CardGame.instance.mainF.updateTitle(current_player.name(), "Main Phase");
 
        
        CardGame.instance.getTriggers().trigger(Triggers.MAIN_FILTER);
        
        
        // alternate in placing effect until bith players pass
        int number_passes = 0;
        
        boolean play = false;
        
        CardGame.instance.mainF.updateAll(current_player);
        
        try {
            play = CardGame.instance.mainF.getSelector().choose(current_player, true);
        } catch (InterruptedException ex) {
            Logger.getLogger(DefaultMainPhase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (!play)
            ++number_passes;
        
        while (number_passes < 2) {
            CardGame.instance.mainF.updateTitle(CardGame.instance.getPlayer(response_player_idx).name(), "Main Phase");
            CardGame.instance.mainF.updateAll(CardGame.instance.getPlayer(response_player_idx));
            
            try {
                play = CardGame.instance.mainF.getSelector().choose(CardGame.instance.getPlayer(response_player_idx),false);
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultMainPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (play)
                number_passes=0;
            else ++number_passes;
            
            response_player_idx = (response_player_idx+1) % 2;
        }
        
        CardGame.instance.getStack().resolve();
    }
    
}
