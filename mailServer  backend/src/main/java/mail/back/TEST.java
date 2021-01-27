package mail.back;

public class TEST {

	public static void main(String[] args) {


		App app=new App();
		//app.signup(testUser);
		 //User test=FolderManagerBIN.getUser( "youssef@yahoo.com");
		 User testContact=new User("youssef","akl" , "youssefAkl@yahoo.com",  "fuckfuck");
		 User testContact2=new User("youssef","hussein" , "youssefHussein@yahoo.com",  "fuckfuck");
		 User testContact3=new User("ahmed","hazem" , "ahmedHazem@yahoo.com",  "fuckfuck");
		 testContact.saveToFileSystem();
		 testContact2.saveToFileSystem();
		 testContact3.saveToFileSystem();
		 app.addContact(2, "youssefAkl@yahoo.com");
	}

}
