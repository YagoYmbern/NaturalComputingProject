/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnn;

import data.input_output.InputData;
import data.input_output.OutputParameters;
import error.ErrorSumOfSquares;
import java.util.concurrent.ThreadLocalRandom;
import nc.MetaOptimizer;
import nc.function.optimize.Network;
import nc.input_output.InputOutput_Tensor;
import nc.input_output.InputOutput_Vector;
import nc.layer.neural_network.Layer_FreeNN;
import nc.neurocomputing.activation.Activation_Linear;
import nc.strategyToAscertainOffspring.brood_competition.FittestBroodSelection;
import nc.strategyToAscertainParentsToMate.mate.EachParentIsACouple;
import nc.strategyToCombineParentsAndOffspring.StrategyToCombineParentsAndOffspring;
import nc.strategyToExchangeSolutionsAmongOptimizers.ExchangeSolutionsBetweenOptimizersAtRandom;
import nc.strategyToExchangeSolutionsAmongOptimizers.ExchangeSolutionsBetweenOptimizersByNetworkComplexity;
import nc.strategyToGenerateSolutionsFromParents.Procreation;
import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.BroodByCoupleProportionalToFitness;
import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.FunctionToDecideNumberOfOffspring;
import nc.variable.vneuron.FreeNeuronNetwork;
import nc.solution.ChromosomeFNN;
import nc.strategyToAscertainOffspring.brood_competition.AbsenceBroodCompetition;
import nc.variable.vdouble.RealNumber;
import nc.variable.vdouble.VDouble;

/**
 *
 * @author Oscar Lao
 */
public class TestNonLinearFunction {

    public static void main(String[] args) throws Exception {
        InputData id = new InputData(0);
        OutputParameters out = new OutputParameters();
        double c1 = 1;
        double c2 = 2;
        double c3 = 2;
        double c4 = 1;

        for (int r = 0; r < 500; r++) {
            double[][][][] v = new double[2][1][1][2];
            v[0][0][0][0] = 4 * ThreadLocalRandom.current().nextDouble() - 2;
            v[0][0][0][1] = 4 * ThreadLocalRandom.current().nextDouble() - 2;
            v[1][0][0][0] = v[0][0][0][0] + 0.5 * ThreadLocalRandom.current().nextGaussian();
            v[1][0][0][1] = v[0][0][0][1] + 0.5 * ThreadLocalRandom.current().nextGaussian();
//            v[2][0][0][0] = v[0][0][0][0];
//            v[2][0][0][1] = v[0][0][0][1];
//            v[3][0][0][0] = v[0][0][0][0];
//            v[3][0][0][1] = v[0][0][0][1];
            id.add(new InputOutput_Tensor(v));
            double[] outPut = new double[2];
            outPut[0] = Math.pow(Math.cos(c1 * v[1][0][0][0]) - Math.cos(c2 * v[1][0][0][1]), 3);
            outPut[1] = Math.pow(Math.sin(c3 * v[1][0][0][0]) - Math.sin(c4 * v[1][0][0][1]), 3);

            out.add(new InputOutput_Vector(outPut));
        }

        System.out.println("SIZE TO TRAIN " + id.size());

        Network net = new Network(id, new ErrorSumOfSquares(out));

        net.add(new Layer_FreeNN(false, out.get(0).getV().length, new Activation_Linear()));//new Activation_Elliot(new VDouble(new RealNumber(0.01)))));

//        MetaOptimizer mo = new MetaOptimizer(net, new ExchangeSolutionsBetweenOptimizersByNetworkComplexity(40, 1));

        ExchangeSolutionsBetweenOptimizersAtRandom ex = new ExchangeSolutionsBetweenOptimizersAtRandom(0);

        MetaOptimizer mo = new MetaOptimizer(net, ex);
        EachParentIsACouple eap = new EachParentIsACouple();

//        FunctionToDecideNumberOfOffspring fuk = new OffspringByCoupleFixNumber(1);
        FunctionToDecideNumberOfOffspring fuk = new BroodByCoupleProportionalToFitness(2, 8);

        Procreation rs = new Procreation(fuk);

        //AbsenceBroodCompetition sta = new AbsenceBroodCompetition();
        FittestBroodSelection sta = new FittestBroodSelection(0.5);

        StrategyToCombineParentsAndOffspring scp = new StrategyToCombineParentsAndOffspring(20, 1);

        mo.add_optimizer(eap, rs, sta, scp);
        mo.add_optimizer(eap, rs, sta, scp);
        mo.add_optimizer(eap, rs, sta, scp);
        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);
//        mo.add_optimizer(eap, rs, sta, scp);

        for (int it = 1; it < 400; it++) {
            if(it%20==0)
            {
                ex.setNumber_of_solutions_to_exchange_by_Optimizer(1);
            }
            else
            {
                ex.setNumber_of_solutions_to_exchange_by_Optimizer(0);                
            }
            mo.nextIteration();
//            if(it%10==0)
//            {
//                mo.add_optimizer(eap, rs, sta, scp);                
//            }
            System.out.println(it + " " + mo.get_Best_Solution().getParentalCareFitness() + " " + mo.get_Best_Solution().getFitness() + " " + ((ChromosomeFNN)mo.get_Best_Solution().get(0)).size());
        }
// Test
        FreeNeuronNetwork fr = ((ChromosomeFNN) mo.get_Best_Solution().get(0)).getFr();

        fr.set_in_training_mode(false);

        System.out.println("TRAINED SIZE " + id.size());

        for (int r = 0; r < id.size(); r++) {
            fr.reset_activations();
            InputOutput_Tensor nid = id.get(r);
//InputOutput_Tensor nid = id.get(r);            
            double[] pr = fr.predict(nid);
            System.out.println(r + " " + pr[0] + " " + out.get(r).getV()[0]);// + " " + pr[1] + " " + outPut[1]);
        }

        System.out.println("**********");

        for (int r = 0; r < id.size(); r++) {
            fr.reset_activations();
            double[][][][] v = new double[2][1][1][2];
            v[0][0][0][0] = 4 * ThreadLocalRandom.current().nextDouble() - 2;
            v[0][0][0][1] = 4 * ThreadLocalRandom.current().nextDouble() - 2;
            v[1][0][0][0] = v[0][0][0][0] + 0.01 * ThreadLocalRandom.current().nextGaussian();
            v[1][0][0][1] = v[0][0][0][1] + 0.01 * ThreadLocalRandom.current().nextGaussian();
//            v[2][0][0][0] = v[0][0][0][0];
//            v[2][0][0][1] = v[0][0][0][1];
//            v[3][0][0][0] = v[0][0][0][0];
//            v[3][0][0][1] = v[0][0][0][1];
            double[] outPut = new double[2];
            outPut[0] = Math.pow(Math.cos(c1 * v[1][0][0][0]) - Math.cos(c2 * v[1][0][0][1]), 3);
            outPut[1] = Math.pow(Math.sin(c3 * v[1][0][0][0]) - Math.sin(c4 * v[1][0][0][1]), 3);
            InputOutput_Tensor nid = new InputOutput_Tensor(v);
//InputOutput_Tensor nid = id.get(r);            
            double[] pr = fr.predict(nid);
            System.out.println(r + " " + pr[0] + " " + outPut[0] + " " + pr[1] + " " + outPut[1]);
        }
        System.exit(0);
    }
}
