///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package fnn;
//
//import java.util.concurrent.ThreadLocalRandom;
//import maths.MDS;
//
///**
// *
// * @author Oscar Lao
// */
//public class TestMDS {
//    public static void main(String [] args)
//    {
//        double [][] x = new double[100][2];
//        for(int i =0; i<x.length;i++)
//        {
//            for(int v=0;v<x[i].length;v++)
//            {
//                x[i][v] = ThreadLocalRandom.current().nextGaussian() + 5*(i/50);
//            }
//            System.out.println(x[i][0] + " " + x[i][1]);
//        }
//        
//        double [][] d = new double[x.length][x.length];
//        for(int i1=0;i1<x.length-1;i1++)
//        {
//            for(int i2 = i1+1; i2<x.length;i2++)
//            {
//                for(int v = 0; v< x[i1].length; v++)
//                {
//                d[i1][i2] += Math.pow(x[i1][v]-x[i2][v],2);                    
//                }
//                d[i1][i2] = Math.pow(d[i1][i2],0.5);
//                d[i2][i1] = d[i1][i2];
//            }
//        }
//        
//        MDS mds = new MDS(d);
//        
//        double [][] vs = mds.getEigenVectors(2);
//        
//        System.out.println(vs.length + " " + vs[0].length);
//        
//        for(int i =0; i<vs.length;i++)
//        {
//            System.out.println(vs[i][0] + " " + vs[i][1]);
//        }        
//    }    
//}
