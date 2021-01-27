package mail.back;

import java.util.ArrayList;
import java.util.List;

import mail.back.interfaces.Criteria;

public class CriteriaSender implements Criteria {

	@Override
	public List<Email> meetCriteria(List<Email> Emails, String Criteria) {
		// TODO Auto-generated method stub
		String sender=Criteria;
		List<Email> returnedMails =new ArrayList<Email>();
		for( Email email:Emails) {
			if (email.getSender().contains(sender)) {
				returnedMails .add(email);
			}
		}
		return returnedMails;
	}

	
}