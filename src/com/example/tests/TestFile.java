package com.example.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TestFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("text1.sql");
		FileWriter filew = null;
		try {
			filew = new FileWriter(file, true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter buff = new BufferedWriter(filew);
		try {
			buff.write("hello world\n");
			buff.write("¾ü\n");
			buff.flush();
			System.out.println("pass");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
