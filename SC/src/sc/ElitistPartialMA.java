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
public class ElitistPartialMA extends MemeticAlgorithm{

    public ElitistPartialMA(Instances inst, int col_class, int seed, int iter, double prop) {
        super(inst, col_class, seed, iter, prop);
    }

    @Override
    protected void Improve() {
        List<Integer> index_best_sols = new ArrayList<>();
        int num_best = (int) getProportion() * getPopulation(), index, eval_neigh;
        
        index_best_sols.add(index_best_solution);
        
        while(index_best_sols.size() < num_best){
            index = -1;
            
            for(int i = 0; i < parents.size(); i++){
                if(index == -1 || (eval_parents.get(i) > eval_parents.get(index) && !index_best_sols.contains(i))){
                    index = i;
                }
            }
            
            index_best_sols.add(index);
        }
        
        for(int i = 0; i < index_best_sols.size(); i++){
            car = parents.get(index_best_sols.get(i)).clone();
            num_c_sel = num_c_sel_parents.get(index_best_sols.get(i));

            GenerateNeighbour();

            eval_neigh = Evaluate();

            if(eval_neigh > eval_parents.get(index_best_sols.get(i))){
                parents.set(index_best_sols.get(i), car.clone());
                num_c_sel_parents.set(index_best_sols.get(i), num_c_sel);
                eval_parents.set(index_best_sols.get(i), eval_neigh);
            }
        }
    }
    
}
