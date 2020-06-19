/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

/**
 *
 * @author Oscar Lao
 */
public class Neuron_Weight implements Comparable<Neuron_Weight>{
    
    private final Neuron n;
    private final double w;
    
    public Neuron_Weight(Neuron n, double w)
    {
        this.n = n;
        this.w = w;
    }

    /**
     * Get the neuron
     * @return 
     */
    public Neuron getN() {
        return n;
    }

    /**
     * Get the weight
     * @return 
     */
    public double getW() {
        return w;
    }

    /**
     * The smallest is the weight, the greater is the position
     * @param o
     * @return 
     */
    
    @Override
    public int compareTo(Neuron_Weight o) {
        if(o.getW()>this.getW()) return -1;
        if(o.getW()<this.getW()) return 1;
        return 0;
    }            
}
