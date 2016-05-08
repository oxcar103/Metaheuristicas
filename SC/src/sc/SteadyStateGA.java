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
    private final int num_childs = 2;
    
    public SteadyStateGA(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed, 1, 0.001);
    }

    @Override
    protected void Selection() {
    int asp;
        
        while(selected_parents.size() != num_childs){
            asp = BinaryTournament();
            
            if(!selected_parents.contains(asp)){
                selected_parents.add(asp);
            }
        }
    }

    @Override
    protected void Mutation() {
        double mut_prob = getMut_prob();   // Set value in a variable for efficiency
        
        // For each child...
        for(int i = 0; i < childs.size(); i++) {
            // ... and each caracteristic...
            for(int j = 0; j < getNumCar(); j++) {
                //... try to mute
                if(rnd.nextDouble() < mut_prob){
                    // Flip
                    childs.get(i)[j] = !childs.get(i)[j];
                
                    if(childs.get(i)[j]){
                        num_c_sel_childs.set(i, num_c_sel_childs.get(i)+1);
                    }
                    else{
                        num_c_sel_childs.set(i, num_c_sel_childs.get(i)-1);
                    }
                }
            }
            
            // Evaluate solutions childs
            eval_childs.add(Evaluate(childs.get(i)));
        }
    }

    @Override
    protected void Inheritance() {
        
    }

}
