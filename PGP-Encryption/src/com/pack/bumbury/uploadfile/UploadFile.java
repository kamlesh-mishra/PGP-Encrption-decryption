package com.pack.bumbury.uploadfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.pack.bumbury.delete.FolderDelete;
import com.pack.bumbury.download.Download;
import com.pack.bumbury.property.ReadPropFile;
import com.pack.bumbury.server.FTPServerDirectory;
import com.pack.bumbury.server.MoveRemoteFile;

public class UploadFile {
    static Logger logger =Logger.getLogger(UploadFile.class);
	public static void uploadSingleFile(String localFilePath) throws IOException {
		//logger.info("File Uploading...");
		//getting file name for server
		File file=new File(localFilePath);
		String fileNameForServer=file.getName();
		String remoteFilePath=Download.serverTargetDir+"/"+fileNameForServer;
		//System.out.println("remote filepath :"+remoteFilePath);
		File localFile = new File(localFilePath);
		ReadPropFile.readPropFile();
		FTPClient ftpClient = new FTPClient();
		InputStream inputStream=null;
		try {
			ftpClient.enterLocalPassiveMode();
			ftpClient.connect(ReadPropFile.ftpUrl.trim(), 21);
			ftpClient.login( ReadPropFile.user.trim(),ReadPropFile.pass.trim());
			//logger.info(">> Upload");
			inputStream = new FileInputStream(localFile);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.storeFile(remoteFilePath, inputStream);
			logger.info("File uploaded : "+remoteFilePath);
			ftpClient.logout();
			ftpClient.disconnect();
			//FolderDelete.deleteDirectory(new File(ReadPropFile.savePath));
			//logger.info("Local source file deleted : "+localFilePath);
			} catch (IOException ex) {
				ex.printStackTrace();
				logger.error("File Upload Failed :"+remoteFilePath+" reason :"+ex);
			}
			finally
			{
				inputStream.close();
			}

	}

}
