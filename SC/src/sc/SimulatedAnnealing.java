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

    private final double T_0, beta;
    private final int max_neigh, max_succ, M;
    
    private double T;
    
    public SimulatedAnnealing(Instances inst, int col_class, int seed){
        super(inst, col_class, seed);
        
        max_neigh = 10 * (getNumCar()-1);
        max_succ = (int) (0.1 * max_neigh);
        M = getMaxEval() / max_neigh;
        T = T_0 = mu * Evaluate() / -log(sigma);
        beta =(T_0 - T_f)/(M * T_0 * T_f);
    }

    private void cauchyAnnealing(){
        T = T / (1 + beta * T);
    }
    
    private boolean MetropolisCriterion(int v_asp, int v_act){
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
        int old_num_c_sel = num_c_sel;
        int eval_act = Evaluate();
        int eval_asp;
        int neigh, succ;
        
        while(T > T_f && !end && getEval() < getMaxEval()){
            neigh = 0;
            succ = 0;
            
            while(neigh < max_neigh && succ < max_succ && getEval() < getMaxEval()){
                GenerateNeighbour();
                neigh++;
                
                eval_asp = Evaluate();
                
                if(MetropolisCriterion(eval_asp, eval_act)){
                    succ++;
                    old_car = car.clone();
                    old_num_c_sel = num_c_sel;
                }
                else{
                    car = old_car.clone();
                    num_c_sel = old_num_c_sel;
                }
            }
            
            cauchyAnnealing();
            
            end = (succ == 0);
        }     
    }
    
}
