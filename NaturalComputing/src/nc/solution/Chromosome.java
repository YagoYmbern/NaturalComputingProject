/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution;

import java.util.ArrayList;
import nc.variable.V;

/**
 * A chromosome is a structure that stores variables.
 * @author Oscar Lao
 * @param <T> is a variable of type V
 */
public class Chromosome<T extends V> extends ArrayList<T>{    
    
    /**
     * Generate a copy of the chromosome (equivalent to do a deep cloning of it)
     *
     * @return
     */ 
    public Chromosome<T> copy()
    {   
        // Copy all the values
        Chromosome<T> copy = new Chromosome();
        this.forEach((T t) -> {    
            V v = (V)t;
            copy.add((T)v.copy());
        });
        // Add the id of the chromosome
        return copy;                
    }
    
    @Override
    public String toString()
    {
        StringBuilder a = new StringBuilder();
        
        this.forEach((T t) -> {    
            V v = (V)t;
            a.append(v.toString()).append(" ");
        });           
        return a.toString();
    }
}
