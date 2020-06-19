/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution;

import nc.variable.vneuron.FreeNeuronNetwork;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import maths.random.RandomMultinomial;
import nc.neurocomputing.activation.Activation_Function;
import nc.neurocomputing.activation.Activation_Generalized;
import nc.variable.vneuron.AxonalConnections;
import nc.variable.vneuron.AxonConnection;
import nc.variable.vneuron.CellBodyHidden;
import nc.variable.vneuron.CellBodyHiddenImplemented;
import nc.variable.vneuron.DendriteConnections;
import nc.variable.vneuron.Neuron;
import nc.variable.vneuron.NeuronInput;
import nc.variable.vneuron.NeuronOutput;
import nc.variable.vneuron.Synapsis;
import nc.variable.vneuron.VSynapsis;
import nc.variable.vneuron.VSynapsisUpdater;

/**
 *
 * @author Oscar Lao
 */
public class ChromosomeFNN extends Chromosome<VSynapsis> {

    private final ArrayOfPossibleActivations activations;
    private final double[] probabilities_of_different_types_of_movements;

    private FreeNeuronNetwork fr = null;

    /**
     * Generate a new ChromosomeFreeNetwork with the n input neurons and y
     * output neurons
     *
     * @param chan
     * @param r
     * @param outputs
     * @param time_series
     * @param c
     */
    public ChromosomeFNN(int chan, int r, int c, boolean time_series, ArrayList<NeuronOutput> outputs) {
        fr = new FreeNeuronNetwork(chan, r, c, time_series, outputs);
        activations = new ArrayOfPossibleActivations();
        probabilities_of_different_types_of_movements = new double[4];
        probabilities_of_different_types_of_movements[0] = 0.1;
        probabilities_of_different_types_of_movements[1] = 0.1;
        probabilities_of_different_types_of_movements[2] = 0.4;
        probabilities_of_different_types_of_movements[3] = 0.4;        
//        for (int e = 0; e < probabilities_of_different_types_of_movements.length; e++) {
//            probabilities_of_different_types_of_movements[e] = 1.0 / (probabilities_of_different_types_of_movements.length);
//        }
    }

    /**
     * Generate a new ChromosomeFreeNetwork with the n input neurons and y
     * output neurons
     *
     * @param chan
     * @param r
     * @param outputs
     * @param time_series
     * @param c
     * @param activations
     */
    public ChromosomeFNN(int chan, int r, int c, boolean time_series, ArrayList<NeuronOutput> outputs, ArrayOfPossibleActivations activations, double[] probabilities_of_different_types_of_movements) {
        fr = new FreeNeuronNetwork(chan, r, c, time_series, outputs);
        this.activations = activations;
        this.probabilities_of_different_types_of_movements = probabilities_of_different_types_of_movements;
    }

    /**
     * Get the activations of this chromosome
     *
     * @return
     */
    public ArrayOfPossibleActivations getActivations() {
        return activations;
    }

    /**
     * Generate the network using the synapses coded in this chromosome. Call it
     * every time that the topology of the NN has changed
     */
    public void generate_network() {
        fr = new FreeNeuronNetwork(fr.getCh(), fr.getR(), fr.getC(), fr.isTime_series(), fr.getOutputs());
        fr.build_network(this);
    }

    /**
     * Get the free neuron network associated to this chromosome
     *
     * @return
     */
    public FreeNeuronNetwork getFr() {
        return fr;
    }

    /**
     * Get the probabilities of the different types of movements
     *
     * @return
     */
    public double[] getProbabilities_of_different_types_of_movements() {
        return probabilities_of_different_types_of_movements;
    }

    /**
     * Generate a copy of the chromosome (equivalent to do a deep cloning of it)
     *
     * @return
     */
    @Override
    public Chromosome<VSynapsis> copy() {
        double[] np = new double[probabilities_of_different_types_of_movements.length];
        System.arraycopy(probabilities_of_different_types_of_movements, 0, np, 0, probabilities_of_different_types_of_movements.length);
        // Copy all the values
        ChromosomeFNN copy = new ChromosomeFNN(fr.getCh(), fr.getR(), fr.getC(), fr.isTime_series(), fr.getOutputs(), activations.copy(), np);
        this.forEach((VSynapsis t) -> {
            copy.add((VSynapsis) t.copy());
        });
        // Generate the network of the copy
        copy.generate_network();
        // Add the id of the chromosome
        return copy;
    }

