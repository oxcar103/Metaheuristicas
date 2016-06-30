/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public abstract class MemeticAlgorithm extends GenerationalGA{
    int iteration, proportion;
    boolean best_childs;
    
    public MemeticAlgorithm(Instances inst, int col_class, int seed, int iter, int prop, boolean best) {
        super(inst, col_class, seed);
        
        iteration = iter;
        proportion = prop;
        best_childs = best;
    }
    
}
