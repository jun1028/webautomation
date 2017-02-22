/**
 * 
 */
package com.broada.spring.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	/**
	 * Ĭ��ʱ��
	 */
	public final static int TIME_OUT = Integer.parseInt(SilencerProperties
			.getInstance().getString("element.find.timeout"));
	/**
	 * ��ȡ�������Ϣ
	 */
	public final static String STR_NAV_JS = " return navigator.userAgent.toLowerCase() ; ";

	/**
	 * ������ cmd
	 */
	public static String CMD_KILL = "cmd /c tskill ";
	/**
	 * �ر�IE driver ����
	 */
	public static String CMD_KILL_IEDRIVER = CMD_KILL + "IEDriverServer";
	/**
	 * �ر�IE����
	 */
	public static String CMD_KILL_IE = CMD_KILL + "iexplore";
	/**
	 * �ر�Firefox����
	 */
	public static String CMD_KILL_FIREFOX = CMD_KILL + "firefox";
	/**
	 * �ر�Chrome����
	 */
	public static String CMD_KILL_CHROME = CMD_KILL + "chrome";
	/**
	 * �ر�Chrome����
	 */
	public static String CMD_KILL_CHROMEDRIVER = CMD_KILL + "chromedriver";

	public static String CMD_DELETE_TMPFILES = "cmd /c del /f /s /q %systemdrive%\\*.tmp";

	/**
     * 
     */
	public static String HIGHLIGHT_BORDER = SilencerProperties.getInstance()
			.getString("AutoTest.hightLitht.border");

	/**
	 * 
	 * @return
	 */
	public static String getDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * ִ��Windowϵͳ����
	 * 
	 * @param cmd
	 */
	public static void executeWinCMD(String cmd) {
		Process pr = null;
		try {
			Runtime rt = Runtime.getRuntime();
			pr = rt.exec(cmd);
			pr.waitFor();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pr != null) {
				pr.destroy();
			}
		}
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر�����IE����
	 */
	public static void killIE() {
		executeWinCMD(CMD_KILL_IE);
	}

	/**
	 * �ر�����IeDriverServer����
	 */
	public static void killIeDriverSever() {
		executeWinCMD(CMD_KILL_IEDRIVER);
	}

	/**
	 * �ر�����Firefox����
	 */
	public static void killFirefox() {
		executeWinCMD(CMD_KILL_FIREFOX);
	}

	/**
	 * �ر�����chrome
	 */
	public static void killChrome() {
		executeWinCMD(CMD_KILL_CHROME);
	}

	/**
	 * �ر�����chromeDriver����
	 */
	public static void killChromeDriver() {
		executeWinCMD(CMD_KILL_CHROMEDRIVER);
	}

	/**
	 * ɾ�����й����в�������ʱ�ļ�
	 */
	public static void deleteTmpFiles() {
		executeWinCMD(CMD_DELETE_TMPFILES);
	}

}