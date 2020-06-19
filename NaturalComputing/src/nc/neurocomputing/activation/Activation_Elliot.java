/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.activation;

import nc.variable.vdouble.VDouble;

/**
 * 
 * @author olao
 */
public class Activation_Elliot extends Activation_Function{
// The slope
    private final VDouble slope;

    public Activation_Elliot(VDouble slope)
    {
        this.slope = slope;
    }    

    /**
     * Activation Elliot goes from [0,1]. It is a soft-sigmoid function
     * @param v
     * @return 
     */
    @Override
    public double activate(double v) {
        return 0.5*slope.getI().getD()*v/(1+ slope.getI().getD()*Math.abs(v)) + 0.5;
//        return 2*(0.5*slope*v/(1+ slope*Math.abs(v)) + 0.5) -1;        
    }

    @Override
    public Activation_Function copy() {
        return new Activation_Elliot((VDouble)slope.copy());
    }

    @Override
    public void updateParameters() {
        slope.apply_update();
    }
}
