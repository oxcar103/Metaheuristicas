/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public class SFS extends Heuristic{
    
    public SFS(Instances inst, int col_class) {
        super(inst, col_class);
    }

    void Train(){
        int c_prom = -1, eval_c_prom = 0, eval_c_i, eval_act = Evaluate();
        boolean end = false;

        while(num_c_sel != getNumCar() && !end){
            for(int i = 0; i < getNumCar(); i++){
                if(car[i] != true && i != instances.classIndex()){
                    eval_c_i = Evaluate(i);

                    if(c_prom == -1){
                        c_prom = i;
                        eval_c_prom = eval_c_i;
                    }
                    else if(eval_c_i > eval_c_prom){
                        c_prom = i;
                        eval_c_prom = eval_c_i;
                    }
                }
            }

            if(eval_c_prom > eval_act){
                Flip(c_prom);
                c_prom = -1;
                eval_act = eval_c_prom;
            }
            else{
                end = true;
            }
        }
    }
    
}
