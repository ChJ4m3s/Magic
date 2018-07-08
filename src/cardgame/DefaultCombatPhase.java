package cardgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultCombatPhase implements Phase {

    private class CombatEntry {
        public DecoratedCreature attacker;
        public ArrayList<DecoratedCreature> defenders=new ArrayList<>();
    }
    
    @Override
    public void execute() {
        final Player current_player = CardGame.instance.getCurrentPlayer();
        final Player adversary = CardGame.instance.getCurrentAdversary();
        final int current_player_idx = (CardGame.instance.getPlayer(0) == current_player)?0:1;
        Scanner reader = CardGame.instance.getScanner();
        ArrayList<CombatEntry> combat = new ArrayList<>();
        int active_player;
        int number_passes;
        
        ////////////////////////////////////////////////////////////////////////////
        CardGame.instance.mainF.updateTitle(current_player.name(), "Combat Phase");
        CardGame.instance.mainF.updateAll(current_player);   
        /////////////////////////////////////////////////////////////////////////////
        
        CardGame.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);
         
        // declare attackers
        if (!current_player.getCreatures().isEmpty()) {
            
            //////////////////////////////////////////////////////////////////////////////////////////////////
            CardGame.instance.mainF.updateTitle(current_player.name() + ": choose attackers", "Combat Phase");
            CardGame.instance.mainF.updateAll(current_player);
            try {
                CardGame.instance.mainF.updateButton("play", true, false);
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultCombatPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //////////////////////////////////////////////////////////////////////////////////////////////////
            
            
            ////////////////////////////////////////////////////////////////////////////
            boolean obscured[] = new boolean[current_player.getCreatures().size()];
            int i = 0;
            for (DecoratedCreature c : current_player.getCreatures()) {
                if (!c.canAttack())
                    obscured[i] = true;
                i++;
            }
            CardGame.instance.mainF.getField().obscure(obscured, 1);   
            boolean selected[] = {};
            try {
                selected = CardGame.instance.mainF.getSelector().selectFromField();                   
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultCombatPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int j = 0; i < selected.length; i++) {
                if (selected[i]) {
                    CombatEntry entry = new CombatEntry();
                    entry.attacker = current_player.getCreatures().get(i);
                    entry.attacker.attack();
                    combat.add(entry);
                }
            }
            ////////////////////////////////////////////////////////////////////////////
        }
        
        //allow other player to respond first
        active_player = (current_player_idx + 1) % 2;
        number_passes = 0;
        while (number_passes < 2) {
            
            //////////////////////////////////////////////////////////////////////////////////////////////////
            CardGame.instance.mainF.updateTitle(CardGame.instance.getPlayer(active_player).name() 
                    + ": attacker declaration response subphase", "Combat Phase");
            
            CardGame.instance.mainF.updateAll(CardGame.instance.getPlayer(active_player));
            
            try {
                CardGame.instance.mainF.updateButton("Play", true, false);
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultCombatPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                if (CardGame.instance.mainF.getSelector().choose(CardGame.instance.getPlayer(active_player), false))
                    number_passes=0;
                else ++number_passes;
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultCombatPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////
            
            active_player = (active_player+1) % 2;
        }
        
        CardGame.instance.getStack().resolve();
        
        HashSet<DecoratedCreature> defenders = new HashSet<>();
        
        // declare defenders
        if (!adversary.getCreatures().isEmpty()) {           
            for (CombatEntry e: combat) {
                if (e.attacker.isRemoved()) continue;
                
                ///////////////////////////////////////////////////////////////////////////////////////////////
                CardGame.instance.mainF.updateTitle(adversary.name() + ": choose creatures defending from" 
                    + e.attacker, "Combat Phase");
                CardGame.instance.mainF.updateAll(adversary);
                ///////////////////////////////////////////////////////////////////////////////////////////////
                
                ArrayList<DecoratedCreature> potential_defenders=new ArrayList<>();
                
                boolean[] obscurated;
                
                for (DecoratedCreature c : adversary.getCreatures()) 
                    if (c.canDefend() && !defenders.contains(c)) 
                        potential_defenders.add(c);
                
                obscurated = new boolean[adversary.getCreatures().size()];
                int j = 0;
                
                for (int i = 0; i < adversary.getCreatures().size(); i++) {
                    if (adversary.getCreatures().get(i) != potential_defenders.get(j))
                        obscurated[i] = true;
                    else
                        j++;
                }
                
                CardGame.instance.mainF.getField().obscure(obscurated, 1);
                
                ///////////////////////////////////////////////////////////////////////////////////
                
                CardGame.instance.mainF.getField().activeFieldToChoose();
                
                boolean index_chooses[] = null;
                try {
                    index_chooses = CardGame.instance.mainF.getSelector().selectFromField();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DefaultCombatPhase.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for (int i = 0; i < index_chooses.length; i++) {
                    DecoratedCreature defender = potential_defenders.get(i);
                    if (!defenders.contains(defender)) {
                        defender.defend();
                        e.defenders.add(defender);
                        defenders.add(defender);
                    }
                }   
                
                ///////////////////////////////////////////////////////////////////////////////////
            }
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        //allow priority player to respond first
        active_player = current_player_idx;
        number_passes = 0;
        while (number_passes < 2) {
            CardGame.instance.mainF.updateTitle(CardGame.instance.getPlayer(active_player).name() + 
                ": Defender declaration response subphase", "Combat Phase");
            ////////////////////////////////////////////////////////////////////////////////////////////////
            CardGame.instance.mainF.updateAll(CardGame.instance.getPlayer(active_player));
            
            
            try {
                if (CardGame.instance.mainF.getSelector().choose(CardGame.instance.getPlayer(active_player), false))
                    number_passes=0;
                else ++number_passes;
            } catch (InterruptedException ex) {
                Logger.getLogger(DefaultCombatPhase.class.getName()).log(Level.SEVERE, null, ex);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////
            
            active_player = (active_player+1) % 2;
        }
        
        CardGame.instance.getStack().resolve();
          
        //distribute damage
        
        ////////////////////////////////////////////////////////////////////////////////////////////////
        CardGame.instance.mainF.updateTitle(current_player.name() + ": Damage distribution subphase", 
                "Combat Phase");
        ////////////////////////////////////////////////////////////////////////////////////////////////
        
        for (CombatEntry e:combat) {
            if (e.attacker.isRemoved()) continue;
            
            boolean is_blocked=false;
            int defender_damage = 0;
            int attacker_residual_power = e.attacker.power();
            
            for (DecoratedCreature defender : e.defenders) {
                if (defender.isRemoved()) continue;
                is_blocked=true;
                
                //compute damage attacker inflict to this defender
                int attacker_damage = Math.min(attacker_residual_power, 
                        defender.toughness()-defender.getDamage() );
                //accumulate defender damage
                defender_damage += Math.max(0,defender.power());
                
                //inflict damage to defender
                if (attacker_damage>0) {
                    defender.inflictDamage(attacker_damage);
                    attacker_residual_power-=attacker_damage;
                }
            }
            
            
            if (!is_blocked) {
                //inflict damage to adversary player
                System.out.println(e.attacker + " deals " + e.attacker.power() + " damage to " + adversary.name());
                adversary.inflictDamage(Math.max(0,e.attacker.power()));
            } else {
                //inflict cumulative damage to attacker
                defender_damage = Math.min(defender_damage, 
                        e.attacker.toughness()-e.attacker.getDamage() );
                e.attacker.inflictDamage(defender_damage);
            }
            
        }
        
    }
    
}
