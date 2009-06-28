/* ******************************************************************* 	*/
/* DB2-JMIN / MULTI-JMIN (MULTI DB CLIENT)								*/
/* Copyright (C) 2007  Jose' Ricardo de Oliveira Damico					*/
/* damico@dcon.com.br													*/
/* 																		*/
/* This program is free software; you can redistribute it and/or		*/
/* modify it under the terms of the GNU General Public License			*/
/* as published by the Free Software Foundation; either version 2		*/
/* of the License, or (at your option) any later version.				*/
/* 																		*/
/* This program is distributed in the hope that it will be useful,		*/
/* but WITHOUT ANY WARRANTY; without even the implied warranty of		*/
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the		*/
/* GNU General Public License for more details.							*/
/* 																		*/
/* You should have received a copy of the GNU General Public License	*/
/* along with this program; if not, write to the Free Software			*/
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 			*/
/* MA  02110-1301, USA.													*/
/* ******************************************************************** */

package db2jmin.pojo.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is a simple logger code used in entire application
 * 
 * @author Jose Damico (damico@dcon.com.br)
 * */

public class Logger {

	private String filen = null;
	private String tempDir = "/tmp/";

	public Logger(String fFile) {

		tempDir = SystemOper.singleton().getTempPath();
		/*
		 * Format formatter; Date date = new Date(); formatter = new
		 * SimpleDateFormat("yyyyMMdd_HHmmss"); String stime =
		 * formatter.format(date); filen = fFile + "_" + stime + ".log";
		 */

		filen = tempDir + fFile + ".log";
		createLog(filen);

	}

	private boolean createLog(String filename) {
		boolean ret = false;

		try {
			File file = new File(filename);

			// Create file if it does not exist
			boolean success = file.createNewFile();
			if (success) {
				// File did not exist and was created
				ret = true;
			} else {
				// File already exists

				if (file.length() > Constants.FIXED_LOGLIMIT) {
					/*
					 * check if file is too big
					 */
					file.delete();

				}

				ret = file.createNewFile();
			}
		} catch (IOException e) {
			ret = false;
		}

		return ret;
	}

	public boolean AddLogLine(String line) {
		boolean ret = false;

		Format formatter;
		Date date = new Date();
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stime = formatter.format(date);

		try {
			FileWriter fw = new FileWriter(filen, true);
			BufferedWriter bwr = new BufferedWriter(fw);
			String logLine = stime + ": " + line + "\n";
			if (Constants.VERBOSE_CONSOLE) {
				System.out.println(logLine);
			}
			bwr.write(logLine);
			bwr.close();
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}

}
