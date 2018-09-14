import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

public class player53 implements ContestSubmission
{
	// How many parameters an individual has.
	private static final int INDIVIDUAL_SIZE = 10;

	private static final int POPULATION_SIZE = 100;


	// The change that mutation happens for a genome.
	private static final double MUTATION_PROBABILITY = 0.015;

	// Mutation method can be: "SET_RANDOM_NUMBER", "ADD_RANDOM_NUMBER" "GAUSSIAN"
	private static final String MUTATION_METHOD = "SET_RANDOM_NUMBER";


	private static final int NUM_CHILDREN_GROUPS = 10;

	private static final int NUM_CHILDREN_PER_GROUP = 2;


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

		// Do something with property values, e.g. specify relevant settings of your algorithm
		if (isMultimodal) {
		    // Do something

		} else {
		    // Do something else

		}
	}

	//
	// Should select a number of parents from the population.
	//
	private List<individual> selectParents()
	{
		List<individual> parents = new ArrayList<individual>();

		// Use some selection mechanism to select a number of parents and add them to this list.

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
					// TODO: implement Gaussian mutation: http://www.iue.tuwien.ac.at/phd/heitzinger/node27.html

				}
			}
		}
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
