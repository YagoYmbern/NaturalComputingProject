/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

import nc.variable.Nature;
import nc.variable.V;
import nc.variable.VCompound;

/**
 *
 * @author Oscar Lao
 */
public class VSynapsis extends VCompound<Synapsis>{    
    
    public VSynapsis(Synapsis s, VSynapsisUpdater update)
    {
        super(s,update);
    }

    @Override
    public V copy() {
        return new VSynapsis(((Synapsis)this.getI()).copy(), (VSynapsisUpdater)getStu().copy());
    }

    @Override
    public Nature getNature() {
        return Nature.COMPOUND;
    }

    @Override
    public double distance(V<Synapsis> vo) {
        return 0;
    }
    
    /**
     * Two Vsynapses equals if they have the synapsis
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof VSynapsis) {
            VSynapsis so = (VSynapsis) o;
            return so.getI().equals(this.getI());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getI().hashCode();
    }    
}
