//Global

package com.test.basetest;

import java.awt.List;
import java.awt.Robot;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.log4j.Level;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
/**
 * @author Li Jun Frank
 *
 */
 
public class Global2
{
	protected static HSSFWorkbook g_table;
	protected static String g_strMasterName;
	protected static String g_strChildName;
	private static Map<String, String> g_variable = new Hashtable();
	private static Map<String, String> g_ctype = new Hashtable();
	private static XSSFWorkbook g_workbook;
	private static XSSFSheet g_worksheet;
	protected static String g_homepath;
	protected static String g_resulthome;
	private static ChromeOptions g_chromeoptions;
	public static ChromeDriver g_chromedriver;
	private static int g_waitTimeOut;
	public static Properties g_env = new Properties();
	private static Actions g_Action;
	public static String g_timestamp;
	private static int g_MasterIndex = 0;
	public static Document g_resultRootNode = null;
	private static boolean g_isAllPass;
	private static Robot g_robot;
	private static CloseableHttpClient g_httpclient;
	private static RequestConfig g_requestConfig;
	
	public static RequestConfig getHttpConfig()
	{
		return g_requestConfig;
	}
	
	public static CloseableHttpClient getHttpClient()
	{
		return g_httpclient;
	}
	
	public static Robot getRobot()
	{
		return g_robot;
	}
	
	public static boolean getOverAllResult()
	{
		return g_isAllPass;
	}
	
	public static void failTest()
	{
		g_isAllPass = false;
	}
	
	protected static void setResultHome(String strRHome)
	{
		g_resulthome = strRHome;
	}
	
	public static String getResultHome()
	{
		return g_resulthome;
	}
	
	public static void setResultRoot(Document doc)
	{
		g_resultRootNode = doc;
	}
	
	public static Document getResultRoot()
	{
		return g_resultRootNode;
	}
	
	public static void addMasterIndex()
	{
		g_MasterIndex = g_MasterIndex + 1;
	}
	
	public static int getMasterIndex()
	{
		return g_MasterIndex;
	}
	
