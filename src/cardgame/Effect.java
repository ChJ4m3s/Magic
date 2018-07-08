package cardgame;

public interface Effect extends GameEntity {
    
    // pays for effect and places it in the stack
    boolean play();
    
    // resolves the effect
    void resolve();
    
    void remove();
    
}
