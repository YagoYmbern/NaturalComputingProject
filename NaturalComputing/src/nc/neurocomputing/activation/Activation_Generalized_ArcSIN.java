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
public class Activation_Generalized_ArcSIN extends Activation_Generalized{

    private final VDouble s, c;

    /**
     * Creates a new ReLU activation function with g(x) = k*(s*x + c)
     *
     * @param k
     * @param s
     * @param c
     */
    public Activation_Generalized_ArcSIN(VDouble k, VDouble s, VDouble c) {
        super(k);
        this.s = s;
        this.c = c;
    }

    /**
     * A generic implementation. k, s, and c will be set from a Gaussian
     * distribution with m = 0 and sd = 0.5
     */
    public Activation_Generalized_ArcSIN() {
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
     * Activation Generalized k * min(1,max(0, a * v + c)).
     *
     * @param v
     * @return
     */
    @Override
    public double activate(double v) {
        // the slope must be small
        double a = s.getI().getD();
//        a = 0.01*a/(1+0.01*Math.abs(a));
        double act = (a * v + c.getI().getD());
//        act = 0.001*(0.5*0.1*act/(1+0.1*Math.abs(act))+0.5);
        return getK().getI().getD() *Math.log(act+Math.pow(Math.pow(act,2)+1,0.5));
//        return k.getI().getD() * Math.min(1, Math.max(0, act));
//        return 0.5*a * (v + c.getI().getD())/(1+a*Math.abs(v + c.getI().getD()) + 0.5);
//        return act;        
        
    }

    @Override
    public Activation_Function copy() {
        return new Activation_Generalized_ArcSIN((VDouble) getK().copy(), (VDouble) s.copy(), (VDouble) c.copy());
    }

    @Override
    public void updateParameters() {
        switch (ThreadLocalRandom.current().nextInt(7)) {
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