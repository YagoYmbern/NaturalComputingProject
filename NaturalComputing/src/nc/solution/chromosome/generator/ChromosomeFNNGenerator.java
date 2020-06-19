/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution.chromosome.generator;

import java.util.ArrayList;
import nc.neurocomputing.activation.Activation_Function;
import nc.neurocomputing.activation.Activation_Generalized;
import nc.solution.ArrayOfPossibleActivations;
import nc.solution.ChromosomeFNN;
import nc.variable.vneuron.CellBodyHidden;
import nc.variable.vneuron.CellBodyInput;
import nc.variable.vneuron.CellBodyOutput;
import nc.variable.vneuron.NeuronOutput;
import nc.variable.vneuron.Synapsis;
import nc.variable.vneuron.VSynapsis;
import nc.variable.vneuron.VSynapsisUpdater;

/**
 *
 * @author Oscar Lao
 */
public class ChromosomeFNNGenerator extends ChromosomeGeneratorAbstract<ChromosomeFNN> {

    private final int number_of_output_neurons;
    private final Activation_Function output_activation;
    private final boolean time_series;

    /**
     * Output of the activation function
     * @param output_activation
     * @param time_series is considering a time series
     * @param number_of_output_neurons_by_time_series the number of output neurons each time that the output is called
     */
    public ChromosomeFNNGenerator(Activation_Function output_activation, boolean time_series, int number_of_output_neurons_by_time_series) {
        super(null);
        this.output_activation = output_activation;
        this.number_of_output_neurons = number_of_output_neurons_by_time_series;
        this.time_series = time_series;
    }  

    /**
     * Get the number of output neurons
     *
     * @return
     */
    public int getNumber_of_output_neurons() {
        return number_of_output_neurons;
    }

    /**
     * Generate a new ChromosomeFNN
     *
     * @param ch
     * @param r
     * @param col
     * @return
     */
    public ChromosomeFNN generateChromosome(int ch, int r, int col) {
        ArrayOfPossibleActivations ar = new ArrayOfPossibleActivations();
        // Output neurons
        ArrayList<NeuronOutput> output = new ArrayList<>();
        for (int i = 0; i < number_of_output_neurons; i++) {
            output.add(new NeuronOutput(new CellBodyOutput(i), output_activation.copy()));
        }
        // a new chromosome
        ChromosomeFNN chrom = new ChromosomeFNN(ch, r, col, time_series, output);        
                
// for each output neuron generate synapses with all the input neurons
        output.forEach((NeuronOutput o) -> {
            for (int cch = 0; cch < ch; cch++) {
                for (int rr = 0; rr < r; rr++) {
                    for (int ccol = 0; ccol < col; ccol++) {
//                        int id = ThreadLocalRandom.current().nextInt();
//                        // Check that the proposed id does not exist already in the list of ids
//                        while (chrom.getFr().getNeuron(new CellBodyHidden(id)) != null) {
//                            id = ThreadLocalRandom.current().nextInt();
//                        }
//                        CellBodyHidden a = new CellBodyHidden(id);
//                        // Between input and hidden
//                        Activation_Generalized A_af = new Activation_Generalized();
//                        VSynapsis A_a = new VSynapsis(new Synapsis(new CellBodyInput(cch, rr, ccol), a, A_af), new VSynapsisUpdater());
//                        // New activation:
//                        Activation_Generalized afr_a_B = Activation_Generalized.complementary();
//                        VSynapsis a_B = new VSynapsis(new Synapsis(a, o.getC(), afr_a_B), new VSynapsisUpdater());
//// Add the two connections: between input and hidden. Between hidden and output
//                        chrom.add(A_a);
//                        chrom.add(a_B);
// Add connection between input and output
                        VSynapsis A_B = new VSynapsis(new Synapsis(new CellBodyInput(cch, rr, ccol), o.getC(), ar.generate_a_new_activation()), new VSynapsisUpdater());
                        chrom.add(A_B);
                    }
                }
            }
        });
        // Generate the network associated to this chromosome
        chrom.generate_network();
        return chrom;
    }
}
