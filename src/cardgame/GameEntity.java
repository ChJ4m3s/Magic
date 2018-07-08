
package cardgame;


public interface GameEntity {
    
    String name();    
    boolean isRemoved();
    
    void accept(GameEntityVisitor v);
}
