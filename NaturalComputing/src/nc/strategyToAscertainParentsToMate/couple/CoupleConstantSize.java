/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.couple;

/**
 * This class defines a constant number of solutions mating at each couple
 * @author Oscar Lao
 */
public class CoupleConstantSize extends CoupleSize{
    
    /**
     * 
     * @param number_of_solutions_by_couple 
     */
    public CoupleConstantSize(int number_of_solutions_by_couple)
    {
        this.number_of_solutions_by_couple = number_of_solutions_by_couple;
    }    
    private int number_of_solutions_by_couple;

    public void setNumber_of_solutions_by_couple(int number_of_solutions_by_couple) {
        this.number_of_solutions_by_couple = number_of_solutions_by_couple;
    }

    @Override
    public int number_of_parents() {
        return number_of_solutions_by_couple;
    }
}
