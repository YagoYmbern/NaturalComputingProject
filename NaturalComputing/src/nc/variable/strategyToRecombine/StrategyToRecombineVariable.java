/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.strategyToRecombine;

import java.util.ArrayList;
import nc.variable.VWeighted;
import nc.variable.V;

/**
 *
 * @author Oscar Lao
 */
public abstract class StrategyToRecombineVariable {
    
    /**
     * Recombine the variables
     * @param variables
     * @return 
     */
    public abstract V recombine(ArrayList<VWeighted> variables);    
}
