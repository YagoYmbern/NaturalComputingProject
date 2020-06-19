/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

/**
 *
 * @author Oscar Lao
 */
public class IntToDouble implements Comparable<IntToDouble>{
    private final int i;
    private final double d;
    
    public IntToDouble(int i, double d)
    {
        this.i = i;
        this.d = d;
    }
    
    /**
     * Get the Id
     * @return 
     */
    public int getI()
    {
        return i;
    }
    
    /**
     * Get the double value
     * @return 
     */
    public double getD()
    {
        return d;
    }
    
    @Override
    public int compareTo(IntToDouble o)
    {
        return Double.compare(d,o.getD());
    }
    
}
