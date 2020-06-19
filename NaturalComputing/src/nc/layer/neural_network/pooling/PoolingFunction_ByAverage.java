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
public class PoolingFunction_ByAverage extends PoolingFunction {

    @Override
    public double pool(double[][] v) {
        double o = 0;
        for (double[] vv : v) {
            for (double e : vv) {
                o += e;
            }
        }
        return o / (v.length*v[0].length);
    }
}
