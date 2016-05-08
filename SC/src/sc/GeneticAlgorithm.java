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
    private final int population = 30;
    private double cross_prob;
    private final double mut_prob;
    protected List<boolean[]> parents = new ArrayList<>();
    protected List<boolean[]> childs = new ArrayList<>();
    protected List<Integer> num_c_sel_parents = new ArrayList<>();
    protected List<Integer> num_c_sel_childs = new ArrayList<>();
    protected List<Integer> eval_parents = new ArrayList<>();
    protected List<Integer> eval_childs = new ArrayList<>();
    
    List<Integer> selected_parents = new ArrayList<>();
    int index_best_solution = 0;

    public GeneticAlgorithm(Instances inst, int col_class, int seed, double cross, double mut) {
        super(inst, col_class, seed);
        
        cross_prob = cross;
        mut_prob = mut;
    }

    public int getPopulation() {
        return population;
    }

    public double getCross_prob() {
        return cross_prob;
    }
    
    public double getMut_prob() {
        return mut_prob;
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
        eval_childs.add(Evaluate(child_1));
        eval_childs.add(Evaluate(child_2));
    }
    
    abstract void Mutation();
    abstract void Inheritance();
    
    @Override
    void Train() {
        for(int i = 0; i < population; i++){
            RandomSolution();
            
            parents.add(car);
            num_c_sel_parents.add(num_c_sel);
            eval_parents.add(Evaluate());
        }
        
        while(getEval() < getMaxEval()){
            Selection();
            for(int i = 0; i < selected_parents.size(); i+=2){
                Crossover(i, i+1);
            }
            Mutation();
            Inheritance();
        }
        
        car = parents.get(index_best_solution);
        num_c_sel = num_c_sel_parents.get(index_best_solution);
    }
}
