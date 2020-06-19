/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import data.input_output.InputData;
import data.input_output.OutputNN;
import data.input_output.OutputParameters;
import error.ErrorFrequencySumOfSquares;
import error.ErrorSumOfSquares;
import file.ReadSimulation;
import file.SimulationData;
import java.util.ArrayList;
import java.util.LinkedList;
import nc.MetaOptimizer;
import nc.function.optimize.Network;
import nc.input_output.InputOutput_Tensor;
import nc.input_output.InputOutput_Vector;
import nc.layer.neural_network.Layer_FreeNN;
import nc.neurocomputing.activation.Activation_Elliot;
import nc.neurocomputing.activation.Activation_Linear;
import nc.neurocomputing.activation.Activation_Linear_Range_0_1;
import nc.neurocomputing.activation.Activation_ReLU;
import nc.solution.ChromosomeFNN;
import nc.solution.chromosome.update.StrategyToUpdateChromosomeFNN;
import nc.strategyToAscertainOffspring.brood_competition.AbsenceBroodCompetition;
import nc.strategyToAscertainOffspring.brood_competition.FittestBroodSelection;
import nc.strategyToAscertainParentsToMate.couple.CoupleRandomSize;
import nc.strategyToAscertainParentsToMate.fitness.FitnessCoupleAverage;
import nc.strategyToAscertainParentsToMate.mate.EachParentIsACouple;
import nc.strategyToAscertainParentsToMate.mate.ReproductiveSystem;
import nc.strategyToAscertainParentsToMate.weight.ProbabilityParentRank;
import nc.strategyToCombineParentsAndOffspring.StrategyToCombineParentsAndOffspring;
import nc.strategyToExchangeSolutionsAmongOptimizers.ExchangeSolutionsBetweenOptimizersAtRandom;
import nc.strategyToExchangeSolutionsAmongOptimizers.ExchangeSolutionsBetweenOptimizersByNetworkComplexity;
import nc.strategyToGenerateSolutionsFromParents.Procreation;
import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.BroodByCoupleProportionalToFitness;
import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.FunctionToDecideNumberOfOffspring;
import nc.variable.vdouble.RealNumber;
import nc.variable.vdouble.VDouble;
import nc.variable.vdouble.updater.self_adaptation.NStepSizeSelfAdaptation;
import nc.variable.vneuron.FreeNeuronNetwork;
import read.ReadFile;

/**
 *
 * @author Oscar Lao
 */
public class GenerateNNByNeuroEvolution {

    public static void main(String[] args) throws Exception {
        ReadFile rf = new ReadFile("C:\\Users\\yagoy\\OneDrive\\Documentos\\PRACTICAS\\Codigo 2\\NaturalComputingProject\\sims.sim");

        ReadSimulation rsim = new ReadSimulation(rf);
// Simulation by individual
        ArrayList<SimulationData> sims = rsim.nextSimulation();

        InputData id = new InputData(0);

        OutputParameters out = new OutputParameters();

        int counter = 0;

//        double[][] min_max = new double[sims.get(0).getInputOutput().get()[0].length][2];
//
//        for (int c = 0; c < min_max.length; c++) {
//            min_max[c][0] = Double.MAX_VALUE;
//            min_max[c][1] = Double.MIN_VALUE;
//        }
        while (sims != null && counter < 250) {
            for (SimulationData s : sims) {
                InputOutput_Vector io = s.getOutputParameter();//generate_output(s);
                out.add(io);
                InputOutput_Tensor i = s.getInputOutput();//generate_input(s);
//                for (int t = 0; t < i.getN_time_series(); t++) {
//                    for (int ch = 0; ch < i.getN_channel(); ch++) {
//                        if (min_max[ch][0] > i.get(t, ch, 0, 0)) {
//                            min_max[ch][0] = i.get(t, ch, 0, 0);
//                        }
//                        if (min_max[ch][0] < i.get(t, ch, 0, 0)) {
//                            min_max[ch][1] = i.get(t, ch, 0, 0);
//                        }
//                    }
//                }
                id.add(i);
            }
            sims = rsim.nextSimulation();
            counter++;
        }

//        for (InputOutput_Tensor i : id) {
//            double[][][][] d = i.get();
//            for (int t = 0; t < i.getN_time_series(); t++) {
//                for (int ch = 0; ch < i.getN_channel(); ch++) {
//                    d[t][ch][0][0] = (d[t][ch][0][0] - min_max[ch][0]) / (min_max[ch][1] - min_max[ch][0]);
//                }
//            }
//        }
        System.out.println("SIZE TO TRAIN " + id.size());

        Network net = new Network(id, new ErrorSumOfSquares(out));

//        net.add(new Layer_FreeNN(true, 1, new Activation_Elliot(new VDouble(new RealNumber(0.1)))));
        net.add(new Layer_FreeNN(true, 1, new Activation_Linear_Range_0_1(new VDouble(new RealNumber(0.01)), new VDouble(new RealNumber(0)))));

//        net.add(new Layer_FreeNN(true, 1, new Activation_Linear(new VDouble(new RealNumber(0.1)), new VDouble(new RealNumber(0.0)))));
//        MetaOptimizer mo = new MetaOptimizer(net, new ExchangeSolutionsBetweenOptimizersByNetworkComplexity(40, 1));
        ExchangeSolutionsBetweenOptimizersAtRandom ex = new ExchangeSolutionsBetweenOptimizersAtRandom(0);
//         ExchangeSolutionsBetweenOptimizersByNetworkComplexity ex = new ExchangeSolutionsBetweenOptimizersByNetworkComplexity(20, 1);//new ExchangeSolutionsBetweenOptimizersAtRandom(0);

        MetaOptimizer mo = new MetaOptimizer(net, ex);
        ReproductiveSystem rep = new ReproductiveSystem(20, new FitnessCoupleAverage(), new ProbabilityParentRank(), new CoupleRandomSize(2,3));
        
        EachParentIsACouple eap = new EachParentIsACouple();        
//        FunctionToDecideNumberOfOffspring fuk = new OffspringByCoupleFixNumber(1);
        FunctionToDecideNumberOfOffspring fuk = new BroodByCoupleProportionalToFitness(2, 8);

        Procreation rs = new Procreation(fuk);

        //AbsenceBroodCompetition sta = new AbsenceBroodCompetition();
        FittestBroodSelection sta = new FittestBroodSelection(0.5);

        StrategyToCombineParentsAndOffspring scp = new StrategyToCombineParentsAndOffspring(20, 0);

        mo.add_optimizer(eap, rs, sta, scp);
        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);

 

