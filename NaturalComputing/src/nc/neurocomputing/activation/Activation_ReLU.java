/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.activation;

import nc.variable.vdouble.RealNumber;
import nc.variable.vdouble.VDouble;
import nc.variable.vdouble.updater.no_update.VDoubleNoUpdate;

/**
 *
 * @author olao
 */
public class Activation_ReLU extends Activation_Function{

    private final VDouble slope;
    
    /**
     * Apply a ReLU activation with a slope for the positive values determined by the slope variable
     * @param slope 
     */
    public Activation_ReLU(VDouble slope)
    {
        this.slope = slope;
    }

    /**
     * Create a classical activation ReLU with slope = 1 and no possibility to change it
     */
    public Activation_ReLU()
    {
        this.slope = new VDouble(new RealNumber(1), new VDoubleNoUpdate());
    }    
    
    
    @Override
    public void updateParameters() {
        slope.apply_update();
    }
    
    @Override
    public double activate(double v) {
        return Math.max(0,v*slope.getI().getD());
    }

    @Override
    public Activation_Function copy() {
        return new Activation_ReLU((VDouble)slope.copy());
    }        
}
