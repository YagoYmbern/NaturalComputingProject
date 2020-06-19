/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable;

import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 * A variable that is atomic cannot be divided or merged with other variables
 *
 * @author Oscar Lao
 * @param <T>
 */
public abstract class VAtomic<T> extends V<T> {

    public VAtomic(T i, StrategyToUpdateV<T> stu) {
        super(i, stu);
    }

    @Override
    public Nature getNature() {
        return Nature.ATOMIC;
    }
}
