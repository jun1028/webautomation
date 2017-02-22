package com.broada.spring.extern;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class VBS {

	/**
	 * execute a vbs file.
	 * 
	 * @param vbsfileName
	 *            whole name whitch vbs file to be executed
	 * @throws RuntimeException
	 **/
	public void executeVbsFile(String vbsfileName) {
		try {
			String[] vbsCmd = new String[] { "wscript", vbsfileName };
			Process process = Runtime.getRuntime().exec(vbsCmd);
			process.waitFor();
		} catch (Exception e) {
			throw new RuntimeException("execute extern file failed:"
					+ e.getMessage());
		}
	}

	/**
	 * create a temp vbs file to be executed.
	 * 
	 * @param vbs
	 *            string content to be written into file
	 * @param vbsfileName
	 *            whole name whitch vbs file to be saved
	 * @throws RuntimeException
	 **/
	public void createVbsFile(String vbs, String vbsfileName) {
		File file = new File(vbsfileName);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			writer.write(vbs);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException("execute extern file failed:"
					+ e.getMessage());
		}
	}

	/**
	 * get system environment values.
	 * 
	 * @param virName
	 *            viriable name to get, such as "classpath", "JAVA_HOME"
	 * @return the viriable value
	 * @throws RuntimeException
	 **/
	public String getEnvironment(String virName) {
		byte[] env = new byte[1000];
		try {
			Process process = Runtime.getRuntime().exec(
					"cmd /c echo %" + virName + "% ");
			process.waitFor();
			InputStream iStream = process.getInputStream();
			iStream.read(env);
		} catch (Exception e) {
			throw new RuntimeException("execute extern file failed:"
					+ e.getMessage());
		}
		return new String(env).trim();
	}
}
