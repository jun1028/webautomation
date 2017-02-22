/**
 * 
 */
package com.broada.spring.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	/**
	 * 默认时间
	 */
	public final static int TIME_OUT = Integer.parseInt(SilencerProperties
			.getInstance().getString("element.find.timeout"));
	/**
	 * 获取浏览器信息
	 */
	public final static String STR_NAV_JS = " return navigator.userAgent.toLowerCase() ; ";

	/**
	 * 命令行 cmd
	 */
	public static String CMD_KILL = "cmd /c tskill ";
	/**
	 * 关闭IE driver 进程
	 */
	public static String CMD_KILL_IEDRIVER = CMD_KILL + "IEDriverServer";
	/**
	 * 关闭IE进程
	 */
	public static String CMD_KILL_IE = CMD_KILL + "iexplore";
	/**
	 * 关闭Firefox进程
	 */
	public static String CMD_KILL_FIREFOX = CMD_KILL + "firefox";
	/**
	 * 关闭Chrome进程
	 */
	public static String CMD_KILL_CHROME = CMD_KILL + "chrome";
	/**
	 * 关闭Chrome进程
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
	 * 执行Window系统命令
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
	 * 关闭所有IE窗口
	 */
	public static void killIE() {
		executeWinCMD(CMD_KILL_IE);
	}

	/**
	 * 关闭所有IeDriverServer进程
	 */
	public static void killIeDriverSever() {
		executeWinCMD(CMD_KILL_IEDRIVER);
	}

	/**
	 * 关闭所有Firefox进程
	 */
	public static void killFirefox() {
		executeWinCMD(CMD_KILL_FIREFOX);
	}

	/**
	 * 关闭所有chrome
	 */
	public static void killChrome() {
		executeWinCMD(CMD_KILL_CHROME);
	}

	/**
	 * 关闭所有chromeDriver进程
	 */
	public static void killChromeDriver() {
		executeWinCMD(CMD_KILL_CHROMEDRIVER);
	}

	/**
	 * 删除运行过程中产生的临时文件
	 */
	public static void deleteTmpFiles() {
		executeWinCMD(CMD_DELETE_TMPFILES);
	}

}