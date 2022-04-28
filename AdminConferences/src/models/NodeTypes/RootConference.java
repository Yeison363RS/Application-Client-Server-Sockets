package models.NodeTypes;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class RootConference extends NodeConference{
	public RootConference(String nameData) {
		super.nameData=nameData;
		TypeNode=EnumConference.CONFERENCE;
		this.icon="res/baseM.png";
	}
	@Override
	public boolean addNodeChild(NodeConference node) {
		if(node.getTypeNode()==EnumConference.TOPIC) {
			return true;
		}
		return false;
	}
}
