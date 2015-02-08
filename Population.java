
import java.util.Arrays;
import java.util.Random;

/** 
 * A <code>Population</code> is a collection of individuals (each one
 * representing a candidate solution for the n-queens problem).  To
 * facilitate the implementation of the various methods, <b>the
 * individuals will always be kept in increasing value of fitness</b>.
 */

public class Population {
    private int bestFitness;
    private int popSize;
    private int permLength;
    private Individual[] solutions;
    
    // INSERT YOUR CLASS AND INSTANCE VARIABLES HERE
    
    /**
     * A constructor of arity 2 to initialize the <b>Population</b>.
     * 
     * @param size is the number of individuals of this population
     * @param dimension is the size of the board and also the number of queens
     */

    public Population(int size, int dimension) {
     popSize=size;
     permLength=dimension;
     
     solutions=new Individual[popSize];
     int [] fitness=new int [popSize];
     for (int i=0; i<popSize; i++)
     {
      solutions[i]=new Individual(dimension);
      fitness[i]=solutions[i].getFitness();
     }
     Arrays.sort(fitness);
     bestFitness=fitness[0];
    }
    
    /**
     * The method <code>evolve</code> selects parent individuals. An offspring
     * is then created from the two parents, using the method
     * <code>crossover</code>. With probability <code>MUTATION_RATE</code>, the
     * offspring is <code>mutated</code>. Use 0.8 as the default
     * <code>MUTATION_RATE</code> The resulting child is inserted into the
     * population. As a result, the least fitted individual will be eliminated
     * from the population. Remember that the <code>population</code> is kept in
     * increasing order of fitness. For the selection of the parents, you can
     * experiment with different scenarios. A possible scenario is to randomly
     * select two parents. Another possible one would be to select the most fit,
     * and a randomly selected one. Or else, select the two most fitted
     * individuals.
     */

    public void evolve() {
     double MUTATION_RATE=0.8;
     
     // Automatically sorts the solutions as well as making father the fittest
     Individual father=getFittest();
     // This picks the mother to be any individual in the population other than the most fit, which is the the father
 Individual mother= solutions [1+(int)Math.random()*(popSize-1)];
 
 Individual child= father.recombine(mother);
 if (Math.random()<MUTATION_RATE)
 {
  child.mutate();
 }
 solutions[popSize-1]=child;
    }
    
    /**
     * The instance method <code>public Individual getFittest()</code> returns the
     * "best" individual of the population, i.e. the one that has the smallest
     * fitness value.
     * 
     * @return returns the currently best solution
     */
     
    public Individual getFittest() {

     Arrays.sort(solutions);
     bestFitness=solutions[0].getFitness();
     return solutions[0];
    }
    
    /**
     * Returns a <code>String</code> representation of this <code>Population</code>.
     * 
     * @return the String representation of this Population
     */

    public String toString() {
     StringBuilder writePopulation= new StringBuilder();
     writePopulation.append("The Population is made up of ");
     writePopulation.append(popSize);
     writePopulation.append(" individuals. The individuals in order of fitness are: ");
     for (int i =0; i<popSize; i++)
     {
     writePopulation.append(solutions[i].toString());
     }
     return writePopulation.toString();

 
    }
}