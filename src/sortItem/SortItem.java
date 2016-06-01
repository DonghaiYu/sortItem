package sortItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SortItem {
	
	public static String usage = "wrong! Usage:java -jar datasort.jar <inputFolder> <outputfolder>";
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

	static Item parseItem(String line, String sep){
		Item item = new Item();
		String[] splits = line.split(sep);
		if (splits.length < 3) {
			System.out.println("xiaoyu 3"+splits.length);
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
				item.setId(null);
				item.setOr(null);
				item.setDate(new Date());
			}
		}
		return item;
	}
	

	
	
	public static String sortClean(String line){
		StringBuilder sb = new StringBuilder();
		List<Item> records = new ArrayList<Item>();
		if(sb.length() > 0) {
			sb = sb.delete(0, sb.length());
		}
		String[] recordString = line.split(",");
		if (recordString.length < 3) {
			//System.out.println("too short");
			return null;
			
		}
		for (int i = 0; i < recordString.length; i++) {
			records.add(parseItem(recordString[i], "\\$"));
		}
		Collections.sort(records);
		
		for (int i = 0; i < records.size(); i++) {
			if (i == 0) {
				sb.append(records.get(i));
			}else {
				sb.append(","+records.get(i));
			}
		}
		records.clear();
		sb.append("\n");
		return sb.toString();
	}
	
	@SuppressWarnings("resource")
	public static void startSort(String inputFolder,String outputFolder) throws IOException{
		
		File inputFol = new File(inputFolder);
		if (!inputFol.exists() || inputFol.isFile()) {
			System.out.println(usage);
			return;
		}
		
		//FileInputStream inputStream = null;
		//FileOutputStream outputStream = null;
		FileWriter fw = null;
		File[] parts = inputFol.listFiles();
		System.out.println("totally "+parts.length +" files");
		for (int i = 0; i < parts.length; i++) {
			System.out.println("file:" + parts[i].getName() +" start");
			if (parts[i].isFile()) {
				try {
					//inputStream = new FileInputStream(parts[i]);
					fw = new FileWriter(outputFolder+"/"+parts[i].getName().replaceAll("clean",	"sorted"));
					//outputStream = new FileOutputStream(outputFolder+"/"+parts[i].getName().replaceAll("clean",	"sorted"));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				Scanner sc = new Scanner(parts[i]);
				//BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				String result = "";
				//line = br.readLine();
				long num = 0;
				while (sc.hasNext()) {
					String string = sc.nextLine();
					num ++;
					if (num%10000 == 0) {
						System.out.println(num);
					}
					
					result = sortClean(string.split("\t")[1]);
					if (result == null) {
						continue;
					}
					try {
						fw.write(result);
						fw.flush();
					} catch (IOException e) {
						System.out.println("write wrong!" );
						return;
					}
				}
				/*while ( line != null ) {
					num ++;
					if (num%10000 == 0) {
						System.out.println(num);
					}
					
					result = sortClean(line.split("\t")[1]);
					if (result == null) {
						continue;
					}
					try {
						outputStream.write(result.getBytes());
						outputStream.flush();
					} catch (IOException e) {
						System.out.println("write wrong!" );
						return;
					}
					line = br.readLine();
				}*/
				try {
					//outputStream.flush();
					//outputStream.close();	
					//br.close();
					//inputStream.close();
					fw.flush();
					fw.close();
					sc.close();
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
			System.out.println("file:" + parts[i].getName() + " end");
			System.gc();
		}
		
			
		
	}
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println(usage);
			return;
		}
		
		startSort(args[0], args[1]);
		
		
	}

}
