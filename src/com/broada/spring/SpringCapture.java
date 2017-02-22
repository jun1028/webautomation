package com.broada.spring;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.broada.spring.utils.SilencerProperties;

/**
 * 通过WebDriver抓取当前屏幕
 * 
 * @author chingsir
 * 
 */
class SpringCapture {

	/**
	 * 
	 * Function to take the screen shot and save it to the classpath dir.
	 * 
	 * Usually, you will find the png file under the project root.
	 * 
	 * @param driver
	 *            Webdriver instance
	 * 
	 * @param desc
	 *            The description of the png
	 */
	static void screenShot(WebDriver driver, String title) throws IOException {
		String dir_name = SilencerProperties.getInstance().getString("pngs"); // 这里定义了截图存放目录名
		if (!(new File(dir_name).isDirectory())) { // 判断是否存在该目录
			new File(dir_name).mkdir(); // 如果不存在则新建一个目录
		}
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source_file = ts.getScreenshotAs(OutputType.FILE); // 关键代码，执行屏幕截图，默认会把截图保存到temp目录
		FileUtils.copyFile(source_file, new File(dir_name + File.separator
				+ title + ".png")); // 这里将截图另存到我们需要保存的目录，例如screenshot\20120406-165210.png
	}
}
