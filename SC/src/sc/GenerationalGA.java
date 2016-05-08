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
public class GenerationalGA extends GeneticAlgorithm {
    private final int exp_cross, exp_mut;

    public GenerationalGA(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed, 0.7, 0.001);
        
        exp_cross = (int) (getCross_prob() * getPopulation() / 2);
        exp_mut = (int) (getMut_prob() * getPopulation() * getNumCar());
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
