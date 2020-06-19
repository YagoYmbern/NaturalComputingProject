/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.input_output;

/**
 *
 * @author Oscar Lao
 */
public class InputOutput_Vector {

    private final double[] v;
    private final double [] w;

    /**
     * Create a input/output with a vector
     * @param v 
     */
    public InputOutput_Vector(double[] v) {
        this.v = v;
        w = new double[v.length];
        for(int i=0;i<w.length;i++)
        {
            w[i] = 1;
        }
    }
    
    /**
     * Set the value v in position vv
     * @param i
     * @param vv 
     */
    public void set(int i, double vv)
    {
        v[i] = vv;
    }
    
    /**
     * Get the vector
     * @return 
     */
    public double[] getV() {
        return v;
    }

    /**
     * Get the weights
     * @return 
     */
    public double[] getW() {
        return w;
    }
    
    /**
     * Update the value of the weight at position i
     * @param i
     * @param v 
     */
    public void updateWeight(int i, double v)
    {
        w[i] = v;
    }
    
    
}
