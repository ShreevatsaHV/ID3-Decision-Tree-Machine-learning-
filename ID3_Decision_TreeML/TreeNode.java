import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	String name;//Node name
	TreeNode parentNode;
	TreeNode leftChildNode;
	TreeNode rightChildNode;
	String label;
	int noOfPositives;
	int noOfNegatives;
	List<String[]> data = new ArrayList<String[]>();
	ArrayList<String> attributes = new ArrayList<String>();
	double entropy;
	int number;
	int si;//splitIndex
	public int getSi() {
		return si;
	}
	public void setSi(int si) {
		this.si = si;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getEntropy() {
		return entropy;
	}
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<String> attributes) {
		this.attributes = attributes;
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}
	public TreeNode getParentNode() {
		return parentNode;
	}
	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}
	public TreeNode getLeftChildNode() {
		return leftChildNode;
	}
	public void setLeftChildNode(TreeNode leftChildNode) {
		this.leftChildNode = leftChildNode;
	}
	public TreeNode getRightChildNode() {
		return rightChildNode;
	}
	public void setRightChildNode(TreeNode rightChildNode) {
		this.rightChildNode = rightChildNode;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getNoOfPositives() {
		return noOfPositives;
	}
	public void setNoOfPositives(int noOfPositives) {
		this.noOfPositives = noOfPositives;
	}
	public int getNoOfNegatives() {
		return noOfNegatives;
	}
	public void setNoOfNegatives(int noOfNegatives) {
		this.noOfNegatives = noOfNegatives;
	}

}
