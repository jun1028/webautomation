package com.broada.spring.core;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class providerComparator implements Comparator<Map.Entry<?, ?>> {

	@Override
	public int compare(Entry<?, ?> o1, Entry<?, ?> o2) {
		String s1 = (String) o1.getKey();
		String s2 = (String) o2.getKey();
		int result = 0;
		if (s1 == null && s2 == null) {
			return 0;
		} else if (s1 == null && s2 != null) {
			result = -1;
		} else if (s1 != null && s2 == null) {
			result = 1;
		} else {
			result = s1.compareTo(s2);
		}
		return result;
	}

}
