/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vdouble;

import nc.variable.V;
import nc.variable.VCompound;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;
import nc.variable.vdouble.updater.no_update.VDoubleNoUpdate;

/**
 * A variable that stores a double value
 * @author olao
 */
public class VDouble extends VCompound<RealNumber> {

        
    /**
     * Create a new variable.
     *
     * @param value
     * @param stu
     */
    public VDouble(RealNumber value, StrategyToUpdateV<RealNumber> stu) {
        super(value, stu);
    }
    
    /**
     * Create a new variable that is not going to change (it will have the VDoubleNoUpdate)
     * @param value 
     */
    public VDouble(RealNumber value)
    {
        super(value, new VDoubleNoUpdate());
    }
        
    /**
     * Make a copy of this Variable: the value and the strategy to update the variable
     * @return 
     */
    @Override
    public V copy() {
        return new VDouble(getI().copy(), getStu().copy());
    }
    
    @Override
    public double distance(V<RealNumber> vi)
    {
        return Math.abs(getI().getD()- vi.getI().getD());
    }    
}
