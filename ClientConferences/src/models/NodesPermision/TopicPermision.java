package models.NodesPermision;


public class TopicPermision extends PermissionNode{
	public TopicPermision (String idTopic) {
		this.dataPermision=EnumPermission.TOPIC;
		this.idNodePermission=idTopic;
		this.icon="res/topicM.png";
	}
	@Override
	public boolean addChild(PermissionNode node) {
		if(node.getDataPermision()== EnumPermission.CREATE|| 
				node.getDataPermision()==EnumPermission.DELETE || 
				node.getDataPermision()==EnumPermission.REPORT) {
			return true;
		}
		return false;
	}
}
