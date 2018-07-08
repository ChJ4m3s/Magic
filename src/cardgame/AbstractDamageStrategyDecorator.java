package cardgame;


public abstract class AbstractDamageStrategyDecorator implements DamageStrategyDecorator {

    protected DamageStrategy decorated;
    
    public DamageStrategyDecorator decorate(DamageStrategy c) {
        decorated=c;
        return this;
    }


    public DamageStrategy removeDecorator(DamageStrategyDecorator d) {
        if (d==this) {
            DamageStrategy result=decorated;
            decorated=null;
            return result;
        }
        if (decorated instanceof DamageStrategyDecorator)
            decorated = ((DamageStrategyDecorator)decorated).removeDecorator(d);
        
        return this;
    }

    //by default simply forward the behavior
    public void inflictDamage(int dmg) { 
        decorated.inflictDamage(dmg); 
    }
    public void heal(int pts) { 
        decorated.heal(pts);
    }
    public void lose(String s) {
        decorated.lose(s); 
    }
    public void inflictDamage(Creature c, int dmg) { 
        decorated.inflictDamage(c,dmg); 
    }
    
}
