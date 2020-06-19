/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.update;

import nc.solution.Chromosome;
import nc.variable.V;

/**
 * A strategy to mutate the elements of a chromosome following a non repeated strategy
 * The elements of the chromosome cannot be duplicated
 * @author Oscar Lao
 */
public class StrategyToUpdateChromosome_Of_Non_Repeated_Variables extends StrategyToUpdateChromosomeGeneric {

    /**
     * A strategy to update a chromosome that forces the ascertained values to
     * be non-repeated within the chromosome
     *
     */
    public StrategyToUpdateChromosome_Of_Non_Repeated_Variables() {
    }

    /**
     * Update the chromosome.Force to be 
     * @param ch_filters
     * @param c 
     */
    @Override
    protected void n_update(int[] ch_filters, Chromosome c) {
        for (int ch_filter : ch_filters) {
            // variable to decide if we have to iterate picking a new id because the one we took at ch_filter is already included in id
            boolean channel_is_repeated;
            // attempt to pick a new id is stored in proposed
            V proposed;
            do {
                channel_is_repeated = false;
                // Apply a change in the proposed element
                proposed = ((V)c.get(ch_filter)).copy();
                proposed.apply_update();
                for (Object i : c) {
                    if ((i).equals(proposed)) {
                        channel_is_repeated = true;
                        break;
                    }
                }
            } while (channel_is_repeated);
            c.set(ch_filter, proposed);
        }
    }
}
