package com.pack.bumbury.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.pack.bumbury.LogFile.LogFileLocationFinder;
import com.pack.bumbury.download.Download;
import com.pack.bumbury.mail.EmailNotification;
import com.pack.bumbury.property.ReadPropFile;

public class BumburyClass {
		public static void start() {
		LogFileLocationFinder.getValueForLogFile();
		final Logger logger = Logger.getLogger(BumburyClass.class);
		ReadPropFile.readPropFile();
		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.enterLocalPassiveMode();
			ftpClient.connect(ReadPropFile.ftpUrl.trim(), 21);
			ftpClient.login(ReadPropFile.user, ReadPropFile.pass);
			logger.info(">> Logging start");
			logger.info("Connected to Server");
			Download download = new Download();
			//System.out.println( "Reading from main class :"+ReadPropFile.savePath);
			Download.countFiles = 0;
			download.downloadDirectoryAndDecrypt(ftpClient,
					ReadPropFile.source1, "", ReadPropFile.savePath,
					ReadPropFile.target1);
		download.downloadDirectoryAndDecrypt(ftpClient,	ReadPropFile.source2, "", ReadPropFile.savePath,ReadPropFile.target2);
		download.downloadDirectoryAndDecrypt(ftpClient,ReadPropFile.source3, "", ReadPropFile.savePath,ReadPropFile.target3);

			if (Download.countFiles > 0) {
				EmailNotification
						.sendMail(
								ReadPropFile.reciever,
								"Citibank Decryption Status >> "
										+ (Download.status ? "Files Decrypted Successfully"
												: "Files Decryption Failed")
										+ " >> "
										+ (new SimpleDateFormat(
												"dd-MMM-yyyy HH:mm:ss")
												.format(Calendar.getInstance()
														.getTime())),
								Download.body);
				Download.status = true;
				Download.body = "";
			}
			else{
				logger.info("No Files on server to download");
				EmailNotification
				.sendMail(
						ReadPropFile.reciever,
						"Citibank Decryption Status >> "
								+ ("File not available on server to download")
								+ " >> "
								+ (new SimpleDateFormat(
										"dd-MMM-yyyy HH:mm:ss")
										.format(Calendar.getInstance()
												.getTime())),
						Download.body);
		Download.status = false;
		Download.body = "";
			    }

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.warn("Unable to connect to FTP server : "+ex);
		}
		logger.info("Disconnected ");
		logger.info(">> Logging ends");
		logger.info(" ");
		logger.info(" ");
		logger.info(" ");
		logger.info(" ");
	}
		public static void main(String args[]){
		start();
		}
}
