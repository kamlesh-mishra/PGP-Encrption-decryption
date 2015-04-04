package com.pack.bumbury.server;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;


import org.apache.log4j.Logger;

import com.pack.bumbury.property.ReadPropFile;

public class MoveRemoteFile {
	static final Logger logger=Logger.getLogger(MoveRemoteFile.class); 
public static	void moveRemoteFileToCompletedSource(String existingFilePath, String newFilePath,String folder){
		ReadPropFile.readPropFile();
		FTPClient ftpClient = new FTPClient();
		try {
			
			ftpClient.connect(ReadPropFile.ftpUrl.trim(), 21);
			ftpClient.login(ReadPropFile.user.trim(), ReadPropFile.pass.trim());
			ftpClient.enterLocalPassiveMode();
			//logger.info("Connected to server");
			//file moving
			FTPServerDirectory.createRemoteFolderSource(folder);
		    ftpClient.rename(existingFilePath, newFilePath);
			logger.info("Moving server file : "+existingFilePath);
			logger.info("File moved to : "+newFilePath);
			// log out and disconnect from the server
			ftpClient.logout();
			ftpClient.disconnect();
			//logger.info("Disconnected from server");
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("File moving failed at server : "+existingFilePath);
		}
}
	

}
