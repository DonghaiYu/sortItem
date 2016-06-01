package Influence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GetWeight {

	public static void main(String[] args) throws IOException {
		
		List<InfPair> pairs = new ArrayList<InfPair>();
		Scanner sc = new Scanner(new File(args[0]));
		System.out.println("Influence Pairs Initializing...");
		long lineNum = 0;
		while (sc.hasNext()) {
			lineNum ++;
			if (lineNum % 1000 == 0) {
				System.out.println(lineNum/1000+"k Influence Pairs");
			}
			String s = sc.nextLine();
			InfPair ip = new InfPair(s);
			pairs.add(ip);
		}
		sc.close();		
		
		Map<String, List<InfPair>> weightM = new HashMap<String, List<InfPair>>();
		
		System.out.println("Calculating weights...");
		for (InfPair infPair : pairs) {
			
			if(weightM.get(infPair.getTarget()) == null) {
				List<InfPair> sources = new ArrayList<InfPair>();
				sources.add(infPair);
				weightM.put(infPair.getTarget(), sources);
			}else {
				weightM.get(infPair.getTarget()).add(infPair);
			}			
		}
		
		//将影响源按照影响权值排序并保存
		FileWriter fw = new FileWriter(new File(args[1]));
		
		System.out.println("Getting groups which the first weight is bigger than 100k...");
		for (String target : weightM.keySet()) {
			
			Collections.sort(weightM.get(target));
			
			if(weightM.get(target).get(0).getWeight() > 100000) {
				
				fw.write(target+",");
				for (int i = 0; i < 10 && i < weightM.get(target).size(); i++) {
					fw.write(weightM.get(target).get(i).getSource()+",");
				}
				fw.write("\n");
			}
						
		}		
		fw.flush();
		fw.close();
	}

}
