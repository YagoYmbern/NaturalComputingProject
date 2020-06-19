/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import analysis.SiteFrequencySpectrum;
import analysis.WindowSequenceMaker;
import analysis.WindowsBinarySequence;
import demography.EvolvePopulation;
import demography.IndividualSample;
import demography.Population;
import event.EventEvolutionarySimulator;
import event.HistoryOfEventsEvolutionarySimulator;
import exception.ExceptionDemography;
import exception.ExceptionFragment;
import exception.ExceptionGenome;
import exception.ExceptionIndividual;
import exception.ExceptionMutation;
import exception.ExceptionPopulation;
import exception.ExceptionTimeRun;
import exception.ParameterException;
import java.util.ArrayList;
import locus.BinarySequence;
import locus.FragmentWithAncestry;
import locus.Sequence;
import parametersSimulation.parameters.ModelParameter;
import simulator.DemographicModel;
import simulator.RunSimulation;
import write.WriteFile;

/**
 *
 * @author Oscar Lao
 */
public class WriteSimulation {

    private final WriteFile wf;

    /**
     * Where the simulation is stored
     *
     * @param wf
     */
    public WriteSimulation(WriteFile wf) {
        this.wf = wf;
    }

    public static String locus_Separator()
    {
        return "|";
    }
    
    public static String introgression_Separator()
    {
        return "=";
    }
    
    public static String window_Separator()
    {
        return ":";
    }
    
    public static String parameter_Separator()
    {
        return "<";
    }
    
    public static String sequence_Separator()
    {
        return ";";
    }
    
    /**
     * Write a simulation that comes from the demographic model a, uses the
     * population test to quantify the introgressed ancestry in windows of
     * window_size_in_bp and a sliding window of window_shift
     *
     * @param a
     * @param population_test
     * @param window_size_in_bp
     * @param window_shift
     * @throws ExceptionFragment
     * @throws ExceptionIndividual
     * @throws ExceptionGenome
     * @throws ParameterException
     * @throws ExceptionDemography
     * @throws ExceptionPopulation
     * @throws ExceptionMutation
     */
    public void write(DemographicModel a, String population_test, int window_size_in_bp, int window_shift, double max_time_execution_in_millisec) throws ExceptionFragment, ExceptionIndividual, ExceptionGenome, ParameterException, ExceptionDemography, ExceptionPopulation, ExceptionMutation {
        try {
//Run the simulation and get the evolved populations. If the time of execution excedes the maximum amount of time, then throw an exception that is caught and do nothing
            ArrayList<EvolvePopulation> evolvedPopulations = RunSimulation.run(a, max_time_execution_in_millisec);
            // Write the parameters of the simulation
            HistoryOfEventsEvolutionarySimulator par = a.getDemography();
            ArrayList<Population> pops = par.getPopulations();
            wf.print(pops.get(0).getParameterDemography().getEffectivePopulationSizeOfOneParent().getFg().getGeneratedNumber().intValue());
            for (int pp = 1; pp<pops.size();pp++) {
                Population p = pops.get(pp);
                wf.print(" " + p.getParameterDemography().getEffectivePopulationSizeOfOneParent().getFg().getGeneratedNumber().intValue());
            }
// For each evolutionary event, get the parameters and the values of the parameters
            for (EventEvolutionarySimulator event : par) {
                ArrayList<ModelParameter> params = event.getParameters_in_this_event();
                for (ModelParameter f : params) {
                    wf.print(" " + f.getFg().getGeneratedNumber().doubleValue());
                }
            }
            // Start the SFS of each locus
            wf.print(parameter_Separator());
            // For each simulated locus
            for (int l = 0; l < a.getPg().size(); l++) {
                // binary sequences of the simulation that are going to be used to project the test sequences
                ArrayList<WindowsBinarySequence> binSeqs = new ArrayList<>();
                // Sequences that are going to be used to test the introgression
                ArrayList<WindowsBinarySequence> testSequences = new ArrayList<>();
                // For each population, retrieve the samples
                for (EvolvePopulation e : evolvedPopulations) {
                    ArrayList<IndividualSample> sample = e.getSampleIndividual();
                    // For each individual of the retrieved population, get the sequences
                    for (IndividualSample i : sample) {
                        Sequence[] seq = i.getSequences()[l];
                        // Sequence at each chromosome of the diploid individual
                        for (int dip = 0; dip < 2; dip++) {
                            // A sequence with the status (ancestral/derived) of each mutation of m
                            BinarySequence bs = new BinarySequence(seq[dip]);
                            if (e.getPopulation().getName().equals(population_test)) {
                                testSequences.add(WindowSequenceMaker.generateWindows(window_size_in_bp, window_shift, bs));
                            } else {
                                binSeqs.add(WindowSequenceMaker.generateWindows(window_size_in_bp, window_shift, bs));

                            }
                        }
                    }
                }
// Now compute the SFS of each Window for each test sequence
                for (WindowsBinarySequence t : testSequences) {
                    // for each window, retrieve the binary sequence
                    for (int w = 0; w < t.size(); w++) {
                        // Reference sequences
                        ArrayList<BinarySequence> bin = new ArrayList<>();
                        for (WindowsBinarySequence b : binSeqs) {
                            bin.add(b.get(w));
                        }
                        // SFS of the test sequence from the population of interest and the test sequences
                        int[] sfs = SiteFrequencySpectrum.sfs(t.get(w), bin);
                        wf.print(sfs[0]);
                        for (int e = 1; e<sfs.length;e++) {
                            int s = sfs[e];
                            wf.print(" " + s);
                        }
                        // Amount of introgression
                        double introgressed_frequency = 0;
                        ArrayList<FragmentWithAncestry> intro = t.get(w).getSequence().getFragmentsWithAncestry();
                        for (FragmentWithAncestry fwa : intro) {
                            introgressed_frequency += fwa.length();
                        }
                        introgressed_frequency /= t.get(w).getSequence().getM().getFragment().length();

                        // Marker of introgression
                        wf.print(introgression_Separator() + introgressed_frequency);

                        // Marker of window
                        wf.print(window_Separator());
                    }
                    // Marker of test sequence
                    wf.print(sequence_Separator());
                }
                // Marker of locus
                wf.print(locus_Separator());
            }
            // End of simulation
            wf.println("");
        } catch (ExceptionTimeRun tim) {

        }
    }
}
