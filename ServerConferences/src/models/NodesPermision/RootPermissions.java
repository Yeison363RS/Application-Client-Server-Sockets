package models.NodesPermision;

public class RootPermissions extends PermissionNode{
	public RootPermissions () {
		this.dataPermision=EnumPermission.ROOT;
		this.idNodePermission=EnumPermission.ROOT.name();
		this.icon="res/baseM.png";
	}
	@Override
	public boolean addChild(PermissionNode node) {
		if(node.getDataPermision()== EnumPermission.USER) {
			return true;
		}
		return false;
	}
}
