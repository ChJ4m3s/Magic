package cardgame;



public interface DamageStrategyDecorator extends DamageStrategy {
    DamageStrategyDecorator decorate(DamageStrategy c);  
    DamageStrategy removeDecorator(DamageStrategyDecorator d);
}
