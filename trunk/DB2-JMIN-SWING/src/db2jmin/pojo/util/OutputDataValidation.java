package db2jmin.pojo.util;

public class OutputDataValidation {
	
	private static OutputDataValidation INSTANCE;
	public static OutputDataValidation getInstance(){
		if(INSTANCE == null){
			INSTANCE = new OutputDataValidation();
		}
		return INSTANCE;
	}
	
	public String wrapResult(String output){
		int count  = 0;
		int adjust = 0;
		for(int i = 0; i < output.length(); i++){
			if(i>0) adjust = 1;
			if((output.charAt(i) == ' ' || output.charAt(i-adjust) == ',') && count >= 45){
				output = output.substring(0,i) + "\n" + output.substring(i,output.length());
				count = 0;
			}else{
				count++;
			}
		}
		
		return output;
	}

}
