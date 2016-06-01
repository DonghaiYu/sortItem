package sortItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item implements Comparable<Item>{
	private String id;
	private String or;
	private Date date;

	
	public Item() {
		//System.out.println(line+sep);
		this.id = null;
		this.or = null;
		this.date = new Date();
	}
	
	public Item(String id, String or, Date date) {
		super();
		this.id = id;
		this.or = or;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOr() {
		return or;
	}

	public void setOr(String or) {
		this.or = or;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Item o) {
		return this.date.compareTo(o.date);
	}
	
	@Override
	public String toString() {
		return this.id + "_" + this.or;
	}
	public static void main(String[] args) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		List<Integer> a = new ArrayList<Integer>();
		a.add(1);
		a.add(3);
		System.out.println(String.join(",", a.toArray().toString()));
		System.out.println(sdf.parse("2015-06-05 19:26:21 979").toString());
		System.out.println("1000009746	64$02$2015-06-05 09:26:21 979,667$01$2015-06-07 16:14:03 612".split("\t")[1]);
	}

	
}
