package cardgame;

public class DefaultPhaseManager implements PhaseManager{
    private Phases current_phase_idx=Phases.NULL;

    
    
    public Phases currentPhase() { return current_phase_idx; }
    
    public Phases nextPhase() { 
        current_phase_idx = current_phase_idx.next();
        return currentPhase();
    } 
    
    
}
