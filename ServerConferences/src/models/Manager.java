package models;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import models.NodeTypes.Attendee;
import models.NodeTypes.Conference;
import models.NodeTypes.EnumConference;
import models.NodeTypes.Lecturer;
import models.NodeTypes.NodeConference;
import models.NodeTypes.RootConference;
import models.NodeTypes.Topic;
import models.NodesPermision.EnumPermission;
import models.NodesPermision.Permission;
import models.NodesPermision.PermissionNode;
import models.NodesPermision.RootPermissions;
import models.NodesPermision.TopicPermision;
import models.NodesPermision.UserPermission;

/**@autor
 * Yeison Fernando Rodriguez Sanchez
*/

public class Manager {
	private MyTree tree;
	private TreePermision treePermision;
	private static final Font CATEGORY_FONT = new Font(Font.getFamilyIndex("arial"), 18, Font.BOLD);

	public Manager() {
		this.tree = new MyTree(new RootConference("root"));
		this.treePermision = new TreePermision(new RootPermissions());
		treePermision.addNodePermissions(new UserPermission("1ROOT"), treePermision.getRoot().getData());

	}

	public void addNodeConference(NodeConference idNewChild, NodeConference idFaher) {
		tree.addNodeConferences(idNewChild, idFaher);
	}
	public void chargeTreePermissions(ArrayList<String> userIdList){
		for (String idUser : userIdList) {
			addUserTreePermission(idUser);
		}
	}
	public NodeConference getDataNodeFather(String idData) {
		return tree.getNodeSearch(idData).getData();
	}

	public String generateTable(NodeGeneric<NodeConference> node) throws IOException {
		String path = "reporte" + node.getData().getNameData() + ".pdf";
		try {
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(path));
			} catch (FileNotFoundException fileNotFoundException) {
				System.out.println("no se encontro el archivo");
			}
			document.open();
			Anchor anchor = new Anchor("Reporte de " + node.getData().getNameData(), CATEGORY_FONT);
			Chapter chapTitle = new Chapter(new Paragraph(anchor), 1);
			PdfPTable table = new PdfPTable(2);
			addNodeTable(table, node);
			document.add(chapTitle);
			if (node.getData().getTypeNode() == EnumConference.LECTURER) {
				Image imagen = Image.getInstance(node.getData().getPathImageLecturer());
				imagen.setAlignment(Element.ALIGN_CENTER);
				document.add(imagen);
			}
			document.add(table);
			document.close();
		} catch (DocumentException documentException) {
			System.out.println(
					"The file not exists (Se ha producido un error al generar un documento): " + documentException);
		}
		return path;
	}

	public void printTree() {
		tree.printAll();
	}

	public void addNodeTable(PdfPTable table, NodeGeneric<NodeConference> actual) {
		table.addCell(actual.getData().getTypeNode().name());
		table.addCell(actual.getData().getNameData());
		ArrayList<NodeGeneric<NodeConference>> listChild = actual.getChildren();
		for (int i = 0; i < listChild.size(); i++) {
			addNodeTable(table, listChild.get(i));
		}
	}

	public EnumPermission getTypePermision(String permission) {
		switch (permission) {
		case "borrar":
			return EnumPermission.DELETE;
		case "crear":
			return EnumPermission.CREATE;
		case "reporte":
			return EnumPermission.REPORT;
		default:
			return EnumPermission.REPORT;
		}
	}

	public String generateReport(String idDataConference) throws IOException {
		return generateTable(tree.getNodeSearch(idDataConference));
	}

	public void addUserTreePermission(String idUser) {
		UserPermission user = new UserPermission(idUser);
		treePermision.getRoot().addChild(new NodeGeneric<PermissionNode>(user));
	}

	public NodeConference getDataIdNodeConference(String idDataConference, String typeNode) {
		switch (typeNode) {
		case "Conference":
			return new Conference(idDataConference);
		case "Attendee":
			return new Attendee(idDataConference);
		case "Lecturer":
			return new Lecturer(idDataConference);
		case "Topic":
			return new Topic(idDataConference);
		default:
			return new Topic(idDataConference);
		}
	}

	public void deleteNodePermission(String idNodePermision) {
		treePermision.deleteNodePermission(new TopicPermision(idNodePermision));
	}

	public void deleteNodeConference(String dataText) {
		Topic dataNode = new Topic(dataText);
		tree.deleteNodeConference(dataNode);
	}

	public ArrayList<String> getListIdUsers() {
		return treePermision.getListIdUsers();
	}

	public NodeGeneric<PermissionNode> getTreePermision() {
		return treePermision.getRoot();
	}

	public NodeGeneric<NodeConference> getTreeConference() {
		return tree.getRoot();
	}

	public void addPermission(String idUser, String idNode, EnumPermission permision) {
		UserPermission dataUser = new UserPermission(idUser);
		TopicPermision dataTopic = new TopicPermision(idNode);
		treePermision.addNodePermissions(dataUser, treePermision.getRoot().getData());
		treePermision.addNodePermissions(dataTopic, dataUser);
		treePermision.addNodePermissions(new Permission(permision, dataTopic.getIdNodePermission()), dataTopic);

	}

	public boolean isPermitedReportNode(NodeConference nodeEvaluate, String idUserClient) {
		return isAvalibleActionUser(idUserClient, nodeEvaluate, EnumPermission.REPORT);
	}

	public boolean addNodeConference(NodeConference nodeEvaluate, String idUserClient, NodeConference nodeChild) {
		if (isAvalibleActionUser(idUserClient, nodeEvaluate, EnumPermission.CREATE)) {
			tree.addNodeConferences(nodeChild, nodeEvaluate);
			return true;
		}
		return false;
	}

	public boolean isAvalibleActionUser(String idUserClient, NodeConference nodeFather, EnumPermission permission) {
		while (nodeFather != null) {
			if (treePermision.theUserHasPermission(new UserPermission(idUserClient),
					new TopicPermision(nodeFather.getNameData()), permission)) {
				return true;
			}
			nodeFather = tree.getParentNodeConference(nodeFather) == null ? null
					: tree.getParentNodeConference(nodeFather).getData();
		}
		return false;
	}

	public boolean deleteNodeConferenceByUser(NodeConference nodeEvaluate, String idUserClient) {
		if (isAvalibleActionUser(idUserClient, nodeEvaluate, EnumPermission.DELETE)) {
			deleteNodeConference(nodeEvaluate.getNameData());
			return true;
		}
		return false;
	}
}
