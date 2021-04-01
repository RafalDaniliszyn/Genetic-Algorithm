package pl.rafal;

import java.util.Random;

public class Creature {
    Random random;
    public long[] genotype;
    public int genotypeLength;
    int evaluation = 0;

    public Creature(int genotypeLength) {
        this.genotypeLength = genotypeLength;
        genotype = new long[genotypeLength];
        random = new Random();
        for (int i = 0; i < genotypeLength; i++) {
            genotype[i] = random.nextInt(10);
        }
    }

    void fitness(int[] expectedGenotype){
        for (int i = 0; i < this.genotypeLength; i++) {
            if (this.genotype[i] == expectedGenotype[i]){
                this.evaluation +=1;
            }
        }
    }
}
