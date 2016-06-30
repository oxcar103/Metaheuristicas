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
public abstract class MemeticAlgorithm extends GenerationalGA{
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
        
        if(proportion == 1){
            for(int i = 0; i < getPopulation(); i++){
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
        else{
            
        }
    }
    
    void ImproveBest(){
        
    }
}
