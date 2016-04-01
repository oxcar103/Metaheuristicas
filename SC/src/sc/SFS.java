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

    void Exec(){
        boolean [] copia_c = car.clone();
        int c_prom = -1;
        boolean fin = false;

        while(num_c_sel != num_car && !fin){
            for(int i = 0; i < num_car; i++){
                if(copia_c[i] != true){
                    Evaluate(i);

                    if(c_prom == -1 || Evaluate(i) > Evaluate(c_prom)){
                        c_prom = i;
                    }
                }
            }

            copia_c[c_prom] = true;

            if(Evaluate(copia_c) > Evaluate()){
                car = copia_c;
                num_c_sel++;
            }

            else{
                copia_c = car;
                fin = true;
            }
        }
    }
    
}
