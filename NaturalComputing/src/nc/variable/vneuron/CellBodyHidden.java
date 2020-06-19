/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Oscar Lao
 */
public abstract class CellBodyHidden<T extends CellBodyHidden> extends CellBody<T> implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final AtomicLong atomicRefId = new AtomicLong();

    // transient field is not serialized
    private transient long refId;

    /**
     * default constructor will be called on base class even during deserialization
     * A refId using the class AtomicLong will be generated
     **/
    public CellBodyHidden() {
       refId = atomicRefId.incrementAndGet();
    }
    
    /**
     * Generate a Cell body with a unique long refId
     * @param refId 
     */
    public CellBodyHidden(long refId)
    {
        this.refId = refId;
    }

    public long getRefId() {
        return refId;
    }

    /**
     * Equal by Neuron id
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof CellBodyHidden)
        {
            return ((CellBodyHidden)o).getRefId()==this.getRefId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.refId ^ (this.refId >>> 32));
        return hash;
    }
        
    @Override
    public String toString()
    {
        return Long.toString(getRefId());
    }    
}
