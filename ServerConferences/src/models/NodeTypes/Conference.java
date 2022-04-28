package models.NodeTypes;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/
public class Conference extends NodeConference {
	
	public Conference(String nameData) {
		super.nameData=nameData;
		TypeNode=EnumConference.CONFERENCE;
		this.icon="res/conferenciasM.png";
	}
	@Override
	public boolean addNodeChild(NodeConference node) {
		if(node.getTypeNode()==EnumConference.ATTENDEE || node.getTypeNode()==EnumConference.LECTURER) {
			return true;
		}
		return false;
	}
}
