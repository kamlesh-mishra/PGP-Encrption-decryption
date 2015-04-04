package com.pack.bumbury.decryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Logger;

import com.pack.bumbury.delete.FolderDelete;
import com.pack.bumbury.property.ReadPropFile;
import com.pack.bumbury.uploadfile.UploadFile;

public class DecryptFile {
	static final Logger logger=Logger.getLogger(DecryptFile.class);
	static String outputfile;
	public static boolean decrypt(String fileName,String outfile) {
		boolean state=false;
		ReadPropFile.readPropFile();
		File file = new File(fileName);
		// appending output filename
		outputfile = ReadPropFile.savePath + "\\"+ outfile+"\\"+file.getName();
		File outputFile_ = new File(outputfile);
		File parentDir = outputFile_.getParentFile();
		//System.out.println("parentDir= "+parentDir.getAbsolutePath());
		if (!parentDir.exists()) {
			logger.info("Local target dir created : "+parentDir);
			//System.out.println("creating target directory");
			parentDir.mkdirs();
		}

		// Preparing the command 
		StringBuffer buff = new StringBuffer();
		buff.append(ReadPropFile.cmdPath + " " + outputfile + " "+ ReadPropFile.passphrase + " " + fileName);
		String value = buff.toString();
		System.out.println("value from decrypt class :"+value);
		// executing the command on cmd prompt and storing to local directory
		try {
			//logger.info("Decrypt >>");
			Process p = Runtime.getRuntime().exec("cmd /c  " + value);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = reader.readLine();
			while (line != null) {

				line = reader.readLine();
			}
			logger.info("File decrypted : "+fileName);
			logger.info("File stored : "+outputfile);
			//System.out.println("file decrypted");
			//call upload function
			UploadFile.uploadSingleFile(outputfile);
			// FolderDelete.deleteFileOrDir(outputfile);
			state=true;
			
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("Decrypt failed : "+fileName);
			state=false;
		}
		return state;	
	}
/*public static void main(String args){
	decrypt("C:\\source_downloads_from_server\\source1\\src1a.txt.gpg","");
	
}*/


}