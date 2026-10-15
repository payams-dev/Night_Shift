package com.payamsdev.position;

public class CameraLocationList {

	public static final Location SHOW_STAGE = new Location("Show Stage", Camera.CAM_1A);
	public static final Location DINING_AREA = new Location("Dining Area", Camera.CAM_1B);
	public static final Location PIRATE_COVE = new Location("Pirate Cove", Camera.CAM_1C);
	public static final Location WEST_HALL = new Location("West Hall", Camera.CAM_2A);
	public static final Location WEST_HALL_CORNER = new Location("West Hall Corner", Camera.CAM_2B);
	public static final Location SUPPLY_CLOSET = new Location("Supply Closet", Camera.CAM_3);
	public static final Location EAST_HALL = new Location("East Hall", Camera.CAM_4A);
	public static final Location EAST_HALL_CORNER = new Location("East Hall Corner", Camera.CAM_4B);
	public static final Location BACKSTAGE = new Location("Backstage", Camera.CAM_5);
	public static final Location KITCHEN = new Location("Kitchen", Camera.CAM_6);
	public static final Location RESTROOMS = new Location("Restrooms", Camera.CAM_7);
	public static final Location OFFICE = new Location("Office");

	// Prevent instantiation of this utility class
	private CameraLocationList() {
	}

}
