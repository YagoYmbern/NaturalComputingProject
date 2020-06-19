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
public class Activation_Generalized_Binary extends Activation_Generalized {

    private final VDouble c;

    public Activation_Generalized_Binary(VDouble k, VDouble c) {
        super(k);
        this.c = c;
    }

    /**
     * A generic implementation. k and s are initialized with a Gaussian
     * distribution with mean 0 and sd 1. c is initialized with a Gaussian
     * distribution with mean 0 and sd 1
     */
    public Activation_Generalized_Binary() {
        super();
        this.c = new GeneratorVDoubleGaussian(new NStepSizeSelfAdaptation(1, 0.01, 0.01), 0, 10).generateVariable();
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
        double a = v - c.getI().getD();
        a = (a <= 0) ? 0 : 1;
        return getK().getI().getD() * a;
    }

    @Override
    public Activation_Function copy() {
        return new Activation_Generalized_Binary((VDouble) getK().copy(), (VDouble) c.copy());
    }

    @Override
    public void updateParameters() {
        if (ThreadLocalRandom.current().nextDouble() < 0.5) {
            getK().apply_update();
        } else {
            c.apply_update();
        }
    }
}
