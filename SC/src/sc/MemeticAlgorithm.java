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
public class MemeticAlgorithm extends GenerationalGA{
    int iteration, proportion;
    boolean best_childs;
    
    public MemeticAlgorithm(Instances inst, int col_class, int seed, int iter, int prop, boolean best) {
        super(inst, col_class, seed);
        
        iteration = iter;
        proportion = prop;
        best_childs = best;
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
                if(best_childs){
                    ImproveBest();
                }
                else{
                    Improve();
                }
            }
        }
        
        car = parents.get(index_best_solution);
        num_c_sel = num_c_sel_parents.get(index_best_solution);
    }
    
    void Improve(){
        int eval_neigh;
        
        for(int i = 0; i < getPopulation(); i++){
            if(rnd.nextDouble() < proportion){
                car = parents.get(i).clone();
                num_c_sel = num_c_sel_parents.get(i);

                GenerateNeighbour();

                eval_neigh = Evaluate();

                if(eval_neigh > eval_parents.get(i)){
                    parents.set(i, car.clone());
                    num_c_sel_parents.set(i, num_c_sel);
                    eval_parents.set(i, eval_neigh);
                }
            }
        }        
    }
    
    void ImproveBest(){
        List<Integer> index_best_sols = new ArrayList<>();
        int num_best = proportion * getPopulation(), index, eval_neigh;
        
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
