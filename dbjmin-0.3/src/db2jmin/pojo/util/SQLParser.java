package db2jmin.pojo.util;

import java.util.ArrayList;

public class SQLParser {
	
	private static SQLParser _instance = new SQLParser();
	public static SQLParser singleton(){
		return _instance;
	}
	
	public ArrayList<String> breakSql(String sql){
		
		/* Find the break points */
		String keys = "';";
		int i, quoteChecker = 0, lastPos = 0;	
		ArrayList<String> breakP = new ArrayList<String>();
		for(i=0; i<sql.length(); i++) {
			
			if( sql.charAt(i)==keys.charAt(0) ) quoteChecker++;
			if( sql.charAt(i)==keys.charAt(1) && (quoteChecker % 2 == 0) ){
				breakP.add(sql.substring(lastPos,i));
				lastPos = i+1;
			}

		}
		
		
		return breakP;
	}
}
