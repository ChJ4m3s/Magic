package cardgame;


public interface CreatureDecorator extends Creature {
    CreatureDecorator decorate(Creature c);  
    Creature removeDecorator(CreatureDecorator d);
}
