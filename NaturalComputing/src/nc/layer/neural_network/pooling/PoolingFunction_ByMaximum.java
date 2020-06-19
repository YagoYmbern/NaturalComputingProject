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
public class PoolingFunction_ByMaximum extends PoolingFunction {

    @Override
    public double pool(double[][] v) {
        double o = Double.NEGATIVE_INFINITY;
        for (double [] vv : v) {
            for(double e:vv)
            {
                o = Math.max(e, o);                
            }
        }
        return o;
    }
}
