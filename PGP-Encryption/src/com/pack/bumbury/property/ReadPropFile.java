package com.pack.bumbury.property;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadPropFile {

	static FileOutputStream outputStream,out;
	static FileInputStream fis,fis1;
	static Properties p,p1;
	static InputStream inputStream;
	public static String ftpUrl;
	public static String downloadSavePath;
	public static String savePath;
	public static String user;
	public static String pass;
	public static String cmd;
	public static String cmdPath;
	public static String phrase;
	public static String passphrase;
	public static String reciever;
	public static String sender;
	public static String senderpass;
	public static String logloc;
	public static String target1;
	public static String target2;
	public static String target3;
	public static String source1;
	public static String source2;
	public static String source3;
	public static String secretinputfile;
    public static String secretoutputfile;
	
	public static void readPropFile()  {
		Logger logger=Logger.getLogger(ReadPropFile.class);
		try {
		/*	CodeSource codeSource = ReadPropFile.class.getProtectionDomain().getCodeSource();
			File jarFile=null;
			try{
			jarFile= new File(codeSource.getLocation().toURI().getPath());
		}catch(Exception e){}
		
			File jarDir = jarFile.getParentFile();
			File propFile=null;

			if (jarDir != null && jarDir.isDirectory()) {
			   propFile = new File(jarDir, "./connect.properties");
			}
			*/
			/*File pf = null;
					try{
						
						pf = new File(ReadPropFile.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
						
						
						
					}catch(Exception e){e.printStackTrace();}
					
			String pfs= pf.getAbsolutePath();
			System.out.println("pfs= "+pfs);
			*/
			
			fis = new FileInputStream(".//connect.properties");
			p = new Properties();
			p.load(fis);
			ftpUrl = p.getProperty("url");
			savePath =System.getProperty("java.io.tmpdir")+"source_downloads_from_server";
			cmd = p.getProperty("cmdpath");
			cmdPath =cmd.trim()+" --output";
			phrase=p.getProperty("passphrase");
			passphrase ="--batch --passphrase "+phrase.trim()+" -d";
			//passphrase=p.getProperty("passphrase");
			reciever=p.getProperty("reciever");
			sender=p.getProperty("sender");
			senderpass=p.getProperty("sender_password");
			//logloc=p.getProperty("logfileloc");
			target1=p.getProperty("target1");
			target2=p.getProperty("target2");
			target3=p.getProperty("target3");
			source1=p.getProperty("source1");
			source2=p.getProperty("source2");
			source3=p.getProperty("source3");
			user=p.getProperty("username");
			pass=p.getProperty("password");
			secretinputfile=p.getProperty("secretinpath");
            secretoutputfile=p.getProperty("secretoutpath");
            //System.out.println(" cmd path is  :"+passphrase);
		} catch (FileNotFoundException fnfe) {
			logger.error("Property file not found :"+fnfe);
		
		} catch (IOException ioe) {
		
			logger.error("Unable to open Prop file :"+ioe);
		} finally {
			try {
				fis.close();

			} catch (Exception e) {
				logger.error("Error from Property file :" + e);
			}

		}
		
		
		
	}
	 public static void setReadPropFile(String url,String cmdpath,String passphrase,String sender,String sender_password ,String reciever ,String source1,String source2,String source3,String target1,String target2,String target3,String username,String password)
	 {
	 File f;
	    try {
	        p = new Properties();
	        p.setProperty("url", url);
	        p.setProperty("cmdpath",cmdpath);
	        p.setProperty("passphrase",passphrase);
	         p.setProperty("sender", sender);
	        p.setProperty("sender_password",sender_password);
	        p.setProperty("reciever",reciever);
	         p.setProperty("source1", source1);
	        p.setProperty("source2",source2);
	        p.setProperty("source3",source3); 
	        p.setProperty("target1",target1);
	        p.setProperty("target2",target2);
	         p.setProperty("target3",target3); 
	        p.setProperty("username",username);
	        p.setProperty("password",password);
	        f = new File("C:\\Users\\Admin\\git\\bumbury\\Project - Bunbury-citi\\connect.properties");
	       out = new FileOutputStream( f );
	        p.store(out, "donee");
	    }
	    catch (IOException e ) {
	        e.printStackTrace();
	       // f.close();
	    }
	}
	
	public static void main(String args[])
	{
		readPropFile();
	}
	
}