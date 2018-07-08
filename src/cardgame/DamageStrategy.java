package cardgame;


public interface DamageStrategy {
    
    void inflictDamage(int dmg); //inflict damage to the player
    void heal(int pts); //heal player
    void lose(String s);
    
    void inflictDamage(Creature c, int dmg); //inflict damage to a creature
    
}
