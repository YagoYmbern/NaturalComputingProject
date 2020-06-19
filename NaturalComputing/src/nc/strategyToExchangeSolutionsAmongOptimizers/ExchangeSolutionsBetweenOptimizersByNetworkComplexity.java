/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToExchangeSolutionsAmongOptimizers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import maths.IntToInt;
import maths.KMeans;
import nc.MetaOptimizer;
import nc.Optimizer;
import nc.solution.Chromosome;
import nc.solution.ChromosomeFNN;
import nc.solution.Solution;
import nc.variable.vneuron.ComplexityOfFreeNeuronNetwork;

/**
 *
 * @author Oscar Lao
 */
public class ExchangeSolutionsBetweenOptimizersByNetworkComplexity extends StrategyToExchangeSolutionsAmongOptimizers {

    private final int size_optimizers, pick_best_solutions;

    public ExchangeSolutionsBetweenOptimizersByNetworkComplexity(int size_optimizers, int pick_best_solutions) {
        this.size_optimizers = size_optimizers;
        this.pick_best_solutions = pick_best_solutions;
    }

    /**
     * This class only works with solutions that have a ChromosomeFNN
     *
     * @param mo
     */
    @Override
    public void exchangeSolutions(MetaOptimizer mo) {
        // Do a run of each population using the new batch       
        ArrayList<Solution> ascertained_sols = new ArrayList<>();

        for (int s = 0; s < mo.size(); s++) {
            // Offspring
            ascertained_sols.addAll(mo.getOptimizer(s).generateOffspring());
            // Parents to be included
            if(!mo.getOptimizer(s).isEmpty())
            {
            ascertained_sols.addAll(mo.getOptimizer(s).subList(0, Math.min(mo.getOptimizer(s).size(),pick_best_solutions)));
            // The optimizer is cleaned
            mo.getOptimizer(s).clear();                
            }
        }

        // Complexity of the neural network
        ArrayList<ComplexityOfFreeNeuronNetwork> complexity = new ArrayList<>();

        for (int s = 0; s < ascertained_sols.size(); s++) {
            for (Chromosome c : ascertained_sols.get(s)) {
                if (c instanceof ChromosomeFNN) {
                    complexity.add(new ComplexityOfFreeNeuronNetwork(((ChromosomeFNN) c).getFr()));
                    break;
                }
            }
        }
// All dendrite-axon counts per neuron observed over all the solutions
        HashSet<IntToInt> intToint = new HashSet<>();

        complexity.forEach((cof) -> {
            intToint.addAll(cof.getIntToInt());
        });
// create a matrix with rows with offspring solutions, columns with the dendrite-axon observed        
        double[][] m = new double[ascertained_sols.size()][intToint.size()];

        Iterator<IntToInt> itera = intToint.iterator();
        // For each combination of dendrites and axons
        for (int v = 0; itera.hasNext(); v++) {
            IntToInt intt = itera.next();
            // Compute the mean and the standard deviation
            double x = 0;
            double x2 = 0;
            // For each of the solutions, get the number of times we see the dendrite-axon combination in neurons
            for (int i = 0; i < m.length; i++) {
                m[i][v] = complexity.get(i).getNumberOfNeuronsWithIntToInt(intt);
//                System.out.println(i + " / " + intt.getA() + " " + intt.getB() + " " + complexity.get(i).getNumberOfNeuronsWithIntToInt(intt));
                x += m[i][v];
                x2 += Math.pow(m[i][v], 2);
            }
            x /= m.length;
            x2 /= m.length;
            double sd = Math.pow(x2 - Math.pow(x, 2), 0.5);
            // Standardize the variable so it has a mean 0 and a sd of 1
            for (double[] m1 : m) {
                m1[v] = (m1[v] - x) / sd;
            }
        }

// Cluster the solutions by the statistics of complexity of the FreeNeuralNetwork
        int[] K = KMeans.k_means(m, mo.size());

//        System.out.println(mo.size());
// Add the solutions that belongs to the cluster to the optimizer        
        for (int i = 0; i < K.length; i++) {
//            System.out.println(K[i] + " " + java.util.Arrays.toString(m[i]));
            mo.getOptimizer(K[i]).add(ascertained_sols.get(i));
//            System.out.println(K[i] + " " + ascertained_sols.get(i).get(0).size());
        }

//        System.out.println("");
// If one of the optimizers is empty, it must be repopulated with solutions from a non_empty optimizer
        ArrayList<Optimizer> empty = new ArrayList<>();
        ArrayList<Optimizer> nnempty = new ArrayList<>();

        for (int s = 0; s < mo.size(); s++) {
            // Each optimizer must have at least two elements
            if (mo.getOptimizer(s).isEmpty()) {
                empty.add(mo.getOptimizer(s));
            } else {
                if (mo.getOptimizer(s).size() > 4) {
                    nnempty.add(mo.getOptimizer(s));
                }
            }
        }

        // If it is not empty, it means that we have optimizers that are empty. Repopulate them with solutions from other optimizers
        if (!empty.isEmpty()) {
            for (Optimizer e : empty) {
                // Pick a nnempty Optimizer
                int ennempty = ThreadLocalRandom.current().nextInt(nnempty.size());
                // remove two elements from the non empty optimizer. Add them to the empty Optimizer
                for (int i = 0; i < 2; i++) {
                    Solution s = nnempty.get(ennempty).remove(ThreadLocalRandom.current().nextInt(nnempty.get(ennempty).size()));
                    e.add(s);
                }
                // If we have less than four elements in the nnempty optimizer, then do not use it anymore to repopulate anything!
                if (nnempty.get(ennempty).size() < 4) {
                    nnempty.remove(ennempty);
                }
            }
        }

        for (int s = 0; s < mo.size(); s++) {
            // Sort to get the best in each category
            Collections.sort(mo.getOptimizer(s));
            // Remove size of solutions
            if (mo.getOptimizer(s).size() > 4) {
                // Pick the best 20 or, if there are less than 20, the best 50%
                int retain = Math.min(size_optimizers, (int) (mo.getOptimizer(s).size() * 0.5));
                mo.getOptimizer(s).subList(retain, mo.getOptimizer(s).size()).clear();

//                while(mo.getOptimizer(s).size()>retain)
//                {
//                    mo.getOptimizer(s).remove(retain);
//                }
//                List<Solution> best_sols =  mo.getOptimizer(s).subList(0,retain);
//                mo.getOptimizer(s).clear();
//                for(Solution ss:best_sols)
//                {
//                    mo.getOptimizer(s).add(ss);
//                }
                //mo.getOptimizer(s).addAll(best_sols);
            }
        }

//        for (int s = 0; s < mo.size(); s++) {
//            System.out.print(mo.getOptimizer(s).size() + " ");
//        }
//        System.out.println("");
    }
}
