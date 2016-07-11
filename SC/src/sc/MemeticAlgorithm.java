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
public abstract class MemeticAlgorithm extends GenerationalGA{
    private final double imp_prob;
    private final int iteration;
    
    public MemeticAlgorithm(Instances inst, int col_class, int seed, int iter, double imp) {
        super(inst, col_class, seed, 10);
        
        iteration = iter;
        imp_prob = imp;
    }
    
    @Override
    public void Train() {
        for(int i = 0; i < getPopulation(); i++){
            RandomSolution();
            
            parents.add(car);
            num_c_sel_parents.add(num_c_sel);
            eval_parents.add(Evaluate());
            
            // Looking for Best Solution
            if(eval_parents.get(i) > eval_parents.get(index_best_solution)){
                index_best_solution = i;
            }
        }
        
        // Let the Population Live and Die
        for(int j = 0; getEval() < getMaxEval(); j++){
            // Cleaning the auxiliary vectors
            childs.clear();
            num_c_sel_childs.clear();
            eval_childs.clear();
            selected_parents.clear();
            
            Selection();
            for(int i = 0; i < selected_parents.size(); i+=2){
                Crossover(i, i+1);
            }
            Mutation();
            Inheritance();
            
            if(j+1 % iteration == 0){
                Improve();
            }
        }
        
        car = parents.get(index_best_solution);
        num_c_sel = num_c_sel_parents.get(index_best_solution);
    }

    public double getImp_prob() {
        return imp_prob;
    }

    protected abstract void Improve();
}