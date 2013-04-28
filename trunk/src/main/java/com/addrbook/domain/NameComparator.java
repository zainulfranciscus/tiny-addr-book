package com.addrbook.domain;

import java.io.Serializable;

import com.google.common.collect.Ordering;

public class NameComparator extends Ordering<String> implements Serializable {
	/**
	 * A version id for this class
	 */
	private static final long serialVersionUID = 1L;

	private NameComparator(){}
	
	@Override
	public int compare(String left, String right) {
		
		return left.toLowerCase().compareTo(right.toLowerCase());
	}
	
	public static Ordering<String> descOrder(){
		return new NameComparator().reverse();
	}
	
	public static Ordering<String> ascOrder(){
		return new NameComparator();
	}
}