package mail.back;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import mail.back.Contact;
import mail.back.interfaces.IContact;
import mail.back.interfaces.IFolder;
import mail.back.interfaces.ILinkedList;
import mail.back.interfaces.IMail;
import mail.back.interfaces.ISort;
public class App implements mail.back.interfaces.IApp {



	public Folder folder;
	LoggedInUser LoggedInUser=new LoggedInUser();
	public List<Email> currentlyLoadedEmails=new ArrayList<Email>();
	public List<Contact> currentlyLoadedContacts=new ArrayList<Contact>();
	public sortComparator sort = new sortComparator(6) ;

	public App() {
		folder = new Folder("inbox");

	}


	@Override
	public int signin(String email, String password) {
		if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			return -1;
		}
		User user = FolderManagerBIN.getUser(email);
		if(user == null) {
			return -1;
		}


		if(!(user.password).equals(password)) {
			return -1;
		}
		else {
			LoggedInUser.addLoggedInUser(user,user.id);
			return user.id;
		}
	}


	@Override
	public boolean signup(IContact contact) {
		User user = (User)contact;
		String email = user.getEmail();


		if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
		{
			return false;
		}else if(FolderManagerBIN.getUser(email)!=null)
		{
			return false;
		}

		else if(user.password.length() < 8)
		{
			return false;
		}
		else
		{
			user.saveToFileSystem();
		}
		return true;
	}

	public List<Contact> sortContact(int userID,ISort sort){
		Sorting S=new Sorting();
		 User user = FolderManagerBIN.getUser(userID);
		 List<Contact> returned=new ArrayList<Contact>();
		  for ( int i=0; i<user.contactsIDs.size(); i++){
			User temp_1 = FolderManagerBIN.getUser((int) user.contactsIDs.get(i));
			Contact temp_2 = new Contact();
					temp_2.setContact(temp_1.firstName, temp_1.lastName, temp_1.email);
			currentlyLoadedContacts.add(temp_2);
		  }
		S.quickSortContact(currentlyLoadedContacts, sort);
		for(int i=0;i<currentlyLoadedContacts.size();i++) {
			returned.add((Contact) currentlyLoadedContacts.get(i));
		}
		return returned;
	}
	@Override
	public List<Email> setViewingOptions( int userId, String folderName, int page, ISort sort) {
		currentlyLoadedEmails = getUserEmails(userId,folderName);
		Sorting s_2 = new Sorting();

		
		s_2.quickSortEmail(currentlyLoadedEmails, sort);
		

		return listEmailsForView(page,currentlyLoadedEmails);
	}

	@Override
	public List<Email> listEmailsForView(int page, List<Email> emails) {
		List<Email> return_emails = new ArrayList<Email>();
		for(int i = 0;i < 10 && 10*page + i < currentlyLoadedEmails.size();i++)
			return_emails.add( (Email)emails.get(10*page+i) ) ;
		for(int i=0;i<return_emails.size();i++) {
			System.out.println(return_emails.get(i));
		}
		return return_emails;
	}
	// this requires updating the index file from outside this function in the source folder

	@Override
	public List<Email> getUserEmails(int userId,String fromFile){
		List<Email> returned=new ArrayList<Email>();
		currentlyLoadedEmails=  Email.readEmails();
		User currentUser=FolderManagerBIN.getUser(userId);
		int fileIndex=currentUser.foldersNames.indexOf(fromFile);
		List<Integer> fileEmails=currentUser.folders.get(fileIndex);
		for(int i=0;i<fileEmails.size();i++) {
			for(int j=0;j<currentlyLoadedEmails.size();j++) {

				long temp= Math.round((double)fileEmails.get(i));
				Email tempEmail = currentlyLoadedEmails.get(j);

				if(temp==tempEmail.id) {
					returned.add(tempEmail);
				}
			}
		}
		return returned;
	}
	public List<Email> loadDraftList(int id )
	{
		List<Email> emails=Email.readEmails();
		User temp=FolderManagerBIN.getUser(id);
		List<Integer> draftIDs = temp.folders.get(3);
		List<Email> draft=new ArrayList<Email>();

		for (int i = 0;i < emails.size();i++)
			System.out.print(((Email)emails.get(i)).subject);

		for (int i = 0;i < draftIDs.size();i++){
			for (int j = 0;j < emails.size();j++){
				Email tempEmail=(Email) emails.get(j);
				long draftID= Math.round((double)draftIDs.get(i));
				if(draftID==tempEmail.id){
					draft.add(tempEmail);
				}
			}

		}
		//ID the email object
		return draft;
	}
	public List<Contact> addContact(int userId, String email){
		List<Contact> returned=new ArrayList<Contact>();
		User temp =FolderManagerBIN.getUser(userId);
		List<Integer> contactsIds= temp.getContactsIDs();
		List<User> users=FolderManagerBIN.getUsers();
		System.out.println(email);
		for(int i=0;i<users.size();i++){
			User tempUser=(User) users.get(i);
			if(tempUser.getEmail().equals(email)){
				contactsIds.add(tempUser.id);
			}
		}
		FolderManagerBIN.updateUser(temp);
		for(int i=0;i<contactsIds.size();i++){
			User tempU=FolderManagerBIN.getUser((int)contactsIds.get(i));
			Contact tempContact=new Contact();
					tempContact.setContact(tempU.firstName, tempU.lastName, tempU.getEmail());
			returned.add(tempContact);
		}
		return returned;

	}
	public void deleteEmails(List<Integer> emailsIDs,String from,int id){
		User updatedUser=FolderManagerBIN.getUser(id);
		List <List<Integer>> folders = updatedUser.folders;
		List <String> foldersNames = updatedUser.foldersNames;
		int fromID = foldersNames.indexOf(from);
		List<Integer> temp = folders.get(fromID);
		int index=-1;
		for(int i = 0; i < emailsIDs.size();i++){
			for(int j = 0 ; j<temp.size();j++) {
				if(temp.get(j)==emailsIDs.get(i))
					index = j;
			}
			folders.get(fromID).remove(index);
		}
		
		updatedUser.folders=folders;
			FolderManagerBIN.updateUser(updatedUser);

	}
	public void deletefolder(String from,int id){
		User updatedUser=FolderManagerBIN.getUser(id);
		List <List<Integer>> folders = FolderManagerBIN.getUser(id).folders;
		List <String> foldersNames = FolderManagerBIN.getUser(id).foldersNames;
		int fromID = foldersNames.indexOf(from);
		List<Integer> temp = folders.get(fromID);
			for(int j = 0 ; j<temp.size();j++) {
				temp.remove(j);
			}
			foldersNames.remove(from);
			updatedUser.foldersNames=foldersNames;
			FolderManagerBIN.updateUser(updatedUser);

	}

	public void moveEmails(List<Integer> emailsIDs,String from,String to,int id) {
		User updatedUser=FolderManagerBIN.getUser(id);
		List <List<Integer>> folders = updatedUser.folders;
		List <String> foldersNames = updatedUser.foldersNames;
		int fromID = foldersNames.indexOf(from);
		int toID = foldersNames.indexOf(to);
		List<Integer> temp = folders.get(fromID);
		for(int i = 0; i < emailsIDs.size();i++)
		{
			int index=-1;
			for(int j = 0 ; j<temp.size();j++) {
				if(temp.get(j)==emailsIDs.get(i))
					index = j;
			}

		folders.get(fromID).remove(index);
		folders.get(toID).add(emailsIDs.get(i));

		}
		FolderManagerBIN.updateUser(updatedUser);

		//Email.saveEmail(); we need to implement new fn to save the linked list after deleting them from the folders linked list
	}
	public List<Contact>searchContacts(int userID,String contactName,String SearchIn) {
		 User user = FolderManagerBIN.getUser(userID);
		  List<Contact> contacts = new ArrayList();
		  for ( int i=0; i<user.contactsIDs.size(); i++){
			User temp_1 = FolderManagerBIN.getUser((int) user.contactsIDs.get(i));
			Contact temp_2=new Contact();
			temp_2.setContact(temp_1.firstName, temp_1.lastName, temp_1.email);
			contacts.add(temp_2);
		  }
		  EmailSearchCriteria C=new EmailSearchCriteria();
		 List <Contact> returned=new ArrayList<Contact>();
		  if(SearchIn.equals("Email")) {
		   returned=C.meetCriteria(contacts, contactName);
		 }
		 else if(SearchIn.equals("firstName")) {
			 returned=C.meetCriteriaFirstName(contacts, contactName);
		 }
		  return returned;

	}
	public List<Email>searchEmails(int userID,String searchType,String searchText,String folderName) {
		User user = FolderManagerBIN.getUser(userID);
		List<Email> emails=getUserEmails(userID,folderName);
		List<Email> returned =new ArrayList<Email>();
		switch(searchType) {
		case "date":
			CriteriaDate D=new CriteriaDate();
			returned=D.meetCriteria(emails, searchText);
			break;
		case "reciever":
			CriteriaReciever R=new CriteriaReciever();
			returned=R.meetCriteria(emails, searchText);
			break;
		case "sender":
			CriteriaSender S=new CriteriaSender();
			returned=S.meetCriteria(emails, searchText);
			break;
		case "subject":
			CriteriaSubject SB=new CriteriaSubject();
			returned=SB.meetCriteria(emails, searchText);
			break;
		}

		  return returned;

	}

	@Override
	public boolean compose(IMail email) {

		return false;
	}

	@Override
	public void deleteEmails(mail.back.interfaces.ILinkedList mails) {
		// TODO Auto-generated method stub

	}


	
}
