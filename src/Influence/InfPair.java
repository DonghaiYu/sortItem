package Influence;


public class InfPair implements Comparable<InfPair>{
	String source;
	String target;
	double weight;
	int recordNum;
	
	public InfPair(String line) {
		String[] items = line.split(":");
		this.source = items[0].split("->")[0];
		this.target = items[0].split("->")[1];
		
		String[] ttimes = items[1].split(",");
		this.recordNum = ttimes.length;
		this.weight = WeightFunction.getWeight(ttimes);
	}
	


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.source+"->"+this.target+": weight,"+this.weight+"   record number:"+this.recordNum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}



	@Override
	public int compareTo(InfPair o) {
		// TODO Auto-generated method stub
		if (this.weight < o.weight) {
			return 1;
		}else if (this.weight > o.weight) {
			return -1;
		}
		return 0;
	}
	
	

}
