/**
 * 
 */
package com.yahoo.petermwenda83.util.performance.comparator;

import java.util.Comparator;

import com.yahoo.petermwenda83.bean.chat.Chat;


/** 
 * @author peter
 *
 */
public class DateComparator implements Comparator<Chat> {

	/**
	 * Indicates whether some other object is "equal to" this comparator.
	 * 
	 * @param obj
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		return false;				
	}

	@Override
	public int compare(Chat chat1, Chat chat2) {
		return chat1.getDateSent().compareTo(chat2.getDateSent()); 
	}
}
