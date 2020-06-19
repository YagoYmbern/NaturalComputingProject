/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

/**
 *
 * @author olao
 */
public class Pair implements Comparable<Pair>{

    public Pair(int id, int w, double v)
    {
        this.id = id;
        this.w = w;
        this.v = v;
    }
    
    private final int id, w;
    private final double v;

    /**
     * Get the id
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Get the window
     * @return 
     */
    public int getW() {
        return w;
    }        

    /**
     * Get the value
     * @return 
     */
    public double getV() {
        return v;
    }
    
    
    /**
     * Compare by value
     * @param t
     * @return 
     */
    @Override
    public int compareTo(Pair t) {
        return Double.compare(v, t.getV());
    }           
}
