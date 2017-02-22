package com.example.tests;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class TestFireFox {

	public static void main(String args[]) {
		// ProperFile profile = new ProFile();
		// ((Object)
		// profile).setPreference("account@mozillaonline.com.auto_login",
		// false);
		// profile.setPreference("account@mozillaonline.com.logging",false);
		// profile.setPreference("account@mozillaonline.com.use_bak_server",false);
		// profile.setPreference("account@extensions.firebug.addonBarOpened",false);
		// WebDriver driver = new FirefoxDriver(profile);
		// FirefoxProfile profile = new FirefoxProfile();
		// profile.setPreference("account@mozillaonline.com.auto_login", false);
		// profile.setPreference("account@mozillaonline.com.logging",false);
		// profile.setPreference("account@mozillaonline.com.use_bak_server",false);
		// profile.setPreference("account@extensions.firebug.addonBarOpened",false);
		// WebDriver driver = new FirefoxDriver(profile);

		// WebDriver ff = new FirefoxDriver();
		// System.out.println("vfdfd");
		File file = new File(
				"C:\\Documents and Settings\\Administrator\\Application Data\\Mozilla\\Firefox\\Profiles\\hd8avan3.default");
		FirefoxProfile profile = new FirefoxProfile(file);
		WebDriver driver = new FirefoxDriver(profile);
	}
}
