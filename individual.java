import java.util.Random;

public class individual
{
	// The fitness value of an individual.
	private double fitness;

	// The 10 parameters / genomes / digital dna.
	private double[] params;

	public individual(double[] params)
	{
		setParams(params);
	}

	public double getFitness()
	{
		return fitness;
	}

	public double[] getParams()
	{
		return params;
	}

	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}

	public void setParams(double[] params)
	{
		this.params = params;
	}

	public void updateParam(int index, double newParam)
	{
		this.params[index] = newParam;
	}


	@Override
	public String toString()
	{
		String str = "Individual(\n\t\tfitness: " + fitness + "\n\t\t{";

		for (int i=0; i<params.length; i++) {
			str += ((int) params[i]) + ",";
		}

		return str + "}\n\t)";
	}
}
