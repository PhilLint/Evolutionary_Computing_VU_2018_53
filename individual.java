public class individual {
	private double fitness;
	private double[] params;
	
	public individual(double[] params) {
		setParams(params);
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public double[] getParams() {
		return params;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public void setParams(double[] params) {
		this.params = params;
	}
}