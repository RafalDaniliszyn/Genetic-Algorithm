package pl.rafal;

import java.util.Random;

public class GeneticAlgorithm implements Runnable{
    int[] expectedGenotype;
    Population population;
    Population newPopulation;
    int mutationProbability;
    int selectionSize;
    Random random = new Random();

    public GeneticAlgorithm(int populationLength, int genotypeLength, int mutationProbability, int selectionSize) {
        this.selectionSize = selectionSize;
        this.mutationProbability = mutationProbability;
        population = new Population(populationLength,genotypeLength);
        newPopulation = new Population(populationLength,genotypeLength);
        for (int i = 0; i < newPopulation.creatures.length; i++) {
            newPopulation.creatures[i].evaluation = 0;
        }

        this.expectedGenotype = new int[]{1,2,3,2,2,2,4,5,8,7,5,4,7,1,2,2,7,8,6,3,4,8,5,3,5,6,8,4,1,2};



    }

    public int getBestFitness(){
        int best = 0;
        for (int i = 0; i < population.creatures.length; i++) {
            if (population.creatures[i].evaluation > best){
                best = population.creatures[i].evaluation;
            }
        }
        return best;
    }


    public void selection(){
        for (int i = 0; i < selectionSize; i++) {
            int best = getBestFitness();
            for (int j = 0; j < population.creatures.length; j++) {
                if (population.creatures[j].evaluation == best){
                    for (int k = 0; k < population.creatures[0].genotype.length; k++) {
                        newPopulation.creatures[i].genotype[k] = population.creatures[j].genotype[k];
                        population.creatures[j].evaluation = 0;
                    }
                    break;
                }
            }
        }
    }

    public void reproduction(){

        int chosen1 = random.nextInt(population.creatures.length);
        int chosen2 = random.nextInt(population.creatures.length);
        while (chosen1 == chosen2) {
            chosen1 = random.nextInt(population.creatures.length);
            chosen2 = random.nextInt(population.creatures.length);
        }

        for (int i = selectionSize; i < population.creatures.length; i++) {
            chosen1 = random.nextInt(newPopulation.creatures.length);
            chosen2 = random.nextInt(newPopulation.creatures.length);

            int cutPoint = random.nextInt(population.creatures[0].genotype.length);

            for (int j = 0; j < cutPoint; j++) {
                newPopulation.creatures[i].genotype[j] = population.creatures[chosen1].genotype[j];
            }

            for (int j = cutPoint; j < population.creatures[0].genotype.length; j++) {
                newPopulation.creatures[i].genotype[j] = population.creatures[chosen2].genotype[j];
            }
            mutation();
        }

        for (int i = 0; i < population.creatures.length; i++) {
            for (int j = 0; j < population.creatures[0].genotype.length; j++) {
                population.creatures[i].genotype[j] = newPopulation.creatures[i].genotype[j];
            }

        }
    }

    void writeGenotype(int creatureToWrite){
        System.out.println("evaluation: "+ population.creatures[creatureToWrite].evaluation);
        for (int i = 0; i < population.creatures[0].genotype.length; i++) {
            System.out.print(population.creatures[creatureToWrite].genotype[i] + " ");
        }
    }

    void evaluationReset(Population populationToReset){
        for (int i = 0; i < populationToReset.creatures.length; i++) {
            populationToReset.creatures[i].evaluation = 0;
        }
    }

    void mutation(){
        if (random.nextInt(100) < mutationProbability){
            population.creatures[random.nextInt(population.creatures.length)].genotype[random.nextInt(population.creatures[0].genotype.length)] = random.nextInt(10);
        }
    }

    @Override
    public void run() {
        int generationCounter = 0;
        while(true) {
            System.out.println("\ngenerationCounter: " +generationCounter+ "\n");

            for (int i = 0; i < this.population.creatures.length; i++) {

                this.population.creatures[i].fitness(this.expectedGenotype);

                if (this.population.creatures[i].evaluation == this.population.creatures[0].genotype.length){
                    System.out.println("\nresult obtained in: "+generationCounter+" generations.\n");
                    writeGenotype(i);

                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (int i = 0; i < population.creatures.length; i++) {
                writeGenotype(i);
            }

            selection();
            reproduction();
            evaluationReset(population);
            evaluationReset(newPopulation);

            generationCounter +=1;
        }
    }
}
