/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToUpdateSolution;

import nc.solution.Solution;
import nc.neurocomputing.layer.Layer;


/**
 *
 * @author Oscar Lao
 */
public class UpdateSolution {

    /**
     * Update the values of the solution a following a mutation/update approach
     *
     * @param a
     */
    public void update(Solution a) {
// Update each layer of the function
        for(int l=0;l<a.size();l++)
        {
            ((Layer)a.getF().get(l)).getStu().update(a.get(l));
        }
// Fitness has to be re-computed        
        a.resetFitness();
    }
}

