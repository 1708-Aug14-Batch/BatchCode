package com.example.vehicles;

import com.example.interfaces.Driveable;
import static java.lang.Math.*;

public abstract class Car implements Driveable {

	public double stop(double mph) {
		double seconds = sqrt(mph);
		return seconds/2;
	}
}