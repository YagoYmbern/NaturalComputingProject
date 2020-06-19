/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.activation;

import java.util.concurrent.ThreadLocalRandom;
import nc.variable.vdouble.VDouble;

/**
 *
 * @author olao
 */
public class Activation_Binary_In_Range extends Activation_Function {

    private VDouble min_weight;
    private VDouble max_weight;

    public Activation_Binary_In_Range(VDouble min_weight, VDouble max_weight) {
        if (min_weight.getI().getD() < max_weight.getI().getD()) {
            this.min_weight = min_weight;
            this.max_weight = max_weight;
        } else {
            this.min_weight = max_weight;
            this.max_weight = min_weight;
        }
    }

    public VDouble getMax_weight() {
        return max_weight;
    }

    public VDouble getMin_weight() {
        return min_weight;
    }

    @Override
    public double activate(double v) {
        if (v <= min_weight.getI().getD()) {
            return -1;
        }
        if (v >= max_weight.getI().getD()) {
            return 1;
        }
        return 0;
    }

    @Override
    public void updateParameters() {
        double p = ThreadLocalRandom.current().nextDouble();
        if (p <= 0.5) {
            min_weight.apply_update();
        } else {
            max_weight.apply_update();
        }
        if (min_weight.getI().getD() > max_weight.getI().getD()) {
            VDouble mink = min_weight;
            this.min_weight = max_weight;
            this.max_weight = mink;
        }
    }

    /**
     * Get a copy of this activation function
     *
     * @return
     */
    public Activation_Binary_In_Range copy() {
        Activation_Binary_In_Range ng = new Activation_Binary_In_Range((VDouble) min_weight.copy(), (VDouble) max_weight.copy());
        return ng;
    }
}
