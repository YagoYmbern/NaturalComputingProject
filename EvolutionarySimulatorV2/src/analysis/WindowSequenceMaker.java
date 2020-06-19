/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import exception.ExceptionFragment;
import exception.ExceptionMutation;
import java.util.ArrayList;
import locus.BinarySequence;
import locus.Fragment;

/**
 *
 * @author Oscar Lao
 */
public class WindowSequenceMaker {

    /**
     * Generate the BinarySequences for each fragment of window_size_in_bp
     *
     * @param window_size_in_bp
     * @param window_shift how much we have to move from the start position of
     * the previous window to define a new window. For example, if the window
     * size is 50kb and the window_shift 25kb, it means that the first window
     * will start at position 0 and go to 50kb and the second window from 25kb
     * to 75kb
     * @param binSeqs
     * @return the sequences in non overlapping windows of window_size_in_bp
     * @throws exception.ExceptionFragment
     * @throws exception.ExceptionMutation
     */
    public static WindowsBinarySequence generateWindows(int window_size_in_bp, int window_shift, BinarySequence binSeqs) throws ExceptionFragment, ExceptionMutation {
        WindowsBinarySequence frags = new WindowsBinarySequence(binSeqs.getSequence().getM().getFragment());
        // Generate each window. Start from the begining of the fragment until the end, taking into account that the last window shall finish at the end of the considered fragment
        for (int fr = binSeqs.getSequence().getM().getFragment().getStart(); fr < binSeqs.getSequence().getM().getFragment().getEnd(); fr += window_shift) {
// take a fragment of window size     
            Fragment window = new Fragment(fr, fr + window_size_in_bp);
// pick the BinarySequence that is within this fragment
            frags.add(binSeqs.getFragment(window));
        }
        return frags;
    }
}
