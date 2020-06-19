/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import java.util.ArrayList;
import locus.BinarySequence;
import maths.BaseConversion;

/**
 *
 * @author Oscar Lao
 */
public class SiteFrequencySpectrum {
    
    /**
     * Compute the site frequency spectrum between all sequences. It is assumed that each two sequences define an individual in the sequences
     * @param test_sequence this is the sequence test. We will have SNVs that for this sequence is 0, others for which it is 1
     * @param sequences the sequences where we will project the SFS of the test sequence
     * @return 
     */
    public static int [] sfs(BinarySequence test_sequence, ArrayList<BinarySequence> sequences)
    {
        // Binary representation of the mutations of this sequence. 0 means ancestral state, 1 means derived state
        byte [] test = test_sequence.getBinary();
// Sequences that will be used to project the test sequence
        byte [][] s = new byte[sequences.size()/2][];
        int c = 0;
        for(BinarySequence bs:sequences)
        {
            byte [] k = bs.getBinary();
            // If we are in the second sequence, add the allele to the previous one so we have a genotype
            if((c+1)%2==0)
            {
                for(int v=0;v<k.length;v++)
                {
                    s[c/2][v]+=k[v];
                }
            }
            else
            {
                // we are starting a new individual
                s[c/2] = new byte[k.length];
                System.arraycopy(k, 0, s[c/2], 0, k.length);                
            }
            c++;
        }
        // The total number of combinations is determined by the two possibilities of allele in the test_sequence and all the possible genotype combinations among the individuals
        // which is 3^number of individuals 
        int n = (int)Math.pow(s.length,3);
        int [] sfs_test_sequence = new int[2*n];
        // For each of the SNVs, compute the SFS combination
        for(int snp=0;snp<test.length;snp++)
        {
            int [] comb = new int[s.length];
            for(int e=0;e<s.length;e++)
            {
                comb[e] = s[e][snp];
            }
            int base = BaseConversion.conversion_from_base_to_decimal(comb, 3);
            // Add the SNP pattern to the sfs_test_sequence. Since the test can have values 0 and 1, the combination must be multiplied either by 1 (corresponding to 0 allele in test) or 2 (corresponding to 1 allele in test)
            sfs_test_sequence[base + test[snp]*n]++;
        }
        return sfs_test_sequence;
    }    
}
