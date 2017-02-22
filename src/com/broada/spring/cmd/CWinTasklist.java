package com.broada.spring.cmd;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

/**
 * 获取win系统中的TaskList结果
 * 
 * @author Chingsir
 * 
 */
public class CWinTasklist {

	private CWinTasklist() {
	}

	public static CWinTasklist getInstance() {
		return new CWinTasklist();
	}

	public ArrayList<String> getTaskList() {
		return this.parceTaskList();
	}

	private ArrayList<String> parceTaskList() {
		String cmd = "cmd /c tasklist";
		Process process = null;
		ArrayList<String> list = new ArrayList<String>();
		String line = null;
		int i = 0;
		try {
			process = Runtime.getRuntime().exec(cmd);
			InputStream is = process.getInputStream();
			InputStreamReader ir = new InputStreamReader(is);
			LineNumberReader input = new LineNumberReader(ir);
			line = input.readLine();
			while (line != null) {
				if (i >= 3) {
					String taskName = paserLine(line);
					if (!list.contains(taskName)) {
						list.add(taskName);
					}
				}
				i++;
				line = input.readLine();
			}
		} catch (java.io.IOException e) {
			System.err.println("IOException " + e.getMessage());
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
		return list;
	}

	private String paserLine(String line) {
		String row = line;
		String name = row.substring(0, 30).trim();
		return name;

	}
}
