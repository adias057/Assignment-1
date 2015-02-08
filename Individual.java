import java.util.Random;
import static org.junit.Assert.*;
import java.util.Arrays;




/**
 * An <code>Individual</code> (a chromosome) is a candidate solution for a given
 * problem. Its representation depends on the specific problem to be solved. Two
 * individuals can be combined (see method crossover) to produce a new
 * offspring. As with natural chromosomes, these artificial ones suffer
 * mutations. Each chromosome has a fitness value that indicates the quality of
 * this solution.
 * <p/>
 * <p/>
 * A <code>Population</code> is a collection of chromosomes. At each iteration
 * (generation), the genetic algorithm selects chromosomes for reproduction. The
 * offsprings are inserted into the population, and the least fitted individuals
 * are eliminated. Thus, the size of the population is fixed.
 * <p/>
 * <p/>
 * For this assignment, an <code>Individual</code> represents a solution to the
 * <code>n</code>-Queens problem. As introduced in the assignment description, a
 * candidate solution is represented by a permutation of size <code>n</code>,
 * such that attribute <code>i</code> represents the row for the queen found at
 * column <code>i</code>.
 * <p/>
 * <p/>
 * Not all permutations are valid solutions to <code>n</code>-Queens problem. A
 * permutation is a valid solution if no two queens can attack each other. Two
 * queens are attacking each other if they are on the same row or column, which
 * is impossible given this representation, but also if they are found on the
 * same minor or major diagonal.
 * <p/>
 * <p/>
 * Herein, we define the fitness value of an individual as the number of pairs
 * of queens attacking each other.
 * <p/>
 * You must complete the implementation of the class <code>Individual</code>
 * following all the directives.
 *
 * @author Marcel Turcotte (turcotte@eecs.uottawa.ca)
 */


public class Individual implements Comparable<Individual> {

    private int fitness = 0;
    private int[] permutation;
    private final int size;

//********************************************************************************************************************

    public Individual(int size) {
      
       
        permutation = Util.getPermutation(size);
        this.size = size;

        for
          (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
          /* "i" represents the column of the queen 
          *  "j" represents the position where the column is looked at
          *   permuations [j] is the queen in the column being looked at
          *   permuations [i] is the queen in the row being looked at
          *   i-j represents the distance of the columns horizontally
          *   i permutations j represents tthe distance between the rows vertically.
          *   If you follow this process the queen is diagonal to each other. 
          */

                if (permutation[j] == permutation[i] + j - i || permutation[j] == permutation[i] - j + i)
                    fitness++;
            }
        }
        fitness = (fitness / 2);
      /*The part above is the two queens attacking each other from part 1 to part 2 and from part 2 to part 1
        */


    }
//************************************************************************************************************************

    public Individual(int[] permutation) {

        this.permutation = permutation;
        size = permutation.length;

        for
                (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
          /* "i" represents the column of the queen 
          *  "j" represents the position where the column is looked at
          *   permuations [j] is the queen in the column being looked at
          *   permuations [i] is the queen in the row being looked at
          *   i-j represents the distance of the columns horizontally
          *   i permutations j represents tthe distance between the rows vertically.
          *   If you follow this process the queen is diagonal to each other. 
          */

                if (permutation[j] == permutation[i] + j - i || permutation[j] == permutation[i] - j + i)
                    fitness++;
            }
        }
        fitness = fitness / 2;
      /*The part above is the two queens attacking each other from part 1 to part 2 and from part 2 to part 1
        */


    }

    //***************************************************************************************************************

    public Individual crossover(Individual other, int position) {
     /* Step 1: Copy the value before the position into a new permutation
      * Step 2: Use the position as a starting point to add values to the newest permutation.
      * Step 3: Compare each of the values in the original permutation to each value in the other permutation
      * Step 4: If there is a math, move on to the next other permutation value to test. 
      * Step 5: If there is no match found after comparing all the vaues int he original permutation, add the other value to the new permutation and increase the  starting point to the next value in the new matrix and add 1
      */

        int[] newPermutation = new int[other.size];
        Individual child = new Individual(newPermutation);
        //Step 1
        for
                (int i = 0; i < position; i++) {
            newPermutation[i] = this.permutation[i];
            //Step 2 (The starting values)
            int j = position;


            while (j < size) {
                //Step 3

                for (int scrollOther = 0; scrollOther < other.size; scrollOther++) {
                    for (int scrollThis = 0; scrollThis < position; scrollThis++) {

                        if ((permutation[scrollThis]) == (permutation[scrollOther]))
                            //Step 4
                            scrollThis = position;

                        else if (scrollThis == position - 1) {
                            //Step 5
                            newPermutation[j] = other.permutation[scrollOther];
                            j++;
                        }


                    }
                }
            }


        }
        return child;
    }


    //****************************************************************************************************************
    /* Randomize the position of the cross over point anywhere to the permutation */
    public Individual recombine(Individual other) {

        int position = ((int) Math.floor(Math.random() * size));
        return crossover(other, position);


    }


    //********************************************************************************************************************
    public Individual mutate(int i, int j) {
        int newJ = this.permutation[j];
        int newI = this.permutation[i];
        int[] mutatedPermutation = new int[size];

        for (int x = 0; x < size; x++) {
            mutatedPermutation[x] = this.permutation[x];
        }

        mutatedPermutation[i] = newI;
        mutatedPermutation[j] = newJ;
        return new Individual(mutatedPermutation);
    }

    //********************************************************************************************************************
    public Individual mutate() {
        if (size == 1)
            return this;
        int i = (int) Math.floor(size * Math.random());
        int j = (int) Math.floor(size * Math.random());
        while (j == i) {

            j = (int) Math.floor(size * Math.random());
        }
        return this.mutate(i, j);
    }

    //********************************************************************************************************************
    public int getFitness() {

        return this.fitness;


    }

    //********************************************************************************************************************
    public int compareTo(Individual other) {
     

        return (this.fitness - other.fitness);  // Returns the other
    }
    

//********************************************************************************************************************
    private String permutationToString(String permString) {
        // This creates the string of the permutation
        StringBuilder writePermutation = new StringBuilder(); //Check with marcel if we can use string builder
        writePermutation.append("{");

        for (int i = 0; i < size; i++) {
            writePermutation.append(this.permutation[i]);
            if (i != size - 1)
                writePermutation.append("}");
        }
        permString = writePermutation.toString();
        return permString;
    }

//********************************************************************************************************************
    public String toString() {


      
      String returnTheString = Arrays.toString(permutation);
      return returnTheString;


    }

 //********************************************************************************************************************
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Individual queen = new Individual(1); 
            String test = queen.toString();
            System.out.print(test); //Prints out the to.String() 1st bracket
            System.out.println("Fitness:"+ queen.getFitness()); //Prints out the fitness
            
            
            System.out.print("Mutate:" + queen.mutate());// Prints ot the mutate 2nd bracket
            
            Individual i1 = new Individual(1); // For the cross over 3rd bracket
            Individual i2 = new Individual(1); // For the cross over 3RD bracket
            System.out.println("Crossover:" + i1.recombine(i2)); // For recombine.   
            
            
           
           
            
            
            
            
        
            
            
            
            
        }
    }

//********************************************************************************************************************      

}


   
 

