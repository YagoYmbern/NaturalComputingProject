/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToGenerateSolutionsGivenParents.number_of_offspring;

import nc.strategyToAscertainParentsToMate.Couple;

/**
 *
 * @author olao
 */
public class BroodByCoupleProportionalToFitness extends FunctionToDecideNumberOfOffspring {

    /**
     * Generate the number of offspring following the invasive-weed strategy (Natural Computing)
     * @param min_number_offspring
     * @param max_number_offspring 
     */
    public BroodByCoupleProportionalToFitness(int min_number_offspring, int max_number_offspring) {
        this.min_number_offspring = min_number_offspring;
        this.max_number_offspring = max_number_offspring;
    }

    private int min_number_offspring, max_number_offspring;

    /**
     * Get the maximum number of offspring that a parent can have, given its
     * weight
     *
     * @param max_number_offspring
     */
    public void setMax_number_offspring(int max_number_offspring) {
        this.max_number_offspring = max_number_offspring;
    }

    /**
     * Get the minimum number of offspring that a parent can have, given its
     * weight
     *
     * @param min_number_offspring
     */
    public void setMin_number_offspring(int min_number_offspring) {
        this.min_number_offspring = min_number_offspring;
    }

    /**
     * Get the maximum number of offspring that one can get
     *
     * @return
     */
    public int getMax_number_offspring() {
        return max_number_offspring;
    }

    /**
     * Get the minimum number of offspring that one can get
     *
     * @return
     */
    public int getMin_number_offspring() {
        return min_number_offspring;
    }

    /**
     * Decide the number of offspring as the average of solutions between
     * max_number_offspring, min_number_offspring and the position of each
     * individual in the population according to its fitness
     *
     * @param parents
     * @return
     */
    @Override
    public int number_of_descendents_of_this_Couple(Couple parents) {
        // number of offspring
        int size = 0;
        // compute the total number of offspring for all the parents
        size = parents.stream().map((p) -> p.getWeight()).map((position) -> (int) ((max_number_offspring - min_number_offspring) * position) + min_number_offspring).reduce(size, Integer::sum);// Position is determined by weight, which is determined by the fitness of the solution in the population of solutions
        // return the average
        return size / parents.size();
    }
}
