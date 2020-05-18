package com.init;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.Properties;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestData {

	public static String rndmString(int length) {
		String rnd1 = RandomStringUtils.randomAlphabetic(length);
		return rnd1;
	}

	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	static int hour1 = TestData.randBetween(7, 12); // Hours will be displayed
													// in between 9 to 22
	static int min = TestData.randBetween(0, 59);

	static int hour2 = TestData.randBetween(13, 20);

	public static String intime = hour1 + ":" + min;
	public static String outtime = hour2 + ":" + min;

	public static int diff = (((hour2) * 60) + min) - (((hour1) * 60) + min);

	public static String total_time = Integer.toString(diff);

	/* Generate Random String
	 * as per length given in @param
	*/
	public static String generateRandomString(String preWrod, int length) {
		String fname = RandomStringUtils.randomAlphabetic(length);
		String rndm = preWrod+"_"+fname+"_automation";
		return rndm;
	}
	
	public static String generateRandomGroupName(String preWrod, int length) {
		String fname = RandomStringUtils.randomAlphabetic(length);
		String rndm = preWrod+"_"+fname+"_auto_group";
		return rndm;
	}
	
	
	public static String getCurrentDateTime(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Calcutta");
        String t = timeZone.getID().toString();
        df.setTimeZone(TimeZone.getTimeZone(t));
		System.out.println(df.format(calobj.getTime()));
		String time = df.format(calobj.getTime());
	return time;
	}
	
	
	/**
	 * For Read / Write making connection with ExcelSheet
	 * Pass Sheet number as parameter
	 * Get Data from the Excel
	 * @param sheetIndex
	 * @return Sheet
	 */
	
	public static Sheet getExcelSheet(int sheetIndex) {
		String dataFilePath = "Resources/DataConfigurations.xlsx";
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();
		Sheet firstSheet = null;

		try {

			/*System.out.println("full path " + datafile.getAbsolutePath()
					+ " con " + datafile.getCanonicalPath());*/

			FileInputStream inputStream = new FileInputStream(
					new File(fullpath));

			Workbook workbook = new XSSFWorkbook(inputStream);
			firstSheet = workbook.getSheetAt(sheetIndex);

			workbook.close();
			inputStream.close();

		} catch (Exception e) {

		}
		return firstSheet;
	}

	
	/**
	 * using getExcelSheet(sheetName) method
	 * Get the Cell Data from Excel Sheet
	 * @param inside method sheet number, row number, cell number
	 * @return String
	 */
	public static String getDataFromExcelCell() {	
		DataFormatter formatter = new DataFormatter(); 
		 Cell cell = getExcelSheet(0).getRow(3).getCell(3);
		 String username = formatter.formatCellValue(cell);
		 return username;
	}
	
	
	/*
	 * Set/Write Key and values into 
	 * Properties File 
	 * 
	*/
	public static Properties prop = new Properties();
	
	public static void setProperties(String key, String value) {

		  String fileName = System.getProperty("user.dir") +"/Resources/DataConfiguration.properties"; 
		  try { 
			  InputStream inputStream =new FileInputStream(fileName); 
			  prop.load(inputStream); 
			  inputStream.close();
		  
		  FileOutputStream outputStream = new FileOutputStream(fileName);
		  
		  prop.setProperty(key, value);
		  System.out.println("#####    Setting Property:|   Key-"+key+"   |  value-"+value); prop.store(outputStream, null);
		  
		  outputStream.close();
		  
		  } catch (IOException ioe) { ioe.printStackTrace();
		  System.out.println("Sorry, unable to locate file in : " + fileName); }
		 
		
		}

	
	
	/*
	 * Get the Value from properties file
	 * @param = Key
	*/
	public static String getProperties(String key) {

        String fileName = System.getProperty("user.dir") + "/Resources/DataConfiguration.properties";

        try {
            InputStream input = new FileInputStream(fileName);
            prop.load(input);
            String value = prop.getProperty(key);
            input.close();
            return value.trim(); //  Return value after removing leading and trailing blank spaces.

        } catch (IOException e) {
            e.printStackTrace();
            return "Sorry, unable to find " + fileName;
        }
    }
	
	
	public static String readDataProperties(String propertieName)
	  {
	     String result="";
	     File file = new File("Resources/DataConfiguration.properties");
	     FileInputStream fileInput = null;
	     try {
	      fileInput = new FileInputStream(file);
	     } catch (FileNotFoundException e) {
	      e.printStackTrace();
	     }
	     Properties prop = new Properties();
	     try {
	       prop.load(fileInput);
	       result = prop.getProperty(propertieName);
	       System.out.println(result);
	     } catch (Exception e) {
	      System.out.println("Exception: " + e);
	     } finally {
	     }
	     
	     return result;
	  }
	
	
	public String getProperties(String path, String key) {
		Properties propwrite = new Properties();
        String fileName = System.getProperty("user.dir") + "/"+path;

        try {
            InputStream input = new FileInputStream(fileName);
            propwrite.load(input);
            String value = propwrite.getProperty(key);
            input.close();
            return value.trim(); //  Return value after removing leading and trailing blank spaces.

        } catch (IOException e) {
            e.printStackTrace();
            return "Sorry, unable to find " + fileName;
        }
    }
	
			public static long sysStartTime(){
				long startTime = System.currentTimeMillis();
				return startTime ;
			}
			
			public static long sysEndTime(){
				long startTime = System.currentTimeMillis();
				return startTime ;
			}
	
			public static long calTotaltimeTaken(long start , long end){
				long total = (end - start)/1000;
				return total;
			}
	
			public static String randomStringGenerate(int i) {
				String sc = "DS "+rndmString(i)+" by Automation "+rndmString(i);
				return sc;
			}
		
			
			
			public static String getURL() {		
				/*DataFormatter formatter = new DataFormatter(); 
				Cell cell = getExcelSheet(0).getRow(3).getCell(4);*/
				String url = getProperties("TestUrl");	 
				return url;
			}
			
						 
			
		
		
		public static String getRecentFileName(String path){
			
			Path parentFolder = Paths.get(path);
			String s = null; 
			
			
			Optional<File> mostRecentFileOrFolder =
			    Arrays
			        .stream(parentFolder.toFile().listFiles())
			        .max(
			            (f1, f2) -> Long.compare(f1.lastModified(),
			                f2.lastModified()));
		 
			Optional<File> mostRecentFile =
				    Arrays
				        .stream(parentFolder.toFile().listFiles())
				        .filter(f -> f.isFile())
				        .max(
				            (f1, f2) -> Long.compare(f1.lastModified(),
				                f2.lastModified()));		
			
			if (mostRecentFile.isPresent()) {
			    File mostRecent = mostRecentFileOrFolder.get();
			    
			    s = mostRecent.getName(); 
			    
			    System.out.println("most recent is:" + s);
			} else {
			    System.out.println("folder is empty!");
			}
			return s;
		}
		
		
	public static String getRecentFolderName(String path){
				
				Path parentFolder = Paths.get(path);
				String s = null; 
				
				Optional<File> mostRecentFileOrFolder =
				    Arrays
				        .stream(parentFolder.toFile().listFiles())
				        .max(
				            (f1, f2) -> Long.compare(f1.lastModified(),
				                f2.lastModified()));
			
				Optional<File> mostRecentFolder =
					    Arrays
					        .stream(parentFolder.toFile().listFiles())
					        .filter(f -> f.isDirectory())
					        .max(
					            (f1, f2) -> Long.compare(f1.lastModified(),
				                f2.lastModified()));
			
			
				
				if (mostRecentFolder.isPresent()) {
				    File mostRecent = mostRecentFileOrFolder.get();
				    
				    s = mostRecent.getName(); 
				    
				    System.out.println("most recent is:" + s);
				   
				} else {
				    System.out.println("folder is empty!");
				}
				return s;
			}
	
		public static void deleteFolder(String pathtofolder){
			File index = new File(pathtofolder);
			
			System.err.println(index);
			
			String[]entries = index.list();
			for(String sss: entries){
			    File currentFile = new File(index.getPath(),sss);
			    currentFile.delete();
			}
			if (!index.exists()) {
			    index.mkdir();
			} else {
			    index.delete();
			    System.err.println("Folder deleted");
			}
			
		}
		
		
		public static String filesfromFolder(String pathf){
			String filename= null;
			
			File folder = new File(pathf);
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			    	filename = listOfFiles[i].getName();
			        System.out.println("File:::::: " + listOfFiles[i].getName());
			      } else if (listOfFiles[i].isDirectory()) {
			        System.out.println("Directory " + listOfFiles[i].getName());
			      }
			    }
			  return filename;  
		}
	
		
		
		
	/*	Unzip Files
	 *  Extract into another Folder
	 * 	
	*/		
		 public static String extractZipFile(String zipFilePath , String destDirectory ) throws IOException
		    {
		  //      String zipFilePath = "E:\\FalkonryAutomation\\FalkonryAutomation\\Download\\DS ExS by Automation Xja create Run Model full flow-EpisodeGallery-1508840785280.zip";
		  //      String destDirectory = "E:\\FalkonryAutomation\\FalkonryAutomation\\zipoutput";
		    	String filePath = null;
		        try {
		          filePath =   unzip(zipFilePath, destDirectory);
		        } catch (Exception ex) {
		            // some errors occurred
		            ex.printStackTrace();
		        }
		        return filePath;
		        }
		 
		 public static String unzip(String zipFilePath, String destDirectory) throws IOException {
		        
			 File destDir = new File(destDirectory).getAbsoluteFile();
		        String filePath = null;
		        
		        if (!destDir.exists()) {
		            destDir.mkdir();
		        }
		        
		        String path1 = new File(zipFilePath).getAbsolutePath();
		        System.err.println(path1);
		        
		        
		        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		        ZipEntry entry = zipIn.getNextEntry();
		        // iterates over entries in the zip file
		        while (entry != null) {
		             filePath = destDirectory + File.separator + entry.getName();
		            if (!entry.isDirectory()) {
		                // if the entry is a file, extracts it
		                extractFile(zipIn, filePath);
		            } else {
		                // if the entry is a directory, make the directory
		                File dir = new File(filePath);
		                dir.mkdir();
		            }
		            zipIn.closeEntry();
		            entry = zipIn.getNextEntry();
		        }
		        zipIn.close();
		        return  filePath.replaceAll("\\\\", "/");
		    }
		 private static final int BUFFER_SIZE = 4096;
		  private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		        byte[] bytesIn = new byte[BUFFER_SIZE];
		        int read = 0;
		        while ((read = zipIn.read(bytesIn)) != -1) {
		            bos.write(bytesIn, 0, read);
		        }
		        bos.close();
		    }
	    
	    	
}
	

