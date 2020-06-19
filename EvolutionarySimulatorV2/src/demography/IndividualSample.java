/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demography;

import exception.ExceptionGenome;
import java.util.Collections;
import locus.Sequence;
import parametersSimulation.ParametersGenome;

/**
 *
 * @author Oscar Lao
 */
public class IndividualSample extends Individual {

    public IndividualSample(Population population, ParametersGenome pg) {
        super(population, pg);
    }

    /**
     * Get the sequences associated to this individual
     *
     * @return
     * @throws ExceptionGenome
     */
    public Sequence[][] getSequences() throws ExceptionGenome {
        Sequence[][] sequences = new Sequence[size()][2];
        for (int g = 0; g < size(); g++) {
            for (int dip = 0; dip < 2; dip++) {
                sequences[g][dip] = genome[g].getChromosome(dip).first().getFirst();
                Collections.sort(sequences[g][dip]);
            }
        }
        return sequences;
    }
}
