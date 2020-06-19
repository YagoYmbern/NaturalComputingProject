/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution;

import nc.function.optimize.FunctionToOptimize;
import data.input_output.OutputParameters;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import nc.Optimizer;
import nc.neurocomputing.layer.Layer;

/**
 * Solution is a class that stores the variables to be estimated into
 * chromosomes. Each chromosome is a domain defined by the mutation rate (number
 * of elements that mutate at a given new solution) and recombination rate. The
 * function to optimize must converse the elements of each chromosome into the
 * parameters needed for the function.
 *
 * @author Oscar Lao
 */
public class Solution extends ArrayList<Chromosome> implements Runnable, Comparable<Solution> {

    // To which optimizer this solution is associated
    protected Optimizer o;
    // Function to optimize
    protected final FunctionToOptimize<Layer> f;
    // How close are the parameters f(x) of our solution with regards to the observed parameters
    protected double fitness = Double.NaN;
    // f(x) the output of running f with the variables
    protected OutputParameters f_x = null;
    // fitness of the previous generations
    protected double fitness_of_parents = Double.NaN;
    protected double fit = Double.NaN;

//    // Couple that generated this solution
//    protected Couple parents;

    /**
     * This is the best solution so far
     */
//    protected Solution best_solution_so_far = null;    
    
    /**
     * Create a solution using the list of ancestors
     * @param f
     * @param fitness_of_parents 
     */
    public Solution(FunctionToOptimize f, double fitness_of_parents)
    {
        this.f = f;
        this.fitness_of_parents = fitness_of_parents;
    }

    /**
     * Create a new solution. No parents existed before the solution was created
     * @param f 
     */
    public Solution(FunctionToOptimize f)
    {
        this.f = f;
    }
    
    /**
     * Get the function to optimize
     *
     * @return
     */
    public FunctionToOptimize getF() {
        return f;
    }

    /**
     * Get the optimizer to which this solution belongs to
     *
     * @return
     */
    public Optimizer getO() {
        return o;
    }

    /**
     * Set the optimizer to which this solution belongs to
     *
     * @param o
     */
    public void setO(Optimizer o) {
        this.o = o;
    }    
    
    
//    /**
//     * Set the best solution so far
//     *
//     * @param best_solution_so_far
//     */
//    public void setBest_solution_so_far(Solution best_solution_so_far) {
//        this.best_solution_so_far = best_solution_so_far;
//    }
//    /**
//     * Set the parents that generated this solution
//     *
//     * @param parents
//     */
//    public void setParents(Couple parents) {
//        this.parents = parents;
//    }
//
//    /**
//     * Get the parents that generated this solution
//     *
//     * @return
//     */
//    public Couple getParents() {
//        return parents;
//    }

    
    /**
     * Get the weighted fitness of this solution by the fitness of the parents
     * @return 
     */
    public double getParentalCareFitness()
    {
        return fit;
    }
    
    /**
     * Get how well the solution solves the problem we want to optimize. This is
     * the function we want to estimate
     *
     * @return
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Set the fitness
     *
     * @param fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
        double b = ThreadLocalRandom.current().nextDouble();
        fit = (b*fitness_of_parents+(1-b)*fitness);
//        if(Double.isNaN(fitness_of_parents))
//        {
//            fitness_of_parents = fitness;
//        }
//        this.fitness = fitness;
//        int p = fitness_of_ancestors.size();
////        if(!fitness_of_ancestors.isEmpty())
////        {
////            if(fitness_of_ancestors.getFirst()!=fitness)
////            {
////                fitness_of_ancestors.addFirst(fitness);                                    
////            }
////        }
////        else
////        {
//            fitness_of_ancestors.addFirst(fitness);                    
////        System.out.println("Mi parent has " + p + " Now I have " + fitness_of_ancestors.size());
//
////        }
//        // The list of fitness must be maintained at 5
//        if(fitness_of_ancestors.size()>10)
//        {
//            while(fitness_of_ancestors.size()>10)
//            fitness_of_ancestors.removeLast();
//        }
////        System.out.println("SIZE " + fitness_of_ancestors.size());
//        // Compute the weighted mean of the fitness of this solution
//        fit = 0;
//        double t = 0;
//        double c = 1;
//        for(Double ff:fitness_of_ancestors)
//        {
//            double w = Math.pow(c,-0.5);
//            fit+=ff*w;
//            t+=w;
//            c++;
//        }
//        // Weighted mean
//        fit/=t;
////        fit = Math.min(fit,fitness);
    }

    /**
     * Reset the fitness so it has to be computed again for this solution.
     */
    public void resetFitness() {
        this.fitness = Double.NaN;
    }

    /**
     * Get the output parameters of running the function to optimize with the
     * proposed values
     *
     * @return
     */
    public OutputParameters getOutputParameters() {
        return f_x;
    }

    /**
     * Set the output parameters
     *
     * @param f_x
     */
    public void setOutputParameters(OutputParameters f_x) {
        this.f_x = f_x;
    }

//    /**
//     * Get the best solution so far that comes from this solution
//     *
//     * @return
//     */
//    public Solution bestProposedSolutionFar() {
//        // If it is the first time we initialize, we must set the current solution as the best one
//        if(best_solution_so_far==null)
//        {
//            best_solution_so_far = this;
//        }
//        return best_solution_so_far;
//    }
    /**
     * Evaluate the fitness of this solution
     */
    @Override
    public void run() {
        evaluate();
    }

    /**
     * Compute the output parameters and evaluate the fitness
     */
    public void evaluate() {
//        if (Double.isNaN(fitness)) {
            f_x = f.compute_f_x_Of_solution(this);
            setFitness(f.getErr().get_error(f_x));
//        }
//        else
//        {
//            System.out.println("I had already a fitness! " + fitness);
//        }
    }

    /**
     * Compare by the parental weighted fitness
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Solution o) {
        int c = Double.compare(this.getParentalCareFitness(), o.getParentalCareFitness());
        if(c==0)
        {
            return Integer.compare(this.get(0).size(),o.get(0).size());
        }
        return c;
    }

    /**
     * Generate a copy of the solution (equivalent to clone it)
     *
     * @return
     */
    public Solution copy() {
        Solution copy = new Solution(f, fitness_of_parents);
        copy.setO(o);
        // Copy the chromosomes
        this.forEach((t) -> {
            copy.add(t.copy());
        });
        copy.setFitness(fitness);
        return copy;
    }

    /**
     * Update the solution
     */
    public void update() {
        // Update each chromosome
        for (int l = 0; l < f.size(); l++) {
            f.get(l).getStu().update(this.get(l));
        }
    }
    
    @Override
    public String toString()
    {
        StringBuilder a = new StringBuilder();
        a.append("Solution\n");
        this.forEach((l) -> {
            a.append(l.toString()).append("\n");
        });
        a.append("Fitness ").append(this.getFitness()).append("\n").append("Fitness parents ").append(this.getFitness()).append("\n");
        return a.toString();
    }
}
