/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import java.util.ArrayList;
import locus.BinarySequence;
import locus.Fragment;

/**
 *
 * @author Oscar Lao
 */
public class WindowsBinarySequence extends ArrayList<BinarySequence>{
    
    private final Fragment fr;
    
    /**
     * The fragment that covers the windows
     * @param fr 
     */
    public WindowsBinarySequence(Fragment fr)
    {
        this.fr = fr;
    }

    /**
     * Get the fragment
     * @return 
     */
    public Fragment getFr() {
        return fr;
    }
    
    
    
}
