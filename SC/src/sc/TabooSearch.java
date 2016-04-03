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
public class TabooSearch extends RandomHeuristic{
    private final int max_neigh = 30;
    
    private final int max_size_t_l;
    
    public TabooSearch(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed);
        
        max_size_t_l = (getNumCar()-1)/3;
    }
 
    @Override
    void Train() {
        List<Integer> taboo_list = new ArrayList<Integer> ();
        boolean [] old_car = car.clone();
        boolean [] best_car = car.clone();
        boolean [] asp_car = car.clone();
        int old_num_c_sel = num_c_sel;
        int num_best_c_sel = num_c_sel;
        int num_asp_c_sel = num_c_sel;
        int eval_act, eval_best = eval_act = Evaluate();
        int eval_neigh, eval_asp;
        int index, best_index = 0;
        
        while(getEval() < getMaxEval()){
            eval_asp = eval_act;
            
            for(int i = 0; i < max_neigh && getEval() < getMaxEval(); i++){
                index = GenerateNeighbour();
                
                eval_neigh = Evaluate();
                
                if(eval_neigh > eval_best){
                    eval_best = eval_asp = eval_neigh;
                    asp_car = car.clone();
                    best_car = car.clone();
                    num_best_c_sel = num_asp_c_sel = num_c_sel;
                    best_index = index;
                }
                else if(!taboo_list.contains(index)){
                    if(eval_neigh > eval_asp){
                        eval_asp = eval_neigh;
                        asp_car = car.clone();
                        num_asp_c_sel = num_c_sel;
                        best_index = index;
                    }
                }
                
                // Restore original values
                car = old_car.clone();
                num_c_sel = old_num_c_sel;
            }
        
            // If aspiration criterion is actived, remove used index
            if(taboo_list.contains(best_index)){
                taboo_list.remove(taboo_list.indexOf(best_index));
            }
            
            // Add used element's index to taboo list
            taboo_list.add(best_index);
            
            // If taboo list is full, remove extra elements
            while(taboo_list.size() > max_size_t_l){
                taboo_list.remove(max_size_t_l);
            }
            
            // Set new solution
            eval_act = eval_asp;
            car = asp_car.clone();
            num_c_sel = num_asp_c_sel;
        }
        
        // Set best solution
        car = best_car.clone();
        num_c_sel = num_best_c_sel;
    }
    
}
