/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.input_output;

/**
 *
 * @author olao
 */
public interface InputOutput {

    /**
     * Get the number of channels
     *
     * @return
     */
    public int getN_channel();

    /**
     * Get the number of columns
     *
     * @return
     */
    public int getN_columns();

    /**
     * Get the number of rows
     *
     * @return
     */
    public int getN_rows();    
    
    /**
     * put in the channel and window the value if the value is greater than 0
     *
     * @param channel
     * @param row
     * @param column
     * @param value
     * @return
     */
    public boolean put(int channel, int row, int column, double value);

    /**
     * Get the value
     *
     * @param channel
     * @param row
     * @param column
     * @return
     */
    public double get(int channel, int row, int column);

    /**
     * Get the submatrix representation of the list of channels
     *
     * @param channels
     * @return
     */
    public double [][][] get(int[] channels);
    
    
    /**
     * Get all the data in matrix format
     * @return 
     */
    public double [][][] get();  
}
