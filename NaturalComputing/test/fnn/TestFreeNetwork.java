/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnn;

import data.input_output.InputData;
import data.input_output.OutputNN;
import data.input_output.OutputParameters;
import error.ErrorSumOfSquares;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import nc.MetaOptimizer;
import nc.function.optimize.Network;
import nc.input_output.InputOutput_Tensor;
import nc.input_output.InputOutput_Vector;
import nc.layer.neural_network.Layer_FreeNN;
import nc.neurocomputing.activation.Activation_Elliot;
import nc.neurocomputing.activation.Activation_Generalized_PLU;
import nc.neurocomputing.activation.Activation_Linear;
import nc.strategyToAscertainOffspring.brood_competition.FittestBroodSelection;
import nc.strategyToAscertainParentsToMate.mate.EachParentIsACouple;
import nc.strategyToCombineParentsAndOffspring.StrategyToCombineParentsAndOffspring;
import nc.strategyToExchangeSolutionsAmongOptimizers.ExchangeSolutionsBetweenOptimizersAtRandom;
import nc.strategyToGenerateSolutionsFromParents.Procreation;
import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.BroodByCoupleProportionalToFitness;
import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.FunctionToDecideNumberOfOffspring;
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
public class TestFreeNetwork {

    public static void main(String[] args) throws Exception {
        CellBodyInput a = new CellBodyInput(0,0,0);
        CellBodyInput x = new CellBodyInput(0,0,1);
        CellBodyHiddenImplemented b = new CellBodyHiddenImplemented(2);
        CellBodyHiddenImplemented c = new CellBodyHiddenImplemented(3);
        CellBodyOutput d = new CellBodyOutput(4);
        CellBodyOutput e = new CellBodyOutput(5);
        CellBodyHiddenImplemented z = new CellBodyHiddenImplemented(6);
        CellBodyHiddenImplemented y = new CellBodyHiddenImplemented(7);
        VSynapsis x_b = new VSynapsis(new Synapsis(x, b, new Activation_Generalized_PLU(new VDouble(new RealNumber(-1), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(0.5), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(3), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)))), new VSynapsisUpdater());
        VSynapsis x_z = new VSynapsis(new Synapsis(x, z, new Activation_Generalized_PLU(new VDouble(new RealNumber(3), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(1), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(-2), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)))), new VSynapsisUpdater());
        VSynapsis z_c = new VSynapsis(new Synapsis(z, c, new Activation_Generalized_PLU(new VDouble(new RealNumber(3), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(1), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(-2), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)))), new VSynapsisUpdater());
        VSynapsis z_z = new VSynapsis(new Synapsis(z, z, new Activation_Generalized_PLU(new VDouble(new RealNumber(3), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(1), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(-2), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)))), new VSynapsisUpdater());
        VSynapsis z_y = new VSynapsis(new Synapsis(z, y, new Activation_Generalized_PLU(new VDouble(new RealNumber(3), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(1), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(-2), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)))), new VSynapsisUpdater());
        VSynapsis z_d = new VSynapsis(new Synapsis(z, d, new Activation_Generalized_PLU(new VDouble(new RealNumber(-3), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(1), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)),new VDouble(new RealNumber(-2), new NStepSizeSelfAdaptation(0.1, 0.1, 0.5)))), new VSynapsisUpdater());
        VSynapsis a_b = new VSynapsis(new Synapsis(a, b, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        VSynapsis b_c = new VSynapsis(new Synapsis(b, c, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        VSynapsis c_c = new VSynapsis(new Synapsis(c, c, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        VSynapsis c_d = new VSynapsis(new Synapsis(c, d, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        VSynapsis c_y = new VSynapsis(new Synapsis(c, y, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        VSynapsis y_e = new VSynapsis(new Synapsis(y, e, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        VSynapsis a_d = new VSynapsis(new Synapsis(a, d, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        VSynapsis b_e = new VSynapsis(new Synapsis(b, e, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        VSynapsis d_e = new VSynapsis(new Synapsis(d, e, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        
        VSynapsis d_b = new VSynapsis(new Synapsis(d, b, new Activation_Generalized_PLU()), new VSynapsisUpdater());
        ArrayList<VSynapsis> synapses = new ArrayList<>();
        synapses.add(a_b);
        synapses.add(b_c);
        synapses.add(c_d);
        synapses.add(a_d);
        synapses.add(d_b);
        synapses.add(b_e);
        synapses.add(c_c);
        synapses.add(x_b);
        synapses.add(x_z);
        synapses.add(z_c);
        synapses.add(c_y);
        synapses.add(y_e);
        synapses.add(z_z);
        synapses.add(z_y);
        synapses.add(z_d);
        synapses.add(d_e);
        
        ArrayList<NeuronOutput> cb_output = new ArrayList<>();
        cb_output.add(new NeuronOutput(d, new Activation_Elliot(new VDouble(new RealNumber(0.1)))));
        cb_output.add(new NeuronOutput(e, new Activation_Elliot(new VDouble(new RealNumber(0.1)))));

        FreeNeuronNetwork fnn = new FreeNeuronNetwork(1,1,2, false, cb_output);
        fnn.build_network(synapses);

        InputData id = new InputData(0);
        OutputParameters out = new OutputParameters();

        for (int r = 0; r < 1000; r++) {
            double[][][][] v = new double[2][1][1][2];
            fnn.reset_activations();
            v[0][0][0][0] = ThreadLocalRandom.current().nextGaussian();
            v[0][0][0][1] = ThreadLocalRandom.current().nextGaussian();
            v[1][0][0][0] = ThreadLocalRandom.current().nextGaussian();
            v[1][0][0][1] = ThreadLocalRandom.current().nextGaussian();
            id.add(new InputOutput_Tensor(v));
            double [] outPut = fnn.predict(new InputOutput_Tensor(v));
            out.add(new InputOutput_Vector(outPut));
        }
        
        Network net = new Network(id, new ErrorSumOfSquares(out));

        net.add(new Layer_FreeNN(false, 2, new Activation_Linear()));

        MetaOptimizer mo = new MetaOptimizer(net, new ExchangeSolutionsBetweenOptimizersAtRandom(0));

        EachParentIsACouple eap = new EachParentIsACouple();

//        FunctionToDecideNumberOfOffspring fuk = new OffspringByCoupleFixNumber(1);
        FunctionToDecideNumberOfOffspring fuk = new BroodByCoupleProportionalToFitness(2, 8);

        Procreation rs = new Procreation(fuk);

        //NoCompetenceBetweenOffspringOfSameCouple sta = new NoCompetenceBetweenOffspringOfSameCouple();
        FittestBroodSelection sta = new FittestBroodSelection(0.5);

        StrategyToCombineParentsAndOffspring scp = new StrategyToCombineParentsAndOffspring(20, 1);

        mo.add_optimizer(eap, rs, sta, scp);

        for (int it = 0; it < 100; it++) {
            mo.nextIteration();
            System.out.println(mo.get_Best_Solution().getFitness());
        }
    }
}

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nc.variable.vneuron;
//
//import java.util.ArrayList;
//import nc.input_output.InputOutput_Vector;
//import nc.neurocomputing.activation.Activation_Generalized_Elliot;
//import nc.variable.vdouble.RealNumber;
//import nc.variable.vdouble.VDouble;
//
///**
// *
// * @author Oscar Lao
// */
//public class Test {
//
//    public static void main(String[] args) {
//        CellBody a = new CellBody(0);
//        CellBody b = new CellBody(1);
//        CellBody c = new CellBody(2);
//        CellBody d = new CellBody(3);
//        CellBody e = new CellBody(4);
//        VSynapsis a_b = new VSynapsis(new Synapsis(a, b, new Activation_Generalized_Elliot(), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
//        VSynapsis b_c = new VSynapsis(new Synapsis(b, c, new Activation_Generalized_Elliot(), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
//        VSynapsis c_c = new VSynapsis(new Synapsis(c, c, new Activation_Generalized_Elliot(), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
//        VSynapsis c_d = new VSynapsis(new Synapsis(c, d, new Activation_Generalized_Elliot(), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
//        VSynapsis a_d = new VSynapsis(new Synapsis(a, d, new Activation_Generalized_Elliot(), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
//        VSynapsis b_e = new VSynapsis(new Synapsis(b, e, new Activation_Generalized_Elliot(), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
//// Loop
//        VSynapsis d_b = new VSynapsis(new Synapsis(d, b,new Activation_Generalized_Elliot(), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
//        ArrayList<VSynapsis> synapses = new ArrayList<>();
//        synapses.add(a_b);
//        synapses.add(b_c);
//        synapses.add(c_d);
//        synapses.add(a_d);
//        synapses.add(d_b);
//        synapses.add(b_e);
//        synapses.add(c_c);
//        
//        ArrayList<CellBody> cb_input = new ArrayList<>();
//        cb_input.add(a);
//        
//        ArrayList<CellBody> cb_output = new ArrayList<>();
//        cb_output.add(d);
//        cb_output.add(e);
//
////        CellBody a = new CellBody(0);
////        CellBody d = new CellBody(3);
//////        CellBody e = new CellBody(4);        
////        VSynapsis a_d = new VSynapsis(new Synapsis(a,d, new Activation_Linear(new VDouble(new RealNumber(1))), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
////        VSynapsis d_d = new VSynapsis(new Synapsis(d,d, new Activation_Linear(new VDouble(new RealNumber(1))), new VDouble(new RealNumber(1))), new VSynapsisUpdater());
////// Loop
////        ArrayList<VSynapsis> synapses = new ArrayList<>();
////        synapses.add(a_d);
////        synapses.add(d_d);        
//        FreeNeuronNetwork fnn = new FreeNeuronNetwork(cb_input, cb_output);
//        fnn.build_network(synapses);
//
//        double[] v = new double[1];
//
//        v[0] = 4;
//
//        fnn.show_to_the_input(new InputOutput_Vector(v));
//
//        System.out.println(fnn.getOutput(d));
////        System.out.println(fnn.getOutput(e));  
//        System.out.println("*******************");
//        v[0] = 2;
//// show a new input
//        fnn.show_to_the_input(new InputOutput_Vector(v));
//        System.out.println(fnn.getOutput(d));
////        System.out.println(fnn.getOutput(e));  
//
////        
////        Neuron A = new NeuronInput(4);
////        NeuronHidden B = new NeuronHidden();
////        NeuronHidden C = new NeuronHidden();
////        NeuronHidden D = new NeuronHidden();
////        NeuronHidden E = new NeuronHidden();
////        D.add_dendrite_connection(new DendriteConnection(E, new Activation_Linear(new VDouble(new RealNumber(1))), new RealNumber(1)));
////        D.add_dendrite_connection(new DendriteConnection(C, new Activation_Linear(new VDouble(new RealNumber(1))), new RealNumber(1)));
////        C.add_dendrite_connection(new DendriteConnection(B, new Activation_Linear(new VDouble(new RealNumber(1))), new RealNumber(1)));
////        C.add_dendrite_connection(new DendriteConnection(E, new Activation_Linear(new VDouble(new RealNumber(1))), new RealNumber(1)));        
////        B.add_dendrite_connection(new DendriteConnection(A, new Activation_Linear(new VDouble(new RealNumber(1))), new RealNumber(1)));
////        E.add_dendrite_connection(new DendriteConnection(A, new Activation_Linear(new VDouble(new RealNumber(1))), new RealNumber(1)));
////        System.out.println(D.getActivationValue());
//    }
//}
