package cardgame;

// utility class to implementing behavior common to all effects, 
// namely placing it in the stack when played
public abstract class AbstractEffect extends AbstractGameEntity implements Effect {
   public boolean play() { 
        CardGame.instance.getStack().push(this);
        return true;
    }
   
   public void remove() { 
       isRemoved=true; 
   }
   public void accept(GameEntityVisitor v) { 
       v.visit(this); 
   }
}
