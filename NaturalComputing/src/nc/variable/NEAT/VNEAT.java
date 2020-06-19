/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.NEAT;

import nc.variable.V;
import nc.variable.VAtomic;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 *
 * @author Oscar Lao
 */
public class VNEAT extends VAtomic<NEAT>{

    public VNEAT(NEAT i, StrategyToUpdateV<NEAT> stu) {
        super(i, stu);
    }

    @Override
    public V<NEAT> copy() {
        return new VNEAT(((NEAT)getI()).copy(), getStu().copy());
    }

    @Override
    public double distance(V<NEAT> vo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
