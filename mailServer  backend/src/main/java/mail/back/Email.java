package mail.back;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import mail.back.interfaces.IMail;
@Component
public class Email implements IMail, Serializable
{
	public int id;
	public int priority;
	public String subject;
	public String body;
	public String date;
	
	public int senderID;
	public String senderEmail;
	public int receiverID;
	public String receiverEmail;
	public List<String> attachments;

	/**
	 *
	 * @param subject
	 * @param body
	 * @param senderID
	 * @param senderEmail
	 * @param receiverID
	 * @param receiverEmail
	 * @param numOfAttachements
	 * @param priority
	 */
	

	public String getSubject(){
		return subject;
	}

	public String getSender(){
		return senderEmail;
	}
	public String getReceiver(){
		return receiverEmail;
	}
	public String getBody(){
		return body;
	}
	public String getDate(){
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		String strDate = dateFormat.format(date);  
		return strDate;
	}
	public List<String> getAttachments() {
		return attachments;
	}

	/*public static void DeleteTrash(int userID)
	{
		String folders[] = Folder.listFolders(userID);
		for(String folder: folders)
		{
			String path = "./Users/" + userID + "/" + folder + "/index.json";
			List<Email> emails = (List<Email>)FolderManagerBIN.ReadEmailsFromFile(path);
			for(int i = 0; i < emails.size();i++)
			{
				Email m = (Email)emails.get(i);
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime emailDate = m.date;
				if(now.compareTo(emailDate.plusDays(30)) >= 0)
				{
					String emailPath = "./Users/" + userID + "/" + folder + "/" + m.id;
					File index = new File(emailPath);
					String files[] = index.list();
					for(String s: files)
					{
						File currentFile = new File(index.getPath(), s);
						currentFile.delete();
					}
					index.delete();
					emails.remove(i--);
				}
			}
			FolderManagerBIN.WriteObjectToFile(emails, path);
		}
	}
*/

	/*
	 * Send Logic: call this function with the sender id and "Sent" folder
	 * Receive Logic: call this function with the Receiver id and "inbox" folder
	 */


	int calculateEmailID()
	{

		int id;
		//User user = FolderManagerBIN.getUser(userID);
		List<Email> emails = readEmails();


		for (int i = 0;i < emails.size();i++)
			System.out.print(((Email)emails.get(i)).subject);



		id=emails.size();

		System.out.println(id);
		return id;
	}

	public void saveEmail()
	{
		List<Email> emails = readEmails();
		//ID the email object

		this.id = calculateEmailID();
		this.receiverID=FolderManagerBIN.getUser(this.receiverEmail).id;
		this.senderEmail=FolderManagerBIN.getUser(senderID).email;
		this.date=this.getDate();

		User sender = FolderManagerBIN.getUser(senderID);
		User receiver = FolderManagerBIN.getUser(receiverID);
		sender.folders.get(1).add(this.id);
		receiver.folders.get(0).add(this.id);
		
		emails.add(this);
		String path = "./Emails/emails.json";
		FolderManagerBIN.WriteObjectToFile(emails, path);
		FolderManagerBIN.updateUser(sender);
		FolderManagerBIN.updateUser(receiver);

	}


	public void saveDraft()
	{
		List<Email> emails = Email.readEmails();

		for (int i = 0;i < emails.size();i++)
			System.out.print(((Email)emails.get(i)).subject);

		//ID the email object

		this.id = calculateEmailID();
		if(receiverEmail != null)
		this.receiverID=FolderManagerBIN.getUser(this.receiverEmail).id;
		this.senderEmail=FolderManagerBIN.getUser(senderID).email;
		User sender = FolderManagerBIN.getUser(senderID);
		this.date=this.getDate();
		sender.folders.get(3).add(this.id);
		emails.add(this);
		String path = "./Emails/emails.json";
		FolderManagerBIN.WriteObjectToFile(emails, path);
		System.out.println(sender.folders.get(3).get(0));
		FolderManagerBIN.updateUser(sender);

	}


	/**
	 *
	 * @param userID
	 * @param Folder object, its type will be used (inbox, sent, trash ..)
	 * @return DoubleLinkedList of the Email objects inside the folder
	 */
	public static List<Email> readEmails()
	{

		String path = "./Emails/emails.json";
		List<Email> emails = FolderManagerBIN.ReadEmailsFromFile(path);
		if (emails == null)
			return new ArrayList<Email>();

		return emails;
	}



}
