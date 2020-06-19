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
public class Activation_Generalized_Leaky_ReLU extends Activation_Generalized {

    private final VDouble s, c;

    /**
     * Creates a new ReLU activation function with g(x) = k*(s*x + c)
     *
     * @param k
     * @param s
     * @param c
     */
    public Activation_Generalized_Leaky_ReLU(VDouble k, VDouble s, VDouble c) {
        super(k);
        this.s = s;
        this.c = c;
    }

    /**
     * A generic implementation. k, s, and c will be set from a Gaussian
     * distribution with m = 0 and sd = 0.5
     */
    public Activation_Generalized_Leaky_ReLU() {
super();
this.s = new GeneratorVDoubleGaussian(new NStepSizeSelfAdaptation(1, 0.01, 0.01), 0, 0.5).generateVariable();
        this.c = new GeneratorVDoubleGaussian(new NStepSizeSelfAdaptation(1, 0.01, 0.01), 0, 0.5).generateVariable();
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
     * Activation Generalized k * min(1,max(0, a * v + c)).
     *
     * @param v
     * @return
     */
    @Override
    public double activate(double v) {
        // the slope must be positive
        double a = s.getI().getD();
        double act = (a * v + c.getI().getD());
        if(act>0)
        {
            return getK().getI().getD()*act;
        }
        return getK().getI().getD()*0.01*act;
    }

    @Override
    public Activation_Function copy() {
        return new Activation_Generalized_Leaky_ReLU((VDouble) getK().copy(), (VDouble) s.copy(), (VDouble) c.copy());
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
            case 3:
                c.apply_update();
                getK().apply_update();
                break;
            case 4:
                getK().apply_update();
                s.apply_update();
                break;
            case 5:
                c.apply_update();
                s.apply_update();
                break;
            case 6:
                getK().apply_update();
                c.apply_update();
                s.apply_update();
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "f(x) = k*(s*x + c)\n c: " + c.toString() + " k: " + getK().toString() + " s:" + s.toString();
    }
}
