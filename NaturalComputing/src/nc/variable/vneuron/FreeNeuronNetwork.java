/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vneuron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import nc.exception.ActivationFNNException;
import nc.input_output.InputOutput_Tensor;
import nc.solution.ChromosomeFNN;

/**
 *
 * @author Oscar Lao
 */
public class FreeNeuronNetwork {

    // A HashMap to store the network. 
    protected HashMap<CellBody, Neuron> brain = new HashMap<>();
    // The inputs from the network
    protected final ArrayList<CellBodyInput> inputs;
    // The output from the network
    protected final ArrayList<NeuronOutput> n_outputs;
    // Is the neural network active. Neurons of this network will become inactive after an input has been shown to them.
    // In order to make them active again, one has to call either reset_activations(), and all the values are set to 0
    // or activateNeurons, and the previous values are keept in memory
    private boolean isActive = true;
    private final int ch, r, c;
    private final boolean time_series;

    /**
     * Generate a new FreeNeuronalNetwork using the synapses
     *
     * @param ch the number of channels
     * @param r the number of rows
     * @param outputs which are the output neurons
     * @param time_series
     * @param c the number of columns
     */
    public FreeNeuronNetwork(int ch, int r, int c, boolean time_series, ArrayList<NeuronOutput> outputs) {
        this.inputs = new ArrayList<>();
        this.time_series = time_series;
        for (int cch = 0; cch < ch; cch++) {
            for (int rr = 0; rr < r; rr++) {
                for (int cc = 0; cc < c; cc++) {
                    inputs.add(new CellBodyInput(cch, rr, cc));
                }
            }
        }

        this.n_outputs = new ArrayList<>();
        outputs.forEach((n) -> {
            this.n_outputs.add(new NeuronOutput((CellBodyOutput)n.getC(), n.getAf().copy()));
        });
        this.c = c;
        this.ch = ch;
        this.r = r;
    }

    /**
     * Is this neural network using a time series for the output?
     *
     * @return
     */
    public boolean isTime_series() {
        return time_series;
    }

    /**
     * Get the number of columns of the input
     *
     * @return
     */
    public int getC() {
        return c;
    }

    /**
     * Get the number of channels of the input
     *
     * @return
     */
    public int getCh() {
        return ch;
    }

    /**
     * Get the number of rows of the input
     *
     * @return
     */
    public int getR() {
        return r;
    }

    /**
     * Get the body cell of the inputs
     *
     * @return
     */
    public ArrayList<CellBodyInput> getInputs() {
        return inputs;
    }

    /**
     * Get the body cell of the outputs
     *
     * @return
     */
    public ArrayList<NeuronOutput> getOutputs() {
        return n_outputs;
    }

    /**
     * Get the neurons of this network
     *
     * @return
     */
    public ArrayList<Neuron> getNeurons() {
        return new ArrayList(brain.values());
    }

    /**
     * Get the neuron of the cell body c. Return null if cell body c does not
     * exist in the network
     *
     * @param c
     * @return
     */
    public Neuron getNeuron(CellBody c) {
        return brain.get(c);
    }

    /**
     * Set the neural network in training mode.It means that the synapses will
     * add some noise in probability to avoid overfitting. By default the neural
     * network is in training mode
     *
     * @param training
     */
    public void set_in_training_mode(boolean training) {
        getNeurons().forEach((n) -> {
            n.setTraining_mode(training);
        });
    }

    /**
     * Create the network using the synapses
     *
     * @param synapses
     */
    public void build_network(ArrayList<VSynapsis> synapses) {
        brain = new HashMap<>();
        // Initialize the input neurons
        inputs.forEach((i) -> {
            NeuronInput ninput = new NeuronInput(i);
            brain.put(i, ninput);
        });
        // Initialize the output neurons
        n_outputs.forEach((o) -> {
            brain.put(o.getC(), o);
        });
        synapses.forEach((vs) -> {
            // Check that the input that connects to the output exists
            CellBody input = vs.getI().getInput_neuron();
            Neuron n_input = brain.get(input);
            if (n_input == null) {
                // It is a hidden neuron (it has connections from another neuron)
                n_input = new NeuronHidden((CellBodyHidden) input);
                brain.put(input, n_input);
            }
            // The output neuron
            CellBody output = vs.getI().getOutput_neuron();
            Neuron n_output = brain.get(output);
            if (n_output == null) {
                n_output = new NeuronHidden((CellBodyHidden) output);
                brain.put(output, n_output);
            }
            // add the dendrite to the output neuron with the connection comming from the input neuron
            ((NeuronHidden) n_output).add_dendrite_connection(new DendriteConnection(n_input, vs));
        });
    }

