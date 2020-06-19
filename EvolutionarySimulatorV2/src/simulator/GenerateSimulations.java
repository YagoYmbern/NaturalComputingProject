/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import file.WriteSimulation;
import parametersSimulation.ParametersChromosome;
import parametersSimulation.ParametersGenome;
import write.WriteFile;

/**
 *
 * @author Oscar Lao
 */
public class GenerateSimulations {
    
    public static void main(String [] args) throws Exception
    {
        WriteFile wf = new WriteFile("C:\\Users\\Oscar Lao\\OneDrive - CRG - Centre de Regulacio Genomica\\Master students\\2020\\Yago\\Analyses\\sims2.sim");
        WriteSimulation wss = new WriteSimulation(wf);
        for (int rep = 0; rep < 5000; rep++) {
            // Define the parameters of the genome to be simulated
            ParametersGenome pg = new ParametersGenome();
            // All mutations are in the mutation list
            // Simulate a fragment of 1 Mb.
            pg.add(new ParametersChromosome(400000));
            AdmixtureSimpleModel a = new AdmixtureSimpleModel(pg);
            wss.write(a, "European", 50000, 25000, 1*30*1000);
        }
        wf.close();                
    }    
}
