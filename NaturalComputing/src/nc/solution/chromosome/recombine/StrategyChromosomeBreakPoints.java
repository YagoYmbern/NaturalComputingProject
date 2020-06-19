/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.recombine;

/**
 *
 * @author Oscar Lao
 */
public abstract class StrategyChromosomeBreakPoints {
    
    private final int total_number_of_variables;
    
    public StrategyChromosomeBreakPoints(int total_number_of_variables)
    {
        this.total_number_of_variables = total_number_of_variables;
    }

    public int getTotal_number_of_variables() {
        return total_number_of_variables;
    }        
    
    /**
     * Generate the next break point of the chromosome
     * @return 
     */
    public int nextBreakPoint()
    {
        int next = getNextStep();
        if(next>=total_number_of_variables)
        {
            return -1;
        }
        return next;
    }
    
    protected abstract int getNextStep();
    
    /**
     * Copy this strategy to break the chromosome
     * 
     * @return 
     */
    public abstract StrategyChromosomeBreakPoints copy();
    
}
