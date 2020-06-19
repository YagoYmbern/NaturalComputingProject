/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToGenerateSolutionsGivenParents.number_of_offspring;

import nc.solution.Solution;
import nc.strategyToAscertainParentsToMate.Couple;

/**
 *
 * @author Oscar Lao
 */
public abstract class FunctionToDecideNumberOfOffspring {
    
    /**
     * return the number of offspring from the solutions 
     * @param a
     * @return 
     */
    public abstract int number_of_descendents_of_this_Couple(Couple a);
    
}
