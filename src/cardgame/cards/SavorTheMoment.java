package cardgame.cards;

import cardgame.*;



public class SavorTheMoment extends AbstractCard {
    static private final String cardName = "Savor the Moment";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new SavorTheMoment(); }
                } );
    
    private class SavorTheMomentEffect extends AbstractCardEffect {
        public SavorTheMomentEffect(Player p, Card c) { super(p,c); }
        public void resolve() {
        CardGame.instance.getCurrentPlayer().setPhase(Phases.UNTAP, new SkipPhase(Phases.UNTAP));
        CardGame.instance.setTurnManager(new ExtraTurn());
        }
    }

    public Effect getEffect(Player owner) { 
        return new SavorTheMomentEffect(owner, this); 
    }
    
    
    private class ExtraTurn implements TurnManager {
        private Player current;
        private Player adversary;
        
        public ExtraTurn() {
            current = CardGame.instance.getCurrentPlayer();
            adversary = CardGame.instance.getCurrentAdversary();
        }
       

        public Player getCurrentPlayer() { return current; }
        public Player getCurrentAdversary() { return adversary; }

        public Player nextPlayer() {
            CardGame.instance.removeTurnManager(this);
            return current;
        }
        
    }
    
    
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Take an extra turn after this one. Skip the untap step of that turn."; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
}
