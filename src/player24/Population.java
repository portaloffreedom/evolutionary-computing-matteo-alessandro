package player24;

import java.util.Random;
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
public class Population {

    private int generation;
    private final int popualtionSize;
    private final Random randomGenerator;

    private Individual[] population;

    public Population(int populationSize, int individualSize, Random randomGenerator, ContestEvaluation evaluation) {
        this.generation = 1;
        this.popualtionSize = populationSize;
        this.randomGenerator = randomGenerator;
        this.population = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            this.population[i] = new Individual(individualSize, randomGenerator, evaluation);
        }
    }

    /**
     * Evolve the population to the new generation
     *
     * @throws player24.RunsFinishedException
     */
    public void evolveGeneration() throws RunsFinishedException {
        this.generation++;
        System.out.println("#1# "+this);
        this.mutatePopulation();
        System.out.println("#2# "+this);
        this.population = this.selectionWheel(population);
        System.out.println("#3# "+this);
        this.evaluate();
    }

    private Individual[] selectParents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void evaluate() throws RunsFinishedException {
        for (int i = 0; i < this.population.length; i++) {
            population[i].getFitness();
        }
    }

    private void mutatePopulation() {
        for (int i = 0; i < this.population.length; i++) {
            population[i].mutate(0, 0.1);
        }
    }

    private Individual[] selectionWheel(Individual[] select) throws RunsFinishedException {
        // initialization section
        double selectionProbability[] = new double[select.length];
        double totalWeigth = 0,
                minFitnessValue = Double.MAX_VALUE;
        for (int i = 0; i < select.length; i++) {
            selectionProbability[i] = select[i].getFitness();
            if (minFitnessValue > selectionProbability[i]) {
                minFitnessValue = selectionProbability[i];
            }
        }
        minFitnessValue -= 0.1;
        for (int i = 0; i < selectionProbability.length; i++) {
            selectionProbability[i] -= minFitnessValue;
            totalWeigth += selectionProbability[i];
        }
        for (int i = 0; i < selectionProbability.length; i++) {
            selectionProbability[i] /= totalWeigth;
        }

        Individual[] selection = new Individual[this.popualtionSize];
        // selection section
        double spin = 0;
        for (int i = 0; i < selection.length; i++) {
            spin += this.randomGenerator.nextDouble();
            spin %= 1;

            // pick the one with that spin
            double wheel = 0;
            int j;
            for (j = 0; wheel < spin; j++) {
                wheel += selectionProbability[j];
            }
            selection[i] = select[j - 1].clone();
        }

        return selection;
    }

    @Override
    public String toString() {
        String populationString = "";
        for (int i=0; i<population.length; i++) {
            populationString += ("[" + population[i] + ']');
        }
        return "Population{" + "generation=" + generation + ", population=" + populationString + '}';
    }
    
    

}
