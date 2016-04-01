/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public class SimulatedAnnealing extends RandomHeuristic{
    private static final double sigma, mu = sigma = 0.3;
    private static final double T_f = 10^-3;

    private static double T_0, T, beta;
    private static int max_neigh, max_succ, M;
    
    public SimulatedAnnealing(Instances inst, int col_class, int seed){
        super(inst, col_class, seed);
        
        max_neigh = 10 * num_car;
        max_succ = (int) (0.1 * max_neigh);
        M = max_eval / max_neigh;
        T = T_0 = mu * Evaluate() / -log(sigma);
        beta =(T_0 - T_f)/(M * T_0 * T_f);
    }

    private void cauchyAnnealing(){
        T = T / (1 + beta * T);
    }
    
    private boolean MetropolisCriterion(boolean[] aspirant, boolean[] actual){
        int v_asp = Evaluate(aspirant), v_act = Evaluate(actual);
        
        if(v_asp <= v_act){
            if(rnd.nextDouble() > exp((v_act - v_asp)/ T)){
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    void Train() {
        boolean [] old_car = car.clone();
        boolean end = false;
        int neigh, succ, old_num_car = num_car;
        
        while(T > T_f || end){
            neigh = 0;
            succ = 0;
            
            while(neigh < max_neigh && succ < max_succ){
                GenerateNeighbour();
                neigh++;
                
                if(MetropolisCriterion(car, old_car)){
                    succ++;
                    old_car = car.clone();
                    old_num_car = num_car;
                }
                else{
                    car = old_car.clone();
                    num_car = old_num_car;
                }
            }
            
            cauchyAnnealing();
            end = (succ == 0);
        }     
    }
    
}
