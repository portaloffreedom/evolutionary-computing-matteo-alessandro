/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

import player24.Population;
import player24.RunsFinishedException;

/**
 *
 * @author matteo
 */
public class player24 implements ContestSubmission {

    private final Random randomGenerator;
    private ContestEvaluation evaluation;
    private int evaluationsLimit;

    private final int populationSize;
    private final int genomeSize;
    private Population population;

    public player24() {
        this.genomeSize = 10;
        this.populationSize = 5;
        randomGenerator = new Random();
        //this.population = new Population(populationSize, populationSize, randomGenerator);
    }

    @Override
    public void setSeed(long seed) {
        // Set seed of algortihms random process
        randomGenerator.setSeed(seed);
    }

    @Override
    public void setEvaluation(ContestEvaluation evaluation) {
        // Set evaluation problem used in the run
        this.evaluation = evaluation;

        // Get evaluation properties
        Properties props = evaluation.getProperties();
        evaluationsLimit = Integer.parseInt(props.getProperty("Evaluations"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("GlobalStructure"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

        // Change settings(?)
        if (isMultimodal) {
            // Do sth
        } else {
            // Do sth else
        }
        
        this.population = new Population(populationSize, genomeSize, randomGenerator, evaluation);
    }

    @Override
    public void run() {
        // Run your algorithm here
        try {
            for (int evalCounter = 0; evalCounter < evaluationsLimit + 2; evalCounter++) {
                // Select parents
                // Apply variation operators and get children
                //  double child[] = ...
                //  Double fitness = evaluation.evaluate(child);
                population.evolveGeneration();

                System.out.println("run(" + evalCounter + "): " + this.evaluation.getFinalResult());
                System.out.println(population);
                // Select survivors
            }
        } catch (RunsFinishedException e) {
            System.out.println("Runs finished");
        }

        System.out.println("this is the result: " + this.evaluation.getFinalResult());
    }
}
