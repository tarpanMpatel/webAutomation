package com.demo.homePage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.init.Common;
import com.init.SeleniumInit;

public class HomeIndex extends SeleniumInit {
	
	@Test
	public void requestCallBack() throws UnsupportedEncodingException, URISyntaxException, IOException {
		int numOfFailure = 0;
		int step=1;
		int exp=1;
		testcaseId(" TC_HP_01 ");
		testDescription(" To verify Request a call back works correctly.");
		log("Step "+step++ +" : Open url: <a>" + testUrl +"</a>");
	
		Common.pause(1);
		logverification(exp++,"To verify user redirects to the Home page and can see the all contents on the page.");
		if (homeVerificationPage.verifyKiwiQAHomepage()) {
			logStatus("pass");
		} else {
			logStatus("fail");
			numOfFailure++;
		}
		
		log("Step "+step++ +" : Check 'Request A Call'");
		Common.pause(1);
		
		String name = "test";
		String email = "testFramework@yopmail.com";
		String sub = "Check Feature";
		String msg = "Hello, This is test message to check framework.";
		
		homeIndexPage.callback(name, email, sub, msg);

		logverification(exp++, "To verify 'Request a Call' is successful");
		if (homeVerificationPage.verifyThankyouPage()) {
			logStatus("pass");
		} else {
			logStatus("fail");
			numOfFailure++;
		}
		
		if (numOfFailure > 0) {
			markFailForBrowserSatck();
			Assert.assertTrue(false);
		}
		else{
			markPassForBrowserSatck();
		}
	}
}