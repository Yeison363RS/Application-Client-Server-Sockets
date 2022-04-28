package models.NodesPermision;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class Permission extends PermissionNode {
	public Permission(EnumPermission typePermision, String id_father) {
		selectTypeIcon(typePermision);
		this.dataPermision = typePermision;
		this.idNodePermission = id_father+typePermision;
	}
	public Permission(String idComplete,EnumPermission typePermision) {
		selectTypeIcon(typePermision);
		this.dataPermision = typePermision;
		this.idNodePermission = idComplete;
	}
	public void selectTypeIcon(EnumPermission typePermission) {
		switch (typePermission) {
		case DELETE:
			this.icon="res/borrarM.png";
			break;
		case CREATE:
			this.icon="res/addM.png";
			break;
		case REPORT:
			this.icon="res/reporteM.png";
			break;
		default:
			break;
		}
	}

	@Override
	public boolean addChild(PermissionNode node) {
		return false;
	}
}
