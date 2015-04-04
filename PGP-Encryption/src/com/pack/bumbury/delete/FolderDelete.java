package com.pack.bumbury.delete;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FolderDelete {
		static Logger logger=Logger.getLogger(FolderDelete.class);	
	 
		 public static boolean deleteDirectory(File dir)
		 { if (dir.isDirectory()) 
		 {
		 File[] children = dir.listFiles(); 
		for (int i = 0; i < children.length; i++)
		 { 
		 boolean success = deleteDirectory(children[i]); 
		 if (!success) { return false; } } 
		 } 

		logger.info("removing file or directory : " + dir.getName());
		return dir.delete();

		}
		    


//    public static void main(String[] args)    {	
//   
//    	deleteDirectory(new File("c:\\test\\"));
//   	
//    }
// 
}