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
public class Activation_Linear extends Activation_Function{
    
    private final VDouble slope, c;
    
    /**
     * Create a Linear activation with a linear slope
     * @param slope 
     * @param c
     */
    public Activation_Linear(VDouble slope, VDouble c)
    {
        this.slope = slope;
        this.c = c;
    }

    /**
     * Create a classical activation linear with slope = 1 and no possibility to change it
     */
    public Activation_Linear()
    {
        this.slope = new VDouble(new RealNumber(1), new VDoubleNoUpdate());
        this.c = new VDouble(new RealNumber(0), new VDoubleNoUpdate());
    }    
    
    @Override
    public double activate(double v) {
        return v*slope.getI().getD() + c.getI().getD();
    }   
    
    @Override
    public void updateParameters() {
        slope.apply_update();
    }

    /**
     * Get the slope
     * @return 
     */
    public VDouble getSlope() {
        return slope;
    }

    /**
     * Get the independent term
     * @return 
     */
    public VDouble getC() {
        return c;
    }

    @Override
    public Activation_Function copy() {
        return new Activation_Linear((VDouble)slope.copy(),(VDouble)c.copy());
    }    
}
