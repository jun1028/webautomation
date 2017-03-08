package com.example.tests;

import org.databene.benerator.anno.Database;
import org.databene.benerator.anno.Source;
import org.databene.feed4testng.FeedTest;
import org.databene.platform.db.DBSystem;
import org.testng.annotations.Test;

@Database(id = "db", url = "jdbc:mysql://127.0.0.1:3306/mysql", driver = "com.mysql.jdbc.Driver", user = "root", password = "hello1234")
public class feed4testngTest extends FeedTest {

	static DBSystem db;

	@Test(dataProvider = "feeder")
	@Source(id = "db", selector = "SELECT a.keyvalue FROM page.firstpage a")
	public void test(String key) {
		System.out.println(key);
	}

}
