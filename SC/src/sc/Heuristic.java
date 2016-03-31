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
public class Heuristic {
    // Constants
    public final int max_eval = 15000;
    public final int exec = 10;
    public final int seed = 103;
    
    // Variables
    protected Instances instances;
    protected boolean[] car;
    protected int num_car;
    Random rnd = new Random(seed);
    protected int[] seeds;

    public Heuristic(Instances inst, int col_class){
        instances = new Instances(inst);
        instances.delete(col_class);
        
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
}
