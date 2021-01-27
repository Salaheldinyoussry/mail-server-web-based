package mail.back;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import mail.back.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
public class Controller {
	static App app = new App();	
	static LoggedInUser US=new LoggedInUser();
	@PostMapping ("compose")//done
	public void compose(@RequestBody Email e ) {
		Email temp = e;
		temp.saveEmail();
	}
	@PostMapping ("compose/saveDraft")//done
	public void saveDraft(@RequestBody Email e ) {
		Email temp = e;
		
		temp.saveDraft();
	}
	@GetMapping("compose/loadDraftList/{userId}")//done
	public List<Email> loadDraftList(@PathVariable int userId) {
		//load dratfs lists
		return app.loadDraftList(userId);
	}
	@GetMapping("Contacts/addContact/{userId}/{email}")
	public List<Contact> addContact(@PathVariable int userId,@PathVariable String email) {
		
		return app.addContact(userId, email);
	}
	@GetMapping("compose/loadDraft/{userId}/{draftId}")//done
	public Email loadDraft(@PathVariable int userId, @PathVariable int draftId ) {
		List<Email> draftList = app.loadDraftList(userId);
		Email draft = draftList.get(0);
		for ( int i=0; i<draftList.size(); i++){
			if ( draftList.get(i).id == draftId)  
				draft = draftList.get(i);
		}
		//load specific dratf mail
		return draft;
	}
	@GetMapping ("signUP/{first_Name}/{last_Name}/{Email}/{pass}")
	public boolean SignUP(@PathVariable String first_Name, @PathVariable String last_Name, @PathVariable String Email, @PathVariable String pass) {
		User user = new User(first_Name, first_Name, Email, pass);
		if ( app.signup(user) )
			return true;
		else
			return false;
	}

	@GetMapping("signIn/{email}/{pass}")
	public int getIDAndSignIN(@PathVariable String email,@PathVariable String pass) {
		/// return error if user not registereddsd
		int signOutput= app.signin(email, pass);
		
		return signOutput;
	}	
	@GetMapping("userState/{userID}")
	public int isUserSignedIn(@PathVariable int userID) {
		List<User> loggedInUsers = LoggedInUser.getLoggedInUsers();
		for ( int i=0; i<loggedInUsers.size(); i++){
			if ( userID == loggedInUsers.get(i).getID() )
			return 1;
		}
		return 0;
		/// return yes if signed
		/// else  return no
	}	
	
	
	@GetMapping("getEmails/{userID}/{folderName}/{pageNo}")//done
	public List<Email> getEmails(@PathVariable int userID ,@PathVariable String folderName ,@PathVariable int pageNo) {
		//page number starts from ZERO
		System.out.println("id"+userID);
		System.out.println(folderName);
		System.out.println(pageNo);
		return app.setViewingOptions(userID, folderName, pageNo, app.sort);
				
	}	
	
	@GetMapping("Compose/getEmail/{userID}")//done
	public  Object  getEmail(@PathVariable int userID ) {
		//
		User temp= FolderManagerBIN.getUser(userID);
		Object email=temp.getEmail();
	  return email;
	}	
	
	@GetMapping("getOneEmail/{userID}/{folderName}/{emailID}")//done
	public Email getOneEmail(@PathVariable int userID ,@PathVariable String folderName ,@PathVariable int emailID) {
		//page number starts from ZERO
	 List<Email> temp=app.getUserEmails(userID, folderName);
	 Email tempEmail=temp.get(0);
	 for ( int i=0; i<temp.size(); i++){
			if ( temp.get(i).id == emailID)  
				tempEmail= temp.get(i);
		}
	  return tempEmail;
	}	
	
	
	@GetMapping("sort/{userID}/{folderName}/{sortType}/{pageNo}")
	public List<Email> sort(@PathVariable int userID ,@PathVariable String folderName ,@PathVariable int sortType, @PathVariable int pageNo ) {
		// sort type is integer " switch case "
		app.sort = new sortComparator(sortType);
	  	return app.setViewingOptions(userID, folderName, pageNo, app.sort);
	 }	
	
	@GetMapping("search/{userID}/{folderName}/{searchType}/{searchText}")
	public List<Email> search(@PathVariable int userID ,@PathVariable String folderName ,@PathVariable String searchType,@PathVariable String searchText) {
		return app.searchEmails(userID, searchType, searchText, folderName);
	}	
	
