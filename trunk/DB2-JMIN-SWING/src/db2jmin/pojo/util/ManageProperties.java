package db2jmin.pojo.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.jdamico.dbjmin.crypto.DesEncrypter;

public class ManageProperties {
	
	private static ManageProperties INSTANCE;
	public static ManageProperties getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new ManageProperties();
		}
		return INSTANCE;
	}
	
	public Properties read(String fs){
		// Read properties file.
		Properties properties = new Properties();
	    try {
	        properties.load(new FileInputStream(fs));
	    } catch (IOException e) {
	    }
	    return properties;
	}
    
    public boolean write(String fs, ArrayList<String> form_data){
    	// Write properties file.
    	boolean ret = false;
    	Properties prop = new Properties();
        try {
        	prop.setProperty("remotedb", 	cipherStr(form_data.get(0)));
			prop.setProperty("portdb", 		cipherStr(form_data.get(1)));
			prop.setProperty("namedb", 		cipherStr(form_data.get(2)));
			prop.setProperty("userdb", 		cipherStr(form_data.get(3)));
			prop.setProperty("pwddb", 		cipherStr(form_data.get(4)));
			prop.setProperty("driver", 		cipherStr(form_data.get(5)));
            prop.store(new FileOutputStream(fs), null);
            ret = true;
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return ret;
    }
    
    private String cipherStr(String rawStr){
		String ret = null;
		try {
	        DesEncrypter encrypter = new DesEncrypter(Constants.COMMON_KEY);
	        ret = encrypter.encrypt(rawStr);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return ret;
	}
}
