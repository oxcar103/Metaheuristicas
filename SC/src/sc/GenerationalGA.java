/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public class GenerationalGA extends GeneticAlgorithm {
    private final int exp_cross, exp_mut, total_gens;

    public GenerationalGA(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed, 0.7, 0.001);
        
        // Best solution is at the end of array
        index_best_solution = getPopulation();
        
        exp_cross = (int) (getCross_prob() * getPopulation() / 2);
        total_gens = getPopulation() * getNumCar();
        exp_mut = (int) (getMut_prob() * total_gens);
    }

    @Override
    protected void Selection() {
        int asp;
        
        while(selected_parents.size() != exp_cross){
            asp = BinaryTournament();
            
            if(!selected_parents.contains(asp)){
                selected_parents.add(asp);
            }
        }
    }

    @Override
    protected void Mutation() {
        List<Integer> muted_gens = new ArrayList<>();
        List<Integer> muted_sols = new ArrayList<>();
        int gen, sol, c;
        
        // Add parents that don't crossover
        for(int i = 0; i < getPopulation(); i++){
            if(!selected_parents.contains(i)){
                childs.add(parents.get(i));
                num_c_sel_childs.add(num_c_sel_parents.get(i));
                eval_childs.add(eval_parents.get(i));
            }
        }
        
        while(muted_gens.size() != exp_mut){
            gen = rnd.nextInt(total_gens);
            sol = gen / getNumCar();
            c = gen % getNumCar();
            
            // Add gen & solution
            if(!muted_gens.contains(gen)){
                muted_gens.add(gen);
                if(!muted_sols.contains(sol)){
                    muted_sols.add(sol);
                }
                
                // Flip
                childs.get(sol)[c] = !childs.get(sol)[c];
                
                if(childs.get(sol)[c]){
                    num_c_sel_childs.set(sol, num_c_sel_childs.get(sol)+1);
                }
                else{
                    num_c_sel_childs.set(sol, num_c_sel_childs.get(sol)-1);
                }
            }
        }
        
        // Re-evaluate muted solutions
        for(int i = 0; i < muted_sols.size(); i++){
            eval_childs.set(muted_sols.get(i), Evaluate(childs.get(muted_sols.get(i))));
        }
    }

    @Override
    protected void Inheritance() {
        int i_worst = 0;

        // Search Worst Solution
        for(int i = 0; i < getPopulation(); i++){
            if(eval_parents.get(i) < eval_parents.get(i_worst)){
              i_worst = i;  
            }
        }
        
        // Elitism
        parents.set(i_worst, parents.get(index_best_solution));
        num_c_sel_parents.set(i_worst, num_c_sel_parents.get(index_best_solution));
        eval_parents.set(i_worst, eval_parents.get(index_best_solution));
        
        // Add Old Best Solution
        childs.add(parents.get(index_best_solution));
        num_c_sel_childs.add(num_c_sel_parents.get(index_best_solution));
        eval_childs.add(eval_parents.get(index_best_solution));
        
        // Replace Old Generation
        parents = new ArrayList(childs);
        num_c_sel_parents = new ArrayList(num_c_sel_childs);
        eval_parents = new ArrayList(eval_childs);
        
        // New Best Solution
        for(int i = 0; i < getPopulation(); i++){
            if(eval_parents.get(i) > eval_parents.get(index_best_solution)){
                parents.set(index_best_solution, parents.get(i));
                num_c_sel_parents.set(index_best_solution, num_c_sel_parents.get(i));
                eval_parents.set(index_best_solution, eval_parents.get(i));
            }
        }
    }
}