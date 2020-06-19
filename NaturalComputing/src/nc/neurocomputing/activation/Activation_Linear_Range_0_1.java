/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.activation;

import java.util.concurrent.ThreadLocalRandom;
import nc.variable.vdouble.RealNumber;
import nc.variable.vdouble.VDouble;
import nc.variable.vdouble.updater.no_update.VDoubleNoUpdate;

/**
 *
 * @author Oscar Lao
 */
public class Activation_Linear_Range_0_1 extends Activation_Function {

    private final VDouble slope, c;

    /**
     * Create a Linear activation with a linear slope
     *
     * @param slope
     */
    public Activation_Linear_Range_0_1(VDouble slope, VDouble c) {
        this.slope = slope;
        this.c = c;
    }

    /**
     * Create a classical activation linear with slope = 1 and no possibility to
     * change it
     */
    public Activation_Linear_Range_0_1() {
        this.slope = new VDouble(new RealNumber(1), new VDoubleNoUpdate());
        this.c = new VDouble(new RealNumber(0), new VDoubleNoUpdate());
    }

    @Override
    public double activate(double v) {
        return Math.max(0, Math.min(v * slope.getI().getD() + c.getI().getD(), 1));
    }

    @Override
    public void updateParameters() {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                slope.apply_update();
                break;
            case 1:
                c.apply_update();
                break;
            case 2:
                slope.apply_update();
                c.apply_update();
                break;
            default:
                break;
        }
    }

    /**
     * Get the slope
     *
     * @return
     */
    public VDouble getSlope() {
        return slope;
    }

    /**
     * Get the independent term
     *
     * @return
     */
    public VDouble getC() {
        return c;
    }

    @Override
    public Activation_Function copy() {
        return new Activation_Linear_Range_0_1((VDouble) slope.copy(), (VDouble) c.copy());
    }
}
