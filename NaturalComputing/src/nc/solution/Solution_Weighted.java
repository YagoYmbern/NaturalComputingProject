/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution;

/**
 *
 * @author Oscar Lao
 */
public class Solution_Weighted {
    /**
     * Create a new object that stores the solution and a weight with regards to the population to which the solution belongs to
     * @param a
     * @param weight 
     */
    public Solution_Weighted(Solution a, double weight)
    {
        this.a = a;
        this.weight = weight;
    }
    
    private final Solution a;
    private final double weight;

    public Solution getSolution() {
        return a;
    }

    public double getWeight() {
        return weight;
    }
    
    /**
     * Get the chromosome weight in layer l
     * @param l
     * @return 
     */
    public ChromosomeWeighted get(int l)
    {
        return new ChromosomeWeighted(a.get(l),weight);
    }
}
