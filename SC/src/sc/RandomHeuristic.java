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
        NewSolution();
        
        for(int i = 0; i < getNumCar(); i++){
            if(i != instances.classIndex()){
                if(rnd.nextBoolean()){
                    Flip(i);
                }
            }
        }
    }
    
    protected int GenerateNeighbour(){
        int i;
        
        do{
            i = rnd.nextInt(getNumCar());
        }while(i == instances.classIndex());
        
        Flip(i);
        
        return i;
    }
    
}