        for (int it = 1; it < 200; it++) {
            if(it==101)
            {
                mo.getOptimizer(0).setMas(rep);
                mo.getOptimizer(1).setMas(rep);                
            }
            else
            {
                mo.getOptimizer(0).setMas(eap);
                mo.getOptimizer(1).setMas(rep);                
            }
            if (it == 100) {
                ex.setNumber_of_solutions_to_exchange_by_Optimizer(10);
            } else {
                ex.setNumber_of_solutions_to_exchange_by_Optimizer(0);
            }
            mo.nextIteration();
//            if(it%10==0)
//            {
//                mo.add_optimizer(eap, rs, sta, scp);                
//            }

            System.out.println(it + " " + mo.get_Best_Solution().getParentalCareFitness() + " " + mo.get_Best_Solution().getFitness() + " " + ((ChromosomeFNN) mo.get_Best_Solution().get(0)).size());
        }

//        ((StrategyToUpdateChromosomeFNN) ((Layer_FreeNN) net.get(0)).getStu()).do_not_update_topology();
//        for (int it = 700; it < 800; it++) {
//            if (it % 20 == 0) {
//                ex.setNumber_of_solutions_to_exchange_by_Optimizer(1);
//            } else {
//                ex.setNumber_of_solutions_to_exchange_by_Optimizer(0);
//            }
//            mo.nextIteration();
////            if(it%10==0)
////            {
////                mo.add_optimizer(eap, rs, sta, scp);                
////            }
//            System.out.println(it + " " + mo.get_Best_Solution().getParentalCareFitness() + " " + mo.get_Best_Solution().getFitness() + " " + ((ChromosomeFNN) mo.get_Best_Solution().get(0)).size());
//        }
// Test
        FreeNeuronNetwork fr = ((ChromosomeFNN) mo.get_Best_Solution().get(0)).getFr();

        fr.set_in_training_mode(false);

        System.out.println("TRAINED SIZE " + id.size());

        for (int r = 0; r < id.size(); r++) {
            fr.reset_activations();
            InputOutput_Tensor nid = id.get(r);
//InputOutput_Tensor nid = id.get(r);            
            double[] pr = fr.predict(nid);
            for (int cc = 0; cc < pr.length; cc++) {
                System.out.println(r + " " + pr[cc] + " " + out.get(r).getV()[cc]);
            }
        }

        System.out.println("****************************");

        for (int r = 0; r < 300; r++) {
            for (SimulationData s : sims) {
                fr.reset_activations();
                InputOutput_Tensor iot = s.getInputOutput();//generate_input(s);
//            double[][][][] d = iot.get();
//            for (int t = 0; t < iot.getN_time_series(); t++) {
//                for (int ch = 0; ch < iot.getN_channel(); ch++) {
//                    d[t][ch][0][0] = (d[t][ch][0][0] - min_max[ch][0]) / (min_max[ch][1] - min_max[ch][0]);
//                }
//            }

                InputOutput_Vector o = s.getOutputParameter();///generate_output(s);
                double[] pr = fr.predict(iot);
                for (int cc = 0; cc < pr.length; cc++) {
                    System.out.println(r + " " + pr[cc] + " " + o.getV()[cc]);
                }
            }
            sims = rsim.nextSimulation();
        }
        rf.close();
        System.exit(0);
    }

    public static InputOutput_Tensor generate_input(SimulationData s) {
        double[][][][] new_t = new double[s.getInputOutput().get().length - 2][s.getInputOutput().get()[0].length][1][3];
        for (int t = 1; t < s.getInputOutput().get().length - 1; t++) {
            for (int t2 = 0; t2 < 3; t2++) {
                for (int ch = 0; ch < s.getInputOutput().get()[0].length; ch++) {
                    new_t[t - 1][ch][0][t2] = s.getInputOutput().get()[t + t2 - 1][ch][0][0];
                }
            }
        }
        return new InputOutput_Tensor(new_t);
    }

    public static InputOutput_Vector generate_output(SimulationData s) {
        double[] v = s.getOutputParameter().getV();
        double[] new_v = new double[v.length - 2];
        System.arraycopy(v, 1, new_v, 0, new_v.length);
        return new InputOutput_Vector(new_v);
    }

}
