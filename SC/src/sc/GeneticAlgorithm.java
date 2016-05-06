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
public abstract class GeneticAlgorithm extends RandomHeuristic{
    int population = 30;
    double mut_prob = 0.001;
    double cross_prob;

    public GeneticAlgorithm(Instances inst, int col_class, int seed) {
        super(inst, col_class, seed);
    }
    abstract void Selection();
    abstract void Crossover();
    abstract void Mutation();
    abstract void Inheritance();
}
