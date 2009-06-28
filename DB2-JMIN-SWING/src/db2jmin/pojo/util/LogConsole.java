package db2jmin.pojo.util;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import db2jmin.pojo.swing.SwingUtils;

public class LogConsole extends JFrame {

	private static final long serialVersionUID = -352415149519756673L;

	private static final LogConsole getInstance = new LogConsole();

	public static LogConsole singleton() {
		return getInstance;
	}

	private static ReaderThread tUniqeLogConsole = null;

	public ReaderThread start() {
		ReaderThread rt = null;
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setRows(20);
		textArea.setColumns(50);
		JScrollPane sPane = new JScrollPane(textArea);
		getContentPane().add(sPane, BorderLayout.CENTER);
		pack();
		setResizable(false);
		setVisible(true);
		setIconImage(SwingUtils.singleton().getDefaultIcon());
		setTitle(Constants.APPNAME + " LOG");
		if (tUniqeLogConsole == null) {
			rt = new ReaderThread(textArea);
			rt.start();
			tUniqeLogConsole = rt;
		}

		return tUniqeLogConsole;

	}

	public class ReaderThread extends Thread {

		private String logPath = SystemOper.singleton().getTempPath()
				+ Constants.LOGNAME + ".log";
		private int lines = 0;
		private JTextArea textArea;

		public ReaderThread(JTextArea textArea) {
			this.textArea = textArea;

		}

		public void run() {

			textArea.append(readLog().toString());
			while (true) {

				try {
					sleep(3000);
					textArea.setCaretPosition(textArea.getDocument()
							.getLength());

					if (!isVisible()) {
						System.out.println("LogConsole [" + this.getName()
								+ "] thread killed.");
						return;
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int tLines = textArea.getLineCount();
				if (tLines < readLogLines())
					textArea.append(getDiffLog(tLines).toString());
			}

		}

		public StringBuffer readLog() {
			StringBuffer sb = new StringBuffer();
			FileReader fr = null;
			BufferedReader in = null;
			try {
				fr = new FileReader(logPath);
				in = new BufferedReader(fr);
				String str;

				while ((str = in.readLine()) != null) {
					sb.append(str + "\n");
					lines++;
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			return sb;
		}

		public StringBuffer getDiffLog(int tLines) {
			StringBuffer sb = new StringBuffer();
			FileReader fr = null;
			BufferedReader in = null;
			try {
				fr = new FileReader(logPath);
				in = new BufferedReader(fr);
				String str;
				int lines = 0;
				while ((str = in.readLine()) != null) {
					if (lines >= tLines)
						sb.append(str + "\n");
					lines++;
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			return sb;
		}

		public int readLogLines() {
			int ret = 0;
			FileReader fr = null;
			BufferedReader in = null;
			try {
				fr = new FileReader(logPath);
				in = new BufferedReader(fr);
				while ((in.readLine()) != null) {
					ret++;
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			return ret;
		}

	}
}
