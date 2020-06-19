/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainOffspring.brood_competition;

import java.util.List;
import nc.solution.Solution;
import nc.strategyToAscertainParentsToMate.Brood;

/**
 *
 * @author olao
 */
public abstract class BroodCompetition {
    
    /**
     * Implement a strategy so the offspring compete between them. 
     * @param o
     * @return 
     */
    public abstract List<Solution> getSurvivorBrood(Brood o);
            
    
}
