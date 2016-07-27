/**
 * 
 */
package com.yahoo.petermwenda83.persistence.chat;

import java.util.List;

import com.yahoo.petermwenda83.bean.chat.Chat;

/**
 * @author peter
 *
 */
public interface SchoolChatDAO {
	/**
	 * 
	 * @param senderUuid
	 * @param receiverUuid
	 * @return
	 */
	public Chat getChat(String senderUuid,String receiverUuid);
	
	/**
	 * 
	 * @param senderUuid
	 * @param receiverUuid
	 * @return
	 */
	public List<Chat> getChatList(Chat chat);
	/**
	 * 
	 * @param senderUuid
	 * @return
	 */
	public List<Chat> getChatList(String senderUuid);
	/**
	 * 
	 * @param chat
	 * @return
	 */
	public boolean putChat(Chat chat);
	/**
	 * 
	 * @param chat
	 * @return
	 */
	public boolean deleteChat(Chat chat);

}
