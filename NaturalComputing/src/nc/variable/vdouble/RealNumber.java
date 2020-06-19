/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vdouble;

/**
 *
 * @author Oscar Lao
 */
public class RealNumber {
    
    private double d;
    
    public RealNumber(double d)
    {
        this.d = d;
    }

    /**
     * Get the double value
     * @return 
     */
    public double getD() {
        return d;
    }

    /**
     * Set the double value
     * @param d 
     */
    public void setD(double d) {
        this.d = d;
    }
    
    /**
     * Copy this value
     * @return 
     */
    public RealNumber copy()
    {
        return new RealNumber(d);
    }
    
    @Override
    public String toString()
    {
        return Double.toString(d);
    }
}
