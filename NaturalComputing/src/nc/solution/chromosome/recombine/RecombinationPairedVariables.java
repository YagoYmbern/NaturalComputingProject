/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.recombine;

import java.util.ArrayList;
import nc.solution.Chromosome;
import nc.solution.ChromosomeWeighted;
import nc.variable.V;
import nc.variable.VWeighted;
import nc.variable.strategyToRecombine.StrategyToRecombineVariable;

/**
 * A strategy to recombine the chromosome of paired variables (the place of each
 * variable defines to which variables from the other chromosomes the variable
 * can recombine Use a default full linkage equilibrium recombination strategy
 *
 * @author Oscar Lao
 */
public class RecombinationPairedVariables extends RecombinationChromosomes {

    private final StrategyToRecombineVariable rec;

    /**
     * Create an object to recombine chromosomes whose variables are paired. Use
     * the recombine variable strategy
     *
     * @param rec
     */
    public RecombinationPairedVariables(StrategyToRecombineVariable rec) {
        this.rec = rec;
    }

    @Override
    public Chromosome recombine(ArrayList<ChromosomeWeighted> parents) {
        Chromosome rechr = parents.get(0).getChr().copy();
// Empty the chromosome so we can fill it with the values recombined from the parents
        rechr.clear();
        for(int v = 0; v < parents.get(0).getChr().size(); v++)
        {
            double [] w = new double[parents.size()];
            double t = 0;
            for(int ch=0;ch<parents.size();ch++)
            {
                w[ch] = parents.get(ch).getWeight();
                t+=w[ch];
            }            
            // Generate the Variable with the weights
            ArrayList<VWeighted> vs = new ArrayList<>();
            for(int ch=0;ch<parents.size();ch++)
            {
                vs.add(new VWeighted((V)parents.get(ch).getChr().get(v),w[ch]/t));
            }
            // Add the recombined variable
            rechr.add(rec.recombine(vs));
        }
        return rechr;
    }
}
