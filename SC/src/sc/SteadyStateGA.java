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
        super(inst, col_class, seed, 1, 0.001);
    }

    @Override
    protected void Selection() {
    int asp;
        
        while(selected_parents.size() != 2){
            asp = BinaryTournament();
            
            if(!selected_parents.contains(asp)){
                selected_parents.add(asp);
            }
        }
    }

    @Override
    protected void Mutation() {
        double mut_prob = getMut_prob();   // Set value in a variable for efficiency
        
        for(int i = 0; i < childs.size(); i++) {
            for(int j = 0; j < getNumCar(); j++) {
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
        }
        
        // Evaluate solutions childs
        eval_childs.add(Evaluate(childs.get(0)));
        eval_childs.add(Evaluate(childs.get(1)));
    }

    @Override
    protected void Inheritance() {
        
    }

}
