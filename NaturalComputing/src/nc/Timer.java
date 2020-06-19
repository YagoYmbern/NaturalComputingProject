/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

/**
 *
 * @author Oscar Lao
 */
public class Timer {
    
    int t = 0;
    
    public Timer()
    {        
    }
    
    public Timer(int t)
    {
        this.t = t;
    }
        
    
    /**
     * add one to the current number of iterations
     */
    public void update_number_iterations()
    {
        t++;
    }
    
    /**
     * Get the number of current iterations
     * @return 
     */
    public int get_current_iteration()
    {
        return t;
    }
    
    /**
     * Get a copy of the timer
     * @return 
     */
    public Timer copy()
    {
        return new Timer(t);
    }
    
}
