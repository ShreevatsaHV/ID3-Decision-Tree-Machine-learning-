import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static int count1 , count0 ;
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		/*String trainingfile="C:/Users/Avinash/Documents/Third_Sem/MachineLearning/Assignemnt2/DecisionTree/training_set.csv";//"C:/Users/Avinash/Documents/Third_Sem/MachineLearning/Assignemnt2/DecisionTree/training_set.csv";
		String validationfile="C:/Users/Avinash/Documents/Third_Sem/MachineLearning/Assignemnt2/DecisionTree/validation_set.csv";//"C:/Users/Avinash/Documents/Third_Sem/MachineLearning/Assignemnt2/DecisionTree/validation_set.csv";
		String testingfile="C:/Users/Avinash/Documents/Third_Sem/MachineLearning/Assignemnt2/DecisionTree/test_set.csv";//"C:/Users/Avinash/Documents/Third_Sem/MachineLearning/Assignemnt2/DecisionTree/test_set.csv";*/
		String trainingfile =args[0];
		
		String validationfile=args[1];
				
		String testingfile =args[2];
				
		double pf =Double.parseDouble(args[3]); 
		
		File file = new File(trainingfile);
		File fileValid = new File(validationfile);
		File fileTest = new File(testingfile);
		FileReader fr = new FileReader(file);
		FileReader fv = new FileReader(fileValid);
		FileReader ft = new FileReader(fileTest);
		int countPos = 0, countNeg = 0;
		String[] attr = {};
		ArrayList<String> attr1 = new ArrayList<String>();
		
		BufferedReader buffer = new BufferedReader(fr);
		BufferedReader bufferValid = new BufferedReader(fv);
		BufferedReader bufferTest = new BufferedReader(ft);
		String str = buffer.readLine();
		String strv = bufferValid.readLine();
		String strt = bufferTest.readLine();
		Calculate cal = new Calculate();
		if(str.isEmpty())
		{
			str = buffer.readLine();
		}
		else{
			attr = str.split(",");//taking the attributes names by reading the first line from the file.
			
			for(int i=0;i<str.split(",").length;i++){
				//System.out.println(attr[i]);
				attr1.add(attr[i]);
				Calculate.attrMain.add(attr[i]);
			}
		}
		
		List<String[]> data = new ArrayList<String[]>();
		List<String[]> datal = new ArrayList<String[]>();
		List<String[]> datar = new ArrayList<String[]>();
		List<String[]> datav = new ArrayList<String[]>();
		List<String[]> datat = new ArrayList<String[]>();
		while(((str=buffer.readLine())!=null) || ((str=buffer.readLine())!=null))
		{
			String[] rowStr = str.split(",");//taking the attributes names by reading the first line from the file.
			if(rowStr.length != 0){
				if(rowStr[rowStr.length-1].equals("0")){
					countNeg++;
					datal.add(rowStr);
				}
				else{
					countPos++;
					datar.add(rowStr);
				}
			}
			
			data.add(rowStr);
		}
		
		while(((strv=bufferValid.readLine())!=null) || ((strv=bufferValid.readLine())!=null))
		{
			String[] rowStrv = strv.split(",");//taking the attributes names by reading the first line from the file.
			datav.add(rowStrv);
		}
		while(((strt=bufferTest.readLine())!=null) || ((strt=bufferTest.readLine())!=null))
		{
			String[] rowStrt = strt.split(",");//taking the attributes names by reading the first line from the file.
			datat.add(rowStrt);
		}
		double parEntropy = cal.getEntropy(countPos, countNeg);
		String[] attrArr = new String[1];
		attrArr[0] = "Class";
		TreeNode root = new TreeNode();
		root.data = data;
		root.noOfNegatives = countNeg;
		root.noOfPositives = countPos;
		root.setEntropy(parEntropy);
		root.setAttributes(attr1);
		root.setNumber(Calculate.numberInc++);
		cal.getMaxInfoGain(attr1,data,datal,datar,root,-1);
		
		PrintingTree p = new PrintingTree();
		p.print(root,"",0);
		
		System.out.println();
		System.out.println();
		System.out.println("Pre-Pruned Accuracy");
		System.out.println("--------------------------------");
		System.out.println("Number of Training instances = "+root.getData().size());
		int s = Calculate.attrMain.size();
		System.out.println("Number of Training attributes = "+(s-1));
		System.out.println("Total Number of Nodes in the Tree = "+(cal.getNodes(root, 0)-1));
		System.out.println("Number of Leaf Nodes in the Tree = "+(cal.getLeafNodes(root,0)));
		for(String[] stri : data){
			cal.getAccuracyNew(root, stri);
		}
		System.out.println("Accuracy of the model on Training dataset = "+((double)Calculate.countForAccuracy/(data.size()))*100+"%");//val/root.getData().size()
		System.out.println();
		Calculate.countForAccuracy = 0;
		for(String[] stri : datav){
			cal.getAccuracyNew(root, stri);
		}
		System.out.println("Number of Validation instances = "+datav.size());
		s = datav.get(0).length;
		System.out.println("Number of Validation attributes = "+(s-1));
		System.out.println("Accuracy of the model on the validation dataset = "+((double)Calculate.countForAccuracy/(datav.size()))*100+"%");
		System.out.println();
		Calculate.countForAccuracy = 0;
		for(String[] stri : datat){
			cal.getAccuracyNew(root, stri);
		}
		System.out.println("Number of Testing instances = "+datat.size());
		s = datat.get(0).length;
		System.out.println("Number of Testing attributes = "+(s-1));
		System.out.println("Accuracy of the model on testing dataset = "+((double)Calculate.countForAccuracy/(datat.size()))*100+"%");
		System.out.println();
		Pruning prune = new Pruning();
		prune.doPruning(root, pf);
		System.out.println();
		System.out.println("Post-Pruned Accuracy");
		System.out.println("--------------------------------");
		System.out.println("Number of Training instances = "+root.getData().size());
		int t = Calculate.attrMain.size();
		System.out.println("Number of Training attributes = "+(t-1));
		System.out.println("Number of Nodes in the Tree = "+(cal.getPrunedNodes(root, 0)));
		System.out.println("Number of Leaf Nodes in the Tree = "+(cal.getPrunedLeafNodes(root,0)));
		Calculate.countForAccuracy = 0;
		for(String[] stri : data){
			cal.getAccuracyNew(root, stri);
		}
		System.out.println("The accuracy for Training dataset = "+((double)Calculate.countForAccuracy/(data.size()))*100+"%");
		System.out.println();
		Calculate.countForAccuracy = 0;
		for(String[] stri : datav){
			cal.getAccuracyNew(root, stri);
		}
		System.out.println("Number of Validation instances = "+datav.size());
		s = datav.get(0).length;
		System.out.println("Number of Validation attributes = "+(s-1));
		System.out.println("The accuracy for Validation dataset = "+((double)Calculate.countForAccuracy/(datav.size()))*100+"%");
		System.out.println();
		Calculate.countForAccuracy = 0;
		for(String[] stri : datat){
			cal.getAccuracyNew(root, stri);
		}
		System.out.println("Number of Testing instances = "+datat.size());
		s = datav.get(0).length;
		System.out.println("Number of Testing attributes = "+(s-1));
		System.out.println("The accuracy for Testing dataset = "+((double)Calculate.countForAccuracy/(datat.size()))*100+"%");
		System.out.println();
	}
}