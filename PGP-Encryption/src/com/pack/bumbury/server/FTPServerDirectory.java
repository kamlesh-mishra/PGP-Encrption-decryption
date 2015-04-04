package com.pack.bumbury.server;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.pack.bumbury.property.ReadPropFile;

public class FTPServerDirectory {
	public static void createRemoteFolderSource(String folderName)
	{
		Logger logger=Logger.getLogger(FTPServerDirectory.class);
		ReadPropFile.readPropFile();
		String server = ReadPropFile.ftpUrl.trim();
		int port = 21;
		String user = ReadPropFile.user.trim();
		String pass = ReadPropFile.pass.trim();
		FTPClient ftpClient = new FTPClient();
		try {
		    ftpClient.connect(server, port);
		    int replyCode = ftpClient.getReplyCode();
		    if (!FTPReply.isPositiveCompletion(replyCode)) {
		        logger.error("Connection failed Server reply code: " + replyCode);
		        return;
		    }
		    boolean success = ftpClient.login(user, pass);
		    if (!success) {
		        logger.error("Could not login to the server");
		        return;
		    }
		    // Creates a directory
		    
		    String dirToCreate = "/"+folderName+"/"+"completed";
		    String dirToCreate1="/"+folderName+"/"+"error";
		    success = ftpClient.makeDirectory(dirToCreate);
		    		  ftpClient.makeDirectory(dirToCreate1);
		    if (success) {
		       logger.info("Dir completed created at server: " + dirToCreate);
		        logger.info("Dir error created at server: " + dirToCreate1);
		    } else {
		        logger.error("Failed to create directory.");
		    }
		    // logs out
		    ftpClient.logout();
		    ftpClient.disconnect();
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		 String[] replies = ftpClient.getReplyStrings();
	        if (replies != null && replies.length > 0) {
	            for (String aReply : replies) {
	               logger.info("SERVER: " + aReply);
	            }
	        }
	}
public static void main(String args[]){

	createRemoteFolderSource("source1");
}
}