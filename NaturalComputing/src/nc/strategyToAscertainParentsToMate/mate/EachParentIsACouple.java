/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.mate;

import java.util.ArrayList;
import nc.Optimizer;
import nc.solution.Solution_Weighted;
import nc.strategyToAscertainParentsToMate.Couple;
import nc.strategyToAscertainParentsToMate.fitness.FitnessCoupleAverage;

/**
 * Each parent reproduces in this implementation
 *
 * @author olao
 */
public class EachParentIsACouple extends ReproductiveSystem {
    
    /**
     * Create an object where each solution from the current iteration is a couple.
     * 
     */
    public EachParentIsACouple() {
        super(-1, new FitnessCoupleAverage(), null, null);
    }

    /**
     * Return all the parents to reproduce. Add a weight to each parent
     * depending on its position in the array.
     *
     * @param this_iteration
     * @return
     */
    @Override
    public ArrayList<Couple> generate_couples(Optimizer this_iteration) {
// If we need to take into account the other solutions to decide the position, then we need to update f
        ArrayList<Couple> mp = new ArrayList<>();
// Go through each solution. Add a weight according to f
        this_iteration.stream().map((s) -> {
            // mating parents is only a parent (the current solution)
            Couple mpp = new Couple(fc);
            // Create the solution with weights defined in f
            mpp.add(new Solution_Weighted(s, 1));
            return mpp;
        }).forEachOrdered((mpp) -> {
            mp.add(mpp);
        });        
        return mp;
    }
}
