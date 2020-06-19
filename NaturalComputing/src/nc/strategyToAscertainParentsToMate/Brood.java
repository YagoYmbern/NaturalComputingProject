/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate;

import java.util.ArrayList;
import nc.solution.Solution;

/**
 * The offspring that comes from a couple
 * @author olao
 */
public class Brood extends ArrayList<Solution>{
    
    /**
     * Offspring out of parents
     * @param parents 
     */
    public Brood(Couple parents)
    {
        this.parents = parents;
    }
    
    private final Couple parents;

    /**
     * Get the parents
     * @return 
     */
    public Couple getParents() {
        return parents;
    }
}
