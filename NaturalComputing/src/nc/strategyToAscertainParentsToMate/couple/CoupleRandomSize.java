/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.couple;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author olao
 */
public class CoupleRandomSize extends CoupleSize{
    

    /**
     * The minimum number and maximum number of solutions that can compose a couple. The strategy to generate solutions given parents should be able
     * to deal with the proposed numbers
     * @param min_number_of_solutions_by_couple
     * @param max_number_of_solutions_by_couple 
     */
    public CoupleRandomSize(int min_number_of_solutions_by_couple, int max_number_of_solutions_by_couple)
    {
        this.min_number_of_solutions_by_couple = min_number_of_solutions_by_couple;
        this.max_number_of_solutions_by_couple = max_number_of_solutions_by_couple;
        
    }    
    private int min_number_of_solutions_by_couple, max_number_of_solutions_by_couple;

    public void setMax_number_of_solutions_by_couple(int max_number_of_solutions_by_couple) {
        this.max_number_of_solutions_by_couple = max_number_of_solutions_by_couple;
    }

    public void setMin_number_of_solutions_by_couple(int min_number_of_solutions_by_couple) {
        this.min_number_of_solutions_by_couple = min_number_of_solutions_by_couple;
    }
    
    @Override
    public int number_of_parents() {
        return (ThreadLocalRandom.current().nextInt(min_number_of_solutions_by_couple-1, max_number_of_solutions_by_couple)+1);
    }
}