package Influence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sortItem.Item;

public class GetInf {

public static String usage = "wrong! Usage:java -jar getInf.jar <inputFolder> <outputfolder>";
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	public volatile static Map<String, Map<String, List<Long>>> influences = new HashMap<String, Map<String,List<Long>>>();
	

	static Item parseItem(String line, String sep){
		Item item = new Item();
		String[] splits = line.split(sep);
		if (splits.length < 3) {
			System.out.println("less than 3 records! "+splits.length);
			item.setId(null);
			item.setOr(null);
			item.setDate(new Date());
		}else {
			try {
				item.setId(splits[0]);
				item.setOr(splits[1]);
				item.setDate(sdf.parse(splits[2]));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return item;
	}
	

	
	
	public static int getInfluences(String line, Map<String, Map<String, List<Long>>> influences){
		StringBuilder sb = new StringBuilder();
		List<Item> records = new ArrayList<Item>();
		if(sb.length() > 0) {
			sb = sb.delete(0, sb.length());
		}
		String[] recordString = line.split(",");
		if (recordString.length < 3) {
			//System.out.println("short trajectory");
			//System.out.println(line);
			return 0;
			
		}
		
		for (int i = 0; i < recordString.length; i++) {
			records.add(parseItem(recordString[i], "\\$"));
		}
		Collections.sort(records);
		int a = 0;
		for (int i = 0; i < records.size()-1; i++) {
			Item source = records.get(i);
			Item target = records.get(i+1);
			long s = records.get(i).getDate().getTime();
			long t = records.get(i+1).getDate().getTime();
			long sub = (t - s) / 60000;
			//System.out.println(sub);
			if (influences.get(target.toString()) == null ) {
				List<Long> list = new ArrayList<Long>();
				list.add(sub);
				Map<String, List<Long>> influence = new HashMap<String, List<Long>>(); 
				influence.put(source.toString(), list);
				influences.put(target.toString(), influence );
				a++;
			}else if (influences.get(target.toString()).get(source.toString()) == null) {
				List<Long> list = new ArrayList<Long>();
				list.add(sub);
	
				influences.get(target.toString()).put(source.toString(), list);
				a++;
			}else {
				
				influences.get(target.toString()).get(source.toString()).add(sub);
				//a++;
			}
		}

		return a;
	}
	
	@SuppressWarnings("resource")
	public static void startSort(String inputFolder,String outputFolder) throws IOException{
		
		File inputFol = new File(inputFolder);
		if (!inputFol.exists() || inputFol.isFile()) {
			System.out.println(usage);
			return;
		}
		
		
		FileWriter fw = null;
		String fwName =  outputFolder+"/influences";
		try {
			
			fw = new FileWriter(fwName);
			
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
			System.out.println("can't write to file: " + fwName);
			
		}
		
		File[] parts = inputFol.listFiles();
		System.out.println("totally "+parts.length +" source files");
		for (int i = 0; i < parts.length; i++) {
			System.out.println("processing file:" + parts[i].getName() +" start");
			if (parts[i].isFile()) {
				
				
				
				Scanner sc = new Scanner(parts[i]);

				long num = 0;

				while (sc.hasNext()) {
					String string = sc.nextLine();
					num ++;
					if (num%10000 == 0) {
						System.out.println(num);
					}
					
					getInfluences(string.split("\t")[1],influences);

				}				
			}
			System.out.println("processing file:" + parts[i].getName() + " end");
			//System.gc();
		}
	
		saveInfluences(fw,influences);
		
	}
	
	public static void saveInfluences(FileWriter fw, Map<String, Map<String, List<Long>>> influences) {
		System.out.println("writing...");
		for(String target : influences.keySet()) {
			for(String source : influences.get(target).keySet()) {
								
				List<Long> Ts = influences.get(target).get(source);
				
				try {
					//System.out.println(source + "->" + target + ":");
					fw.write(source + "->" + target + ":");
					for(Long t : Ts) {
						fw.write(t+",");
					}					
					fw.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("writing finished...");
	}
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println(usage);
			return;
		}
		
		startSort(args[0], args[1]);
		//startSort("input","out");
		
	}

}
