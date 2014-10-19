package messagePosting;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = -6748707665222190673L;
String userName;
 String UserType;
 String message;
 String recepient;
 List<String> recepients;
 String req;
 int action;
}
