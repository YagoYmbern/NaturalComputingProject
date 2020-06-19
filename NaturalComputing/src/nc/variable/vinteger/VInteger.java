/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vinteger;

import nc.variable.V;
import nc.variable.VAtomic;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 *
 * @author Oscar Lao
 */
public class VInteger extends VAtomic<Integer> {

    /**
     * Create an object that contains the non-repeated ids and go from to to as defined in params
     * variables
     *
     * @param i
     * @param stui
     */
    public VInteger(Integer i, StrategyToUpdateV<Integer> stui) {
        super(i, stui);       
    }        

    @Override
    public VInteger copy() {
        return new VInteger(getI(), getStu().copy());
    }

    
    @Override
    public double distance(V<Integer> vi)
    {
        return Math.abs(getI()- vi.getI());
    }    
}
