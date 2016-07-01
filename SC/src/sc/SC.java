/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import ArffReader.ArffReader;
import CSVFileWriter.CSVFileWriter;
import java.util.ArrayList;
import weka.core.Debug.Random;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author oxcar103
 */
public class SC {
    private static final int num_heur = 12;
    
    //Best random seed in the history
    private static final int seed = 103;
    
    private static final int exec = 10;
    private static Random rnd;
    
    private static int[] seeds = new int[10];
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        ArffReader lector = new ArffReader();
        Instances instances = null;
        String [] hr_str = new String[num_heur];
        String f_name;
        int col_class;
        rnd = new Random(seed);
        
        int num_files = Integer.parseInt(args[0]);
        
        hr_str [0] = "SFS";
        hr_str [1] = "LS";
        hr_str [2] = "SA";
        hr_str [3] = "TS";
        hr_str [4] = "BMBS";
        hr_str [5] = "GRASP";
        hr_str [6] = "ILS";
        hr_str [7] = "GGA";
        hr_str [8] = "SSGA";
        hr_str [9] = "TMA";
        hr_str [10] = "PMA";
        hr_str [11] = "BPMA";
        
        for(int i = 0; i < num_files; i++){
            instances = lector.getData(args[3*i+1]);
            col_class = Integer.parseInt(args[3*i+2]);
            f_name = args[3*i+3];
            
            for(int j = 0; j < num_heur; j++){
                Exec(instances, col_class, j, f_name + "_" + hr_str [j] + ".csv");
            }
        }
    }    
    
    private static void Exec(Instances instances, int col_class, int alg, String filename) throws Exception{
        ArrayList<Double> suc_r = new ArrayList<Double> ();
        ArrayList<Double> red_r = new ArrayList<Double> ();
        ArrayList<Double> times = new ArrayList<Double> ();
        CSVFileWriter wrt = new CSVFileWriter();
        Heuristic heuristic = null;
        Instances inst1, inst2;
        double duration = 0;
        long start_t, end_t;
        
        for(int i = 0; i < exec/2 ; i++){
            instances.randomize(rnd);
            Normalize norm = new Normalize();
            norm.setInputFormat(instances);
            instances = Filter.useFilter(instances, norm);
            instances.setClassIndex(col_class);
            seeds[2*i] = rnd.nextInt();
            seeds[2*i+1] = rnd.nextInt();

            for(int j = 0; j < 2; j++){
                inst1 = instances.trainCV(2, j);
                inst2 = instances.testCV(2, j);

                if(alg == 0){
                    heuristic = new SFS(inst1, col_class);
                }
                else if (alg == 1){
                    heuristic = new LocalSearch(inst1, col_class, seeds[2*i+j]);
                }
                else if (alg == 2){
                    heuristic = new SimulatedAnnealing(inst1, col_class, seeds[2*i+j]);
                }
                else if (alg == 3){
                    heuristic = new TabooSearch(inst1, col_class, seeds[2*i+j]);
                }
                else if (alg == 4){
                    heuristic = new BasicMultiBootingSearch(inst1, col_class, seeds[2*i+j]);
                }
                else if (alg == 5){
                    heuristic = new GRASP(inst1, col_class, seeds[2*i+j]);
                }
                else if (alg == 6){
                    heuristic = new IteratedLocalSearch(inst1, col_class, seeds[2*i+j]);
                }
                else if (alg == 7){
                    heuristic = new GenerationalGA(inst1, col_class, seeds[2*i+j]);
                }
                else if (alg == 8){
                    heuristic = new SteadyStateGA(inst1, col_class, seeds[2*i+j]);
                }
                else if (alg == 9){
                    heuristic = new MemeticAlgorithm(inst1, col_class, seeds[2*i+j], 10, 1, false);
                }
                else if (alg == 10){
                    heuristic = new MemeticAlgorithm(inst1, col_class, seeds[2*i+j], 10, 0.1, false);
                }
                else if (alg == 11){
                    heuristic = new MemeticAlgorithm(inst1, col_class, seeds[2*i+j], 10, 0.1, true);
                }
                
                start_t = System.currentTimeMillis();
                heuristic.Train();
                end_t = System.currentTimeMillis();
                
                duration = ((double) end_t - (double) start_t) / 1000.0;
                
                suc_r.add(heuristic.SuccessesRate(inst2));
                red_r.add(heuristic.ReductionRate());
                times.add(duration);
            }
        }
        
        wrt.CSVFileWriter(filename, suc_r, red_r, times);
    }
    
}