    public void update() {
        // Update the values of the new activations that can be used
//        getActivations().updateProbability();
//        getActivations().updateActivations();
//        probability.apply_update();
//        double p = Math.exp(probability.getI().getD())/(1+Math.exp(probability.getI().getD()));
//        if (ThreadLocalRandom.current().nextDouble() < p) {
        // Update the vector of probabilities of the movements
//            RandomUniformWithoutResampling ruf = new RandomUniformWithoutResampling(0, probabilities_of_different_types_of_movements.length);
//            int[] i = ruf.sample(2);
//            double b = ThreadLocalRandom.current().nextDouble();
//            double i1 = probabilities_of_different_types_of_movements[i[0]];
//            double i2 = probabilities_of_different_types_of_movements[i[1]];
//            probabilities_of_different_types_of_movements[i[0]] = b * (i1 + i2);
//            probabilities_of_different_types_of_movements[i[1]] = (1 - b) * (i1 + i2);
        RandomMultinomial rmul = new RandomMultinomial(probabilities_of_different_types_of_movements);
        int type_of_mutation = rmul.sample(1)[0];
        switch (type_of_mutation) {
            case 0:
                add_neuron_between_two_neurons(1);
                break;
            case 1:
                remove_neuron();
                break;
            case 2:
                add_de_novo_synapses(1);
                break;
            case 3:
                remove_synapsis();
            default:
                break;
        }

//        } else {
//            mutate_synapsis();
//        }
        mutate_synapsis();
        generate_network();
    }

    /**
     * Three types of connections are considered: 1) Between two existing
     * neurons that were already connected: 1.1) Input neuron duplicates and its
     * descendant connects to the same neuron as the parental neuron. A genetic
     * dosage compensation is produced, so the two neurons (which would activate
     * in the same way) share the same weight with alpha and 1-alpha proportion
     * towards the output neuron. Therefore, the offspring Network will have
     * EXACTLY the same fitness as the parent network 1.2) Input neuron connects
     * to a new neuron with weight w1 and the new neuron connects to the output
     * neuron with weight w2. Genetic compensation is produced so the output of
     * new neuron is similar to the one produced by the input neuron. Therefore,
     * the offspring network will tend to have a similar fitness as the parent
     * 2) Between two existing neurons that were not connected: 2.1) two neurons
     * that were not connected are linked. Conditions: - k and c are small
     * enough so the connection initially does not affect the performance of the
     * network
     *
     * @param c
     */
    protected void add() {
// a new connection can be generated by three ways: a direct connection between two neurons, a new neuron that appears by copying another neuron
// by adding a linear neuron between two existing connections.
        double p = ThreadLocalRandom.current().nextDouble();
        if (p <= ThreadLocalRandom.current().nextDouble()) {
            add_neuron_between_two_neurons(1);
        } else {
            add_de_novo_synapses(1);
        }
//        } else {
//        }
//        else {
//            duplicate_neuron((ChromosomeFNN) c);
//        }
        generate_network();

    }

    protected void mutate_activation_output() {
        // Pick an output neuron at random
        int n_out = ThreadLocalRandom.current().nextInt(getFr().getOutputs().size());
        // Update the activation function
        getFr().getOutputs().get(n_out).getAf().updateParameters();
    }

    protected void mutate_synapsis() {
        int i_synapse = ThreadLocalRandom.current().nextInt(size());
        VSynapsis v = get(i_synapse);
        double p = ThreadLocalRandom.current().nextDouble();
        if (p < 0.1) {
            v.getI().setAf(getActivations().generate_a_new_activation());
        } else {
            v.apply_update();
        }
    }

    /**
     * Add a neuron in an existing synapsis.The new neuron has an activation
     * function that is generalized Elliot set at parameters k, s and c so it is
     * a linear activation
     *
     * @param n is the number of neurons to add between synapses
     */
    protected void add_neuron_between_two_neurons(int n) {
        // pick a synapse at random. Add a new neuron between the input and output
        for (int n_s = 0; n_s < n; n_s++) {
            int i_synapse = ThreadLocalRandom.current().nextInt(size());
            VSynapsis v = get(i_synapse);
            // offspring a. It is a copy of neuron A
            CellBodyHiddenImplemented a = new CellBodyHiddenImplemented();
            VSynapsis A_a = new VSynapsis(new Synapsis(v.getI().getInput_neuron(), a, v.getI().copy_Activation_Function()), new VSynapsisUpdater());
            // New activation:
            Activation_Generalized afr_a_B = getActivations().generate_a_new_activation();
//            afr_a_B.getC().getI().setD(old_slope_of_ReLU/v.getI().getAf().getC().getI().getD());
            VSynapsis a_B = new VSynapsis(new Synapsis(a, v.getI().getOutput_neuron(), afr_a_B), new VSynapsisUpdater());
            // Remove the old synapsis between A and B in the chromosome
            remove(i_synapse);
            // Add the new synapses A->a and a->B
            add(A_a);
            add(a_B);
        }
    }

