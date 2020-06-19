/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vinteger;

import nc.variable.initialize.Generator_Variable;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 *
 * @author Oscar Lao
 */
public abstract class GeneratorVInteger extends Generator_Variable<Integer>{
    
    /**
     * Initialize the variable of type integer
     * @param stu 
     */
    public GeneratorVInteger(StrategyToUpdateV<Integer> stu) {
        super(stu);
    }
}
