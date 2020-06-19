/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import demography.EvolvePopulation;
import demography.IndividualSample;
import demography.ParametersDemography;
import demography.Population;
import demography.Samples_at_time;
import event.HistoryOfEventsEvolutionarySimulator;
import exception.ExceptionDemography;
import exception.ExceptionPopulation;
import exception.ParameterException;
import java.util.ArrayList;
import locus.BinarySequence;
import locus.FragmentWithAncestry;
import locus.Fragment;
import locus.Sequence;
import maths.FixedValue;
import maths.RandomUniform;
import parametersSimulation.ParametersChromosome;
import parametersSimulation.ParametersGenome;
import parametersSimulation.parameters.ModelParameter;

/**
 *
 * @author olao
 */
public class AdmixtureModel extends DemographicModel {

    public AdmixtureModel(ParametersGenome pg) throws ParameterException, ParameterException, ExceptionPopulation, ExceptionDemography {
        super(pg);
    }

    @Override
    public void defineDemography() throws ParameterException, ExceptionDemography, ExceptionPopulation {
        ArrayList<Population> populations = new ArrayList<>();
        String[] names = {"African", "European", "Easian", "Oceanian", "Neanderthal", "Denisovan"};
        ModelParameter[] Ne = new ModelParameter[names.length];
        Ne[0] = new ModelParameter("NeAfrica", new RandomUniform(new ModelParameter(new FixedValue(10000)), new ModelParameter(new FixedValue(80000))));
        Ne[1] = new ModelParameter("NeEuropean", new RandomUniform(new ModelParameter(new FixedValue(10000)), new ModelParameter(new FixedValue(20000))));
        Ne[2] = new ModelParameter("NeEasian", new RandomUniform(new ModelParameter(new FixedValue(10000)), new ModelParameter(new FixedValue(20000))));  
        Ne[3] = new ModelParameter("NeOceanian", new RandomUniform(new ModelParameter(new FixedValue(10000)), new ModelParameter(new FixedValue(20000))));  
        Ne[4] = new ModelParameter("NeNeanderthal", new RandomUniform(new ModelParameter(new FixedValue(1000)), new ModelParameter(new FixedValue(10000))));
        Ne[5] = new ModelParameter("NeDenisovan", new RandomUniform(new ModelParameter(new FixedValue(1000)), new ModelParameter(new FixedValue(10000))));
        // Sample size is in number of diploid individuals
        int[] sample_size = {1, 1, 1, 1, 1, 1};
        // times when the samples are sampled
        int[] times = {0, 0, 0, 0, 1800, 1414};
        // Create the painting
        for (int p = 0; p < names.length; p++) {
            ParametersDemography pd = new ParametersDemography(Ne[p]);
            boolean traceAncestralityFromThisPopulationInOtherPopulations = names[p].equals("Neanderthal");
            boolean shallICheckTheAncestralityInThisPopulation = names[p].equals("European");
            Population pop = new Population(names[p], pd, shallICheckTheAncestralityInThisPopulation, traceAncestralityFromThisPopulationInOtherPopulations);
            pop.add(new Samples_at_time(sample_size[p], times[p]));
            populations.add(pop);
        }

        events = new HistoryOfEventsEvolutionarySimulator(populations, pg);
//        events.addInitialMigrationBetween(names[0], names[1], new FixedValue(0.00001));
//        events.addInitialMigrationBetween(names[1], names[2], new FixedValue(0.0000000001));                

// EVENTS
// Event tIntrogression Neanderthal Europe
//        ModelParameter tIntrogressionNeanderthal_Europe = new ModelParameter("tIntrogression", new RandomUniform(new ModelParameter(new FixedValue(1000)), new ModelParameter(new FixedValue(2000))));
        ModelParameter tIntrogressionNeanderthal_Europe = new ModelParameter("tIntrogression", new FixedValue(1600));

        ModelParameter introgression = new ModelParameter("introgession", new RandomUniform(new ModelParameter(new FixedValue(0.01)), new ModelParameter(new FixedValue(0.15))));
        events.mergeAPercentageOfSourceIntoRecipient(tIntrogressionNeanderthal_Europe, "European", "Neanderthal", introgression);

// Event time split Oceania Easia

        ModelParameter tSplitOceaniaEasia = new ModelParameter("tSplitOceaniaEasia", new FixedValue(1000));
        events.mergeSourceIntoRecipient(tSplitOceaniaEasia, "Oceanian", "Easian");
        
// Event time split Easia Europe
        ModelParameter tSplitEasiaEurope = new ModelParameter("tSplitEasiaEurope", new FixedValue(1500));
        events.mergeSourceIntoRecipient(tSplitEasiaEurope, "Easian", "European");    

// Event time split Denisovan from Neanderthal
        ModelParameter tSplitDenisovanNeanderthal = new ModelParameter("tSplitDenisovanNeanderthal", new FixedValue(10000));
        events.mergeSourceIntoRecipient(tSplitDenisovanNeanderthal, "Denisovan", "Neanderthal");            
        
// Event time split Europe Africa
//        ModelParameter tSplitAfricaEurope = new ModelParameter("tSplitAfricaEurope", new RandomUniform(tIntrogressionNeanderthal_Europe, new ModelParameter(new FixedValue(4000))));
        ModelParameter tSplitAfricaEurope = new ModelParameter("tSplitAfricaEurope", new FixedValue(1800));
        events.mergeSourceIntoRecipient(tSplitAfricaEurope, "European", "African");        
        
// Event time split Humans from Neanderthal
//        ModelParameter tSplitHumanNeanderthal = new ModelParameter("tSplitHumanNeanderthal", new RandomUniform(tIntrogressionNeanderthal_Europe, new ModelParameter(new FixedValue(10000))));
        ModelParameter tSplitHumanNeanderthal = new ModelParameter("tSplitHumanNeanderthal", new FixedValue(14000));

        events.mergeSourceIntoRecipient(tSplitHumanNeanderthal, "African", "Neanderthal");
    }

