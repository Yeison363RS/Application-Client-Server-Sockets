package models.NodesPermision;

public abstract class PermissionNode extends Object{
	protected EnumPermission dataPermision;
	protected String icon;
	protected String idNodePermission;

	public EnumPermission getDataPermision() {
		return dataPermision;
	}

	public String getIdNodePermission() {
		return idNodePermission;
	}

	public String getIdData() {
		return idNodePermission;
	}
	public String getPathIcon() {
		return this.icon;
	}
	public void setDataPermision(EnumPermission dataPermision) {
		this.dataPermision = dataPermision;
	}

	public abstract boolean addChild(PermissionNode node);
}
