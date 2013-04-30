package com.addrbook.domain;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.Ordering;

/**
 * A comparator used to sort names in an address book
 * 
 * @author Zainul Franciscus
 * 
 */
public class NameComparator extends Ordering<String> implements Serializable {
	/**
	 * A version id for this class
	 */
	private static final long serialVersionUID = 1L;

	private NameComparator() {
	}

	/**
	 * Compare two names ascendingly
	 */
	@Override
	public int compare(String left, String right) {

		checkNotNull(left,"Name provided cannot be null");
		checkNotNull(right, "Name provided cannot be null");
		return left.toLowerCase().compareTo(right.toLowerCase());
	}

	/**
	 * @return a comparator that can be used to sort names in descending order
	 */
	public static Ordering<String> descOrder() {
		return new NameComparator().reverse();
	}

	/**
	 * @return a comparator that can be used to sort names ascendingly
	 */
	public static Ordering<String> ascOrder() {
		return new NameComparator();
	}
}