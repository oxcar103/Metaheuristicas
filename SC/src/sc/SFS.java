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
        boolean [] copy_c = car.clone();
        int c_prom = -1;
        boolean end = false;

        while(num_c_sel != num_car && !end){
            for(int i = 0; i < num_car; i++){
                if(copy_c[i] != true){
                    Evaluate(i);

                    if(c_prom == -1 || Evaluate(i) > Evaluate(c_prom)){
                        c_prom = i;
                    }
                }
            }

            copy_c[c_prom] = true;

            if(Evaluate(copy_c) > Evaluate()){
                Flip(c_prom);
            }

            else{
                copy_c[c_prom] = false;
                end = true;
            }
        }
    }
    
}
