/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.initialize;

import nc.variable.V;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 *
 * @author Oscar Lao
 * @param <T>
 */
public abstract class Generator_Variable<T> {
    
    protected final StrategyToUpdateV<T> stu;
    
    /**
     * Initialize a variable of type T using a strategy to update the variable and a recombination strategy
     * @param stu
     */
    public Generator_Variable(StrategyToUpdateV<T> stu)
    {
        this.stu = stu;
    }    

    /**
     * Get the strategy to update the variable
     * @return 
     */
    public StrategyToUpdateV<T> getStu() {
        return stu;
    }
    
    
    
    /**
     * Generate a variable
     * @return 
     */
    public abstract V<T> generateVariable();    
}
