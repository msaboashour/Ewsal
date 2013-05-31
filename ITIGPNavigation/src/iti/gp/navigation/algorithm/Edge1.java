package iti.gp.navigation.algorithm;

public class Edge1 {
	private int KEY_EdgeID;
	private int StartID;
	private int EndID;
	private int Cost;
	public int get_ID() {
		return KEY_EdgeID;
	}
	public void set_id(int KEY_EdgeID ) {
		this.KEY_EdgeID =KEY_EdgeID;
	}
	public int get_startid() {
		return StartID;
	}
	public void set_startid(int StartID ) {
		this.StartID =StartID;
	}
	public int get_Endid() {
		return EndID ;
	}
	public void setEndid(int Endid) {
		this.EndID = Endid;
	}
	public int get_Cost() {
		return Cost;
	}
	public void set_Cost(int Cost) {
		this.Cost =Cost;
	}
}
