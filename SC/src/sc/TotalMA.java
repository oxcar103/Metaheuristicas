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
public class TotalMA extends PartialMA{
    
    public TotalMA(Instances inst, int col_class, int seed, int iter) {
        super(inst, col_class, seed, iter, 1);
    }
    
}
