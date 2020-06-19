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
public class CellBodyInput extends CellBody<CellBodyInput>{
    
    private final int ch, r, c;
    
    /**
     * Create a new input cell body from a neuron for the channel ch, row r and col c
     * @param ch
     * @param r
     * @param c 
     */
    public CellBodyInput(int ch, int r, int c)
    {
        this.ch = ch;
        this.r = r;
        this.c = c;
    }

    /**
     * Get the column of this neuron
     * @return 
     */
    public int getC() {
        return c;
    }

    /**
     * Get the channel
     * @return 
     */
    public int getCh() {
        return ch;
    }

    /**
     * Get the row
     * @return 
     */
    public int getR() {
        return r;
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof CellBodyInput)
        {
            CellBodyInput io = (CellBodyInput)o;
            return(io.getC()==this.getC() && io.getCh()==this.getCh() && io.getR() == this.getR());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.ch;
        hash = 73 * hash + this.r;
        hash = 73 * hash + this.c;
        return hash;
    }


    @Override
    public CellBodyInput copy() {
        return new CellBodyInput(ch, r, c);
    }

    @Override
    public String toString() {
        return "" + " " + ch + " " + r + " " + c;
    }
}
