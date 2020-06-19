/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution;

import java.util.concurrent.ThreadLocalRandom;
import maths.random.RandomMultinomial;
import maths.random.RandomUniformWithoutResampling;
import nc.neurocomputing.activation.Activation_Function;
import nc.neurocomputing.activation.Activation_Generalized_Binary;
import nc.neurocomputing.activation.Activation_Generalized_Gaussian;
import nc.neurocomputing.activation.Activation_Generalized_Leaky_ReLU;
import nc.neurocomputing.activation.Activation_Generalized_Linear_Range_0_1;
import nc.neurocomputing.activation.Activation_Generalized;
import nc.neurocomputing.activation.Activation_Generalized_ReLU;
import nc.neurocomputing.activation.Activation_Generalized_SIN;

/**
 *
 * @author Oscar Lao
 */
public class ArrayOfPossibleActivations {

    private double[] frequency;
    private Activation_Generalized[] activations;

    public ArrayOfPossibleActivations() {
        frequency = new double[5];
        for (int f = 0; f < frequency.length; f++) {
            frequency[f] = 1.0 / frequency.length;
        }
        activations = new Activation_Generalized[frequency.length];
        activations[1] = new Activation_Generalized_SIN();
        activations[0] = new Activation_Generalized_Binary();
        activations[2] = new Activation_Generalized_ReLU();
        activations[3] = new Activation_Generalized_Leaky_ReLU();
        activations[4] = new Activation_Generalized_Linear_Range_0_1();
    }

    public ArrayOfPossibleActivations(double[] frequency, Activation_Generalized[] activations) {
        this.frequency = frequency;
        this.activations = activations;
    }

    /**
     * Generate a new activation using the stored probability of being an
     * activation function
     *
     * @return
     */
    public Activation_Generalized generate_a_new_activation() {
        RandomMultinomial rmult = new RandomMultinomial(frequency);
        return (Activation_Generalized)activations[rmult.sample(1)[0]].copy();
    }

    /**
     * Update the probability of ascertaining a particular activation function
     */
    public void updateProbability() {
//        RandomUniformWithoutResampling ruf = new RandomUniformWithoutResampling(0, frequency.length);
//        int[] i = ruf.sample(2);
//        double b = ThreadLocalRandom.current().nextDouble();
//        double i1 = frequency[i[0]];
//        double i2 = frequency[i[1]];
//        frequency[i[0]] = b * (i1 + i2);
//        frequency[i[1]] = (1 - b) * (i1 + i2);
    }

    public void updateActivations() {
        // Update the activations
        for (Activation_Function a : activations) {
            a.updateParameters();
        }
    }

    /**
     * Get a copy
     *
     * @return
     */
    public ArrayOfPossibleActivations copy() {
        double[] f = new double[frequency.length];
        System.arraycopy(frequency, 0, f, 0, f.length);
        Activation_Generalized[] af = new Activation_Generalized[activations.length];
        for (int a = 0; a < activations.length; a++) {
            af[a] = (Activation_Generalized)activations[a].copy();
        }
        return new ArrayOfPossibleActivations(f, af);
    }

}