	protected static void setTimestamp()
	{
		String strtimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Timestamp(System.currentTimeMillis()));
		g_timestamp = strtimeStamp.replaceAll("\\.", ""); 
	}
	
	public static String getSystemTime()
	{
		String strDynamicTS = new SimpleDateFormat("HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
		return strDynamicTS.replaceAll("\\.", "");
	}
	
	public static String getSystemDate()
	{
		String strDynamicTS = new SimpleDateFormat("yyyy/MM/dd").format(new Timestamp(System.currentTimeMillis()));
		return strDynamicTS.replaceAll("\\.", "");
	}
	
	protected static void setAction()
	{
		g_Action = new Actions(Global2.getDriver());
	}
	
	public static Actions getAction()
	{
		return g_Action;
	}
	
	protected static void setEnv(InputStream objFileStream) throws Exception
	{
		g_env.load(objFileStream);
		return;
	}
	
	public static Properties getEnv()
	{
		return g_env;
	}
	
	protected static void setTimeOut(int numTimeOunt)
	{
		g_waitTimeOut = numTimeOunt;
		Global2.getDriver().manage().timeouts().implicitlyWait(numTimeOunt, TimeUnit.SECONDS);
	}
	
	public static int getTimeOut()
	{
		return g_waitTimeOut;
	}
	
	public static void initDriver()
	{
		try
		{
			org.apache.log4j.Logger mylog = org.apache.log4j.Logger.getLogger("");
			mylog.setLevel(Level.OFF);
			
			g_chromeoptions = new ChromeOptions();
			g_chromeoptions.addArguments("--test-type");
			g_chromeoptions.addArguments("--disable-extensions");
			//g_chromeoptions.addArguments("user-data-dir=" + Global.getHomePath() + "//MJ_LIB//src//utility//option");
			System.setProperty("webdriver.chrome.driver", Global2.getHomePath() + "\\chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--load-extension=/path/to/extension/directory"));
			g_chromedriver = new ChromeDriver(g_chromeoptions);
			g_chromedriver.manage().window().maximize();
			g_chromedriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			setTimestamp();
			g_isAllPass = true;
			g_robot = new Robot();
			Global2.getRobot().mouseMove(0, 0);
//			Report.r_Browser_Version = g_chromedriver.getCapabilities().getVersion();
//			Report.r_Browser_Name = g_chromedriver.getCapabilities().getBrowserName().toUpperCase();
//			Report.r_Local_MachineName = InetAddress.getLocalHost().getHostName();
			
			String arch = System.getenv("PROCESSOR_ARCHITECTURE");
			String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

			String realArch = arch.endsWith("64")
			                  || wow64Arch != null && wow64Arch.endsWith("64")
			                      ? "64" : "32";
			
//			Report.r_Local_OS = System.getProperty("os.name").toString() + " " + realArch;
		}
		catch(Exception e)
		{
			System.out.println("Error while initing driver.");
			e.printStackTrace();
		}
	}
	
	
	public static void initHttpClient()
	{
		try
		{
			org.apache.log4j.Logger mylog = org.apache.log4j.Logger.getLogger("");
			mylog.setLevel(Level.OFF);
			Global2.g_httpclient = HttpClients.createDefault();
			Global2.g_requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
//			Report.r_Browser_Version = "";
//			Report.r_Browser_Name = "HttpClient";
//			Report.r_Local_MachineName = InetAddress.getLocalHost().getHostName();
//			
			String arch = System.getenv("PROCESSOR_ARCHITECTURE");
			String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

			String realArch = arch.endsWith("64")
			                  || wow64Arch != null && wow64Arch.endsWith("64")
			                      ? "64" : "32";
			
//			Report.r_Local_OS = System.getProperty("os.name").toString() + " " + realArch;
		}
		catch(Exception e)
		{
			System.out.println("Error while initing driver.");
			e.printStackTrace();
		}
	}
	
	
	public static ChromeDriver getDriver()
	{
		return g_chromedriver;
	}
	
	public static void setGlobal(String strVarName, String strValue)
	{
		g_variable.put(strVarName, strValue);
		System.out.println("Set variable name '" + strVarName + "' with value '" + strValue + "'.");
	}
	
	public static String getGlobal(String strVarName)
	{
		if(g_variable.containsKey(strVarName))
		{
			return g_variable.get(strVarName);
		}
		else
		{
			return "";
		}
	}
	
	protected static void setworkbook(XSSFWorkbook workbook)
	{
		g_workbook = workbook;
		for(int i=0;i<g_workbook.getNumberOfSheets();i++)
		{
			XSSFSheet objSheet = g_workbook.getSheetAt(i);
			for(int j=0;j<=objSheet.getLastRowNum();j++)
			{
				XSSFRow objRow = objSheet.getRow(j);
				for(int k=0;k<=objRow.getLastCellNum();k++)
				{
					if(objRow.getCell(k) != null && !objRow.getCell(k).getStringCellValue().isEmpty())
					{
						if(objRow.getCell(k).getStringCellValue().contains("{{TS}}"))
						{
							String strData = objRow.getCell(k).getStringCellValue().replaceAll("\\{\\{TS\\}\\}", getTimeStamp());
							System.out.println("Data replaced to " + strData);
							objRow.getCell(k).setCellValue(strData);
						}
						if(objRow.getCell(k).getStringCellValue().contains("{{SX}}"))
						{
							String strData = objRow.getCell(k).getStringCellValue().replaceAll("\\{\\{SX\\}\\}", Global2.getEnv().getProperty("suffix"));
							System.out.println("Data replaced to " + strData);
							objRow.getCell(k).setCellValue(strData);
						}
					}
					
				}
			}
		}
	}
	
	public static String getTimeStamp()
	{
		//return "20160516205604581";
		return g_timestamp;
	}
	
	public static String getDynamicTimeStamp()
	{
		return (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Timestamp(System.currentTimeMillis()))).replaceAll("\\.", "");
	}
	
	public static XSSFWorkbook getWorkbook()
	{
		return g_workbook;
	}
	
	protected static void setMasterName(String strMasterName)
	{
		g_strMasterName = strMasterName;
	}
	
	protected static void setChildName(String strChildName)
	{
		g_strChildName = strChildName;
	}
	
	protected static void setDataSheet(XSSFSheet sheet)
	{
		g_worksheet = sheet;
	}
	
	public static String getCurrentSheetName()
	{
		return g_strChildName;
	}
	
	public static String getCurrentCaseName()
	{
		return g_strMasterName;
	}
	
	protected static XSSFSheet getDataSheet()
	{
		return g_worksheet;
	}
	
	public static String getHomePath()
	{
		return g_homepath;
	}
	public static String getRootpath()
	{
		return "L:\\mojing-qa\\";
	}
	
	protected static void initChartType()
	{
		Document objDoc = null;
		try
		{
			System.out.println(Global2.getHomePath() + "//MJ_LIB//src//lib//ChartTypeID.xml");
			File file = new File(Global2.getHomePath() + "//MJ_LIB//src//lib//ChartTypeID.xml");
		
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			objDoc = dBuilder.parse(file);
			System.out.println("Root element :" + objDoc.getDocumentElement().getNodeName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		
		NodeList listCType = objDoc.getElementsByTagName("ctype");
		
		for(int i=0;i<listCType.getLength();i++)
		{
			String strChartType = "";
			String strChartTypeID = "";
			NodeList listCTypeContent = listCType.item(i).getChildNodes();
			for(int j=0;j<listCTypeContent.getLength();j++)
			{
				if(listCTypeContent.item(j).getNodeName() != null && 
						listCTypeContent.item(j).getNodeName().equalsIgnoreCase("name"))
				{
					strChartType = listCTypeContent.item(j).getTextContent();
				}
				
				if(listCTypeContent.item(j).getNodeName() != null && 
						listCTypeContent.item(j).getNodeName().equalsIgnoreCase("id"))
				{
					strChartTypeID = listCTypeContent.item(j).getTextContent();
				}
			}
			if(!strChartType.isEmpty() && !strChartTypeID.isEmpty())
			{
				g_ctype.put(strChartType, strChartTypeID);
			}

		}
	}
	
	public static String getCTypeID(String strCTypeName)
	{
		if(g_ctype.containsKey(strCTypeName))
		{
			return g_ctype.get(strCTypeName);
		}
		else
		{
			return "";
		}
	}
	
	protected static void createResultFolder()
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			g_resultRootNode = builder.newDocument();
			
		}
		catch(Exception e)
		{
			System.out.println("Error while setting result folder.");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static String getExceptionMsg(Exception e)
	{
		StringWriter sw = new StringWriter();  
        PrintWriter pw = new PrintWriter(sw);  
        e.printStackTrace(pw);
		return sw.toString();
	}
}