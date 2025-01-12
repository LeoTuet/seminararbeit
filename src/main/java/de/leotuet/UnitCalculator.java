package de.leotuet;

public class UnitCalculator {

	public static double kilometersToMeters(double kilometers) {
		return kilometers * 1000;
	}

	public static double kilometersPerHourToMetersPerSecond(int kilometersPerHour) {
		return kilometersPerHour / 3.6;
	}

	public static double nanosecondsToSeconds(long nano) {
		return nano / Math.pow(10, 9);
	}

}
