/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

/**
 *
 * @author Oscar Lao
 */
public class CellBodyHiddenImplemented extends CellBodyHidden<CellBodyHiddenImplemented>{

    public CellBodyHiddenImplemented()
    {
        super();
    }
    
    /**
     * Generate a Cell body with a unique long refId
     * @param refId 
     */
    public CellBodyHiddenImplemented(long refId)
    {
        super(refId);
    }
    
        /**
     * Copy this neuron
     * @return 
     */
    @Override
    public CellBodyHiddenImplemented copy()
    {
        return new CellBodyHiddenImplemented(getRefId());
    }    
}
