package com.utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

public class RetryTestListener implements IAnnotationTransformer
{
	
	/**
	 * Invoke Retry Analyzer.
	 * @param annotation
	 * @param testClass
	 * @param testConstructor
	 * @param testMethod
	 * 
	 */
	public void transform(ITestAnnotation annotation, Class testClass,Constructor testConstructor, Method testMethod) 
	{
		IRetryAnalyzer retry = annotation.getRetryAnalyzer();

		if (retry == null)	
		{
			annotation.setRetryAnalyzer(RetryTest.class);
		}
		
	}
}
