package pl.rafal;

public class Main {

    public static void main(String[] args) {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100,30, 8, 10);

        Thread algorytm = new Thread(geneticAlgorithm);
        algorytm.start();

    }
}
