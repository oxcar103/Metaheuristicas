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
public class LocalSearch extends RandomHeuristic{
    
    public LocalSearch(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed);        
    }

    @Override
    void Train() {
        boolean improv = true;
        
        while(improv){
            improv = false;
            
            for(int i = 0; i < num_car && !improv; i++){
                if(Evaluate(i) > Evaluate()){
                    Flip(i);
                    improv = true;
                }
            }
        }
    }
    
    
    
}
