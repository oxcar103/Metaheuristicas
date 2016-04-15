/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import java.util.ArrayList;
import java.util.List;
import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public class IteratedLocalSearch extends LocalSearch{
    private final int num_initial_solutions, max_num_muted_car;
    private final double prop_muted_car;
    
    public IteratedLocalSearch(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed);
        
        num_initial_solutions = 25;
        prop_muted_car = 0.1;
        max_num_muted_car = (int) prop_muted_car * getNumCar();
    }

    private void NewMutedSolution(){
        List<Integer> muted_car = new ArrayList<Integer>();
        int index_muted_car;
        
        muted_car.add(GenerateNeighbour());
        
        while (muted_car.size() < max_num_muted_car){
            index_muted_car = GenerateNeighbour();
            
            // If this caracteristic muted before, we restore it; else, we add it to list.
            if(muted_car.contains(index_muted_car)){
                Flip(index_muted_car);
            }
            else{
                muted_car.add(index_muted_car);
            }
        }
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
            NewMutedSolution();
            super.Train();
            
            eval_act = Evaluate();
            
            if(eval_act > best_eval){
                best_eval = eval_act;
                best_car = car.clone();
                best_num_car = num_c_sel;
            }
            else{
                car = best_car.clone();
                num_c_sel = best_num_car;
            }
        }    
    }
    
}
