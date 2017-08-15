package homework1;

public class Cow extends Mammal implements Edible {
	
	public static final String COW_SPECIES = "Cow";
	
	public Cow() {
		setSpecies(COW_SPECIES);
	}

	public void cook(double temp) {
		System.out.println("You cook the " + getSpecies() + " to " + temp + "\u00b0F");
	}

	public double eat() {
		System.out.println("You ate " + getSize() + " of the " + getSpecies());
		return getSize();
	}

	@Override
	public void makeNoise() {
		System.out.println("*moo*");
	}

}
