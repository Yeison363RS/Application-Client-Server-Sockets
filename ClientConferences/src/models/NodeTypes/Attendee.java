package models.NodeTypes;


public class Attendee extends NodeConference {

	public Attendee(String nameData) {
		this.nameData = nameData;
		this.TypeNode = EnumConference.ATTENDEE;
		this.icon = "res/asistentesM.png";
	}

	@Override
	public boolean addNodeChild(NodeConference node) {
		return false;
	}
}
