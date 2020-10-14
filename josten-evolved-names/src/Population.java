import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/*
 * Andrew Josten
 * Assignment 2
 */

/**
 * This class represents a population of genomes and can simulate a single 'day' of evolution
 * @author Andrew Josten
 *
 */
public class Population {
	/**
	 * The most fit genome in the current population (0 based fitness)
	 */
	public Genome mostFit;
	/**
	 * The population of genomes in a list
	 */
	Genome[] genomes;
	/**
	 * Used for breeding
	 */
	public Random r = new Random();
	
	/**
	 * Initializes the population with a specified number of default genomes and a mutation rate ( 0 < mr < 1 )
	 * @param numGenomes
	 * @param mutationRate
	 */
	public Population(int numGenomes, Double mutationRate) {
		genomes = new Genome[numGenomes];
		for(int i = 0; i < numGenomes; i++) {
			genomes[i] = new Genome(mutationRate);
		}
		Arrays.sort(genomes, new SortByFitness());
		mostFit = genomes[0];
	}
	
	/**
	 * Go through 1 generation of the genome:
	 * -Discard the least fit (max value)
	 * -Mutate/breed the remaining genomes (equal chance)
	 * -Display most fit(?)
	 */
	void day() {
		Arrays.sort(genomes, new SortByFitness());
		
		//Update most fit
		mostFit = genomes[0];
		
		//Remove least fit
		for(int i = genomes.length/2-1; i < genomes.length; i++) {
			genomes[i] = null;
		}
		
		//Refill population: either pick a random genome and clone/mutate it OR clone a genome and crossover with another and mutate
		int maxIndex = genomes.length/2;
		int selectedGenome = r.nextInt(maxIndex-1);
		boolean flip = r.nextBoolean();
		Genome newGene;
		
		int index = genomes.length/2-1;
		while(genomes[genomes.length-1] == null){			
			if(flip) {//clone
				selectedGenome = r.nextInt(maxIndex-1);
				newGene = new Genome(genomes[selectedGenome]);
				newGene.mutate();
				genomes[index] = newGene;
			}
			else {//crossover
				newGene = new Genome(genomes[selectedGenome]);
				selectedGenome = r.nextInt(maxIndex-1);
				newGene.crossover(genomes[selectedGenome]);
				newGene.mutate();
				genomes[index] = newGene;
			}
			index++;
			//maxIndex++; //<- Commented this out b/c I believe only the initial 50 left over should be used for repopulation. If uncommented, any new genome is a viable parent/host
			selectedGenome = r.nextInt(maxIndex-1);
			flip = r.nextBoolean();
		}
		Arrays.sort(genomes, new SortByFitness());
	}
	
	/**
	 * Displays entire pops
	 */
	public String toString() {
		return Arrays.deepToString(genomes);
	}	
}

class SortByFitness implements Comparator<Genome>{
	@Override
	public int compare(Genome a, Genome b) {
		if(a == null)
			System.out.println("Found this bug in sorting with a");
		if(b == null)
			System.out.println("Found this bug in sorting with b");
		return a.fitness() - b.fitness();
	}
}
