package question7;

public class Employee implements Comparable {
	
	private String name;
	private Department department;
	private int age;
	
	public Employee(String name, Department department, int age) {
		super();
		this.name = name;
		this.department = department;
		this.age = age;
	}

}
