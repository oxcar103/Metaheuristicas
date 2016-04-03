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
        boolean [] old_car = car.clone();
        int old_num_c_sel = num_c_sel;
        int eval_neigh, eval_act = Evaluate();
        
        // If the actual solution is good enough, this variable stop the loop
        int it_wo_succ = 0;
        
        while(eval < max_eval && it_wo_succ < 1.5 * num_car ){
            GenerateNeighbour();

            eval_neigh = Evaluate();
            
            if(eval_neigh > eval_act){
                old_car = car.clone();
                old_num_c_sel = num_c_sel;
                eval_act = eval_neigh;
                it_wo_succ = 0; 
            }
            else{
                car = old_car.clone();
                num_c_sel = old_num_c_sel;
                it_wo_succ++;
            }
        }
    }
    
}
