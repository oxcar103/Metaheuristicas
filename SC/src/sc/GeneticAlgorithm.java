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
    
    protected List<Integer> selected_parents = new ArrayList<>();
    protected int index_best_solution = 0;

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
    
    public int getTotal_gens() {
        return population * getNumCar();
    }
        

    protected int BinaryTournament(){
        int asp_1, asp_2;
        
        asp_1 = rnd.nextInt(population);
        
        do{
            asp_2 = rnd.nextInt(population);
        }while(asp_1 == asp_2);
        
        if(eval_parents.get(asp_1) > eval_parents.get(asp_2)){
            return asp_1;
        }
        else{
            return asp_2;
        }
    }
    
    protected abstract void Selection();
    
    protected void Crossover(int parent_1, int parent_2){
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
    
    protected abstract void Mutation();
    protected abstract void Inheritance();
    
    @Override
    public void Train() {
        for(int i = 0; i < population; i++){
            RandomSolution();
            
            parents.add(car);
            num_c_sel_parents.add(num_c_sel);
            eval_parents.add(Evaluate());
            
            // Looking for Best Solution
            if(eval_parents.get(i) > eval_parents.get(index_best_solution)){
                index_best_solution = i;
            }
        }
        
        // Let the Population Live and Die
        while(getEval() < getMaxEval()){
            // Cleaning the auxiliary vectors
            childs.clear();
            num_c_sel_childs.clear();
            eval_childs.clear();
            selected_parents.clear();
            
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
