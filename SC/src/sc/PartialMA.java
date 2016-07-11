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
public class PartialMA extends MemeticAlgorithm{

    public PartialMA(Instances inst, int col_class, int seed, int iter, double prop) {
        super(inst, col_class, seed, iter, prop);
    }

    @Override
    protected void Improve() {
        int eval_neigh;
        
        for(int i = 0; i < getPopulation(); i++){
            if(rnd.nextDouble() < getImp_prob()){
                car = parents.get(i).clone();
                num_c_sel = num_c_sel_parents.get(i);

                GenerateNeighbour();

                eval_neigh = Evaluate();

                if(eval_neigh > eval_parents.get(i)){
                    parents.set(i, car.clone());
                    num_c_sel_parents.set(i, num_c_sel);
                    eval_parents.set(i, eval_neigh);
                }
            }
        }
    }
    
}
