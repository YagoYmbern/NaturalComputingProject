/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.layer.neural_network.pooling;

/**
 *
 * @author olao
 */
public abstract class PoolingFunction {
    /**
     * Pool using the pooling function
     * @param v
     * @return 
     */
    public abstract double pool(double [][] v);    
}
