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
public class GRASP extends LocalSearch{
    private final double alpha;
    private final int num_initial_solutions;
    
    public GRASP(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed);
        
        alpha = 0.3;
        num_initial_solutions = 25;
    }

    int Threshold(int worst_eval, int best_eval){
        return (int) (best_eval - alpha * (best_eval - worst_eval));
    }
    
    void NewGreedySolution(){
        List<Integer> applicants = new ArrayList<Integer>();
        List<Integer> index_appl = new ArrayList<Integer>();
        int best_eval, worst_eval, eval_c_i, eval_act = Evaluate();
        int c_prom, eval_c_prom, threshold, index;
        boolean end = false;

        NewSolution();
        
        while(num_c_sel != getNumCar() && !end){
            applicants.clear();
            index_appl.clear();
            best_eval = 0;
            worst_eval = instances.numInstances();
            
            for(int i = 0; i < getNumCar(); i++){
                if(car[i] != true && i != instances.classIndex()){
                    eval_c_i = Evaluate(i);
                    applicants.add(eval_c_i);
                    index_appl.add(i);
                    
                    if(eval_c_i < worst_eval){
                        worst_eval = eval_c_i;
                    }
                    if(eval_c_i > best_eval){
                        best_eval = eval_c_i;
                    }
                }
            }
            
            threshold = Threshold(worst_eval, best_eval);
            
            for(int i = applicants.size()-1; i >= 0; i--){
                if(applicants.get(i) < threshold){
                    applicants.remove(i);
                    index_appl.remove(i);
                }
            }
            
            index = rnd.nextInt(applicants.size());
            c_prom = index_appl.get(index);
            eval_c_prom = applicants.get(index);
            
            if(eval_c_prom > eval_act){
                Flip(c_prom);
                eval_act = eval_c_prom;
            }
            else{
                end = true;
            }
        }
    }
    
    @Override
    void Train() {
        boolean [] best_car;
        int best_eval, best_num_car, eval_act;

        NewGreedySolution();
        super.Train();
        
        best_eval = Evaluate();
        best_car = car.clone();
        best_num_car = num_c_sel;
        
        for(int i = 1; i < num_initial_solutions; i++){
            NewGreedySolution();
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
