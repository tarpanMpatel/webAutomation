package com.init;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.internal.Utils;

import com.demo.homePage.HomeIndexPage;
import com.demo.homePage.HomeVerificationPage;

public class SeleniumInit{

	public String suiteName = "";
	public String testName = "";
	/* Minimum requirement for test configuration */
	protected String testUrl;										// Test url
	protected String seleniumHub; 									// Selenium hub IP
	protected String seleniumHubPort; 								// Selenium hub port
	protected String targetBrowser; 								// Target browser
	protected static String test_data_folder_path = null;
	public static String currentWindowHandle = "";					// Get Current Window handle
	public static String browserName = "";
	public static String osName = "";
	public static String browserVersion = "";
	public static String browsernm = "";
	public static String cleanFlag = "";
	public static String datetime = "";
	public static String suiteNameBS ;


	/*
	 * Object Declaration
	 * of Modules Classes
	*/		
	public HomeIndexPage homeIndexPage;
	public HomeVerificationPage homeVerificationPage;
	
	protected static String screenshot_folder_path = null;
	public static String currentTest;				// current running test

	protected static Logger logger = Logger.getLogger("testing");
	//	protected RemoteWebDriver driver;
	public WebDriver driver;

	//	Common Common = new Common(driver);

	/* Page's declaration */

