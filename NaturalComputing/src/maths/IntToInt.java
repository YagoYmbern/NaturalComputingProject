/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maths;

/**
 * Class to store two int values.
 *
 * @author Oscar Lao
 */
public class IntToInt {

    private final int a, b;

    public IntToInt(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    /**
     * Object o is equal to this object if it is IntToInt and parameter a and b
     * are identical to the ones in this object
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof IntToInt) {
            return ((IntToInt) o).getA() == getA() && ((IntToInt) o).getB() == getB();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.a;
        hash = 23 * hash + this.b;
        return hash;
    }

}
