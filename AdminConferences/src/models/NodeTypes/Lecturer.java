package models.NodeTypes;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class Lecturer extends NodeConference {

	public Lecturer(String nameData) {
		this.nameData = nameData;
		this.TypeNode = EnumConference.LECTURER;
		this.icon = "res/conferencistaM.png";
	}

	@Override
	public boolean addNodeChild(NodeConference node) {
		return false;
	}

	public void setPathFileImage(String path) {
		this.pathImageLecturer=path;
	}

}
