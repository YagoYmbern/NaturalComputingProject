/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable;

import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 * A variable that is compound allows to merge the variable with other variables
 * @author Oscar Lao
 * @param <T>
 */
public abstract class VCompound<T> extends V<T>{

    public VCompound(T i, StrategyToUpdateV<T> stu) {
        super(i, stu);
    }
    
    @Override
    public Nature getNature()
    {
        return Nature.COMPOUND;
    }        
}
