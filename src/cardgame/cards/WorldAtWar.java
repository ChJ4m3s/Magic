package cardgame.cards;

import cardgame.*;



public class WorldAtWar extends AbstractCard {
    static private final String cardName = "World at War";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new WorldAtWar(); }
                } );
    
    private class WorldAtWarEffect extends AbstractCardEffect {
        public WorldAtWarEffect(Player p, Card c) { super(p,c); }
        public void resolve() {
            CardGame.instance.getCurrentPlayer().setPhaseManager(new TurnChange());
        }
    }

    private class TurnChange implements PhaseManager {

        private int phaseidx=0;
        
        public Phases currentPhase() { 
            Phases result;
            switch (phaseidx) {
                case 0: result=Phases.MAIN; break;
                case 1: result=Phases.COMBAT; break;
                case 2: result=Phases.MAIN; break;
                default: //should not happen!
                    System.out.println( cardName + " turn beyond range. Odd! Removing it");
                    CardGame.instance.getCurrentPlayer().removePhaseManager(this);
                    result = CardGame.instance.getCurrentPlayer().currentPhase();
            }
            return result;
        }

        public Phases nextPhase() {
            ++phaseidx;
            if (phaseidx>2) {
                System.out.println("Removing " + cardName + " turn changes");
                CardGame.instance.getCurrentPlayer().removePhaseManager(this);
                return CardGame.instance.getCurrentPlayer().nextPhase();
            }
            if (phaseidx==1) {
                //should only untap creatures that have attacked, but I am ignoring this distinction
                //dealing with it would require the game to know abou this card before 
                //it was played and keep the attacker info just for its use
                System.out.println( cardName + " untapping creatures before new combat phase");
                for (Creature c:CardGame.instance.getCurrentPlayer().getCreatures()) {
                    c.untap();
                }
            }
            return currentPhase();
        }
        
    }
    
    public Effect getEffect(Player owner) { 
        return new WorldAtWarEffect(owner, this); 
    }
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "After the main phase this turn, there's an additional combat pahse followed by an additional main phase. At the baginning of that combat, untap all creatures"; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
}