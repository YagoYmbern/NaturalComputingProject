package nc.strategyToAscertainParentsToMate.weight;

import java.util.Collections;
import nc.Optimizer;
import nc.solution.Solution;

/**
 * The probability of a solution of being parent is proportional to its rank in the population
 * @author olao
 */
public class ProbabilityParentRank extends StrategyProbabilityParent
{

    @Override
    public double getWeight(Solution s) {
        Optimizer optimizer = s.getO();
        int p = Collections.binarySearch(optimizer, s);
        return (optimizer.size()-p)/(double)optimizer.size();        
    }    
}