/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demography;

import locus.Genome;
import exception.ExceptionIndividual;
import parametersSimulation.ParametersGenome;


/**
 *
 * @author oscar_000
 */
public class Individual {
    /**
     * Create the individual with the number of independent regions to simulate
     * @param population the population of origin of this individual
     * @param pg 
     */
    public Individual(Population population, ParametersGenome pg)
    {
        this.population = population;
        genome = new Genome[pg.size()];
        for(int e=0;e<genome.length;e++)
        {
            genome[e] = new Genome();
        }
    }

    // Genomes of the individual (for example, if we simulate 22 independent chromosomes, each chromosome is one genome)
    protected Genome [] genome;
    private final Population population;
    
    public int size()
    {
        return genome.length;
    }

    public Population getPopulation() {
        return population;
    }       
    
    /**
     * Get the genome of the individual
     * @param i
     * @return
     * @throws ExceptionIndividual 
     */
    public Genome getGenome(int i) throws ExceptionIndividual
    {
        if(i> -1 && i<genome.length)
        {
            return genome[i];
        }
        else
        {
            throw new ExceptionIndividual("The genome to ascertain from the individual is out of bounds " + i);
        }
    }
    
    
}
