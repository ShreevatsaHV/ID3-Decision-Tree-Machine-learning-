import java.util.ArrayList;
import java.util.List;

public class Calculate {
	
	public static List<String> attrMain = new ArrayList<String>();
	
	public static ArrayList<String> attrMainTemp = new ArrayList<String>();
	
	public static int numberInc = 1;
	
	public static int countForAccuracy;
	
	/**
	 * @param noOfPositives
	 * @param noOfNegatives
	 * @return
	 */
	public double getEntropy(int noOfPositives, int noOfNegatives){
		double h;
		int pos=noOfPositives;
		int neg=noOfNegatives;
		double total = (double)pos/(pos + neg);
		if(pos == 0)
			h= 0.0;
		else if(neg == 0)
			h= 0.0;
		else
			h= ((-1*(total)*((double)Math.log10(total)/Math.log10(2)))-((1-total)*((double)Math.log10(1-total)/Math.log10(2))));
		return h;
	}
	
	/**
	 * @param parEntropy
	 * @param neg1
	 * @param pos1
	 * @param entropyLeft
	 * @param entropyRight
	 * @return
	 */
	public double getInfoGain(double parEntropy, int neg1, int pos1, double entropyLeft, double entropyRight){
		double ig;
		double total1 = (double)pos1/(pos1 + neg1);
		if(pos1 == 0)
			ig =((double) parEntropy - ((double)(1-total1)*(entropyLeft)));
		else if(neg1 == 0)
			ig =((double) parEntropy - (((double)total1*entropyRight)));
		else
			ig =((double) parEntropy - (((double)total1*entropyRight)+((double)(1-total1)*(entropyLeft))));
		return ig;
	}
	
	/**
	 * @param data
	 * @param index
	 * @return
	 */
	public double getChildEntropy(List<String[]> data,int index){
		int countNeg=0, countPos=0;
		for(String[] rows: data){
			if(rows.length != 0){
				if(rows[index-1].equals("0")){
					countNeg++;
				}
				else{
					countPos++;
				}
			}
		}
		
		return getEntropy(countPos, countNeg);
	}
	
	/**
	 * @param attr
	 * @param data
	 * @param datal
	 * @param datar
	 * @param node
	 * @param lor
	 * @return
	 */
	public int getMaxInfoGain(ArrayList<String> attr, List<String[]> data, List<String[]> datal, List<String[]> datar, TreeNode node, int lor){
		
		if((!(attr.isEmpty()) && datal.size()!=0 && datar.size()!=0)){
			double[] infoGains = new double[attr.size()-1];
			double maxInfoGain= -1;
			int index = -1;
			for(int i=0; i < attr.size()-1; i++){
				double leftEntropy = getChildEntropy(getData(data,attrMain.indexOf(attr.get(i)))[0],attrMain.size());
				double rightEntropy = getChildEntropy(getData(data,attrMain.indexOf(attr.get(i)))[1],attrMain.size());
				infoGains[i] = getInfoGain(node.getEntropy(),getData(data,attrMain.indexOf(attr.get(i)))[0].size(),getData(data,attrMain.indexOf(attr.get(i)))[1].size(), leftEntropy, rightEntropy);
			}
			
			for (int i = 0; i < infoGains.length; i++) {
				if(infoGains[i]!= 0.0 && maxInfoGain < infoGains[i]){
					maxInfoGain = infoGains[i];
					index = i;
				}
			}
			if(!(maxInfoGain != -1 && maxInfoGain>0))
				return 0;
			node.setName(node.getAttributes().get(index));
			//System.out.println("The new attr size is" + attr.size() +" and the removed atribute is"+ node.getAttributes().get(index));
			datal = getData(data, attrMain.indexOf(attr.get(index)))[0];
			datar = getData(data, attrMain.indexOf(attr.get(index)))[1];
			attr.remove(node.getAttributes().get(index));
			ArrayList<String> tempArrayList = (ArrayList<String>) attr.clone();
			attrMainTemp = (ArrayList<String>) attr.clone();
			TreeNode leftChild = new TreeNode();
			TreeNode rightChild = new TreeNode();
			
			leftChild.setAttributes(tempArrayList);
			leftChild.setNoOfPositives(getData(datal,attrMain.size()-1)[1].size());
			leftChild.setNoOfNegatives(getData(datal,attrMain.size()-1)[0].size());
			leftChild.setEntropy(getChildEntropy(datal,attrMain.size()));
			leftChild.setData(datal);
			leftChild.setNumber(numberInc++);
			leftChild.setSi(0);
			leftChild.setParentNode(node);
			
			rightChild.setAttributes(tempArrayList);
			rightChild.setNoOfPositives(getData(datar,attrMain.size()-1)[1].size());
			rightChild.setNoOfNegatives(getData(datar,attrMain.size()-1)[0].size());
			rightChild.setEntropy(getChildEntropy(datar,attrMain.size()));
			rightChild.setData(datar);
			rightChild.setNumber(numberInc++);
			rightChild.setSi(1);
			rightChild.setParentNode(node);
			
			if(leftChild.noOfPositives!=0 && leftChild.noOfNegatives==0){
				leftChild.setLabel("1");
				node.setLeftChildNode(leftChild);
			}
			else if(leftChild.noOfPositives==0 && leftChild.noOfNegatives!=0){
				leftChild.setLabel("0");
				node.setLeftChildNode(leftChild);
			}
			else{
				if(leftChild.noOfPositives > leftChild.noOfNegatives)
					leftChild.setLabel("1");
				else
					leftChild.setLabel("0");
				node.setLeftChildNode(leftChild);
				getMaxInfoGain(attrMainTemp, datal, getData(datal,attrMain.indexOf(attr.get(index)))[0], getData(datal,attrMain.indexOf(attr.get(index)))[1], leftChild,0);
			}
			
			if(rightChild.noOfPositives!=0 && rightChild.noOfNegatives==0){
				rightChild.setLabel("1");
				node.setRightChildNode(rightChild);
			}
			else if(rightChild.noOfPositives==0 && rightChild.noOfNegatives!=0){
				rightChild.setLabel("0");
				node.setRightChildNode(rightChild);
			}else{
				if(rightChild.noOfPositives > rightChild.noOfNegatives)
					rightChild.setLabel("1");
				else
					rightChild.setLabel("0");
				node.setRightChildNode(rightChild);
				getMaxInfoGain(node.getRightChildNode().getAttributes(), datar, getData(datar,attrMain.indexOf(attr.get(index)))[0], getData(datar,attrMain.indexOf(attr.get(index)))[1], rightChild,1);
			}
		}
		return 0;
	}
	
