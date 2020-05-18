package com.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTest implements IRetryAnalyzer
{

	 private int retryCounter = 0;
	 private int maxRetryCounter = 1;

	 /**
	  * Manage Retry Counter.
	  * @param result
	  * 
	  */
	 public boolean retry(ITestResult result) 
	 {
		 if (retryCounter < maxRetryCounter) 
		 {
            System.out.println("Retrying test Execution: " + result.getName() + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCounter+1) + " time(s).");
            retryCounter++;
            return true;
	      }
	        return false;
	  }
	 
	 /**
	  * Get Result Status   
	  * @param status
	  * @return
	  */
	 
     public String getResultStatusName(int status)
     {
    	 String resultName = null;
    	 if(status==1)
    	 	 resultName = "SUCCESS";
    	 if(status==2)
    		 resultName = "FAILURE";
    	 if(status==3)
    		 resultName = "SKIP";
		 return resultName;
    }

}
