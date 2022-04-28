package models.NodeTypes;


import models.NodeGeneric;

public abstract class NodeConference extends Object{
	protected String nameData;
	protected String pathImageLecturer;
	protected EnumConference TypeNode;
	protected String icon;
	protected NodeGeneric<NodeConference> nodeFather;
	
	public String getPathImageLecturer() {
		return pathImageLecturer;
	}
	
	public void setPathImageLecturer(String pathImageLecturer) {
		this.pathImageLecturer = pathImageLecturer;
	}
	public abstract boolean addNodeChild(NodeConference node);
	
	public String getNameData() {
		return nameData;
	}
	public void setNameData(String nameData) {
		this.nameData = nameData;
	}
	public EnumConference getTypeNode() {
		return TypeNode;
	}
	public void setTypeNode(EnumConference typeNode) {
		TypeNode = typeNode;
	}
	public String getPathIcon() {
		return icon;
	}

}