	@GetMapping("filter/{userID}/{folderName}/{filterType}/{newFolder}/{Criteria}")
	public void filter(@PathVariable int userID ,@PathVariable String folderName ,@PathVariable  String filterType, @PathVariable  String newFolder,@PathVariable  String Criteria) {
		List<Email> emailsToBeFiltered=app.getUserEmails(userID, folderName);
	
		
		if(filterType.equals("subject")) {
			CriteriaSubject S=new CriteriaSubject();
			emailsToBeFiltered=S.meetCriteria(emailsToBeFiltered, Criteria);
		}
		else if(filterType.equals("sender")) {
			CriteriaSender S=new CriteriaSender();
			emailsToBeFiltered=S.meetCriteria(emailsToBeFiltered, Criteria);
		}
		List<Integer> ids=new ArrayList<Integer>();
		for(int i=0;i<emailsToBeFiltered.size();i++) {
			ids.add(emailsToBeFiltered.get(i).id);
		}
		app.moveEmails(ids, folderName, newFolder,userID);
		
	}
	@GetMapping("searchContacts/{userID}/{searchText}/{searchIn}")
	public List<Contact> getContact(@PathVariable int userID,@PathVariable String searchText,@PathVariable String searchIn) {
		return app.searchContacts(userID, searchText, searchIn);
	}

	@GetMapping("sortContacts/{userID}/{sortType}" )
	public List<Contact> sortContacts(@PathVariable int userID ,@PathVariable int sortType) {
		
		app.sort=new sortComparator(sortType);
		return app.sortContact(userID, app.sort);
		

	}
	@GetMapping("getFolders/{userID}")
	public List<String> getFolders(@PathVariable int userID) {
		User u=FolderManagerBIN.getUser(userID);
		return u.getFolderNames();	
	}
	@PostMapping("moveEmails/{userID}/{from}/{to}")
	public void moveEmails(@PathVariable int userID,@PathVariable String from,@PathVariable String to,@RequestBody List<Integer>IDs) {
		app.moveEmails(IDs, from, to, userID);
	}
	
	
	@GetMapping("getContacts/{userID}")
	public List<Contact> getContacts(@PathVariable int userID) {
	  User user = FolderManagerBIN.getUser(userID);
	  List<Contact> contacts = new ArrayList();
	  for ( int i=0; i<user.contactsIDs.size(); i++){
		long tempID= Math.round((double)user.contactsIDs.get(i));
		User temp_1 = FolderManagerBIN.getUser((int)tempID);
		Contact temp_2 = new Contact();
				temp_2.setContact(temp_1.firstName,temp_1.lastName,temp_1.email);
		contacts.add(temp_2);
	  }
	  return contacts;
	}
	
	@PutMapping("renameFolder/{userID}/{oldName}/{newName}")
	public void renameFolder(@PathVariable int userID, @PathVariable String oldName,@PathVariable String newName) {
		User u=FolderManagerBIN.getUser(userID);
		u.renameFolder(oldName, newName);
		FolderManagerBIN.updateUser(u);
		
	}
	
	
	@PutMapping("addFolder/{userID}/{name}")
	public void addFolder(@PathVariable int userID, @PathVariable String name) {
		User u=FolderManagerBIN.getUser(userID);
		u.setNewFolderName(name);
		FolderManagerBIN.updateUser(u);
	}
	
	
	@DeleteMapping("deleteEmails/{userID}/{folderName}")
	public void addFolder(@PathVariable int userID, @PathVariable String folderName,@RequestBody List<Integer> EmailIDs ) {
		if(folderName.equalsIgnoreCase("Trash")) {
		app.deleteEmails(EmailIDs, folderName, userID);
		}
		else {
			app.moveEmails(EmailIDs, folderName, "Trash", userID);
		}
	}
	
	
	@DeleteMapping("removeFolder/{userID}/{name}")
	public void removeFolder(@PathVariable int userID, @PathVariable String name) {
		app.deletefolder(name, userID);
		
	}
	
	
	
	//////////
	//  delete file in path: "attachments\\" +name  ..
	///
	@DeleteMapping("deleteAttachment/{name}")
	public void deleteAttachment(@PathVariable String name) {
		
	}
	
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
		File file=new File("attachments\\"+fileName) ; 
		InputStreamResource resource = null;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		    return ResponseEntity.ok()
		            .contentLength(file.length())
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .body(resource);
		    
	}
	
	  @PostMapping("/upload" )
	  public void fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
	 
		  		try {
				// read and write the file to the selected location-
				byte[] bytes = file.getBytes();
				Path path = Paths.get("attachments\\" + file.getOriginalFilename());
				/// making a file like the one that was uploaded 
				Files.write(path, bytes);

			} catch (IOException e) {
				e.printStackTrace();
		}

		}
}