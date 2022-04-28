package models.NodeTypes;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class Topic extends NodeConference{
	public Topic(String nameData) {
		this.nameData=nameData;
		this.icon="res/topicM.png";
		this.TypeNode= EnumConference.TOPIC;
	}
	@Override
	public boolean addNodeChild(NodeConference subtopic) {
		if(subtopic.getTypeNode()==EnumConference.TOPIC || subtopic.getTypeNode()==EnumConference.CONFERENCE) {
			return true;
		}
		return false;
	}
}
