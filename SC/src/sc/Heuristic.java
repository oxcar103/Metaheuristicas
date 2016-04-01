/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.lazy.IBk;
import weka.core.Debug.Random;
import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public abstract class Heuristic {
    // Constants
    public final int max_eval = 15000;
    public final int exec = 10;
    public final int num_neig = 3;
    
    //Best random seed in the history
    private final int seed = 103;
    
    // Variables
    protected Instances instances;
    protected int num_inst;
    protected boolean[] car;
    protected int num_car, num_c_sel;
    Random rnd = new Random(seed);
    protected int[] seeds;

    abstract void Exec();
            
    public Heuristic(Instances inst, int col_class){
        num_inst = inst.numInstances();
        instances = new Instances(inst);
        instances.setClassIndex(col_class);
        
        num_c_sel = 0;
        num_car = inst.numAttributes()-1;
        car = new boolean [num_car];
        
        for(int i = 0; i < num_car; i++){
           car[i] = false;
        }
        
        for(int i = 0; i < exec ; i++){
            seeds[i] = rnd.nextInt();
        }
    }
    
    public boolean[] getCar() {
        return car;
    }
    
    double SuccessesRate(Instances inst){
        return (Evaluate(inst) * 100) / (instances.numInstances() * 1.0);
    }
 
    double ReductionRate(){
        return ((num_car - num_c_sel) * 100) / (num_car * 1.0);
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
    
    protected void GenerateNeighbour(int num_c){
        int i = rnd.nextInt(num_c);

        Flip(i);
    }
    
    protected int Evaluate(){
        return Evaluate(car);
    }
    
    protected int Evaluate(int index){
        boolean [] c_sel = car.clone();
        
        return Evaluate(c_sel);
    }
    
    protected int Evaluate(boolean[] c_sel){
        return Evaluate(c_sel, instances);
    }
    
    protected int Evaluate(Instances inst){
        return Evaluate(car, inst);
    }
    
    protected int Evaluate(boolean [] c_sel, Instances inst){
        Instances aux = new Instances(inst);
        Instances neighbours;
        double exp_class;
        int succ = 0, n_inst = inst.numInstances();
        IBk ibk = new IBk();
        
        for(int i = num_car-1; i >= 0; i--){
            if(c_sel[i] == false){
                aux.deleteAttributeAt(i);
            }
        }
        
        for(int i = 0; i < n_inst; i++){
            try {
                neighbours = ibk.getNearestNeighbourSearchAlgorithm().kNearestNeighbours(aux.instance(i), num_neig+1);
                
                // neighbours.instance(0) is equal to aux.instance(i), so we delete it
                neighbours.delete(0);
                
                // Looking for the majority class
                if(neighbours.instance(0) == neighbours.instance(1)){
                    exp_class = neighbours.instance(0).classValue();
                }
                else if(neighbours.instance(0) == neighbours.instance(2)){
                    exp_class = neighbours.instance(0).classValue();
                }
                else if(neighbours.instance(1) == neighbours.instance(2)){
                    exp_class = neighbours.instance(1).classValue();
                }
                else{
                    // If there isn't a majority class, we chose the nearest neighbour's class.
                    exp_class = ibk.getNearestNeighbourSearchAlgorithm().kNearestNeighbours(aux.instance(i), 2).instance(1).classValue();
                }
                
                // If expected class is our instance's class, increase successes
                if(exp_class == aux.instance(i).classValue()){
                    succ++;
                }
            } catch (Exception ex) {
                Logger.getLogger(Heuristic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return succ;
    }
    
}