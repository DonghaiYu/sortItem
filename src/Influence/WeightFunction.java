package Influence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightFunction {
	public static double getWeight(String[] ts) {
		double quality = 0;
		List<Long> tsl = new ArrayList<Long>();
		for (int i = 0; i < ts.length; i++) {
			Long time = Long.parseLong(ts[i]);
			if (time != null && time < Tools.MIN_TRAVEL_TIME) {
				tsl.add(time);
			}
		}
		
		if (tsl.size() == 0) {
			return 0;
		}
		Collections.sort(tsl);
		
		int endIndex = (int) (tsl.size() * Tools.USEFUL_RATE);
		Map<Long, Integer> time_num = new HashMap<Long, Integer>();
		
		endIndex = endIndex<1?1:endIndex;
		
		for (int i = 0; i < endIndex; i++) {
			long t = tsl.get(i);
			if (time_num.containsKey(t)) {
				int num = time_num.get(t);
				num ++;
				time_num.put(t, num);
			}else {
				time_num.put(t, 1);
			}
		}
		for (long ttime : time_num.keySet()) {
			int num = time_num.get(ttime);
			float rate = (float)num / (float)endIndex;
			float w = weightFunction(ttime);
			quality += (rate * w);
		}

		return quality * endIndex;
	}
	
	public static float weightFunction(long t) {
		float weight = 0;
		if (t < Tools.PERIOD) {
			weight = (float) Math.sqrt((float)t / (float)Tools.PERIOD);
		}else {
			weight = (float)Tools.PERIOD / (float)t;
		}
		return weight;
	}

}
