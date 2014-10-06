package player24;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.vu.contest.ContestEvaluation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author matteo
 */
public class Individual {

    public final int size;
    private double genome[];
    private Double fitness;
    private final ContestEvaluation evaluation;
    private final Random randomGenerator;

    public Individual(int size, Random randomGenerator, ContestEvaluation evaluation) {
        this.fitness = null;
        this.size = size;
        this.randomGenerator = randomGenerator;
        this.evaluation = evaluation;
        this.genome = new double[size];
        for (int i = 0; i < this.size; i++) {
            genome[i] = randomGenerator.nextDouble();
        }
    }
    
    private Individual(double[] genome, Random randomGenerator, ContestEvaluation evaluation) {
        // take the needed references
        this.randomGenerator = randomGenerator;
        this.evaluation = evaluation;
        
        // initialize other variables
        this.size = genome.length;
        this.fitness = null;
        
        // copy the genome in the new individual
        this.genome = new double[size];
        System.arraycopy(genome, 0, this.genome, 0, size);
        
    }

    private Double evaluate() throws RunsFinishedException {
        Object value;
        value = evaluation.evaluate(this.genome);
        if (value == null) {
            throw new RunsFinishedException("tries finished");
        } else {
            // what if value from evaluation.evaluate < 0 ?
            return (Double) value;
        }

    }

    public double getFitness() throws RunsFinishedException {
        if (this.fitness == null) {
            this.fitness = this.evaluate();
        }
        return fitness;
    }
    
    public void mutate(double mu, double sigma) {
        for (int i=0; i<this.genome.length; i++) {
            double mutation = randomGenerator.nextGaussian() * sigma + mu;
            this.genome[i] += mutation;
        }
        this.fitness = null;
    }

    @Override
    public String toString() {
        String superToString = super.toString();
        superToString = superToString.substring(superToString.indexOf('@'));
        try {
            this.getFitness();
            return superToString + " | " + this.fitness.toString();
        } catch (Exception ex) {
            return superToString + " | null";
        }
    }

    @Override
    protected Individual clone() {
        return new Individual(genome, randomGenerator, evaluation);
    }
    
    
    

}
