
//author: Saket Vishwasrao
/*
 This class implements methods to read push notification from email:simulation.ece5574@gmail.com
 The format of the email expected is as follows:
 Subject: Simulation_ECE5574
 Content:
 <userID>
 
 Please refer TestReceiveNotifications for sending sample emails
 
 Usage:
 ReadNotifications notifications = new ReadNotifications();
 notifications.setAccountDetails("simulation.ece5574@gmail.com", "password");
 result= testNotifications.readGmail(sim);
 
 sim is the object of class Simulation which this API uses
 Please talk to the team for the password of the user account 
  */
package edu.vt.ece5574.sim;

import java.util.*;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import sim.engine.SimState;
import sim.engine.Steppable;

import javax.mail.Flags;




public class ReadNotifications implements Steppable{

	private static final long serialVersionUID = 1;
	private String userName;
    private String password;
    private String receivingHost;
     
    public void setAccountDetails(String userName,String password){
 
        this.userName=userName;
        this.password=password;
 
    }
	
   
    
    
    
    public String[] readGmail(Simulation sim){
    	 
        
 
        this.receivingHost="imap.gmail.com";
        Properties props2=System.getProperties();
        props2.setProperty("mail.store.protocol", "imaps");
       
        Session session2=Session.getDefaultInstance(props2, null);
 
            try {
 
                    Store store=session2.getStore("imaps");
 
                    store.connect(this.receivingHost,this.userName, this.password);
 
                    Folder folder=store.getFolder("INBOX");//get inbox
 
                    folder.open(Folder.READ_WRITE);//open folder
 
                    Message message[]=folder.getMessages();
                    String result[] =  new String[message.length];
                    for(int i=0;i<message.length;i++){
 
                        //go through all mails
                    	
                    	
                    	if(message[i].getSubject().equals("Simulation_ECE5574")){
                    		//System.out.println(message[i].getSubject());
                    		//System.out.println(message[i].getContent());
                    		//some messages maybe multipart
                    		if(message[i].getContent() instanceof Multipart){
                    			Multipart multipart = (Multipart) message[i].getContent();
                        		for (int j = 0; j < multipart.getCount(); j++) {
                        			Part bodyPart = multipart.getBodyPart(j);
                        			if (bodyPart.isMimeType("text/plain")) {
                        				System.out.println("CONTENT:" + bodyPart.getContent());
                                        String split[];
                                        String userID;
                                        split = bodyPart.getContent().toString().split("\\n");
                                        if(split.length==1){
                                        	
                                        	
                                        	userID=split[0].replace("\n","").replace("\r", "");
                                        	//check for userID, delete email if userID found
                                            if(userID!=null){
                                            	if(sim.agentPushReceived(userID)){
                                            		message[i].setFlag(Flags.Flag.DELETED, true); //delete message
                                            	}
                                            }
                                        	result[i]= userID;
                                        	System.out.println(result[i]);
                  
                                        	continue;
                                        	}
                                        }
                        		}
                    		}
                    		else{
                    			System.out.println("CONTENT:" + message[i].getContent());
                                String split[];
                                split = message[i].getContent().toString().split("\\n");
                                if(split.length==1){
                                	
                                	
                                	String userID;
                                	userID=split[0].replace("\n","").replace("\r", "");
                                	//check for userID, delete email if userID found
                                    if(userID!=null){
                                    	if(sim.agentPushReceived(userID)){
                                    		message[i].setFlag(Flags.Flag.DELETED, true);   //delete message
                                    	}
                                    }
                                	result[i]= userID;
                                	System.out.println(result[i]);
          
                                	}
                                }
                    		}
                    		
                    		 
                    		
                    	}
                        
 
                    //close connections
 
                    folder.close(true);

                    store.close();
                    return result;
 
            } catch (Exception e) {
 
                    System.out.println(e.toString());
 
            }
            //return null if no results
			return null;
 
    }





	@Override
	public void step(SimState state) {
		readGmail((Simulation)state);
	}
      
}
