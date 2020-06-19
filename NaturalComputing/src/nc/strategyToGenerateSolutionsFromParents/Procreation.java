/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToGenerateSolutionsFromParents;

import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.FunctionToDecideNumberOfOffspring;
import nc.strategyToAscertainParentsToMate.Couple;
import nc.strategyToAscertainParentsToMate.Brood;

/**
 *
 * @author Oscar Lao
 */
public class Procreation {

    private FunctionToDecideNumberOfOffspring f;

    /**
     * Define a strategy to generate new solutions from a Couple of parents.We
     * must decide how many offspring we will generate from the Couple (take
     * into account that a couple could be a single solution) and the mutation
     * strategy that we are going to follow
     *
     * @param f
     */
    public Procreation(FunctionToDecideNumberOfOffspring f) {
        this.f = f;
    }

    /**
     * Set the function to decide the number of offspring given the parents
     *
     * @param f
     */
    public void setF(FunctionToDecideNumberOfOffspring f) {
        this.f = f;
    }

    /**
     * Get the function to decide the number of offspring given the parents
     *
     * @return
     */
    public FunctionToDecideNumberOfOffspring getF() {
        return f;
    }

    /**
     * Generate solutions from a couple using a strategy
     *
     * @param parents
     * @return
     */
    public Brood getBrood(Couple parents) {
        Brood offspring = generate_Brood(parents);
// Mutate the offspring using the update function
        offspring.forEach((s) -> {
            s.update();
        });
        return offspring;
    }
    
    /**
     * Generate solutions from a couple
     *
     * @param parents
     * @return
     */
    private Brood generate_Brood(Couple parents) {
        Brood offspring = new Brood(parents);
// number of offspring of this couple
        int size = getF().number_of_descendents_of_this_Couple(parents);
// for each offspring, generate it by mixing the parents
        for (int r = 0; r < size; r++) {
            offspring.add(parents.reproduce());
        }
// return the offspring
        return offspring;
    }       
}
