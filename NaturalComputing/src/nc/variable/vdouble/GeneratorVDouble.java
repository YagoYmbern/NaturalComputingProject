/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vdouble;

import nc.variable.initialize.Generator_Variable;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 *
 * @author Oscar Lao
 */
public abstract class GeneratorVDouble extends Generator_Variable<RealNumber>{
    
    public GeneratorVDouble(StrategyToUpdateV<RealNumber> stu) {
        super(stu);
    }    
}
