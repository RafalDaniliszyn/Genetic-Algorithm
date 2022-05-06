package pl.rafal;

public class Population {

    public Creature[] creatures;

    public Population(int populationLength, int genotypeLength) {
        creatures = new Creature[populationLength];
        for (int i = 0; i < populationLength; i++) {
            creatures[i] = new Creature(genotypeLength);
        }
    }
}
