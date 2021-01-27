 package mail.back;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mail.back.interfaces.IContact;

public class User implements IContact, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3469096028769736643L;
	int id;
	public String firstName;
	String lastName;

	List <List<Integer>> folders=new ArrayList<List <Integer>>();
	List <String> foldersNames=new ArrayList<String>();
	String email;
	List<Integer> contactsIDs = new ArrayList<Integer>();
	public String password;

	public List<String> getFolderNames(){
		return this.foldersNames;
	}
	public void setNewFolderName(String folderName) {
		if (this.foldersNames.size()<10) {
			foldersNames.add(folderName);
			folders.add(new ArrayList<Integer>());

		}
	}
	public void renameFolder(String folderToBeRenamed,String newName) {
		for(int i=0;i<foldersNames.size();i++) {
		if (this.foldersNames.get(i).equalsIgnoreCase(folderToBeRenamed)) {
			foldersNames.remove(i);
			foldersNames.add(i, newName);
			
			//copy the data from the folders list of doublelinked to the new file and delete the old one

		}
		}
	}
	public void createUserSubDirectory()
	{
		foldersNames.add("Inbox");
		folders.add(0,new ArrayList<Integer>());
		foldersNames.add("Sent");
		folders.add(1,new ArrayList<Integer>());
		foldersNames.add("Trash");
		folders.add(2,new ArrayList<Integer>());
		foldersNames.add("Draft");
		folders.add(3,new ArrayList<Integer>());
	}


	private int calculateNewUserID()
	{
		try
		{
			List<User> users = FolderManagerBIN.getUsers();
			id=users.size();
		}catch(Exception e)
		{
			System.out.println("hello");
			id = 0;
		}
		return id;
	}

	public User(String firstName, String lastName, String email, String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public void saveToFileSystem()
	{
		this.id = calculateNewUserID();
		createUserSubDirectory();
		FolderManagerBIN.addNewUser(this);
	}


	public int getID() {
		return this.id;
	}

	public List<Integer> getContactsIDs() {
		return contactsIDs;
	}
	public boolean idExistInContacts(int id) {
		for(int i=0;i<contactsIDs.size();i++) {
			if(id==(int)contactsIDs.get(i)) {
				return true;
			}
		}
		return false;
	}


	public String getEmail() {
		return email;
	}

	public void addContactID(int id) {
		contactsIDs.add(id);
		FolderManagerBIN.updateUser(this);
	}
	public void printEmails() {

		System.out.println(this.email);
	}
}
