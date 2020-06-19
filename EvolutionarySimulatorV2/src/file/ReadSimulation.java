/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import data.input_output.OutputParameters;
import java.io.IOException;
import java.util.ArrayList;
import nc.input_output.InputOutput_Tensor;
import nc.input_output.InputOutput_Vector;
import read.ReadFile;

/**
 *
 * @author Oscar Lao
 */
public class ReadSimulation {
    
    private ReadFile rf;
    
    public ReadSimulation(ReadFile rf)
    {
        this.rf = rf;
    }
    
    public ArrayList<SimulationData> nextSimulation() throws IOException
    {
        ArrayList<SimulationData> sims = new ArrayList<>();
        String line = rf.readRow();
        // If the line is empty (end of the file) return null
        if(line==null)
        {
            return null;
        }
        
        String [] split_by_content = line.split(WriteSimulation.parameter_Separator());
// first correspond to the parameters
        ArrayList<Double> params = new ArrayList<>();  
        for(String s:split_by_content[0].split(" "))
        {
            params.add(Double.parseDouble(s));
        }

        String [] split_by_locus = split_by_content[1].split("\\"+WriteSimulation.locus_Separator());
        // for each locus, get the windows of the sequences
        for(String ss:split_by_locus)
        {
            // for each sequence
            String [] sequences = ss.split(WriteSimulation.sequence_Separator());
            for(String s:sequences)
            {
                // for each window
                String [] windows = s.split(WriteSimulation.window_Separator());
                double [][][][] m = new double[windows.length][][][];
                    double [] o = new double[windows.length];
                int ww = 0;
                for(String w:windows)
                {
                    // for each window, retrieve the SFS and the frequency of introgressed region
                    String [] intro = w.split(WriteSimulation.introgression_Separator());
                    o[ww] = Double.parseDouble(intro[1]);                    
                    String [] sfs = intro[0].split(" ");
                    m[ww] = new double[sfs.length][1][1];
                    for(int i=0;i<sfs.length;i++)
                    {
                        m[ww][i][0][0] = Double.parseDouble(sfs[i]);
                    }
                    ww++;
                }
                InputOutput_Tensor io = new InputOutput_Tensor(m);
                InputOutput_Vector out = new InputOutput_Vector(o);
                sims.add(new SimulationData(params,io,out));
            }                    
        }
        return sims;
    }    
}