    /**
     * Retrieve the synapses from this neural network
     *
     * @return
     */
    public ChromosomeFNN getChromosome() {
        ChromosomeFNN cr = new ChromosomeFNN(ch, r, c, isTime_series(), n_outputs);
        ArrayList<Neuron> neurons = getNeurons();
        neurons.stream().filter((n) -> (n instanceof NeuronHidden)).map((n) -> ((NeuronHidden) n).getDendrites()).forEachOrdered((dcs) -> {
            dcs.forEach((dc) -> {
                cr.add(dc.getV());
            });
        });
        cr.generate_network();
        return cr;
    }

    /**
     * Remove all the Synapses that are not activated. Return a new chromosome
     * that contains the good
     *
     * @return
     */
    public ChromosomeFNN prune() {
        this.reset_activations();
        // output over the t iterations
        double[] output = new double[n_outputs.size()];
        // Now add the input values to the input neurons
        for (int t = 0; t < 2; t++) {
            this.activateNeurons();
            for (int chan = 0; chan < ch; chan++) {
                for (int ro = 0; ro < r; ro++) {
                    for (int co = 0; co < c; co++) {
                        Neuron n_i = brain.get(new CellBodyInput(chan, ro, co));
                        ((NeuronInput) n_i).setInput(0);
                    }
                }
            }
            // Go through all the output neurons. Get the activation output
            for (int n = 0; n < n_outputs.size(); n++) {
                output[n] = n_outputs.get(n).getActivationOutput();
            }
        }
        // Now check all the synapses. Only keep the synapses that have been activated        
        // Check all the neurons in the network
        ChromosomeFNN cr = new ChromosomeFNN(ch, r, c, isTime_series(), n_outputs);
        Iterator<CellBody> cells = brain.keySet().iterator();
        for (; cells.hasNext();) {
            CellBody co = cells.next();
            Neuron n = brain.get(co);
            // For all the dendrites that connect to this neuron
            for (DendriteConnection d : n.getDendrites()) {
                // If the synapsis has been activated, it means that it is part of the path that goes from input to output. Keep the synapsis
                if (d.getV().getI().isSynapsis_has_been_activated()) {
                    cr.add(d.getV());
                }
            }
        }
        this.reset_activations();
        return cr;
    }

