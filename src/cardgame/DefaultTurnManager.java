package cardgame;

public class DefaultTurnManager implements TurnManager {

    private final Player[] Players;
    int current_player_idx=1;
    
    public DefaultTurnManager(Player[] p) { Players=p; }
    
    public Player getCurrentPlayer() { return Players[current_player_idx]; }
    
    public Player getCurrentAdversary() { return Players[(current_player_idx+1)%2]; }
    
    public Player nextPlayer() { 
        current_player_idx = (current_player_idx+1)%2;
        return getCurrentPlayer();
    }
}
