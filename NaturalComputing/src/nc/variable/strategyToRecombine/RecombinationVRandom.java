/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.strategyToRecombine;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import nc.variable.V;
import nc.variable.VWeighted;

/**
 * A class that implements a type of recombination between variables based on
 * picking the one with the largest weight.
 *
 * @author Oscar Lao
 */
public class RecombinationVRandom extends StrategyToRecombineVariable {

    @Override
    public V recombine(ArrayList<VWeighted> variables) {
        // Return one at random
        return ((V)variables.get(ThreadLocalRandom.current().nextInt(variables.size())).getV()).copy();
    }
}
