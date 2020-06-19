/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.neurocomputing.activation;

import nc.variable.vdouble.GeneratorVDoubleGaussian;
import nc.variable.vdouble.VDouble;
import nc.variable.vdouble.updater.self_adaptation.NStepSizeSelfAdaptation;

/**
 * Defines the activation function: f(x) = k*[max(s*(x+c)-c, min(s*(x+c)+c, x))]
 *
 * @author Oscar Lao
 */
public abstract class Activation_Generalized  extends Activation_Function{
private final VDouble k;
    public Activation_Generalized(VDouble k)
    {
        this.k = k;
    }
    
    public Activation_Generalized()
    {
        this.k = new GeneratorVDoubleGaussian(new NStepSizeSelfAdaptation(1, 0.01, 0.01), 0, 10).generateVariable();        
    }

    
    /**
     * Get the weight term
     *
     * @return
     */
    public VDouble getK() {
        return k;
    }    

}
