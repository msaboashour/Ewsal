package iti.gp.navigation;

public class AccessPoint {

	// Properties to class
	private int id;
	private String name;
	private String mac_Address;	
	private float xCordinate;
	private float yCordinate;
	private float level;
	private float distance;

	// Getter and Setter Method To this Class
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMac_Address() {
		return mac_Address;
	}

	public void setMac_Address(String mac_Address) {
		this.mac_Address = mac_Address;
	}

	public float getxCordinate() {
		return xCordinate;
	}

	public void setxCordinate(float xCordinate) {
		this.xCordinate = xCordinate;
	}

	public float getyCordinate() {
		return yCordinate;
	}

	public void setyCordinate(float yCordinate) {
		this.yCordinate = yCordinate;
	}

	public float getlevel() {
		return level;
	}

	public void setlevel(float level) {
		this.level = level;
	}
	public float getDistance() {
		
		return distance;
	}
	public void setDistance(float Distance) {
		this.distance= Distance;
	}


	// Function To Convert from Level Or dBm to the percentage of signal
	
	// strength
	public float Calculate_Distance()
	{
		return 20*(1- (GetPercentage()/100) );
	}
	
	public float GetPercentage()
	{
		if(level >= -31)  // start from -30
			return 100;
		else if(level < -31 && level >= -39)
			return (float)(level+129);
		else if(level <= -40 && level >= -49)
			return (float)(level+127);
		else if(level <= -50 && level >= -59)
			return (float)(level+124);		
		else if(level <= -60 && level >= -69)
			return (float)(level+122.5);
		else if(level <= -70 && level >= -79)
			return (float)(level+120.5);
		else if(level <= -80 && level >= -89)
			return (float)(level+118.5);
		else if(level <= -90 && level >= -99)
			return (float)(level+116);
		else if(level <= -100 && level >= -110)
			return (float)(level+106);
		else
			return 0;
		
	}

}
