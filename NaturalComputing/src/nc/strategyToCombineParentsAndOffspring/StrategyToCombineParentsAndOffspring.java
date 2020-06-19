/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToCombineParentsAndOffspring;

import java.util.ArrayList;
import java.util.Collections;
import nc.solution.Solution;

/**
 *
 * @author olao
 */
public class StrategyToCombineParentsAndOffspring {

    // size of solutions
    private int size_of_solutions;
    // frequency of parents we want to keep to the next generation
    private int n_parents;
    
    /**
     * Object to decide how to combine the parents and offspring.
     * Size_of_solutions is the final number of solutions that we will consider.
     * p_parents is the percentage of parents (respect to the parents) that we
     * are going to keep in the next generation. 0 means completely replacement
     * by the offspring. 1 means no replacement at all by the offspring. In this case, the population does not evolve.
     *
     * @param size_of_solutions
     * @param n_parents the number of best parents to retain from the previous generation
     */
    public StrategyToCombineParentsAndOffspring(int size_of_solutions, int  n_parents) {
        this.size_of_solutions = size_of_solutions;
        this.n_parents = n_parents;
    }


    /**
     * Get the number of best parents to be retained in the next generation
     *
     * @return
     */
    public int getN_parents() {
        return n_parents;
    }

    /**
     * Set the size of the solutions
     *
     * @param size_of_solutions
     */
    public void setSize_of_solutions(int size_of_solutions) {
        this.size_of_solutions = size_of_solutions;
    }

    /**
     * Get the size of the solutions
     *
     * @return
     */
    public int getSize_of_solutions() {
        return size_of_solutions;
    }

    /**
     * Retrieve the list of the final elements
     *
     * @param parents
     * @param offspring
     * @return
     */
    public ArrayList<Solution> ascertain_members(ArrayList<Solution> parents, ArrayList<Solution> offspring) {
//// Sort the offspring
        Collections.sort(offspring);
// How many parents do we want to keep to the next generation?
// Add the best parents        
        ArrayList<Solution> pop = new ArrayList<>();        
        for(int e=0;e<n_parents;e++)
        {
            pop.add(parents.get(e));
        }
// now add the offspring
        for(int e=0;e<Math.min(offspring.size(),getSize_of_solutions())-n_parents;e++)
        {
            pop.add(offspring.get(e));
        }
// order the ascertained parental and offspring solutions        
        Collections.sort(pop);
        return pop;
    }
}
