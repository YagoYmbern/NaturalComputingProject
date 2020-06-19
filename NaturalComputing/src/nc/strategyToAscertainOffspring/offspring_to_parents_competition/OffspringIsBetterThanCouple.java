/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainOffspring.offspring_to_parents_competition;

import nc.solution.Solution;
import nc.strategyToAscertainParentsToMate.Couple;

/**
 * A class that models the comparison between offspring and parents. The
 * offspring is better than parents independently of its fitness
 *
 * @author Oscar Lao
 */
public class OffspringIsBetterThanCouple extends StrategyToDecideHowGoodIsAnOffspringComparedToItsParents {

    /**
     * Return the solution offspring as it is.
     *
     * @param parents
     * @param offspring
     * @return
     */
    @Override
    public Solution compareToParents(Couple parents, Solution offspring) {
        return offspring;
    }
}
