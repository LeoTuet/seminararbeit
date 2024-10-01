package de.leotuet;

public class UnitCalculator {
	public static double kilometersToMeters(double kilometers) {
		return kilometers * 1000;
	}

	public static double kilometersPerHourToMetersPerSecond(int kilometersPerHour) {
		return kilometersPerHour / 3.6;
	}
}
