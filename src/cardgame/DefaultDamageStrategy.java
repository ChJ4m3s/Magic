package cardgame;

public class DefaultDamageStrategy implements DamageStrategy {

    Player player;
    
    public DefaultDamageStrategy(Player p) { 
        player = p; 
    }
    
    public void inflictDamage(int dmg) {  
        player.changeLife(-dmg);
        if (player.getLife() <= 0)
            player.lose("received fatal damage");
    }
    
    public void heal(int pts) {  
        player.changeLife(pts); 
    }
    
    public void lose(String s) { 
        throw new EndOfGame(player.name() + " lost the game: " + s); 
    }

    
    public void inflictDamage(Creature c, int dmg) { 
        c.inflictDamage(dmg); 
    }
    
}
