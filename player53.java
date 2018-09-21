import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class player53 implements ContestSubmission
{
	// How many parameters an individual has.
	private static final int INDIVIDUAL_SIZE = 10;

	private static final int POPULATION_SIZE = 10;


	// The change that mutation happens for a genome.
	private static final double MUTATION_PROBABILITY = 0.05;

	private static final double GAUSSIAN_STANDARD_DEVIATION = 1.0;

	// Mutation method can be: "SET_RANDOM_NUMBER", "ADD_RANDOM_NUMBER" "GAUSSIAN"
	private static final String MUTATION_METHOD = "GAUSSIAN";


	private static final int NUM_CHILDREN_GROUPS = 3;

	private static final int NUM_CHILDREN_PER_GROUP = 2;

	// Parent selection method can be: "FITNESS", "ROULETTE_WHEEL", etc.
	private static final String PARENT_SELECTION_METHOD = "ROULETTE_WHEEL";


	private Random random;

	private ContestEvaluation evaluation;

	private int evaluationsLimit;

	// The list of individuals which represents out population.
	private List<individual> population;

	public player53()
	{
		random = new Random();
	}

	//
	// Create a new population with random individuals.
	//
	private void initPopulation()
	{
		population = new ArrayList<individual>();

		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.add(createRandomIndividual());
		}
	}

	//
	// Creates and returns an individual with random parameters between -5 and 5.
	//
	private individual createRandomIndividual()
	{
		double[] params = new double[INDIVIDUAL_SIZE];

		for (int i = 0; i < INDIVIDUAL_SIZE; i++) {
			params[i] = random.nextDouble() * 10 - 5;
		}

		return new individual(params);
	}

	//
	// For each individual in a population set the fitness value.
	// If false is returned the maximum number of evaluations is used.
	//
	private boolean setPopulationFitness()
	{
		for (individual in : population) {

			Object result = evaluation.evaluate(in.getParams());
			if (result == null) {
				// If we have reached the maximum number of evaluations evaluation.evaluate will return null.
				return false;
			}

			in.setFitness((double) result);
		}

		return true;
	}


	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		random.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		this.evaluation = evaluation;

		// Get evaluation properties
		Properties props = evaluation.getProperties();

		// Get evaluation limit
		evaluationsLimit = Integer.parseInt(props.getProperty("Evaluations"));
		System.out.println("Evaluations Limit: " + evaluationsLimit);

		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
		boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
		boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
		boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		if (! isMultimodal && ! hasStructure && !isSeparable) {
			// BentCigarFunction
			System.out.println("We are using BentCigarFunction");

		}
		if (isMultimodal && ! hasStructure && !isSeparable) {
			// Katsuura Function
			System.out.println("We are using Katsuura Function");

		}
		if (isMultimodal && hasStructure && !isSeparable) {
			// Schaffers Function
			System.out.println("We are using Schaffers Function");

		}
	}

	//
	// Should select a number of parents from the population.
	//
	private List<individual> selectParents()
	{
		List<individual> parents = new ArrayList<individual>();
		int numParents = NUM_CHILDREN_GROUPS * NUM_CHILDREN_PER_GROUP;
		switch(PARENT_SELECTION_METHOD)
		{
			case "FITNESS":
			{
				//System.out.println("Fitness Parent Selection");
				// Sort population.
				Collections.sort(population, Collections.reverseOrder());
				// Select the best individuals to be parents.
				parents.addAll(population.subList(0, numParents));
			} break;
			case "ROULETTE_WHEEL":
			{
				//System.out.println("Roulette Wheel Parent Selection");
				// Compute the sum of all fitnesses.
				double sumFitness = 0.0;
				for (individual in : population)
				{
					sumFitness += in.getFitness();
				}
				// For each candidate consider it has an interval the size of
				// its fitness.
				// Choose a random number between 0 and the sum of all fitnesses.
				// Check in which interval the number falls and then select the
				// respective individual to be a parent.
				for (int i = 0; i < numParents; i++)
				{
					double rd = random.nextDouble() * sumFitness;
					double partialSum = 0.0;
					for (individual in : population)
					{
						partialSum += in.getFitness();
						if (rd < partialSum)
						{
							parents.add(in);
							break;
						}
					}

				}
			} break;
			default: { }
		}


		return parents;
	}

	//
	// Given a list of parents; for each pair of parents this should create a child from them.
	//
	private List<individual> createChildrenFromParents(List<individual> parents)
	{
		List<individual> children = new ArrayList<individual>();

		// Use some way to pair 2 (or more?) parents.
		// Create a child from these 2 parents.

		return children;
	}

	//
	// For each child apply some mutation.
	//
	private void mutateChildren(List<individual> children)
	{
		for (individual in : children) {

			for (int i=0; i<in.getParams().length; i++) {

				double rd = random.nextDouble();
				if (rd > MUTATION_PROBABILITY) {
					continue;
				}

				if (MUTATION_METHOD.equals("SET_RANDOM_NUMBER")) {
					double mutatedParam = random.nextDouble() * 10 - 5;
					in.updateParam(i, mutatedParam);
				}

				if (MUTATION_METHOD.equals("ADD_RANDOM_NUMBER")) {
					double randomNumber = random.nextDouble() * 10;
					double mutatedParam = in.getParams()[i] + randomNumber;
					if (mutatedParam > 5) {
						mutatedParam = -5 + (mutatedParam - 5);
					}

					in.updateParam(i, mutatedParam);
				}

				if (MUTATION_METHOD.equals("GAUSSIAN")) {
					double gaussianRandomNumber = gaussian(in.getParams()[i], GAUSSIAN_STANDARD_DEVIATION);
					double mutatedParam = in.getParams()[i] + gaussianRandomNumber;
					if (mutatedParam > 5) {
						mutatedParam = -5 + (mutatedParam - 5);
					}
					if (mutatedParam < -5) {
						mutatedParam = 5 + (mutatedParam + 5);
					}

					in.updateParam(i, mutatedParam);
				}
			}
		}
	}

	//
	// Returns a random number from a Gaussian distribution
	//
	private double gaussian(double mean, double standardDev)
	{
		return random.nextGaussian() * standardDev + mean;
	}

	//
	// Select N number of survivors from the population.
	//
	private void selectSurvivors()
	{
		// Our population is too big because of the children.
		// Remove elements from the population list until we have the original POPULATION_SIZE again.
	}

	public void run()
	{
		int evals = 0;

		// Step 1. Initialize population
		initPopulation();

		// Step 2. Calculate population fitness
		setPopulationFitness();

		// print the population to the console.
		debugPrintPopulation();

		while(evals < evaluationsLimit) {
			// Step 3. Select parents
			List<individual> parents = selectParents();
			System.out.println("Parents(");
			for (individual in : parents) {
				System.out.println("\t" + in);
			}
			System.out.println(")");

			// Step 4. Create children from parents using crossover.
			List<individual> children = createChildrenFromParents(parents);

			// Step 5. Apply mutation
			mutateChildren(children);

			population.addAll(children);

			// New individuals in our population, yay! recalculate the fitness.
			boolean success = setPopulationFitness();
			if (! success) {
				break;
			}

			// Step 6. Select survivors and 'kill' the others.
			selectSurvivors();

			evals++;
		}
	}

	//
	// Debug prints the population to the console.
	//
	private void debugPrintPopulation()
	{
		System.out.println("Population(");
		for (individual in : population) {
			System.out.println("\t" + in);
		}
		System.out.println(")");
	}
}
