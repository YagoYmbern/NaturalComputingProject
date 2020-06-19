/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vdouble.updater.social.PSO;

/**
 *
 * @author olao
 */
public class HyperParameters_PSO_Atomic_Charged extends HyperParameters_PSO{

       /**
     * Generate a new movement of Swarm type.Low values for c1 and c2 encourage
     * particles to explore far away from already uncovered good points as there
     * is less emphasis on past learning.High values of these parameters
     * encourage more intensive search of regions close to these.Exploration is
     * encouraged by selecting a high value of W = wmax -
     * itercurr*(wmax-wmin)/itermax relative to the values of c1 and c2
     * points.(Extracted from Natural Computing)
     *
     * @param c1 is the weight coefficient that controls the velocity of best
     * solution associated to this object
     * @param c2 is the weight coefficient that controls the velocity of best
     * solution associated to the whole population
     * @param min_momentum_weight the minimum momentum weight to multiply. For
     * example 0.4 current speed
     * @param max_momentum_weight the maximum momentum weight to multiply. For
     * example 0.9 current speed
     * @param maximum_number_iterations maximum number of iterations that our
     * algorithm is running
     * @param probability_of_being_charged the probability that a particle is
     * charged. Is set to 0, then classical Canonical is done
     * @param pdist the maximum distance with respect to the standard deviation
     * of the variable at which two particles are charged. pdist must be
     * positive. For example, a pdist of 1 means that any distance that is
     * greater than the standard deviation will be considered as not related.
     * Not used if probability_of_being_charged = 0
     * @param pcoredist the minimum distance with respect to the standard
     * deviation of the variable at which two particles that are charged will
     * use formula 8.18 in Natural Computing and start using formula 8.19.
     * pcoredist must be positive and smaller than pdist. For example, a
     * pcoredist of 0.1 means that a distance at a variable between two
     * solutions smaller than 0.1*standard deviation will follow formula 8.19.
     * Not used if probability_of_being_charged = 0
     */    
    public HyperParameters_PSO_Atomic_Charged(double c1, double c2, double min_momentum_weight, double max_momentum_weight, int maximum_number_iterations, double probability_of_being_charged, double pdist, double pcoredist) {
        super(c1,c2, min_momentum_weight, max_momentum_weight, maximum_number_iterations);
        this.probability_of_being_charged = probability_of_being_charged;
        this.pdist = pdist;
        this.pcoredist = pcoredist;
    }

    private final double probability_of_being_charged, pdist, pcoredist;

    /**
     * Get the minimum core distance at which the repulsion is maximum
     * @return 
     */
    public double getPcoredist() {
        return pcoredist;
    }

    /**
     * Get the maximum distance at which the particle does not influence anymore
     * @return 
     */
    public double getPdist() {
        return pdist;
    }

    /**
     * Get the probability of being charged
     * @return 
     */
    public double getProbability_of_being_charged() {
        return probability_of_being_charged;
    }
}
