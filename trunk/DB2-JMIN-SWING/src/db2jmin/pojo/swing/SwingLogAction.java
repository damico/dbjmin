package db2jmin.pojo.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import db2jmin.pojo.util.LogConsole;

public class SwingLogAction extends AbstractAction {

	private static final long serialVersionUID = 4456525829715287954L;

	public void actionPerformed(ActionEvent e) {
		LogConsole.singleton().start();
		
	}

}
