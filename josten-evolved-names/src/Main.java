import java.time.Duration;
import java.time.Instant;

/*
 * Andrew Josten
 * Assignment 2
 */

/**
 * This class creates a population of 100 genomes with a mutation rate of 5%
 * and attempts to mutate them to the name CHRISTOPHER PAUL MARRIOTT
 * @author Andrew Josten
 *
 */
public class Main {
	/**
	 * Instantiates a population and calls day() 
	 * until the target string is a part of the population. 
	 */
	public static void main(String args[])  {
		/*Commented out for grading*
		testGenome();
		testPopulation();
		/**************************/
		
		Instant start = Instant.now();
		
		int numGenomes = 100;
		Double mutationRate = 0.05;
		
		//Instantiate population
		Population pops = new Population(numGenomes, mutationRate);
		int generations = 0;
		
		//Call day() until fitness is 0
		boolean sentinel = false;
		while(!sentinel) {
			pops.day();
			generations++;
			System.out.println(pops.mostFit.toString());
			if(pops.mostFit.fitness() == 0) {
				sentinel = true;
			}
		}
		Instant finish = Instant.now();
		System.out.println("Generations: " + generations);
		System.out.println("Runtime: " + Duration.between(start, finish).toMillis() + " milliseconds");
	}
	
	/**
	 * Tests the methods inside the Genome() class
	 */
	static void testGenome() {
		boolean errFound = false;
		
		//Test constructor 1
		Genome x = new Genome(0.05);
		if(!x.sequence.contentEquals("A") || !x.target.contentEquals("CHRISTOPHER PAUL MARRIOTT") || x.mutationRate != 0.05) {
			System.out.println("Error: Genome() constructor is incorrect."
					+ "\nShould be 'A', 'CHRISTOPHER PAUL MARRIOTT', and rate of 0.05"
					+ "\nWas " + x.sequence + " " + x.target + " " + x.mutationRate);
			errFound = true;
		}
		
		//Constructor 2
		Genome y = new Genome(x);
		if(!y.sequence.contentEquals("A") || !y.target.contentEquals("CHRISTOPHER PAUL MARRIOTT") || y.mutationRate != 0.05) {
			System.out.println("Error: Genome() copy constructor is incorrect."
					+ "\nShould be 'A', 'CHRISTOPHER PAUL MARRIOTT', and rate of 0.05"
					+ "\nWas " + y.sequence + " " + y.target + " " + y.mutationRate);
			errFound = true;
		}
		
		//Test fitness() for first time.
		if(x.fitness() != 49) {
			System.out.println("Error: Genome() fitness should be 49. Was " + x.fitness());
			errFound = true;
		}
		
		//Test mutate
		for(int i = 0; i< 100; i++) {
			x.mutate();
		}
		if(x.sequence.contentEquals("A")) {
			System.out.println("Error: Genome() mutate should sufficiently change the string. Was " + x.sequence);
			errFound = true;
		}
		
		//Test fitness again
		if(x.fitness() == 49) {
			System.out.println("Error: Genome() fitness should be sufficiently changed. Was " + x.fitness());
			errFound = true;
		}
		
		//Test crossover()
		for(int i = 0; i< 100; i++) {
			y.crossover(x);
		}
		if(y.sequence.contentEquals("A")) {
			System.out.println("Error: Genome() crossover should sufficiently change the string. Was " + y.sequence);
			errFound = true;
		}
		
		//No errors
		if(!errFound) {
			System.out.println("No errors detected in Genome()");
		}
	}
	
	/**
	 * Tests the methods inside the Population() class
	 */
	static void testPopulation() {
		boolean errFound = false;
		
		//Test constructor and populate with genomes
		Population X = new Population(50, 0.05);
		for(int  i = 0; i < X.genomes.length; i++) {
			if(!X.genomes[i].sequence.contentEquals("A") || X.genomes[i].mutationRate != 0.05) {
				System.out.println("Error: Population() constructor incorrect. Each Genome() in Population() should be (\"A\", 0.05)");
				errFound = true;
			}
		}
		
		//Test Day. Several days should improve the fitness of the best genome.
		for(int j = 0; j < 50; j++) {
			X.day();
		}
		if(X.mostFit.fitness() == 49) {
			System.out.println("Error: Population() day() is insufficient in mutating the population.");
			errFound = true;
		}
		
		//No errors
		if(!errFound) {
			System.out.println("No errors detected in Population()");
		}
	}
}
