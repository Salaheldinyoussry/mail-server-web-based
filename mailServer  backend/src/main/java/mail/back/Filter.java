package mail.back;
import java.util.List;

import mail.back.Email;
public interface Filter {
	public List<Email> meetCriteria(List<Email> Emails);
}