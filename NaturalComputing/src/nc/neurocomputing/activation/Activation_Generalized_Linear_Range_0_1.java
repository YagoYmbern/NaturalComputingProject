/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.activation;

import java.util.concurrent.ThreadLocalRandom;
import nc.variable.vdouble.GeneratorVDoubleGaussian;
import nc.variable.vdouble.VDouble;
import nc.variable.vdouble.updater.self_adaptation.NStepSizeSelfAdaptation;

/**
 *
 * @author Oscar Lao
 */
public class Activation_Generalized_Linear_Range_0_1 extends Activation_Generalized {

    private final VDouble s, c;

    public Activation_Generalized_Linear_Range_0_1(VDouble k, VDouble s, VDouble c) {
        super(k);
        this.s = s;
        this.c = c;
    }

    /**
     * A generic implementation. k and s are initialized with a Gaussian
     * distribution with mean 0 and sd 1. c is initialized with a Gaussian
     * distribution with mean 0 and sd 1
     */
    public Activation_Generalized_Linear_Range_0_1() {
        super();
        this.s = new GeneratorVDoubleGaussian(new NStepSizeSelfAdaptation(1, 0.01, 0.01), 0, 1).generateVariable();
        this.c = new GeneratorVDoubleGaussian(new NStepSizeSelfAdaptation(1, 0.01, 0.01), 0, 1).generateVariable();
    }

    /**
     * Get s from k*(s*x + c)
     *
     * @return
     */
    public VDouble getS() {
        return s;
    }

    /**
     * Get the independent term c from k*(s*x + c)
     *
     * @return
     */
    public VDouble getC() {
        return c;
    }

    /**
     * Activation Generalized goes between [-k,0] if k < 0 and [0,k] if k > 0.
     *
     *
     * @param v
     * @return
     */
    @Override
    public double activate(double v) {
        double a = s.getI().getD() * v + c.getI().getD();
        return getK().getI().getD() * Math.max(0, Math.min(a, 1));
    }

    @Override
    public Activation_Function copy() {
        return new Activation_Generalized_Linear_Range_0_1((VDouble) getK().copy(), (VDouble) s.copy(), (VDouble) c.copy());
    }

    @Override
    public void updateParameters() {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                getK().apply_update();
                break;
            case 1:
                c.apply_update();
                break;
            case 2:
                s.apply_update();
                break;
            default:
                break;
        }
    }
}
