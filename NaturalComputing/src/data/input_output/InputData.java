/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.input_output;

import java.util.ArrayList;
import nc.input_output.InputOutput_Tensor;

/**
 * 
 * @author olao
 */
public class InputData extends ArrayList<InputOutput_Tensor>{
    
    /**
     * Create a input data object setting the batchSize
     * @param batchSize 
     */
    public InputData(int batchSize)
    {
        this.batchSize = batchSize;
    }
    
    private int batchSize = 0;
            
}
