/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution;

/**
 *
 * @author Oscar Lao
 * @param <C>
 */
public class ChromosomeWeighted<C extends Chromosome> {
    
    private final C chr;
    private double weight;
    
    /**
     * Generate a chromosome with an associated weight (i.e. the fitness of the solution)
     * @param chr
     * @param weight 
     */
    public ChromosomeWeighted(C chr, double weight)
    {
        this.chr = chr;
        this.weight = weight;
    }

    /**
     * Get the chr
     * @return 
     */
    public C getChr() {
        return chr;
    }

    /**
     * Get the weight
     * @return 
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Set the weight
     * @param w 
     */
    public void setWeight(double w)
    {
        this.weight = w;
    }
}
