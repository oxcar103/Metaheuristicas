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
        
        while(eval < max_eval){
            GenerateNeighbour();

            eval_neigh = Evaluate();
            
            if(eval_neigh > eval_act){
                old_car = car.clone();
                old_num_c_sel = num_c_sel;
                eval_act = eval_neigh;
            }
            else{
                car = old_car.clone();
                num_c_sel = old_num_c_sel;
            }
        }
    }
    
}
