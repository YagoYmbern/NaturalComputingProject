/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import analysis.SiteFrequencySpectrum;
import analysis.WindowSequenceMaker;
import analysis.WindowsBinarySequence;
import data.input_output.InputData;
import data.input_output.OutputParameters;
import demography.EvolvePopulation;
import demography.IndividualSample;
import demography.ParametersDemography;
import demography.Population;
import demography.Samples_at_time;
import error.ErrorFrequencySumOfSquares;
import error.ErrorSumOfSquares;
import event.HistoryOfEventsEvolutionarySimulator;
import exception.ExceptionDemography;
import exception.ExceptionPopulation;
import exception.ParameterException;
import file.ReadSimulation;
import file.SimulationData;
import file.WriteSimulation;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import locus.BinarySequence;
import locus.Fragment;
import locus.FragmentWithAncestry;
import locus.Sequence;
import maths.FixedValue;
import maths.RandomUniform;
import nc.MetaOptimizer;
import nc.function.optimize.Network;
import nc.input_output.InputOutput_Tensor;
import nc.layer.neural_network.Layer_FreeNN;
import nc.neurocomputing.activation.Activation_Elliot;
import nc.neurocomputing.activation.Activation_Linear;
import nc.solution.ChromosomeFNN;
import nc.strategyToAscertainOffspring.brood_competition.FittestBroodSelection;
import nc.strategyToAscertainParentsToMate.mate.EachParentIsACouple;
import nc.strategyToCombineParentsAndOffspring.StrategyToCombineParentsAndOffspring;
import nc.strategyToExchangeSolutionsAmongOptimizers.ExchangeSolutionsBetweenOptimizersAtRandom;
import nc.strategyToGenerateSolutionsFromParents.Procreation;
import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.BroodByCoupleProportionalToFitness;
import nc.strategyToGenerateSolutionsGivenParents.number_of_offspring.FunctionToDecideNumberOfOffspring;
import nc.variable.vdouble.RealNumber;
import nc.variable.vdouble.VDouble;
import nc.variable.vneuron.FreeNeuronNetwork;
import parametersSimulation.MutationList;
import parametersSimulation.ParametersChromosome;
import parametersSimulation.ParametersGenome;
import parametersSimulation.parameters.ModelParameter;
import read.ReadFile;
import write.WriteFile;

/**
 *
 * @author Oscar Lao
 */
public class AdmixtureSimpleModel extends DemographicModel {

    public AdmixtureSimpleModel(ParametersGenome pg) throws ParameterException, ParameterException, ExceptionPopulation, ExceptionDemography {
        super(pg);
    }

