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
public class BasicMultiBootingSearch extends LocalSearch{
    private final int num_initial_solutions;
    
    public BasicMultiBootingSearch(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed);
        
        num_initial_solutions = 25;
    }

    @Override
    void Train() {
        boolean [] best_car;
        int best_eval, best_num_car, eval_act;
        
        super.Train();
        
        best_eval = Evaluate();
        best_car = car.clone();
        best_num_car = num_c_sel;
        
        for(int i = 1; i < num_initial_solutions; i++){
            RandomSolution();
            super.Train();
            
            eval_act = Evaluate();
            
            if(eval_act > best_eval){
                best_eval = eval_act;
                best_car = car.clone();
                best_num_car = num_c_sel;
            }
        }
        
        car = best_car.clone();
        num_c_sel = best_num_car;
    }

}
