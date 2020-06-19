/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable;

import nc.variable.strategyToUpdateSolution.common.StrategyToUpdateV;

/**
 * Create a new variable of type T
 *
 * @author olao
 * @param <T> the type of data that the variable is going to model. For example,
 * a double, an integer, a cluster id, etc
 */
public abstract class V<T> {

    private T i;
    private final StrategyToUpdateV<T> stu;

    /**
     * Variable to optimize.A Variable must propose an update based on an update
     * strategy suitable for the type of variable
     *
     * @param i
     * @param stu
     */
    public V(T i, StrategyToUpdateV<T> stu) {
        this.i = i;
        this.stu = stu;
    }
        
    /**
     * Set the parameter to the value i
     *
     * @param i
     */
    public void setI(T i) {
        this.i = i;
    }

    /**
     * Get the value associated to this variable
     *
     * @return
     */
    public T getI() {
        return i;
    }

    /**
     * Get the strategy to update the variable
     *
     * @return
     */
    public StrategyToUpdateV<T> getStu() {
        return stu;
    }

    /**
     * Equals works with getI()
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof V) {
            return ((V) o).getI().equals(this.getI());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getI().hashCode();
    }    
    
    /**
     * Apply the update to this variable using the implemented strategy to
     * update the variable
     *
     */
    public void apply_update() {
        getStu().update(this);
    }

    /**
     * Make a copy of this variable
     *
     * @return
     */
    public abstract V<T> copy();
    
    
    /**
     * Compute a distance between this variable and another variable
     * @param vo
     * @return 
     */
    public abstract double distance(V<T> vo);
        
    /**
     * Implement a method to retrieve as String the result of this variable
     * @return 
     */
    @Override
    public String toString()
    {
        return getI().toString();
    }
    
    /**
     * Define the nature of the variable
     * @return 
     */
    public abstract Nature getNature();                
}
