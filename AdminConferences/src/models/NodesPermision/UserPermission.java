package models.NodesPermision;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class UserPermission extends PermissionNode {
	public UserPermission(String idUser) {
		this.dataPermision = EnumPermission.USER;
		this.idNodePermission = idUser;
		this.icon="res/usuario.png";
	}

	@Override
	public boolean addChild(PermissionNode node) {
		if (node.getDataPermision() == EnumPermission.CONFERENCE || node.getDataPermision() == EnumPermission.TOPIC) {
			return true;
		}
		return false;
	}
}
