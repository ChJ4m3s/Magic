package cardgame;

import java.util.ArrayList;
import java.util.ArrayDeque;


public class Triggers {
    
    
    private class Entry { 
        public int filter; 
        public TriggerAction action;
        public boolean valid=true;
        public Entry(int f, TriggerAction a) { filter=f; action=a; }
        
    }
    
    ArrayList<Triggers.Entry> actions = new ArrayList<Triggers.Entry>();
    
    
    public void register(int phase_trigger, TriggerAction a) {
        actions.add(new Triggers.Entry(phase_trigger, a));
    }
    
    public void remove(TriggerAction a) {
        Triggers.Entry result=null;
        for(Triggers.Entry p:actions) {
            if (p.action == a) result=p;
        }
        if (result!=null) {
            result.valid=false;
            actions.remove(result);
        }
    }
    
      
    public void trigger(int phase) {
        ArrayDeque<Triggers.Entry> triggerableActions = new ArrayDeque<>();
        
        for (Triggers.Entry p:actions) {
            if ((p.filter & phase)!=0) triggerableActions.push(p);
        }
        
        //execute last-inserted-first
        while (!triggerableActions.isEmpty()) {
            Triggers.Entry action=triggerableActions.pop();
            if (action.valid) action.action.execute();
        }
    }
    
    public static final int DRAW_FILTER=1;
    public static final int UNTAP_FILTER=2;
    public static final int COMBAT_FILTER=4;
    public static final int MAIN_FILTER=8;
    public static final int END_FILTER=16;
    public static final int ENTER_CREATURE_FILTER=32;
    public static final int EXIT_CREATURE_FILTER=64;
}
