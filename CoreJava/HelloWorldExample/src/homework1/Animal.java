package homework1;

public abstract class Animal {
	
	private double size;	
	private String species;
	
	public String getSpecies() {
		return species;
	}
	
	public void setSpecies(String species) {
		this.species = species;
	}
	
	public double getSize() {
		return size;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	public abstract void makeNoise();
	
}
