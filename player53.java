import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class player53 implements ContestSubmission
{
	private Random random;

	private ContestEvaluation evaluation;

	private int evaluationsLimit;
	
	private static final int INDIVIDUAL_SIZE = 10;
	private static final int POPULATION_SIZE = 100;
	private individual population[];

	public player53()
	{
		random = new Random();
		population = new individual[POPULATION_SIZE];
	}
	
	public void initPopulation() {
		for (int i = 0; i < POPULATION_SIZE; i++) {
		double[] newParams = new double[INDIVIDUAL_SIZE];
			
			for (int j = 0; j < INDIVIDUAL_SIZE; j++) {
				newParams[j] = random.nextDouble() * 10 - 5;
			}
			population[i] = new individual(newParams);
		}
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
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
		boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
		boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
		boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
		if(isMultimodal){
		    // Do sth
		} else {
		    // Do sth else
		}
	}

	public void run()
	{
		// Run your algorithm here
		int evals = 0;
		// init population
		initPopulation();

		// calculate fitness
		

		while(evals<evaluationsLimit){
		    // Select parents


		    // Apply crossover / mutation operators


		    double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		    // Check fitness of unknown fuction


		    Double fitness = (double) evaluation.evaluate(child);
		    evals++;


		    // Select survivors


		}
	}
}
