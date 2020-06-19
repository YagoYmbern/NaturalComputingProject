/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vinteger;

import java.util.concurrent.ThreadLocalRandom;
import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;
import nc.variable.V;

/**
 * Update a variable integer within a range defined by the from - to
 * @author Oscar Lao
 */
public class UpdateVIntegerRange extends StrategyToUpdateV<Integer>{
    
    private int from, to;
    
    /**
     * Create an update variable in the integer range
     * @param from
     * @param to 
     */
    public UpdateVIntegerRange(int from, int to)
    {
        this.from = from;
        this.to = to;
    }

    /**
     * Get the lower limit
     * @return 
     */
    public int getFrom() {
        return from;
    }

    /**
     * Get the upper limit
     * @return 
     */
    public int getTo() {
        return to;
    }    

    /**
     * Set the lower limit of this range
     * @param from 
     */
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * Set the upper limit of the range
     * @param to 
     */
    public void setTo(int to) {
        this.to = to;
    } 
    
    @Override
    public void update(V<Integer> b) {
        b.setI(ThreadLocalRandom.current().nextInt(to - from) + from);
    }    

    @Override
    public StrategyToUpdateV copy() {
        return new UpdateVIntegerRange(from, to);
    }
}
