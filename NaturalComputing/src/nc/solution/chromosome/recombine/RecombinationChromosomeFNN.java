/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.recombine;

import java.util.ArrayList;
import maths.random.RandomDirichlet;
import nc.solution.Chromosome;
import nc.solution.ChromosomeFNN;
import nc.solution.ChromosomeWeighted;
import nc.variable.vdouble.RealNumber;
import nc.variable.vneuron.CellBodyOutput;
import nc.variable.vneuron.VSynapsis;

/**
 *
 * @author Oscar Lao
 */
public class RecombinationChromosomeFNN extends RecombinationChromosomes {

    @Override
    public Chromosome recombine(ArrayList<ChromosomeWeighted> parents) {        
        // Probabilities of each parent
        int [] alphas = new int[parents.size()];
        for(int a=0;a<alphas.length;a++)
        {
            alphas[a] = 1;
        }
        RandomDirichlet rdir = new RandomDirichlet(alphas);
        double [] probs = rdir.sample();
        ChromosomeFNN rechr = (ChromosomeFNN)parents.get(0).getChr().copy();
// Empty the chromosome so we can fill it with the values recombined from the parents
        rechr.clear();
        int l = 0;
        for(ChromosomeWeighted ch:parents)
        {
            // Pick the ChromosomeFNN of the parent
            ChromosomeFNN chrFnn = (ChromosomeFNN)ch.getChr();
            // For each of the synapses of the parental chromosome, check
            for(VSynapsis syn:chrFnn)
            {
                // Do a copy of the parental synapsis so it can be added to the offspring in an independent way
                VSynapsis c = (VSynapsis)syn.copy();
                // If the synapsis reaches the output, then multiply the weight of the activation by the associated probability of the parental chromosome.
                if(c.getI().getOutput_neuron() instanceof CellBodyOutput)
                {
                    c.getI().getAf().getK().setI(new RealNumber(c.getI().getAf().getK().getI().getD()*probs[l]));
                }
                rechr.add(c);
            }
            l++;
        }
//        if(probs.length>1)    
//        {
//            System.out.print(java.util.Arrays.toString(probs) + " " + rechr.size());  
//        for(ChromosomeWeighted ch:parents)
//        {
//            System.out.print(" " + ch.getChr().size());
//        }
//        System.out.println("");
//        }        
        rechr.generate_network();
        return rechr;
    }
}
