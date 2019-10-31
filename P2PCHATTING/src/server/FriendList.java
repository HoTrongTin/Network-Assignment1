package server;

import java.util.ArrayList;
import java.util.HashMap;

import basic.Friend;

public class FriendList {
	private ArrayList<Friend> list ;
	private HashMap<String,Boolean>  markOnline;
	public FriendList(){
		list = new ArrayList<Friend>();
		markOnline = new HashMap<String,Boolean>();
	}
	public boolean getStatus(String username)
	{
		return markOnline.get(username);
	}
	public int getFriendCount()
	{
		return list.size();
	}
	public void addFriend(Friend friend)
	{
		
		if (friend != null)
		{ 
			for (int i = 0; i < list.size();i++)
			{
				if (friend.getAccountName().matches(list.get(i).getAccountName()))
					return;
			}
			list.add(friend);
			markOnline.put(friend.getAccountName(),true);
		}
	}
	public ArrayList<Friend> getFriendList()
	{
		return list;
	}
	public void resetStatus()
	{
		for (int i = 0; i < list.size(); i++)
		{
			markOnline.replace(list.get(i).getAccountName(), false);	
		}
	}
	public void keepAlive(String username,int status)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).getAccountName().matches(username) == true)
			{
				if (status == 0)
					markOnline.replace(username, true);
				else
					markOnline.replace(username, false);
			}
		}
	}
	public void refeshList()
	{
		int i = 0;
		while( i < list.size())
		{
			if (markOnline.get(list.get(i).getAccountName()) == false)
				list.remove(i);
			else
				i++;
		}
	}
	public void offlineAFriend(String username)
	{
		for (int i = 0 ; i < list.size();i++)
		{
			if (list.get(i).getAccountName().matches(username))
			{
				list.remove(i);
				markOnline.remove(username);
				return;
			}
			
		}
	}
}