    public static void main(String[] args) throws Exception {
        // Define the parameters of the genome to be simulated
        ParametersGenome pg = new ParametersGenome();
        // All mutations are in the mutation list
        // Simulate a fragment of 1 Mb.
        pg.add(new ParametersChromosome(100000));        
        AdmixtureModel a = new AdmixtureModel(pg);
        // Evolve the demography of the Admixture Model
        ArrayList<EvolvePopulation> evolvedPopulations = RunSimulation.run(a, 4*60*1000);        
        ArrayList<BinarySequence> binSeqs = new ArrayList<>();
        // For each population, retrieve the samples
        for (EvolvePopulation e : evolvedPopulations) {
            System.out.println(e.getPopulation().getName());
            ArrayList<IndividualSample> sample = e.getSampleIndividual();
            // For each individual of the retrieved population, get the sequences
            for (IndividualSample i : sample) {
                Sequence[][] sequences = i.getSequences();
                // Sequences by independent locus
                for (Sequence[] seq : sequences) {
                    // Sequence at each chromosome
                    for (int dip = 0; dip < 2; dip++) {
                        // A sequence with the status (ancestral/derived) of each mutation of m
                        BinarySequence bs = new BinarySequence(seq[dip]);                                               
                        binSeqs.add(bs);
                        //System.out.println(java.util.Arrays.toString(bs.getBinary()));
                        ArrayList<FragmentWithAncestry> fr = seq[dip].getFragmentsWithAncestry();
                        if(!fr.isEmpty())
                        {
                            for(FragmentWithAncestry f:fr)
                            {
                                System.out.println(f);
                            }
                        }
                    }
                }
            }
        }
        
// take a fragment of 100kb and move it along the 1 Mb window.
        
        Fragment window = new Fragment(0, 50000);
        
        for(BinarySequence bs:binSeqs)
        {
            double introgressed_frequency = 0;
            ArrayList<FragmentWithAncestry> intro = bs.get_the_Ancestries_of_this_fragment(window);
            for(FragmentWithAncestry fwa:intro)
            {
                introgressed_frequency += fwa.length();
            }
            introgressed_frequency/= window.length();
            System.out.println(java.util.Arrays.toString(bs.getBinary(window)) + " " + introgressed_frequency);
            System.out.println(java.util.Arrays.toString(bs.getBinary()));
            System.out.println("");
        }
    }
}
