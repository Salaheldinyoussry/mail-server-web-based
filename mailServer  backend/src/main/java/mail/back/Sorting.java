package mail.back;




import java.util.List;


import eg.edu.alexu.csd.datastructure.stack.Classes.Stack;
import mail.back.interfaces.ISort;

public class Sorting {
	public  void quickSortEmail(List<Email> arr, ISort comp)
	{
		if (arr.size() != 0)
			_quickSortEmail(arr, 0, arr.size()-1, comp);
	}
	
	public void _quickSortEmail(List<Email> arr, int l, int h, ISort comp) {
		
		//Changed from StackDS because the jar we have imported it is called Stack
		Stack stack = new Stack();
		stack.push(l);
		stack.push(h);
		 
		while(!stack.isEmpty()){
			h = (int) stack.pop();
			l = (int) stack.pop();
			
			int p = partitionEmail(arr, l, h, comp); 
			if (p - 1 > l) { 
	            stack.push(l);
	            stack.push(p-1);
	        }
			
			if (p + 1 < h) { 
				stack.push(p + 1);
				stack.push(h);
	        }
		}
	}
	
	
	private int partitionEmail(List<Email> arr, int l, int h, ISort c) 
	{ 
		Object x = arr.get(h); 
		
		sortComparator comp = (sortComparator)c;
	    int i = (l - 1); 
	  
	    for (int j = l; j <= h - 1; j++) { 
	    	if (comp.mycompare(arr.get(j), x) <= 0) {
	    		i++;
	    		Email temp = arr.get(i);
	    		arr.set(i, arr.get(j));
	    		arr.set(j, temp);
	    	}
	    } 
	    
	    Email temp = arr.get(i+1);
	    arr.set(i+1, arr.get(h));
	    arr.set(h, temp);
	    return (i + 1); 
	} 
	public  void quickSortContact(List<Contact> arr, ISort comp)
	{
		if (arr.size() != 0)
			_quickSortContact(arr, 0, arr.size()-1, comp);
	}
	
	public void _quickSortContact(List<Contact> arr, int l, int h, ISort comp) {
		
		//Changed from StackDS because the jar we have imported it is called Stack
		Stack stack = new Stack();
		stack.push(l);
		stack.push(h);
		 
		while(!stack.isEmpty()){
			h = (int) stack.pop();
			l = (int) stack.pop();
			
			int p = partitionContact(arr, l, h, comp); 
			if (p - 1 > l) { 
	            stack.push(l);
	            stack.push(p-1);
	        }
			
			if (p + 1 < h) { 
				stack.push(p + 1);
				stack.push(h);
	        }
		}
	}
	

	private int partitionContact (List<Contact>arr, int l, int h, ISort c) 

	{ 
		Object x = arr.get(h); 
		
		sortComparator comp = (sortComparator)c;
	    int i = (l - 1); 
	  
	    for (int j = l; j <= h - 1; j++) { 
	    	if (comp.mycompare(arr.get(j), x) <= 0) {
	    		i++;
	    		Contact temp = (Contact)arr.get(i);
	    		arr.set(i, arr.get(j));
	    		arr.set(j, temp);
	    	}
	    } 
	    
	    Contact temp = (Contact)arr.get(i+1);
	    arr.set(i+1, arr.get(h));
	    arr.set(h, temp);
	    return (i + 1); 
	} 
	
	
}