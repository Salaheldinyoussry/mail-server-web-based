package mail.back;

import mail.back.interfaces.ISort;

public class sortComparator implements ISort{
	int type;
	//here to set the type of comparasion
	public sortComparator(int type) {
		this.type = type;
	}
		public int mycompare(Object a, Object b)
		{
			switch(type)
			{
			case 0:
				try{return ((Contact)a).first_name.compareTo(((Contact)b).first_name);}
				catch(Exception e) {
					e.printStackTrace();
				}
				
			case 1:
				String emailA =((User)a).getEmail();
				String emailB =((User)b).getEmail();
				return emailA.compareTo(emailB);
			case 2:
				return ((Email)a).subject.compareTo(((Email)b).subject);
			case 3:
				return ((Email)a).senderEmail.compareTo(((Email)b).senderEmail);
			case 4:
				return ((Email)a).receiverEmail.compareTo(((Email)b).receiverEmail);
			case 5:
				if(((Email)a).id>(((Email)b).id))
				return -1;
				else {
					return 1;
				}
			default:
				{
				if(((Email)a).priority < ((Email)b).priority)
					return -1;
				else if(((Email)a).priority == ((Email)a).priority)
					return 0;
				else
					return 1;
				}
			}	
		}
	}
