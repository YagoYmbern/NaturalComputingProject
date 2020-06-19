/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import java.util.Comparator;

/**
 *
 * @author Oscar Lao
 */
public class ComparatorOptimizersBySize implements Comparator<Optimizer> {

    /**
     * Compare by number of solutions. If the number of solutions in o1 is
     * bigger than o2, return -1. If equal, return 0. If smaller, return 1
     *
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Optimizer o1, Optimizer o2) {
        return -Integer.compare(o1.size(), o2.size());
    }
}
