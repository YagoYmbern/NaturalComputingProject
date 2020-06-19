/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.strategyToAscertainParentsToMate.mate;

import nc.strategyToAscertainParentsToMate.couple.CoupleConstantSize;
import nc.strategyToAscertainParentsToMate.fitness.FitnessCoupleAverage;
import nc.strategyToAscertainParentsToMate.weight.ProbabilityParentUniform;

/**
 * A particular implementation in which we only have one individual
 * @author olao
 */
public class ARandomParentDefineACouple extends ReproductiveSystem {   
    public ARandomParentDefineACouple()
    {
        super(1, new FitnessCoupleAverage(), new ProbabilityParentUniform(), new CoupleConstantSize(1));
    }
}
