package mail.back;

import org.springframework.stereotype.Component;

@Component
public class Contact {
    public String first_name;
    public String last_name;
    public String email;

    public void setContact(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public void setFirstName(String firstName) {
    	this.first_name=firstName;
    }
    public void setlastName(String lastName) {
    	this.last_name=lastName;
    }
    public void setEmails(String ema) {
    	this.email=ema;
    }
    public String getEmail() {
    	return this.email;
    }
    public String getFirstName() {
    	return this.first_name;
    }
    public String getLastName() {
    	return this.last_name;
    }
    
    
}