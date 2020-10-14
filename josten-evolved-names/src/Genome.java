/*
 * Andrew Josten
 * Assignment 2
 */

import java.util.Random; 
/**
 * This class represents a single 'genome' name sequence as a string.
 * It also has a target string which it can compare its own sequence to.
 * @author Andrew Josten
 *
 */
public class Genome {
	/**
	 * Used for mutation seeding
	 */
	public Random r = new Random();
	/**
	 * Current name in this genome
	 */
	String sequence;
	/**
	 * Target name to evolve to
	 */
	String target = "CHRISTOPHER PAUL MARRIOTT";
	/**
	 * Letters to mutate to
	 */
	char characters[] = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'
					, 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '­', '’'};
	/**
	 * The rate at which a name mutates.
	 */
	Double mutationRate;
	
	/**
	 * Constructs a genome. Initializes to value 'A'
	 * @param mutationRate Rate at which a genome mutates (0<r<1)
	 */
	public Genome(double rate) {
		sequence = "A";
		//Mutation rate must be 0<r<1
		if(rate < 1 && rate > 0) {
			mutationRate = rate;
		}
		else {//I made my own default rate
			mutationRate = 0.05;
		}
	}
	
	/**
	 * Creates a genome by copying the arguement
	 * @param gene Genome to be copied
	 */
	public Genome(Genome gene) {
		mutationRate = gene.mutationRate;
		sequence = gene.sequence;
	}
	
	/**
	 * Mutates the sequence string. With a mutationRate chance (eg, if m-rate is 0.05, its a 1/20 chance),
	 * add a random char, and/or
	 * delete a random char (string must be at least 2 long), and/or
	 * replace a char with a randomly selected char (do this for each char in string)
	 */
	void mutate() {
		StringBuilder str = new StringBuilder(sequence);
		
		Double mPercent = mutationRate;
		int ranSequenceIndex = r.nextInt(sequence.length()+1);
		int ranCharIndex = r.nextInt(29);
		
		//Add random char to random location
		if(Math.random() <= mPercent) {
			ranSequenceIndex = r.nextInt(sequence.length()+1);
			ranCharIndex = r.nextInt(29);
			str.insert(ranSequenceIndex, characters[ranCharIndex]);
		}
		
		//Delete char at random location
		ranSequenceIndex = r.nextInt(sequence.length());
		if(Math.random() <= mPercent && sequence.length() > 1) {
			ranSequenceIndex = r.nextInt(sequence.length()+1);
			str.delete(ranSequenceIndex, ranSequenceIndex+1);
		}
		
		//Run over each character, swap char with another random char (mutationRate chance)
		for(int i = 0; i < str.length(); i++) {
			if(Math.random() <= mPercent) {
				ranCharIndex = r.nextInt(29);
				str.setCharAt(i, characters[ranCharIndex]);
			}
		}
		sequence = str.toString();
	}
	
	/**
	 * Crosses this genome with another.
	 * -Choose one parent string. Add it's character to this one. If there is no char, end.
	 * @param other The genome breeding with this genome
	 */
	void crossover(Genome other) {
		StringBuilder parent1 = new StringBuilder(sequence);
		StringBuilder parent2 = new StringBuilder(other.sequence);
		StringBuilder offspring = new StringBuilder("");
		
		boolean flip = r.nextBoolean();
		boolean sentinel = true;
		int count = 0;
		
		while(sentinel) {
			if(flip) {
				if(parent1.length() < count + 1) 
					sentinel = false;
				else
					offspring.append(parent1.charAt(count));
			}
			else {
				if(parent2.length() < count + 1) 
					sentinel = false;
				else
					offspring.append(parent2.charAt(count));
			}
			count++;
		}
		
		//Update the sequence
		sequence = offspring.toString();
	}
	
	/**
	 * Returns the string fitness of this relative to its target.
	 * Adds 1 for each character difference / missing character
	 * @return fitness, where 0 is same as target
	 */
	int fitness() {		
		int n = sequence.length();
		int m = target.length();
		int l = Math.max(m, n);
		int k = Math.min(m, n);
		int f = Math.abs(m-n);
		
		for(int i = 0; i < l; i++) {
			if(i < k) {
				if((sequence.charAt(i) != target.charAt(i))) {
					f++;
				}
			}
			else {
				f++;
			}			
		}	
		return f;		
	}
	
	/**
	 * Returns a string representing the current sequence in the genome.
	 */
	public String toString() {
		return "(\"" + sequence + "\", " + fitness() + ")" ;
	}
}
