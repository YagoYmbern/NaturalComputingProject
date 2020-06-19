/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vdouble.updater.no_update;

import nc.variable.V;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;
import nc.variable.vdouble.RealNumber;

/**
 *
 * @author Oscar Lao
 */
public class VDoubleNoUpdate extends StrategyToUpdateV<RealNumber>{    
    
    /**
     * Do nothing. The Variable double does not change
     *
     * @param v
     */
    @Override
    public void update(V<RealNumber> v) {
    } 

    @Override
    public VDoubleNoUpdate copy() {
        return new VDoubleNoUpdate();
    }
}
