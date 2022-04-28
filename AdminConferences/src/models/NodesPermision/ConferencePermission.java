package models.NodesPermision;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class ConferencePermission extends PermissionNode{
	public ConferencePermission(String idConferdence) {
		this.idNodePermission=idConferdence;
		this.dataPermision=EnumPermission.CONFERENCE;
		this.icon="res/conferenciasM.png";
	}
	@Override
	public boolean addChild(PermissionNode node) {
		if(node.getDataPermision()==EnumPermission.CREATE || 
				node.getDataPermision()==EnumPermission.DELETE || 
				node.getDataPermision()==EnumPermission.REPORT) {
			return true;
		}
		return false;
	}
}
