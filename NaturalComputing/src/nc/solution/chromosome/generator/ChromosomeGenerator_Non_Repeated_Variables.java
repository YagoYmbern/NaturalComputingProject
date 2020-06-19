/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.generator;

import nc.solution.Chromosome;
import nc.variable.initialize.Generator_Variable;
import nc.variable.V;

/**
 * Create a chromosome that forces all the variables in the chromosome to be non repeated.
 * Each variable is compared to the other variables in the same chromosome by means of the equals method
 * implemented by each type of variable.
 * @author Oscar Lao
 */
public class ChromosomeGenerator_Non_Repeated_Variables extends ChromosomeGenerator {

    public ChromosomeGenerator_Non_Repeated_Variables(Generator_Variable init) {
        super(init);
    }

    /**
     * Generate the chromosome of elements that are not repeated in the chromosome
     *
     * @param size
     * @return
     */
    @Override
    public Chromosome generateChromosome(int size) {
        Chromosome variable = new Chromosome();
        for (int e = 0; e < size; e++) {
            V v = (init).generateVariable();
            boolean is_repeated;
            do {
                // Start assuming that the variable v is not included in the the chromosome
                is_repeated = false;
                for (Object vv : variable) {
                    if (v.equals(vv)) {
                        // if the variable equals to another variable in the chromosome, then it is repeated. Do not continue, just break
                        is_repeated = true;
                        break;
                    }
                }
            } while (is_repeated); // Create a new variable and add it to the arraylist
            // If it is not repeated
            variable.add(v);
        }
        return variable;
    }
}
