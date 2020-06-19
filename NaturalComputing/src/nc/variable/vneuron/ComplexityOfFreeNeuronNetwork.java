/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import maths.IntToInt;

/**
 *
 * @author Oscar Lao
 */
public class ComplexityOfFreeNeuronNetwork {
    
    private HashMap<IntToInt,Integer> neuron_connections;
    
    public ComplexityOfFreeNeuronNetwork(FreeNeuronNetwork fn)
    {
        getStats(fn);
    }

    /**
     * Compute stats of complexity of the network: for each combination of dendrites and axons, the number of neurons
     *
     * @param fn
     * @return
     */
    private void getStats(FreeNeuronNetwork fn) {
        neuron_connections = new HashMap<>();
        fn.getNeurons().forEach((n) -> {
            // Add another neuron with the observed dendrite and axon profile. If it does not exist in the HashMap, create it and add one neuron.
            try
            {
                neuron_connections.put(new IntToInt(n.getDendrites().size(),n.getAxons().size()),neuron_connections.get(new IntToInt(n.getDendrites().size(),n.getAxons().size())) + 1);
            }catch(NullPointerException tok)
            {
                neuron_connections.put(new IntToInt(n.getDendrites().size(),n.getAxons().size()),1);
            }
        });        
    }
    
    /**
     * get the key set of int to int combinations
     * @return 
     */
    public Set<IntToInt> getIntToInt()
    {
        return neuron_connections.keySet();
    }
    
    /**
     * get the number of neurons that show this int to int pattern
     * @param i
     * @return 
     */
    public int getNumberOfNeuronsWithIntToInt(IntToInt i)
    {
        int n = 0;
        try
        {
            n = neuron_connections.get(i);
        }catch(NullPointerException tok){
            // It does not exist. We will return 0            
        }
        return n;
    }
    
    /**
     * Get the distance in the absolute number of different neurons at each int to int category
     * @param o
     * @return 
     */
    public double distance(ComplexityOfFreeNeuronNetwork o)
    {
        double d = 0;
        // Unique IntToInt elements
        HashSet<IntToInt> unique_int_to_int = new HashSet<>(this.getIntToInt());
        unique_int_to_int.addAll(o.getIntToInt());
        // Now, for each int to int, check the number of neurons in this object and in o.
        Iterator<IntToInt> itera = unique_int_to_int.iterator();
        for(;itera.hasNext();)
        {
// now get the difference in the number of neurons that have a particular dendrite and axon profile in both neural networks        
            IntToInt iti = itera.next();
            d+=Math.abs(this.getNumberOfNeuronsWithIntToInt(iti)-o.getNumberOfNeuronsWithIntToInt(iti));
        }
        return d;
    }
}
