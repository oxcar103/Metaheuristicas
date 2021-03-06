/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public abstract class Heuristic {
    // Constants
    private final int max_eval = 15000;
    private final int num_neigh = 3;
    
    // Variables
    private final int num_car;
    private int eval;
    protected Instances instances;
    protected boolean[] car;
    protected int num_c_sel;
    
    abstract void Train();
            
    public Heuristic(Instances inst, int col_class){
        instances = new Instances(inst);
        instances.setClassIndex(col_class);
        
        eval = 0;
        
        num_car = inst.numAttributes();
        car = new boolean [num_car];
        
        NewSolution();
    }
    
    public int getNumCar() {
        return num_car;
    }

    public int getMaxEval() {
        return max_eval;
    }

    public int getEval() {
        return eval;
    }
    
    protected final void NewSolution(){
        for(int i = 0; i < num_car; i++){
           car[i] = false;
        }
        
        num_c_sel = 0;
    }
    
    double SuccessesRate(Instances inst){
        return (Evaluate(inst) * 100) / (instances.numInstances() * 1.0);
    }
 
    double ReductionRate(){
        return (((num_car-1)  - num_c_sel) * 100) / ((num_car-1) * 1.0);
    }
    
    protected void Flip(int index){
        car[index] = !car[index];
        
        if(car[index] == true){
            num_c_sel++;
        }
        else{
            num_c_sel--;
        }
    }
    
    protected int Evaluate(){
        return Evaluate(car);
    }
    
    protected int Evaluate(int index){
        boolean [] c_sel = car.clone();
        
        c_sel[index] = !c_sel[index];
        
        return Evaluate(c_sel);
    }
    
    protected int Evaluate(boolean[] c_sel){
        return Evaluate(c_sel, instances);
    }
    
    protected int Evaluate(Instances inst){
        return Evaluate(car, inst);
    }
    
    protected int Evaluate(boolean [] c_sel, Instances inst){
        Instances eval_car = new Instances(inst);
        Instances neighbours;
        double exp_class;
        int succ = 0, n_inst = inst.numInstances();
        IBk ibk = new IBk();
        
        eval_car.setClass(inst.classAttribute());
        
        for(int i = num_car-1; i >= 0; i--){
            if(c_sel[i] == false && i != inst.classIndex()){
                eval_car.deleteAttributeAt(i);
            }
        }
        try {
            
            ibk.buildClassifier(eval_car);

            for(int i = 0; i < n_inst; i++){
                neighbours = ibk.getNearestNeighbourSearchAlgorithm().kNearestNeighbours(eval_car.instance(i), num_neigh+1);
                
                // neighbours.instance(0) is equal to aux.instance(i), so we delete it
                neighbours.delete(0);
                
                // Looking for the majority class
                if(neighbours.instance(0).classValue() == neighbours.instance(1).classValue()){
                    exp_class = neighbours.instance(0).classValue();
                }
                else if(neighbours.instance(0).classValue() == neighbours.instance(2).classValue()){
                    exp_class = neighbours.instance(0).classValue();
                }
                else if(neighbours.instance(1).classValue() == neighbours.instance(2).classValue()){
                    exp_class = neighbours.instance(1).classValue();
                }
                else{
                    // If there isn't a majority class, we chose the nearest neighbour's class.
                    exp_class = ibk.getNearestNeighbourSearchAlgorithm().kNearestNeighbours(eval_car.instance(i), 2).instance(1).classValue();
                }
                
                // If expected class is our instance's class, increase successes
                if(eval_car.instance(i).classValue() == exp_class){
                    succ++;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        eval++;
        
        return succ;
    }
    
}