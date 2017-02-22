package com.example.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataHelper {

	public static void main(String[] args) {
		try {
			DataHelper data = new DataHelper();
			File file = new File("data.txt");
			if (!file.exists()) {
				System.out.println(file.getAbsolutePath());
			}
			InputStream in = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");
			FileWriter writer = new FileWriter("result.txt");
			BufferedReader readerbuff = new BufferedReader(reader);
			BufferedWriter writerbuff = new BufferedWriter(writer);
			String temp = null;
			while ((temp = readerbuff.readLine()) != null) {
				writerbuff.write(data.processData(temp) + "\n");
			}
			readerbuff.close();
			writerbuff.close();
			writer.close();
			reader.close();

			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String processData(String tests) {
		// String tests =
		// "insert into page.totalelement(xpathvalue, keyvalue) VALUES(\"//a[@href='navigator.do?method=stockSearch']\", \"ø‚¥Êπ‹¿Ì\")";
		StringBuffer sb = new StringBuffer("");
		sb.append(tests.substring(0, tests.indexOf("xpathvalue")));
		sb.append("keyvalue, xpathvalue) VALUES(");
		tests = tests.substring(tests.indexOf("VALUES(") + "VALUES(".length());
		String[] s = tests.split("\"");
		sb.append("\"" + s[3] + "\", ");
		sb.append("\"" + s[1] + "\");");

		System.out.println(sb);
		return sb.toString();

	}
}
