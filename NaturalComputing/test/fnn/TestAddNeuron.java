/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnn;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import nc.input_output.InputOutput_Tensor;
import nc.neurocomputing.activation.Activation_Function;
import nc.neurocomputing.activation.Activation_Generalized;
import nc.neurocomputing.activation.Activation_Generalized_Linear_Range_0_1;
import nc.neurocomputing.activation.Activation_Generalized_PLU;
import nc.neurocomputing.activation.Activation_Linear;
import nc.variable.vdouble.RealNumber;
import nc.variable.vdouble.VDouble;
import nc.variable.vdouble.updater.self_adaptation.NStepSizeSelfAdaptation;
import nc.variable.vneuron.CellBodyHidden;
import nc.variable.vneuron.CellBodyHiddenImplemented;
import nc.variable.vneuron.CellBodyInput;
import nc.variable.vneuron.CellBodyOutput;
import nc.variable.vneuron.FreeNeuronNetwork;
import nc.variable.vneuron.NeuronOutput;
import nc.variable.vneuron.Synapsis;
import nc.variable.vneuron.VSynapsis;
import nc.variable.vneuron.VSynapsisUpdater;

/**
 *
 * @author Oscar Lao
 */
public class TestAddNeuron {
    
    public static void main(String[] args) throws Exception {
        CellBodyInput x = new CellBodyInput(0, 0, 0);
        CellBodyOutput y = new CellBodyOutput(0);
        Activation_Generalized_PLU A_af = new Activation_Generalized_PLU();
        VSynapsis x_y = new VSynapsis(new Synapsis(x, y, A_af), new VSynapsisUpdater());
        ArrayList<VSynapsis> synapses = new ArrayList<>();
        synapses.add(x_y);
        
        ArrayList<NeuronOutput> cb_output = new ArrayList<>();
        cb_output.add(new NeuronOutput(y, new Activation_Linear()));
        
        FreeNeuronNetwork fnn = new FreeNeuronNetwork(1, 1, 2, false, cb_output);
        
        fnn.build_network(synapses);
        
        double[][][][] v = new double[1][1][1][1];
        fnn.reset_activations();
        v[0][0][0][0] = ThreadLocalRandom.current().nextGaussian();
        
        double[] output = fnn.predict(new InputOutput_Tensor(v));
        
        System.out.println("OUTPUT: " + output[0]);        
        
        CellBodyHiddenImplemented a = new CellBodyHiddenImplemented(3);
        // Between input and hidden
        VSynapsis A_a = new VSynapsis(new Synapsis(x, a, A_af), new VSynapsisUpdater());
        // New activation:
        Activation_Generalized_Linear_Range_0_1 afr_a_B = new Activation_Generalized_Linear_Range_0_1();
        VSynapsis a_B = new VSynapsis(new Synapsis(a, y, afr_a_B), new VSynapsisUpdater());
// Add the two connections: between input and hidden. Between hidden and output
        fnn = new FreeNeuronNetwork(1, 1, 2, false, cb_output);
        synapses = new ArrayList<>();
        synapses.add(A_a);
        synapses.add(a_B);
        fnn.build_network(synapses);
        fnn.reset_activations();
        
        output = fnn.predict(new InputOutput_Tensor(v));
        
        System.out.println("OUTPUT: " + output[0]);        
        
    }
}
