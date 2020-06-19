/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable.vinteger;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Oscar Lao
 */
public class GeneratorVIntegerRange extends GeneratorVInteger{
    
    public GeneratorVIntegerRange(UpdateVIntegerRange stu) {
        super(stu);
    }

    @Override
    public VInteger generateVariable() {
        int from = ((UpdateVIntegerRange)stu).getFrom();
        int to = ((UpdateVIntegerRange)stu).getTo();
        return new VInteger(ThreadLocalRandom.current().nextInt(to - from) + from, getStu());
    }           
}
