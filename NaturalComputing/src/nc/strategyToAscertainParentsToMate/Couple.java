/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate;

import nc.strategyToAscertainParentsToMate.fitness.StrategyFitnessCouple;
import java.util.ArrayList;
import nc.neurocomputing.layer.Layer;
import nc.solution.ChromosomeWeighted;
import nc.solution.Solution;
import nc.solution.Solution_Weighted;

/**
 * An ArrayList with all the parents that mate in this couple to create
 * offspring. Usually one would think in a couple of parents, but depending on
 * the mating strategy, other types of mating is possible
 *
 * @author Oscar Lao
 */
public class Couple extends ArrayList<Solution_Weighted> {

    /**
     * A couple whose fitness as couple is defined by fc
     *
     * @param fc
     */
    public Couple(StrategyFitnessCouple fc) {
        this.fc = fc;
    }

    // By default is the average.
    private StrategyFitnessCouple fc = null;

    /**
     * Get how well the Couple solves the problem we want to optimize.
     *
     * @return
     */
    public double getFitness() {
        return fc.fitness(this);
    }

    /**
     * Method to extract the chromosome with weights as defined by
     * Solution_PositionInPopulation from the couple
     *
     * @param chr
     * @return
     */
    public ArrayList<ChromosomeWeighted> chromosome_of_couple(int chr) {
        ArrayList<ChromosomeWeighted> chr_couple = new ArrayList<>();
        this.forEach((s) -> {
            chr_couple.add(s.get(chr));
        });
        return chr_couple;
    }
    
    /**
     * Generate a solution from parents by recombining the chromosomes
     * @return 
     */
    public Solution reproduce()
    {
        // Offspring solution
        Solution o = new Solution(this.get(0).getSolution().getF(), this.getFitness());
        o.setO(this.get(0).getSolution().getO());
        // For each layer of the solution
        for(int layer = 0; layer<this.get(0).getSolution().getF().size();layer++)
        {
            ArrayList<ChromosomeWeighted> chr_parents = new ArrayList<>();
            // Add the chromosome of the layer for each parent
            for(Solution_Weighted p:this)
            {
                chr_parents.add(p.get(layer));
            }
// Recombine the chromosomes to generate the variables of the new layer           
            o.add(((Layer)this.get(0).getSolution().getF().get(layer)).getRecombine().recombine(chr_parents));
        }
        return o;        
    }      
}
