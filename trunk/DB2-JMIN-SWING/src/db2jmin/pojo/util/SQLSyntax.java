/*******************************************************************************
 * DB2-JMIN / MULTI-JMIN (MULTI DB CLIENT)	
 * Copyright (c) 2009 Mario C. Ponciano and others.
 * This program is free software; you can redistribute it and/or		
 * modify it under the terms of the GNU General Public License			
 * as published by the Free Software Foundation; either version 2		
 * of the License, or (at your option) any later version.
 *
 *Contributors:
 *Mario C. Ponciano a.k.a Razec (mrazec@gmail.com)
 *  	
 *******************************************************************************/

package db2jmin.pojo.util;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *This class is used for give suggests hints to complete the sqlcommands. The
 * vocabulary contains a word that starts with what has been typed.
 * */

public class SQLSyntax implements DocumentListener {

	public static JTextArea sqltext = new JTextArea();

	private static enum Mode {
		INSERT, COMPLETION
	};

	private Mode mode = Mode.INSERT;
	private List<String> sqlDict;
	private static final String COMMIT_ACTION = "commit";

	public SQLSyntax(JTextArea sqltext) {

		this.sqltext = sqltext;
		// System.out.println(sqltext.getText());
		InputMap im = sqltext.getInputMap();
		ActionMap am = sqltext.getActionMap();
		im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
		am.put(COMMIT_ACTION, new CommitAction());

		/*
		 * This fuction it`s test but I think that is very slow
		 * 
		 * 
		 * sqlDict= new ArrayList<String>(); Scanner sc; try { sc = new
		 * Scanner(new File("sql_syntax")); while (sc.hasNext()){
		 * sqlDict.add(sc.nextLine()); } } catch (FileNotFoundException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */

		sqlDict = new ArrayList<String>();
		sqlDict.add("create");
		sqlDict.add("select");
		sqlDict.add("table");
		sqlDict.add("from");
		sqlDict.add("where");
		sqlDict.add("like");
		sqlDict.add("insert");
		sqlDict.add("into");
		sqlDict.add("delete");
		sqlDict.add("alter");

	}

	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

	public void insertUpdate(DocumentEvent ev) {
		if (ev.getLength() != 1) {
			return;
		}

		int pos = ev.getOffset();
		String content = null;
		try {
			content = sqltext.getText(0, pos + 1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		int w;
		for (w = pos; w >= 0; w--) {
			if (!Character.isLetter(content.charAt(w))) {
				break;
			}
		}
		if (pos - w < 2) {
			return;
		}

		String prefix = content.substring(w + 1).toLowerCase();
		int n = Collections.binarySearch(sqlDict, prefix);
		if (n < 0 && -n <= sqlDict.size()) {
			String match = sqlDict.get(-n - 1);
			if (match.startsWith(prefix)) {
				String completion = match.substring(pos - w);
				SwingUtilities.invokeLater(new SQLCompletion(completion,
						pos + 1));
			}
		} else {
			mode = Mode.INSERT;
		}
	}

	public void removeUpdate(DocumentEvent e) {

	}

	private class SQLCompletion implements Runnable {
		String completion;
		int position;

		SQLCompletion(String completion, int position) {
			this.completion = completion;
			this.position = position;
		}

		public void run() {
			sqltext.insert(completion, position);
			sqltext.setCaretPosition(position + completion.length());
			sqltext.moveCaretPosition(position);
			mode = Mode.COMPLETION;
		}
	}

	public class CommitAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent ev) {
			if (mode == Mode.COMPLETION) {
				int pos = sqltext.getSelectionEnd();
				sqltext.insert(" ", pos);
				sqltext.setCaretPosition(pos + 1);
				mode = Mode.INSERT;
			} else {
				sqltext.replaceSelection("\n");
			}
		}
	}

}
