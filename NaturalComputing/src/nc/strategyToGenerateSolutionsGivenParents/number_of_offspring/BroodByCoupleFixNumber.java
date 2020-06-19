/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToGenerateSolutionsGivenParents.number_of_offspring;

import nc.strategyToAscertainParentsToMate.Couple;

/**
 *
 * @author olao
 */
public class BroodByCoupleFixNumber extends FunctionToDecideNumberOfOffspring{
    
    /**
     * Fixed number of offspring of any couple to n
     * @param n 
     */
    public BroodByCoupleFixNumber(int n)
    {
        this.n = n;
    }

    private int n;

    /**
     * Set the fix number of offspring for a couple.
     * @param n 
     */
    public void setN(int n) {
        this.n = n;
    }
    

    @Override
    public int number_of_descendents_of_this_Couple(Couple parents) {
        return n;
    }
}