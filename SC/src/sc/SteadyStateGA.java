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
public class SteadyStateGA extends GeneticAlgorithm {

    public SteadyStateGA(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed);
        
        cross_prob = 1;
    }

    @Override
    void Selection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Mutation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void Inheritance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
