/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable;

/**
 * The class Variable_Weighted define
 * @author Oscar Lao
 * @param <T>
 */
public class VWeighted<T> implements Comparable<VWeighted>{
    
    private final V<T> v;
    private final double w;
            
    
    /**
     * Create a Variable Weight with variable v and weight w
     * @param v
     * @param w 
     */
    public VWeighted(V<T> v, double w)
    {
        this.v = v;
        this.w = w;
    }

    /**
     * Get the variable
     * @return 
     */
    public V<T> getV() {
        return v;
    }

    /**
     * Get the weight
     * @return 
     */
    public double getW() {
        return w;
    }

    @Override
    public int compareTo(VWeighted o) {
        return Double.compare(w, o.getW());
    }
}