    @Override
    public void defineDemography() throws ParameterException, ExceptionDemography, ExceptionPopulation {
        ArrayList<Population> populations = new ArrayList<>();
        String[] names = {"African", "European", "Neanderthal", "Denisovan"};
        ModelParameter[] Ne = new ModelParameter[names.length];
        Ne[0] = new ModelParameter("NeAfrica", new RandomUniform(new ModelParameter(new FixedValue(10000)), new ModelParameter(new FixedValue(30000))));
        Ne[1] = new ModelParameter("NeEuropean", new RandomUniform(new ModelParameter(new FixedValue(10000)), new ModelParameter(new FixedValue(20000))));
        Ne[2] = new ModelParameter("NeNeanderthal", new RandomUniform(new ModelParameter(new FixedValue(1000)), new ModelParameter(new FixedValue(10000))));
        Ne[3] = new ModelParameter("NeDenisovan", new RandomUniform(new ModelParameter(new FixedValue(1000)), new ModelParameter(new FixedValue(10000))));
        // Sample size is in number of diploid individuals
        int[] sample_size = {1, 1, 1, 1};
        // times when the samples are sampled
        int[] times = {0, 0, 1800, 1414};
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

        ModelParameter introgression = new ModelParameter("introgession", new RandomUniform(new ModelParameter(new FixedValue(0.1)), new ModelParameter(new FixedValue(0.15))));
        events.mergeAPercentageOfSourceIntoRecipient(tIntrogressionNeanderthal_Europe, "European", "Neanderthal", introgression);

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
        WriteFile wf = new WriteFile("C:\\Users\\Oscar Lao\\OneDrive - CRG - Centre de Regulacio Genomica\\Master students\\2020\\Yago\\Analyses\\sims.sim");
        WriteSimulation wss = new WriteSimulation(wf);
        for (int rep = 0; rep < 5000; rep++) {
            // Define the parameters of the genome to be simulated
            ParametersGenome pg = new ParametersGenome();
            // All mutations are in the mutation list
            // Simulate a fragment of 1 Mb.
            pg.add(new ParametersChromosome(200000));
            AdmixtureSimpleModel a = new AdmixtureSimpleModel(pg);
            wss.write(a, "European", 50000, 25000, 1*30*1000);
        }
        wf.close();
//        
//        // Evolve the demography of the Admixture Model
//        ArrayList<EvolvePopulation> evolvedPopulations = RunSimulation.run(a);
//
//        String test_pop = "European";
//
////        ArrayList<BinarySequence> binSeqs = new ArrayList<>();
//        // For each population, retrieve the samples
//        // For each individual of the retrieved population, get the sequences
//        ArrayList<BinarySequence> test = new ArrayList<>();
//        ArrayList<BinarySequence> refs = new ArrayList<>();
//        for (EvolvePopulation e : evolvedPopulations) {
//            System.out.println(e.getPopulation().getName());
//            ArrayList<IndividualSample> sample = e.getSampleIndividual();
//            for (IndividualSample i : sample) {
//                Sequence[][] sequences = i.getSequences();
//                // Sequences by independent locus
//                for (Sequence[] seq : sequences) {
//                    // Sequence at each chromosome
//                    for (int dip = 0; dip < 2; dip++) {
//                        // A sequence with the status (ancestral/derived) of each mutation of m
//                        BinarySequence bs = new BinarySequence(seq[dip]);
//                        if (e.getPopulation().getName().equals(test_pop)) {
//                            test.add(bs);
//                        } else {
//                            refs.add(bs);
//                        }
//                    }
//                }
//            }
//        }
//        int[] sfs = SiteFrequencySpectrum.sfs(test.get(0), refs);
//        double introgressed_frequency = 0;
//        ArrayList<FragmentWithAncestry> intro = test.get(0).get_the_Ancestries_of_this_fragment(pg.get(0).getMutationList().getFragment());
//        for (FragmentWithAncestry fwa : intro) {
//            introgressed_frequency += fwa.length();
//        }
//        introgressed_frequency /= pg.get(0).getMutationList().getFragment().length();
//
//        System.out.println(java.util.Arrays.toString(sfs) + " " + introgressed_frequency);
//        sfs = SiteFrequencySpectrum.sfs(test.get(1), refs);
//        introgressed_frequency = 0;
//        intro = test.get(1).get_the_Ancestries_of_this_fragment(pg.get(0).getMutationList().getFragment());
//        for (FragmentWithAncestry fwa : intro) {
//            introgressed_frequency += fwa.length();
//        }
//        introgressed_frequency /= pg.get(0).getMutationList().getFragment().length();
//        System.out.println(java.util.Arrays.toString(sfs) + " " + introgressed_frequency);
//// take a fragment of 100kb and move it along the 1 Mb window.
//        
//        Fragment window = new Fragment(0, 50000);
//        
//        for(BinarySequence bs:binSeqs)
//        {
//            double introgressed_frequency = 0;
//            ArrayList<FragmentWithAncestry> intro = bs.get_the_Ancestries_of_this_fragment(window);
//            for(FragmentWithAncestry fwa:intro)
//            {
//                introgressed_frequency += fwa.length();
//            }
//            introgressed_frequency/= window.length();
//            System.out.println(java.util.Arrays.toString(bs.getBinary(window)) + " " + introgressed_frequency);
////            System.out.println(java.util.Arrays.toString(bs.getBinary()));
////            System.out.println("");
//        }
    }
}
