package edu.vt.ece5574.sim;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;

//Maybe we shouldn't do json or XML?  There's a java properties file format
//that might be of interest: 
//http://cs.gmu.edu/~eclab/projects/mason/extensions/webtutorial1/#param-file
//Honestly, that seems like a smart decision but it's up to the developer
//
//You should instantiate agents here on loading the config and provided a way 
//to get them back to main through function calls of some sort
//
//Should seed, time and other cmd line args be placed in the config or fed in 
//through the config file?  The config file will take some circumventing of 
//MASON's built in functionality but is possible.  Just annoying. 

/**
 * Basic class for configurations
 * @author Siddharth Bhal
 *
 */
public class Configuration {
	
	private static Configuration instance = null;
	InputStream inputStream;
	BlobstoreInputStream in;
	Properties allProp;

	public Configuration(){
//		String filename = "config.properties";
//		if(!load(filename)) {
//			System.err.println("Failed to load configuration file named " + filename);
//			System.exit(-1);
//		}
	}
	
	public Configuration(String filename){

//		if(!load(filename)) {
//			System.err.println("Failed to load configuration file named " + filename);
//			System.exit(-1);
//		}
	}
	public static Configuration getInstance() {
		if(instance == null) {
			instance = new Configuration();
	    }
	    return instance;
	}
	
	public boolean load(BlobKey blobKey) {
		
		//for debugging purpose
		//log.updateEvent("user", "1", 15, 20);
		
		try {
			allProp = new Properties();
			in = new BlobstoreInputStream(blobKey);
 
			if (in != null) {
				allProp.load(in);
			} else {
				throw new FileNotFoundException("config blob is empty");
			}
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			return false;
		} finally {
			try {
			    if (in != null) {
			    	in.close();
			    }
			  } catch( Exception ex ) {
				  System.out.println( "Exception during Resource.close()"+ ex);
			  }
		}
		return true;
	}
	
	public boolean load(String filename) {

		try {
			allProp = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				allProp.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			return false;
		} finally {
			try {
			    if (inputStream != null) {
			    	inputStream.close();
			    }
			  } catch( Exception ex ) {
				  System.out.println( "Exception during Resource.close()"+ ex);
			  }
		}
		return true;
	}
	
	public String getProp(String propName){
		String property = null;
		if(allProp != null){
			property =  allProp.getProperty(propName);
		}
		if(property == null) {
			System.err.println("Failed to load property named " + propName);
        }
		return property;
	}
}
