/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.recombine;

import java.util.ArrayList;
import java.util.HashSet;
import maths.random.RandomUniformWithoutResampling;
import nc.solution.Chromosome;
import nc.solution.ChromosomeWeighted;
import nc.variable.V;

/**
 * This class implements a to generate a new chromosome from parental
 * chromosomes with the constraint that none of the elements (variables) in the
 * offspring chromosome can be repeated according to the equal function of the
 * variable
 *
 * @author Oscar Lao
 */
public class RecombinationNonRepeatedVariables extends RecombinationChromosomes {

    /**
     * Recombine the chromosomes
     *
     * @param parents
     * @return
     */
    @Override
    public Chromosome recombine(ArrayList<ChromosomeWeighted> parents) {
        Chromosome o = parents.get(0).getChr().copy();
        o.clear();
        // non repeated variables will contain all the set variables from all the parents
        HashSet<V> non_repeated_variables = new HashSet<>();
        parents.forEach((p) -> {
            p.getChr().forEach((v) -> {
                non_repeated_variables.add((V)v);
            });
        });

// Pick at random the subset of n variables (same size as the parents). First load the set of variables in v, so we do not have to do an iteration when accessing the ascertained variables
        ArrayList<V> v = new ArrayList(non_repeated_variables);
// Random uniform without resampling
        RandomUniformWithoutResampling ran = new RandomUniformWithoutResampling(0, non_repeated_variables.size());
        int[] ids = ran.sample(parents.get(0).getChr().size());
        for (int i : ids) {
            o.add(v.get(i).copy());
        }
        return o;
    }
}
