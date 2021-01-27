package mail.back;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;






public class FolderManagerBIN {

	public FolderManagerBIN() {

	}

	public static void initProgramDirectories()
	{
		new File("./Users").mkdirs();
		new File("./Emails").mkdirs();
		new File("./attachments").mkdir();

		
	}

	public static User getUser(int id)
	{
		List<User> users = getUsers();

		for(int i = 0; i < users.size();i++)
		{
			User user = (User)users.get(i);
			if (user.getID() == id) {
				if(user.foldersNames.size()<4) {
					user.createUserSubDirectory();
				}
				return user;
			}
		}
		return null;
	}

	public static User getUser(String email)
	{
		List<User>users = getUsers();
		for(int i = 0; i < users.size();i++)
		{
			User user = (User)users.get(i);
			String userEmail =  user.getEmail();
			if (userEmail.equals(email))
				return user;
		}
		return null;
	}

	public static void WriteObjectToFile(List<?> serObj, String path) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
        	String JsonStr = gson.toJson(serObj);
			Files.write(Paths.get(path), JsonStr.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }


	public static List<User> ReadUsersFromFile(String path)
	{
		List <User> s = null;
		try
		{

        	String input = Files.readString(Paths.get(path));
        	Type targetClassType = new TypeToken<ArrayList<User>>() { }.getType();
        	ArrayList<User> targetCollection = new Gson().fromJson(input, targetClassType);
        	s = targetCollection;
		}catch(Exception e)
		{
			new File(path);
		}
		return s;
	}

	public static List<Email> ReadEmailsFromFile(String path)
	{
		List<Email> s = null;
		try
		{
			String input = Files.readString(Paths.get(path));
        	Type targetClassType = new TypeToken<ArrayList<Email>>() { }.getType();
        	ArrayList<Email> targetCollection = new Gson().fromJson(input, targetClassType);
        	s = targetCollection;
		}catch(Exception e)
		{
			new File(path);
		}
		return s;
	}



	public static void saveUsersLinkedList(List<User>users)
	{
		WriteObjectToFile(users, "./Users/usersIndex.json");
	}

	/**
	 * @return a DoublyLinkedList of all the users User objects
	 */
	public static List<User> getUsers()
	{
		List<User> d = ReadUsersFromFile("./Users/usersIndex.json");
		List<User> Users=new ArrayList<User>();
		if(d == null)
			return new ArrayList<User>();
		else {
			for(int i=0;i<d.size();i++) {
				Users.add((User) d.get(i));
			}
		}
		return Users;
	}

	public static void addNewUser(User newUser) {
		List<User> users = getUsers();

		//TODO compare every email in the new user with all the emails of the other users
		for (int i = 0;i < users.size();i++) {
			User user = (User) users.get(i);
			if (newUser.getEmail().equals(user.getEmail())){
				return;
			}
		}

		users.add(newUser);
		saveUsersLinkedList(users);
	}

	/**
	 *
	 * @param updatedUser
	 * the user has to exist
	 */
	public static void updateUser (User updatedUser) {
		List<User> users = getUsers();
		for (int i = 0;i < users.size();i++) {
			User user = (User) users.get(i);
			if (updatedUser.getID() == user.getID()) {
				users.set(i, updatedUser);
				saveUsersLinkedList(users);
				return;
			}
		}

	}
	public static void printUsers() {
		List<User>arr = getUsers();

		for (int i = 0;i < arr.size();i++) {
			User user = (User) arr.get(i);
			System.out.println("---------------------------------------");
			System.out.println("Id = " + user.id);

			String email = user.getEmail();
			String password = user.password;
			System.out.println("Email : " + email + ", pass : " + password);
		}
		System.out.println("........................................");
	}

}