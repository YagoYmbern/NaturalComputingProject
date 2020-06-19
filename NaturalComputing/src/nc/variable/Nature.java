/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.variable;

/**
 * Define the nature of the variable. If it is atomic it means the variable cannot be divided or merged
 * If the variable is compound, it means it can be divided and merged with other variables
 * @author Oscar Lao
 */
public enum Nature {    
    ATOMIC, COMPOUND    
}
