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
public class Heuristic {
    // Constants
    public final int max_eval = 15000;
    public final int exec = 10;
    public final int num_neig = 3;
    public final int seed = 103;
    
    // Variables
    protected Instances instances;
    protected int num_inst;
    protected boolean[] car;
    protected int num_car;
    Random rnd = new Random(seed);
    protected int[] seeds;

    public Heuristic(Instances inst, int col_class){
        num_inst = inst.numInstances();
        instances = new Instances(inst);
        instances.setClassIndex(col_class);
        
        num_car = inst.numAttributes()-1;
        car = new boolean[num_car];
        
        for(int i = 0; i < num_car; i++){
           car[i] = false;
        }
        
        for(int i = 0; i < exec ; i++){
            seeds[i] = rnd.nextInt();
        }
    }
    
    protected void GenerateNeighbours(int num_c){
        int i = rnd.nextInt(num_c);

        car[i] = !car[i];
    }
    
    protected int Evaluate(){
        Instances aux = new Instances(instances);
        Instances neighbours;
        double exp_class;
        int succ = 0;
        IBk ibk = new IBk();
        
        for(int i = num_car-1; i >= 0; i--){
            if(car[i] == false){
                aux.deleteAttributeAt(i);
            }
        }
        
        for(int i = 0; i < num_inst; i++){
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