    /**
     * get the atavistic synapses.These are synapses that connect neurons in
     * paths that do not participate in the output. Therefore, if these synapses
     * are removed from the network, the output would not change
     *
     * @return
     */
    public ArrayList<VSynapsis> atavistic_synapses() {
        ArrayList<VSynapsis> atavistic = new ArrayList<>();
        // Check all the neurons in the network
        Iterator<CellBody> cells = brain.keySet().iterator();
        for (; cells.hasNext();) {
            CellBody co = cells.next();
            Neuron n = brain.get(co);
            // Check that the neuron exists and that it is not a NeuronInput
            if (!(n instanceof NeuronInput) && n != null) {
                // All dendrites are atavistic
                if (!n.reachesOutput()) {
                    n.getDendrites().forEach((d) -> {
                        atavistic.add(d.getV());
                    });
                }
            }
        }
        return atavistic;
    }

//    /**
//     * Remove all the neurons (and dendrite connections) that do not have axons
//     */
//    public void prune() {
//        // Check all the neurons in the network
//        Iterator<CellBody> cells = brain.keySet().iterator();
//        for (; cells.hasNext();) {
//            CellBody c = cells.next();
//            Neuron n = brain.get(c);
//            // if the neuron is hidden, then it has dendrites and axons. If it is a NeuronOutput, then it will not have axons
//            if (n instanceof NeuronHidden && !(n instanceof NeuronOutput)) {
//                // if the output is only connected to itself, then we have to remove the neuron
//                if (n.getAxons().size() == 1) {
//                    if (n.getAxons().get(0).getNeuron().equals(n)) {
//                        n.getDendrites().clear();
//                    }
//                } // Its output is not connected to other neurons
//                else if (n.getAxons().isEmpty()) {
//// Remove all the dendrite connections associated to this neuron and all the axon connections with the inputs. This neuron produces no output and therefore all the inputs and its synapses are irrelevant
//                    n.getDendrites().forEach((d) -> {
//                        d.getNeuron().getAxons().remove(new AxonConnection(n, null));
//                    });
//// This neuron has no connections anymore                    
//                    n.getDendrites().clear();
//                }
//            }
//        }
//    }
    /**
     * Check that the network is functional. A network is functional if all the
     * output neurons are ultimately activated. That is, there is a path between
     * each output neuron and, at least, an input neuron.
     *
     * @return
     */
    public boolean networkIsFunctional() {
        for (NeuronOutput out : n_outputs) {
            if (!out.reachesInputNeuron()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clean the activations of the current instance of input vector and set the
     * activations at t = 0
     */
    public void reset_activations() {
        // Clean the activations of the neurons
        Iterator<Neuron> itera = brain.values().iterator();
        for (; itera.hasNext();) {
            itera.next().cleanActivationValue();
        }
        isActive = true;
    }

    /**
     * Once an input-output vector has been shown to the network, the neurons
     * become inactive. If we activate the Neurons again without reseting the
     * previous value stored in the neurons, then we are doing a recurrent
     * neural network (if loops occur within the neural network) when t in the
     * inputoutput_tensor is >1.
     */
    public void activateNeurons() {
        Iterator<Neuron> itera = brain.values().iterator();
        for (; itera.hasNext();) {
            itera.next().setNeuronToComputeActivation();
        }
        isActive = true;
    }

    /**
     * Get the total number of neurons
     *
     * @return
     */
    public int size() {
        return brain.size();
    }

    /**
     * Show a vector of input values to the current network.Beware that the
     * neural network must be active. Call reset_activations() or
     * activateNeurons() before calling this method after it has been called the
     * first time. Do not store the output of the neurons of the network
     *
     * @param iv
     * @return
     * @throws nc.exception.ActivationFNNException
     */
    public double[] predict(InputOutput_Tensor iv) throws ActivationFNNException {
        if (!isActive) {
            throw new ActivationFNNException("You must activate the neural network before calling this method. Either call reset_activations(), and all the values are set to 0 or activateNeurons");
        }
        // output over the t iterations
        double[] output;
        if (isTime_series()) {
            output = new double[n_outputs.size()*iv.getN_time_series()];
            // Now add the input values to the input neurons
            int k = 0;
            // For each time series, do
            for (int t = 0; t < iv.getN_time_series(); t++) {
                this.activateNeurons();
                for (int chan = 0; chan < iv.getN_channel(); chan++) {
                    for (int r = 0; r < iv.getN_rows(); r++) {
                        for (int c = 0; c < iv.getN_columns(); c++) {
                            Neuron n_i = brain.get(new CellBodyInput(chan, r, c));
                            ((NeuronInput) n_i).setInput(iv.get(t, chan, r, c));
                        }
                    }
                }
                // Go through all the output neurons. Get the activation output
                for (int n = 0; n < n_outputs.size(); n++) {
                    output[k] = n_outputs.get(n).getActivationOutput();
                    k++;
                }
            }
        }
        else
        {
            output = new double[n_outputs.size()];
            // Now add the input values to the input neurons
            for (int t = 0; t < iv.getN_time_series(); t++) {
                this.activateNeurons();
                for (int chan = 0; chan < iv.getN_channel(); chan++) {
                    for (int r = 0; r < iv.getN_rows(); r++) {
                        for (int c = 0; c < iv.getN_columns(); c++) {
                            Neuron n_i = brain.get(new CellBodyInput(chan, r, c));
                            ((NeuronInput) n_i).setInput(iv.get(t, chan, r, c));
                        }
                    }
                }
                // Go through all the output neurons. Get the activation output
                for (int n = 0; n < n_outputs.size(); n++) {
                    output[n] = n_outputs.get(n).getActivationOutput();
                }
            }            
        }

        // The neural network is not active because it is storing the activation values of the neurons after iv went throuhg it
        isActive = false;
        return output;
    }

    /**
     * Get the output of the cell
     *
     * @param cell
     * @return
     */
    public double getOutput(CellBody cell) {
        Neuron n = brain.get(cell);
        if (n != null) {
            return n.getSumOfInputs();
        }
        return Double.NaN;
    }
}