    /**
     * We generate a new synapsis between two neurons that were not connected
     * before.The slope of the ReLU_Generalized must be something very small in
     * order to not generate strong deviations from the previous output
     *
     * @param c
     * @param n the number of de novo synapsis that we are going to implement
     */
    protected void add_de_novo_synapses(int n) {
        // pick a input neuron. It can be an existing neuron in the network or an input neuron that had no connections
        ArrayList<Neuron> neurons = getFr().getNeurons();
        for (int syn = 0; syn < n; syn++) {
            int id_selected_neuron_A = ThreadLocalRandom.current().nextInt(neurons.size());
            ArrayList<Neuron> possible_partners = new ArrayList<>();
            // For each of the possible neurons
            for (Neuron nn : neurons) {
                // If the neuron is not input, then it can be a partner
                if (!((nn instanceof NeuronInput))) {
                    // Get all the axonal connecitons of the neuron A
                    AxonalConnections axCon = neurons.get(id_selected_neuron_A).getAxons();
                    // Go through all the connections. Check that the neuron nn is not already in the list of synapses to A
                    boolean include = true;
                    for (AxonConnection ax : axCon) {
                        if (ax.getNeuron().getC().equals(nn.getC())) {
                            include = false;
                            break;
                        }
                    }
                    if (include) {
                        possible_partners.add(nn);
                    }
                }
            }

            if (!possible_partners.isEmpty()) {
                int id_selected_neuron_B = ThreadLocalRandom.current().nextInt(possible_partners.size());
                // Adding this synapsis will be likely allowed when re-computing the fitness
                VSynapsis A_B = new VSynapsis(new Synapsis(neurons.get(id_selected_neuron_A).getC(), (CellBodyHidden) possible_partners.get(id_selected_neuron_B).getC(), getActivations().generate_a_new_activation()), new VSynapsisUpdater());
                // Add the new synapsis between A and B
//            A_B.getI().setTimeWhenSynapsisWasCreated(-10);
                add(A_B);
            }
        }
    }

    /**
     * Remove a neuron and all its connections. If it is an input Neuron, just
     * remove all the axon synapses
     *
     * @param c
     */
    protected void remove_neuron() {
        ArrayList<Neuron> n = getFr().getNeurons();

        // Neurons with axons
        ArrayList<Neuron> n_with_axons = new ArrayList<>();

        for (Neuron nn : n) {
            // By definition we cannot remove output neurons
            if (!(nn instanceof NeuronOutput)) {
                boolean include_in_n_with_axons = true;
                // Check that the axons are not NeuronOutputs. If they are, check out that they have more dendrites that the neuron here
                AxonalConnections ax = nn.getAxons();
                for (AxonConnection aax : ax) {
                    // If the axon goes to a Neuron output, then check that the Neuron output gets more entrances from other neurons than this
                    if (aax.getNeuron() instanceof NeuronOutput && aax.getNeuron().getDendrites().size() == 1) {
                        include_in_n_with_axons = false;
                        break;
                    }
                }
                if (include_in_n_with_axons) {
                    n_with_axons.add(nn);
                }
            }
        }
// Pick one of the eligible neurons at random
        if (n_with_axons.size() > 0) {
            int i = ThreadLocalRandom.current().nextInt(n_with_axons.size());
            // Remove all the axonal connections
            AxonalConnections axons = n_with_axons.get(i).getAxons();

            axons.forEach((a) -> {
                remove(a.getV());
            });

            // If it is a hidden neuron, also remove all the dendritic connections
            DendriteConnections dendrites = n_with_axons.get(i).getDendrites();
            dendrites.forEach((d) -> {
                remove(d.getV());
            });
// Generate the network
            generate_network();
// Remove the synapses from the chromosome       
            clear();
// Add the new synapses after removing the ones that are not active anymore        
            addAll(getFr().prune());
        }
    }

    /**
     * Remove the connection. An Input cell can have no connection. However,
     * there must be a path between each output neuron and, at least, one input
     * neuron. Removing a connection is dangerous because it is likely that it
     * is going to generate a big jump in the fitness. Intuitively, removing a
     * connection is going to generate a negative effect in the network. Add
     * Parental care so the offspring gets help from the fitness of the parents
     * in case its fitness is worse than the one from the parents.
     *
     * @param c
     */
    protected void remove_synapsis() {
        ArrayList<VSynapsis> synapsis_to_remove = new ArrayList<>();
        for (VSynapsis v : this) {
            if (!(getFr().getNeuron(v.getI().getOutput_neuron()) instanceof NeuronOutput && getFr().getNeuron(v.getI().getOutput_neuron()).getDendrites().size() == 1)) {
                synapsis_to_remove.add(v);
            }
        }
        if (!synapsis_to_remove.isEmpty()) {
// Pick a synapse to erase        
            int i_synapse = ThreadLocalRandom.current().nextInt(synapsis_to_remove.size());
            // That would be the chromosome without the synapsis connection
            remove(synapsis_to_remove.get(i_synapse));
// Generate the network
            generate_network();
// Remove the synapses        
            clear();
// Add the new synapses after removing the ones that are not active anymore        
            addAll(getFr().prune());
        }
    }
}