	/**
	 * Fetches current Date time.
	 *
	 */
	@BeforeSuite(alwaysRun = true)
	public void setCurrentDateTime(ITestContext testContext){		
		  suiteNameBS ="["+TestData.getProperties("TestUrlFromSuite")+"] "+testContext.getSuite().getName()+" ["+TestData.getCurrentDateTime()+"]"; log(suiteNameBS);
		
	}

	
	/**
	 * Fetches suite-configuration from XML suite file.
	 * 
	 * @param testContext
	 */

	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) {
				
		testName = testContext.getName();
		testUrl = TestData.getURL();
	//	testUrl = testContext.getCurrentXmlTest().getParameter("selenium.url");
	//	testUrl = TestData.getProperties("TestUrlFromSuite");
		/* System.out.println("======" + testUrl + "========="); */
		seleniumHub = testContext.getCurrentXmlTest().getParameter("selenium.host");
		seleniumHubPort = testContext.getCurrentXmlTest().getParameter("selenium.port");
		targetBrowser = testContext.getCurrentXmlTest().getParameter("selenium.browser");
		browsernm=targetBrowser;
	}

	/**
	 * WebDriver initialization
	 * 
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ParseException 
	 */
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext, ITestResult testResult) throws IOException, InterruptedException, ParseException {
		
		currentTest = method.getName(); // get Name of current test.
		URL remote_grid = new URL("http://" + seleniumHub + ":"	+ seleniumHubPort + "/wd/hub");

		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";

		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();

		DesiredCapabilities capability = null;

		//	System.out.println("before ff loop............");
		if (targetBrowser == null || targetBrowser.contains("firefox")) {
			FirefoxProfile profile = new FirefoxProfile();
			System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");

			profile.setPreference("dom.max_chrome_script_run_time", "999");
			profile.setPreference("dom.max_script_run_time", "999");
			profile.setPreference("browser.download.folderList", 2);
			//	profile.setPreference("browser.download.dir", path);
			profile.setPreference("browser.helperApps.neverAsk.openFile",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download,manager.focusWhenStarting", false);
			//	profile.setPreference("browser.download.useDownloadDir",true);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", false);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);

			profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			profile.setPreference("pdfjs.disabled", true);
			
			profile.setAcceptUntrustedCertificates(true);
			profile.setPreference("security.OCSP.enabled", 0);
			//	profile.setEnableNativeEvents(false);
			profile.setPreference("network.http.use-cache", false);


			//	FirefoxBinary fb = new FirefoxBinary(new File("‪C:\\Program Files\\Mozilla Firefox\\firefox.exe"));

			profile.setPreference("gfx.direct2d.disabled", true);
			profile.setPreference("layers.acceleration.disabled", true);
			
			//	profile.setPreference("webgl.force-enabled", true);
			//	Proxy proxy = new Proxy().setHttpProxy("localhost:3129");

			//	cap.setCapability(CapabilityType.PROXY, proxy);
			//	capability.setCapability("moz:firefoxOptions.binary", "‪C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			
			capability = DesiredCapabilities.firefox();
			// 	proxy code
			// 	capability.setCapability(CapabilityType.PROXY,proxy);
			capability.setJavascriptEnabled(true);

			capability.setCapability(FirefoxDriver.PROFILE, profile);
			browserName = capability.getBrowserName();
			osName = System.getProperty("os.name");
			browserVersion = capability.getVersion().toString();

			System.out.println("=========" + "firefox Driver " + "==========");

			driver = new RemoteWebDriver(remote_grid, capability);

		} else if (targetBrowser.contains("ie8")) {

			capability = DesiredCapabilities.internetExplorer();
			capability.setPlatform(Platform.ANY);
			capability.setBrowserName("internet explorer");
			// 	capability.setVersion("8.0");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			browserName = capability.getBrowserName();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();

		} else if (targetBrowser.contains("ie9")) {
			
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			browserName = capability.getBrowserName();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
		} 
		else if (targetBrowser.contains("ie11")) {

			capability = DesiredCapabilities.internetExplorer();
			System.setProperty("webdriver.ie.driver","D:\\FalkonryAutomation\\FalkonryAutomation\\drivers\\geckodriver.exe");

			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			browserName = capability.getVersion();
			capability.getPlatform();
			osName = Platform.getCurrent().name();
			browserVersion = capability.getVersion();

			driver = new RemoteWebDriver(remote_grid, capability);

		} else if (targetBrowser.contains("opera")) {
			
			capability = DesiredCapabilities.operaBlink();
			System.setProperty("webdriver.opera.driver", "driver/operadriver.exe");

			capability.setJavascriptEnabled(true);
			browserName = capability.getVersion();
			osName = capability.getPlatform().getCurrent().name();
			browserVersion = capability.getVersion();

			driver = new OperaDriver(capability);

		} else if (targetBrowser.contains("chrome")) {

			ChromeOptions options = new ChromeOptions();
			options.addArguments("no-sandbox");
			options.addArguments("--start-maximized");
			capability = DesiredCapabilities.chrome();

			String chromedriverPath = "driver/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver",chromedriverPath);

			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();

			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("profile.default_content_setting_values.media_stream_mic", 1);
			chromePrefs.put("profile.default_content_setting_values.media_stream_camera", 1);
			chromePrefs.put("profile.default_content_setting_values.geolocation", 1);
            chromePrefs.put("profile.default_content_setting_values.notifications", 1);

            capability.setCapability(ChromeOptions.CAPABILITY, options);

			options.setExperimentalOption("prefs", chromePrefs);

			capability.setBrowserName("chrome");
			capability.setJavascriptEnabled(true);
			browserName = capability.getVersion();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			options.addArguments("disable-geolocation");
			
			String OS = System.getProperty("os.name").toLowerCase();
			System.out.println(" ### CURRENT EXECUTION O.S -->"+OS);
			
			if(OS.indexOf("win") >= 0){
				driver = new ChromeDriver();
			}else {
	        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	        capabilities.setBrowserName("chrome");
	        String CIServerURL="http://104.248.6.102:4444/wd/hub";
	        String localServerURL="http://localhost:4444/wd/hub";
	
	        try {
	        	driver = new RemoteWebDriver(new URL(CIServerURL), capabilities);
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }
		  }			

		} else if (targetBrowser.contains("safari")) {

			// System.setProperty("webdriver.safari.driver","/Users/jesus/Desktop/SafariDriver.safariextz");
			// driver = new SafariDriver();
			SafariDriver profile = new SafariDriver();

			capability = DesiredCapabilities.safari();
			capability.setJavascriptEnabled(true);
			capability.setBrowserName("safari");
			browserName = capability.getBrowserName();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			//	capability.setCapability(SafariDriver.CLEAN_SESSION_CAPABILITY, profile);
			this.driver = new SafariDriver(capability);

		}
		else if (targetBrowser.contains("saucelabs")) {

			String USERNAME = "nishilpatel81";
			String AUTOMATE_KEY = "8cfc1add-3f9e-48c2-9de4-f5db1379e253";
			String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@ondemand.saucelabs.com:80/wd/hub";

			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability("platform", "Windows 8.1");
			caps.setCapability("version", "latest");
			driver = new RemoteWebDriver(new URL(URL), caps);

		}
		else if (targetBrowser.contains("browserstack")) {
			String USERNAME = "colkiwi1";
			String AUTOMATE_KEY = "5Z4YxnQTXyGay8CSzr6L";
			  
			String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

			DesiredCapabilities caps = new DesiredCapabilities();

			try{   
			    String bb = testContext.getCurrentXmlTest().getParameter("browserstack.browser");
			    if(bb.contains("chrome")){
				    caps.setCapability("browser", "Chrome");
				    caps.setCapability("browser_version", "81.0");
				    caps.setCapability("browserstack.selenium_version", "3.5.2");
				    caps.setCapability("os", "Windows");
				    caps.setCapability("os_version", "10");
				    
				    ChromeOptions options = new ChromeOptions();  
				   
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				    
					options.setExperimentalOption("prefs", chromePrefs);
					
				    
			    }
			    else if(bb.contains("firefox")){
			    	caps.setCapability("browser", "Firefox");
			    	caps.setCapability("browser_version", "58.0");
			    	caps.setCapability("browserstack.selenium_version", "3.10.0");
			    	caps.setCapability("os", "Windows");
					caps.setCapability("os_version", "8.1");
			    }
			    else if(bb.contains("ie11")){
			    	 caps.setCapability("browser", "IE");
			    	 caps.setCapability("browser_version", "11.0");
			    	 caps.setCapability("browserstack.selenium_version", "3.10.0");
			    	 caps.setCapability("os", "Windows");
					 caps.setCapability("os_version", "8.1");
			    }
			    else if(bb.contains("edge")){
			    	caps.setCapability("browser", "Edge");
			    	caps.setCapability("browser_version", "80.0");
			    	caps.setCapability("os", "Windows");
			    	caps.setCapability("os_version", "10");
			    	caps.setCapability("browserstack.selenium_version", "3.10.0");
			    }
			}
			catch(Exception e ){
				
			}
			    
			caps.setCapability("resolution", "1920x1080");
			caps.setCapability("browserstack.debug", "true");

			caps.setCapability("browserstack.timezone", "Kolkata");
			caps.setCapability("project", "NWU");

			caps.setCapability("build",suiteNameBS);
			caps.setCapability("name", testContext.getName());
			caps.setCapability("acceptSslCerts", "true");
			caps.setCapability("acceptInsecureCerts", "true");
			caps.setCapability("browserstack.networkLogs", "true");
			
			driver = new RemoteWebDriver(new URL(URL), caps);
		}
		
		suiteName = testContext.getSuite().getName();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(testUrl);
		System.out.println("********** Test URL ::-"+testUrl);	
		
		
		currentWindowHandle = driver.getWindowHandle();
		System.out.println("Current Window Handle ID : --->>" + currentWindowHandle);

		suiteName = testContext.getSuite().getName();
		System.out.println("Current Xml Suite is:=========>" + suiteName);
		
		
		/* Module Class Object's 
		 * Initialization
		*/
		homeIndexPage = new HomeIndexPage(driver);
		homeVerificationPage = new HomeVerificationPage(driver);
		
		System.err.println("Seession "+((RemoteWebDriver)driver).getSessionId());
		log("Seession "+((RemoteWebDriver)driver).getSessionId());
		
			}

	/**
	 * Log For Failure Test Exception.
	 * 
	 * @param tests
	 */
	private void getShortException(IResultMap tests) {

		for (ITestResult result : tests.getAllResults()) {

			Throwable exception = result.getThrowable();
			List<String> msgs = Reporter.getOutput(result);
			boolean hasReporterOutput = msgs.size() > 0;
			boolean hasThrowable = exception != null;
			if (hasThrowable) {
				boolean wantsMinimalOutput = result.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					log("<h3>"
							+ (wantsMinimalOutput ? "Expected Exception"
									: "Failure Reason:") + "</h3>");
				}

				//	Getting first line of the stack trace
				String str = Utils.stackTrace(exception, true)[0];
				Scanner scanner = new Scanner(str);
				String firstLine = scanner.nextLine();
				log(firstLine);
			}
		}
	}

	/**
	 * After Method
	 * 
	 * @param testResult
	 */

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {

		  ITestContext ex = testResult.getTestContext();
		   
		  try {
		   testName = testResult.getName();
		   if (!testResult.isSuccess()) {

			    /* Print test result to Jenkins Console */
			   markFailForBrowserSatck();
			   System.out.println();
			   System.out.println("TEST FAILED - " + testName);
			   System.out.println();
			   System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
			   System.out.println("\n");
			   Reporter.setCurrentTestResult(testResult);
	
			   /*Make a screenshot for test that failed */
			   String screenshotName = Common.getCurrentTimeStampString() + testName;
			   Reporter.log("<br> <b>Please look to the screenshot - </b>");
			   Common.makeScreenshot(driver, screenshotName);
			   Reporter.log("<hr size='4px' noshade color='red'>");
			   //	Reporter.log(testResult.getThrowable().getMessage());
			   getShortException(ex.getFailedTests());
		   } else {
				    try {
				    	/*Reporter.log("<hr size='4px' noshade color='green'>");*/
				    	Common.pause(5);
				    } catch (Exception e) {
				    	log("<br></br> Not able to perform");
				    }
				    
				    System.out.println("TEST PASSED - " + testName + "\n"); // Print
				    Reporter.setCurrentTestResult(testResult);
				    Reporter.log("<hr size='4px' noshade color='green'>");
				    markPassForBrowserSatck();
		   }

		   System.out.println("here is test status--------------------" + testResult.getStatus());   
		   
		   //	driver.manage().deleteAllCookies();

		   	driver.close();
			driver.quit();

		  } catch (Throwable throwable) {
		   System.out.println("message from tear down"
		     + throwable.getMessage());
		//   driver.manage().deleteAllCookies();

		   driver.close();
		   driver.quit();
		  }
	}

	

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void log(String msg) {
		System.out.println(msg);
		Reporter.log("<br></br>" + msg);
	}
	
	
	/* This log is For Test Description Which we define during the start of test case
	 * So when we need to print Test Description need to use This method
	 * 
	*/
	public static void testDescription(String msg) {
		System.out.println(msg);
		Reporter.log("<strong> <h3 style=\"color:DeepPink\"> " +"Test Description: "+ msg
				+ "</h4> </strong>");
	}
	
	public static void testSubDescription(String msg) {
		System.out.println(msg);
		Reporter.log("<strong> <h4 style=\"color:DeepPink\"> " +"Testcase : "+ msg
				+ "</h4> </strong>");
	}
	
	/* USe for Print the Test Case Id
	*/
	public static void testcaseId(String msg) {
		System.out.println(msg);
		Reporter.log("<strong> <h4 style=\"color:DarkViolet\"> " +"Test Case ID: "+ msg
				+ "</h4> </strong>");
	}
	
	
	/* Log Verification
	 * Used for specially Print the Verification Subject with Different Color and style
	 * 
	*/
	public static void logverification(int i , String msg) {
		System.out.println(msg);
		Reporter.log("<br></br><b style=\"color:OrangeRed \"> Expected Result-"+i+": </b><b>"+msg + "</b>");
	}
	
	
	
	/* In Browserstack we need to execute below REST-API for mark the Test cases as Pass
	 * So, After the test end Either in tearDown method or at end of the test case
	 * we have to call this method
	*/
	public void markPassForBrowserSatck() throws URISyntaxException, UnsupportedEncodingException, IOException {
		URI uri = new URI("https://tarpan2:BAwprphx24rUp2N6BrbP@api.browserstack.com/automate/sessions/"+((RemoteWebDriver) driver).getSessionId().toString()+".json");
		  HttpPut putRequest = new HttpPut(uri);

		  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		  nameValuePairs.add((new BasicNameValuePair("status", "PASSED")));
		  nameValuePairs.add((new BasicNameValuePair("reason", "")));
		  putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		  HttpClientBuilder.create().build().execute(putRequest);
		}

	
	/* In Browserstack we need to execute below REST-API for mark the Test cases as Failed
	 * So, After the test end Either in tearDown method or at end of the test case
	 *  we have to call this method
	*/
	public void markFailForBrowserSatck() throws URISyntaxException, UnsupportedEncodingException, IOException {
		  URI uri = new URI("https://tarpan2:BAwprphx24rUp2N6BrbP@api.browserstack.com/automate/sessions/"+((RemoteWebDriver) driver).getSessionId().toString()+".json");
		  HttpPut putRequest = new HttpPut(uri);

		  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		  nameValuePairs.add((new BasicNameValuePair("status", "FAILED")));
		  nameValuePairs.add((new BasicNameValuePair("reason", "")));
		  putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		  HttpClientBuilder.create().build().execute(putRequest);
		}
	
	
	public void logStatus(String Status) throws UnsupportedEncodingException, URISyntaxException, IOException {
        System.out.println(Status);  
        
		if (Status.equalsIgnoreCase("Pass")) {
			log(" <Strong><font color=#008000><b> &#10004 PASS</b></font></strong>");		
			/*if(browsernm.contains("browserstack")){
			markPass();
			}*/
		} else if (Status.equalsIgnoreCase("Fail")) {
			log("<br><Strong><font color=#FF0000><b>&#10008 FAIL</b></font></strong></br>");
			/*if(browsernm.contains("browserstack")){
			markFail();
			}*/
			 /*Make a screenshot for test that failed */
		    String screenshotName = Common.getCurrentTimeStampString()
		      + currentTest;
		    Reporter.log("<br> <b>Please look to the screenshot - </b>");
		    Common.makeScreenshot(driver, screenshotName);
		}
	}
	
	
}