/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import java.util.ArrayList;
import java.util.List;
import weka.core.Instances;

/**
 *
 * @author oxcar103
 */
public abstract class GeneticAlgorithm extends RandomHeuristic{
    int population = 30;
    double mut_prob = 0.001;
    double cross_prob;
    List<Integer> num_c_sel_parents = new ArrayList<Integer>();
    List<Integer> num_c_sel_childs = new ArrayList<Integer>();
    List<boolean[]> parents = new ArrayList<boolean []>();
    List<boolean[]> childs = new ArrayList<boolean []>();

    public GeneticAlgorithm(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed);
    }
    
    abstract void Selection();
    
    void Crossover(int parent_1, int parent_2){
        int first, second, aux;
        boolean[] child_1, child_2;
        int num_c_sel_child_1 = 0, num_c_sel_child_2 = 0;
        
        child_1 = new boolean [getNumCar()];
        child_2 = new boolean [getNumCar()];
        
        first = rnd.nextInt(num_c_sel);
        
        do{
            second = rnd.nextInt(num_c_sel);
        }while(first == second);
        
        if(first > second){
            aux = first;
            first = second;
            second = aux;
        }
        
        for(int i = 0; i < getNumCar(); i++){
            if(i < first){
                child_1[i] = parents.get(parent_1)[i];
                if(child_1[i]){
                    num_c_sel_child_1++;
                }
                child_2[i] = parents.get(parent_2)[i];
                if(child_2[i]){
                    num_c_sel_child_2++;
                }   
            }
            else if(i < second){
                child_1[i] = parents.get(parent_2)[i];
                if(child_1[i]){
                    num_c_sel_child_1++;
                }
                child_2[i] = parents.get(parent_1)[i];
                if(child_2[i]){
                    num_c_sel_child_2++;
                }
            }
            else{
                child_1[i] = parents.get(parent_1)[i];
                if(child_1[i]){
                    num_c_sel_child_1++;
                }
                child_2[i] = parents.get(parent_2)[i];
                if(child_2[i]){
                    num_c_sel_child_2++;
                }
            }
        }
        
        childs.add(child_1);
        childs.add(child_2);
        num_c_sel_childs.add(num_c_sel_child_1);
        num_c_sel_childs.add(num_c_sel_child_2);
    }
    
    abstract void Mutation();
    abstract void Inheritance();
}
