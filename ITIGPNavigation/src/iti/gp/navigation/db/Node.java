package iti.gp.navigation.db;

import iti.gp.navigation.db.DBHelper.NodeTyp;

public class Node {
	private int _id;
	private String nodeName;
	private float nodeXcord;
	private float nodeYcord;
	private NodeTyp nodeTyp;
	private String nodeInfo;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public float getNodeXcord() {
		return nodeXcord;
	}
	public void setNodeXcord(float nodeXcord) {
		this.nodeXcord = nodeXcord;
	}
	public float getNodeYcord() {
		return nodeYcord;
	}
	public void setNodeYcord(float nodeYcord) {
		this.nodeYcord = nodeYcord;
	}
	public NodeTyp getNodeTyp() {
		return nodeTyp;
	}
	public void setNodeTyp(NodeTyp nodeTyp) {
		this.nodeTyp = nodeTyp;
	}
	public String getNodeInfo() {
		return nodeInfo;
	}
	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
	}
	@Override
	public String toString() {
		return nodeName;
	}
}
