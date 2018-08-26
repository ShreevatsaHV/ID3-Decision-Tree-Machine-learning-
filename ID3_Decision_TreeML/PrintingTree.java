
public class PrintingTree {

	/**
	 * @param node
	 * @param s
	 * @param value
	 */
	public void print(TreeNode node, String s, int value) {
		if (node == null)
			return;
		
		if(node.getLeftChildNode()==null && node.getRightChildNode()==null){
			System.out.print(" "+node.label);
			return;
		}
		if(node.name!=null){
			System.out.println();
			System.out.print(s + node.name + " = " + value +" : ");
		}
			if(value==0){
				print(node.leftChildNode, s + "| ",0);
				print(node,s,1);
			}else
			{
				print(node.rightChildNode, s + "| ",0);
			}
	}

}
