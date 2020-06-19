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
public abstract class CellBody<C> {

    /**
     * Equal by Neuron id. If it is a CellBodyInput, looks for the coordinates
     * (channel, x, y). If it is a hidden neuron, by the id
     *
     * @param o
     * @return
     */
    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    /**
     * Copy this neuron
     *
     * @return
     */
    public abstract C copy();

    @Override
    public abstract String toString();
}
