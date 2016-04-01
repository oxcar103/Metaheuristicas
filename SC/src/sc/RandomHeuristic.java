/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import weka.core.Debug.Random;
import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public abstract class RandomHeuristic extends Heuristic{
    protected Random rnd;
    
    
    public RandomHeuristic(Instances inst, int col_class, int seed) {
        super(inst, col_class);
        rnd = new Random(seed);
        
        RandomSolution();
    }
    
    protected final void RandomSolution(){
        for(int i = 0; i < num_car; i++){
            if(rnd.nextBoolean()){
                Flip(i);
            }
        }
    }
    
    protected void GenerateNeighbour(int num_c){
        int i = rnd.nextInt(num_c);

        Flip(i);
    }
    
}
