/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network.filter;
import nc.neurocomputing.activation.Activation_Function;
import nc.layer.neural_network.Layer_NeuralNetwork_With_Weights;
import nc.solution.Chromosome;
import nc.solution.chromosome.generator.ChromosomeGenerator;
import nc.solution.chromosome.recombine.RecombinationChromosomes;
import nc.solution.chromosome.update.StrategyToUpdateChromosome;
import nc.variable.vdouble.VDouble;

/**
 * 
 * @author olao
 * @param <Filter> 
 */
public abstract class Filter<Filter> extends Layer_NeuralNetwork_With_Weights<Chromosome<VDouble>, ChromosomeGenerator>{
    
    protected Activation_Function aFunction;
    protected final int row_size, column_size;    
    
    /**
     * Create a Filter
     * @param init
     * @param stu
     * @param recombine
     * @param row_size
     * @param column_size
     * @param aFunction 
     */
    public Filter(ChromosomeGenerator init, StrategyToUpdateChromosome stu, RecombinationChromosomes recombine, int row_size, int column_size, Activation_Function aFunction)
    {
        super(init,stu,recombine);
        this.aFunction = aFunction;
        this.row_size = row_size;
        this.column_size = column_size;
    }    

    /**
     * Get the row size
     * @return 
     */
    public int getRow_size() {
        return row_size;
    } 

    /**
     * Get the size of the column of this filter
     * @return 
     */
    public int getColumn_size() {
        return column_size;
    }
    
    

    /**
     * Get the activation function
     * @return 
     */
    public Activation_Function getaFunction() {
        return aFunction;
    }       
}
