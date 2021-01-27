package mail.back;

import java.util.ArrayList;
import java.util.List;

import mail.back.interfaces.ISearchCriteria;

public class EmailSearchCriteria implements ISearchCriteria {

	@Override
	public List<Contact> meetCriteria(List<Contact> Contacts, String Criteria) {
		// TODO Auto-generated method stub
		String Email=Criteria;
		List<Contact> returnedMails =new ArrayList<Contact>();
		for( Contact contact:Contacts) {
			String email=contact.getEmail();
			if (email.contains(Email)) {
				returnedMails .add(contact);
		}
		return returnedMails;
	}
		return null;
	}
	public List<Contact> meetCriteriaFirstName(List<Contact> Contacts, String Criteria) {
		// TODO Auto-generated method stub
		String name=Criteria;
		List<Contact> returnedMails =new ArrayList<Contact>();
		for( Contact contact:Contacts) {
			String names=contact.getFirstName();
			
			if (((String) names).contains(name)) {
				returnedMails .add(contact);
			}
		
	}
		return returnedMails;
		
	}

}