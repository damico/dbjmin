package db2jmin.pojo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.jdamico.dbjmin.crypto.DesEncrypter;

import db2jmin.pojo.data.Preferences;

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
    
    public boolean write(String fs, Preferences form_data){
    	// Write properties file.
    	boolean ret = false;
    	Properties prop = new Properties();
        try {
        	prop.setProperty("remotedb", 	cipherStr(form_data.getRemoteDB()));
			prop.setProperty("portdb", 		cipherStr(form_data.getPortdb()));
			prop.setProperty("namedb", 		cipherStr(form_data.getNamedb()));
			prop.setProperty("userdb", 		cipherStr(form_data.getUserdb()));
			prop.setProperty("pwddb", 		cipherStr(form_data.getPwddb()));
			prop.setProperty("driver", 		cipherStr(form_data.getDriver()));
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
    
    public boolean remove(){
    	boolean ret = false;
    	String fs = SystemOper.singleton().getTempPath();
		fs = fs + Constants.TEMP_ALIVE_CREDENTIAL;
		ret = (new File(fs)).delete();
		return ret;
    }
}
