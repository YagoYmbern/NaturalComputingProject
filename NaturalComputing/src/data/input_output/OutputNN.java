/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.input_output;

import nc.input_output.InputOutput_Vector;

/**
 *
 * @author Oscar Lao
 */
public class OutputNN {
    
    private double [] w;
    private final InputOutput_Vector output;
    
    public OutputNN(InputOutput_Vector output)
    {
        this.output = output;
    }
    
    public OutputNN(double [] w, InputOutput_Vector output)
    {
        this.w = w;
        this.output = output;
    }

    public void setW(double [] w) {
        this.w = w;
    }

    public double [] getW() {
        return w;
    }

    public InputOutput_Vector getOutput() {
        return output;
    }
}