	/**
	 * @param data
	 * @param index
	 * @return
	 */
	public List[] getData(List<String[]> data, int index){
		List[] lrdata = new List[2];
		List<String[]> datal = new ArrayList<String[]>();
		List<String[]> datar = new ArrayList<String[]>();
		for (String[] leftArray : data) {
			if(leftArray.length != 0){
				if(leftArray[index].equals("0")){					
					datal.add(leftArray);
				}
				else{
					datar.add(leftArray);
				}
			}
		}
		lrdata[0]=datal;
		lrdata[1]=datar;
		return lrdata;
	}
	
	/**
	 * @param node
	 * @param index
	 * @return
	 */
	public int getAccuracy(TreeNode node, int index){
		int iin = index;
		if(node.name == null){
			if(node.getData().size()>1){
				index=+node.getData().size();
				return index;
			}else 
				return 0;
		}else if(node.name != null){
			iin = getAccuracy(node.getLeftChildNode(), index);
		}
		iin = iin+getAccuracy(node.getRightChildNode(), index);
		return iin;
	}
	
	/**
	 * @param node
	 * @param index
	 * @return
	 */
	public int getNodes(TreeNode node, int index){
		int iin = index;
		if(node.name == null){
			if(node.getData().size()>1){
				index=+1;
				return index;
			}else 
				return 0;
		}else if(node != null){
			iin = 1+getNodes(node.getLeftChildNode(), index);
		}
		iin = 1+iin+getNodes(node.getRightChildNode(), index);
		return iin;
	}
	
	/**
	 * @param node
	 * @param index
	 * @return
	 */
	public int getLeafNodes(TreeNode node, int index){
		int iin = index;
		if(node.name == null){
				index=+1;
				return index;
		}else if(node != null){
			iin = getLeafNodes(node.getLeftChildNode(), index);
		}
		iin = iin+getLeafNodes(node.getRightChildNode(), index);
		return iin;
	}
	
	/**
	 * @param node
	 * @param index
	 * @return
	 */
	public int getPrunedNodes(TreeNode node, int index){
		int iin = index;
		if(node == null){
				//index=+1;
				return 0;
		}else if(node != null){
			iin = 1+getPrunedNodes(node.getLeftChildNode(), index);
		}
		iin = iin+getPrunedNodes(node.getRightChildNode(), index);
		return iin;
	}
	
	/**
	 * @param node
	 * @param index
	 * @return
	 */
	public int getPrunedLeafNodes(TreeNode node, int index){
		int iin = index;
		if(node == null || (node.getLeftChildNode()==null && node.getRightChildNode()==null)){
				index=+1;
				return index;
		
		}else if(node.getLeftChildNode()!=null){
			iin = getPrunedLeafNodes(node.getLeftChildNode(), index);
		}
		iin = iin+getPrunedLeafNodes(node.getRightChildNode(), index);
		return iin;
		
	}
	
	/**
	 * @param node
	 * @param index
	 * @return
	 */
	public int getPrunedAccuracy(TreeNode node, int index){
		int iin = index;
		if(node == null || (node.getLeftChildNode()==null && node.getRightChildNode()==null)){
			if(node.getData().size()>1){
				index=+node.getData().size();
				return index;
			}else 
				return 0;
		}else if(node.getLeftChildNode() != null){
			iin = getPrunedAccuracy(node.getLeftChildNode(), index);
		}
		iin = iin+getPrunedAccuracy(node.getRightChildNode(), index);
		return iin;
	}
	
	/**
	 * @param node
	 * @param data
	 */
	public void getAccuracyNew(TreeNode node, String[] data){
		int index = -1;
		if(node.getLeftChildNode()==null && node.getRightChildNode() == null){
			if(node.getLabel().equals(data[data.length-1])){
				countForAccuracy++;
			}
		}
		else{
			for (int i = 0; i < data.length; i++) {
				if(attrMain.get(i) == node.getName())
					index = i;
			}
			if(index!=-1 && data[index].equals("0")){
				getAccuracyNew(node.getLeftChildNode(), data);
			}else if(index!=-1 && data[index].equals("1")){
				getAccuracyNew(node.getRightChildNode(), data);
			}
		}
	}
}
