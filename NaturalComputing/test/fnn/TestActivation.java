/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fnn;

import nc.neurocomputing.activation.Activation_Generalized_PLU;

/**
 *
 * @author Oscar Lao
 */
public class TestActivation {

    public static void main(String[] args) {
        Activation_Generalized_PLU ar = new Activation_Generalized_PLU();
        ar.getC().getI().setD(0);
        ar.getK().getI().setD(5);
        System.out.println(ar.toString());
        for (int e = -20; e < 20; e++) {
            System.out.println(e + " " + ar.activate(e));
        }
    }
}
