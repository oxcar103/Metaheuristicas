/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import weka.core.Debug.Random;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author oxcar103
 */
public class SC {
    private static final int num_files = 1;
    private static final int num_heur = 4;
    
    //Best random seed in the history
    private static final int seed = 103;
    
    private static final int exec = 10;
    private static Random rnd;
    
    private static int[] seeds = new int[10];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ArffReader lector = new ArffReader();
        Instances instances = null, inst1, inst2;
        int col_class;
        rnd = new Random(seed);
        
        
        for(int i = 0; i < num_files; i++){
            instances = lector.getData(args[2*i]);
            col_class = Integer.parseInt(args[2*i+1]);
            
            for(int j = 0; j < exec/2 ; j++){
                instances.randomize(rnd);
                Normalize norm = new Normalize();
                norm.setInputFormat(instances);
                instances = Filter.useFilter(instances, norm);
                instances.setClassIndex(col_class);
                seeds[2*j] = rnd.nextInt();
                seeds[2*j+1] = rnd.nextInt();

                for(int k = 0; k < 2; k++){
                    inst1 = instances.trainCV(2, k);
                    inst2 = instances.testCV(2, k);
                    
                    Exec(inst1, inst2, col_class, seeds[2*j+k]);
                }
            }
        }
    }    
    
    private static void Exec(Instances inst1, Instances inst2, int col_class, int seed){
        Heuristic [] heuristics = new Heuristic[2];
        
        heuristics[0] = new SFS(inst1, col_class);
        heuristics[1] = new LocalSearch(inst1, col_class, seed);
        heuristics[2] = new SimulatedAnnealing(inst1, col_class, seed);
        heuristics[3] = new TabooSearch(inst1, col_class, seed);
        
        for(int i = 0; i < 4; i++){
            heuristics[i].Train();
            
            System.out.println(heuristics[i].SuccessesRate(inst2) + "\n" + heuristics[i].ReductionRate());
        }
    }
    
}
