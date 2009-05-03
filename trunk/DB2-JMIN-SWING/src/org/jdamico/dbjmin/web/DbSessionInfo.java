package org.jdamico.dbjmin.web;

import java.util.ArrayList;

import org.jdamico.dbjmin.web.action.ActionsFactory;

import db2jmin.pojo.util.Constants;

public class DbSessionInfo {
	private static DbSessionInfo INSTANCE;
	public static DbSessionInfo getInstance(){
		if(INSTANCE == null){
			INSTANCE = new DbSessionInfo();
		}
		return INSTANCE;
	}
	
	public String getCurrentDBinfo(){
		ArrayList<String> dbSession = ActionsFactory.callJetty(null, Constants.W_GET_DB_SESSION).getResult();
		String ret = "<font color='green'><b>"+dbSession.get(5)+"://"+dbSession.get(0)+":"+dbSession.get(1)+"/"+dbSession.get(2)+" ["+dbSession.get(3)+"] </b></font>";
		return ret;
	}
}
