/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.input_output;

/**
 * InputOutput is a class that stores the sparse data in a HashMap
 *
 * @author Oscar Lao
 */
public class InputOutput_Tensor {

    private final double[][][][] m;
    private final int n_time_series, n_channel, n_rows, n_columns;

    /**
     * CreateInputOutput object with this dataset
     *
     * @param n_time_series how many time series are we considering
     * @param n_channel for each time series, we have a picture. Each picture has n channels (i.e. colors)
     * @param n_rows the number of rows of the picture
     * @param n_columns the number of columns of the picture
     */
    public InputOutput_Tensor(int n_time_series, int n_channel, int n_rows, int n_columns) {
        this.n_time_series = n_time_series;
        this.n_channel = n_channel;
        this.n_columns = n_columns;
        this.n_rows = n_rows;
        m = new double[n_time_series][n_channel][n_rows][n_columns];
    }

    /**
     * A tensor with n time series, n chanels, n rows and ncolumns.
     * A value in position i of a vector with no time series would be coded as 0,0,0,i -> v
     * @param m 
     */
    public InputOutput_Tensor(double[][][][] m) {
        this.m = m;
        n_time_series = m.length;
        n_channel = m[0].length;
        n_rows = m[0][0].length;
        n_columns = m[0][0][0].length;
    }

    /**
     * Get the number of time series of the pictures
     * @return 
     */
    public int getN_time_series() {
        return n_time_series;
    }
    
    
    /**
     * Get the number of channels of the picture
     *
     * @return
     */
    public int getN_channel() {
        return n_channel;
    }

    /**
     * Get the number of columns of the picture
     *
     * @return
     */
    public int getN_columns() {
        return n_columns;
    }

    /**
     * Get the number of rows of the picture
     * @return 
     */
    public int getN_rows() {
        return n_rows;
    }        

    /**
     * put in the channel and window the value if the value is greater than 0
     *
     * @param time
     * @param channel
     * @param row
     * @param value
     * @param column
     * @return
     */
    public boolean put(int time, int channel, int row, int column, double value) {
        m[time][channel][row][column] = value;
        return true;
    }

    /**
     * Get the value
     *
     * @param time
     * @param channel
     * @param row
     * @param column
     * @return
     */
    public double get(int time, int channel, int row, int column) {
        return m[time][channel][row][column];
    }

    /**
     * Get the submatrix representation of the list of channels
     *
     * @param channels
     * @return
     */
    public double[][][] get(int[] channels) {
        double[][][] m = new double[channels.length][n_rows][n_columns];
        int r = 0;
        for (int chan : channels) {
            for (int row = 0; row < n_rows; row++) {
                for (int w = 0; w < n_columns; w++) {
                    m[r][row][w] = get(0,chan, row, w);
                }
            }
            r++;
        }
        return m;
    }
    
    /**
     * Get the matrix
     * @return 
     */
    public double [][][][] get()
    {
        return m;
    }
}
