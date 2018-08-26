
public class Pruning {
	/**
	 * @param node
	 * @param pruneFactor
	 */
	public void doPruning(TreeNode node, double pruneFactor){
		Calculate cal = new Calculate();
		int noOfNodesToPrune = (int)(pruneFactor*cal.getNodes(node, 0));
		int[] r = new int[noOfNodesToPrune];
			for(int i=0;i<noOfNodesToPrune;i++){
				r[i] = (int) (Math.random() * (cal.getNodes(node, 0) - 3)) + 2;
				//System.out.println(r[i]);
			}
		trim(node,r);
	}
	
	/**
	 * @param n
	 * @param r
	 */
	public void trim(TreeNode n,int[] r){
		if (n!=null && n.getLeftChildNode() == null && n.getRightChildNode() == null){
			return;
		}
		else{
			for(int i=0;i<r.length;i++){
				if(n.getNumber() == r[i]){
					n.leftChildNode = null;
					n.setLeftChildNode(null);
					n.rightChildNode = null;
					n.setRightChildNode(null);
					//System.out.println("Working");
				}
				
			}
			if ((n!=null && n.getLeftChildNode() != null && n.getRightChildNode() != null)){
				trim(n.getLeftChildNode(),r);
				trim(n.getRightChildNode(),r);
			}
		}
	}
}
