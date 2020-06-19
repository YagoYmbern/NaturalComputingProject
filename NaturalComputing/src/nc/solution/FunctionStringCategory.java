/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.solution;

import java.util.Objects;

/**
 *
 * @author Oscar Lao
 */
public class FunctionStringCategory extends FunctionToDefineCategoryOfSolution{
    
    private final String cat;
    
    public FunctionStringCategory(String cat)
    {
        this.cat = cat;
    }

    /**
     * Get the category
     * @return 
     */
    public String getCat() {
        return cat;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof FunctionStringCategory)
        {
            return ((FunctionStringCategory)o).getCat().equals(cat);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.cat);
        return hash;
    }
}
