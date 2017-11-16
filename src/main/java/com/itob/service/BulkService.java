package com.itob.service;

import com.itob.exception.CustomException;
import com.itob.util.Constants;
import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BulkService {

    private final static Logger LOG = LoggerFactory.getLogger(BulkService.class);

    private final UtilityService utilityService;

    public BulkService(UtilityService utilityService) {
        this.utilityService =utilityService;
    }

    public List<String> checkFileAndGenerateErrorReport(byte [] fileData, String orgUnitId)
        throws CustomException, IOException {

        //creating arrayList for the file report
        List<String> fileErrorReportList=new ArrayList<>();
//        BulkFile bulkFile = null;
//        /*todo delete me this is dummny data for error report
        fileErrorReportList.add("Error , Yes Error,");
        fileErrorReportList.add("Error , Yes Error,");
        fileErrorReportList.add("Error , Yes Error,");
        fileErrorReportList.add("Error , Yes Error,");
        fileErrorReportList.add("Error , Yes Error,");
        return fileErrorReportList;

        /*InputStream is;
        is = new ByteArrayInputStream(fileData);
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(is));

        String line;

        int count = 0;

        while ((line = reader.readLine()) != null) {
            String[] strArray = line.split(",");

            //check minimum line record length for example 4
            if (strArray.length < 4) {
				*//*throw new AbsCustomException(absUtilityService.fetchStatus(
						AbsErrorConstants.ERROR_CODE_FILE_CONTAIN_WHITE_SPACES, new Object[] {}, count+1));*//*
                fileErrorReportList.add(absUtilityService.fetchErrorWithLineNumber(
                    AbsErrorConstants.ERROR_CODE_FILE_CONTAIN_WHITE_SPACES, new Object[]{}, count + 1));
            }

            if (count == 0) {
//             Header e.g.   H,Test file1,5-October-16,Details,Details etc
                if (strArray[0].equals("H")) {
                    //check file header length based on requirement for example length =5
                    if (strArray.length == 5) {
                        // 1. Check file header info depends on requirement
                        // 2. Check file for duplication if its the requirement
                        // 3. Check file min,max length and file name regex
                        // 4. Check file upload date
                        // 5. other checks for header
                        // 6. for reference see vrg BulkDisbursementLogicImpl


                    } else {
						*//*throw new AbsCustomException(absUtilityService.fetchStatus(AbsErrorConstants.ERROR_CODE_FILE_INCOMPLETE_INFO,
								new Object[] {}, count+1));*//*
                        fileErrorReportList.add(absUtilityService.fetchErrorWithLineNumber(AbsErrorConstants.ERROR_CODE_FILE_INCOMPLETE_INFO,
                            new Object[]{}, count + 1));
                    }
                } else {
					*//*throw new AbsCustomException(absUtilityService.fetchStatus(AbsErrorConstants.ERROR_CODE_FILE_HEADER_NOT_FOUND,
							new Object[] {}, count+1));*//*
                    fileErrorReportList.add(absUtilityService.fetchErrorWithLineNumber(AbsErrorConstants.ERROR_CODE_FILE_HEADER_NOT_FOUND,
                        new Object[]{}, count + 1));
                    break;
                }
            } else if (count > 0) {
//           record e.g.     ,00000-0004555-0,03400000555,BTI-175,10.00,Details,
                if (strArray[0] == null || strArray[0].equals("")) {
//                    check the record according to the requirement
                    if (strArray.length == 6) {


                    }

                } else {
					*//*throw new AbsCustomException(absUtilityService.fetchStatus(AbsErrorConstants.ERROR_CODE_FILE_INVALID_INFO,
							new Object[] {}, count+1));*//*
                    fileErrorReportList.add(absUtilityService.fetchErrorWithLineNumber(AbsErrorConstants.ERROR_CODE_FILE_INVALID_INFO,
                        new Object[]{}, count + 1));
                }
            }
            count++;

        }*/

//        check the number of record in header = counted records
       /* if (bulkFile !=null && count - 1 != bulkFile.getCount()) {
			throw new AbsCustomException(absUtilityService.fetchStatus(AbsErrorConstants.HEADER_TXNS_NOT_SAME,
					new Object[] {}, 1));

            fileErrorReportList.add(absUtilityService.fetchErrorWithLineNumber(AbsErrorConstants.HEADER_TXNS_NOT_SAME,
                new Object[]{}, 1));
        }

        if(fileErrorReportList.size()> AbsConstants.INT_ZERO) {
            throw new AbsCustomException(absUtilityService.fetchStatus(AbsErrorConstants.ERROR_BULK_FILE_GENERAL_ERROR,
                new Object[]{}));
        }
       */
//        return fileErrorReportList;
    }


    public static boolean uploadFileAtServer( String filePath, String fileName, final byte[] content, String ip, String pass,
                                              String user) throws JSchException, SftpException, IOException {

       /* ip = "127.0.0.1";
        pass = "password";
        user = "tester";
        filePath = "/UploadedFiles/csvFile.csv";*/

        LOG.info("IP Address=:" + ip);
        LOG.info("Password=:" + pass);
        LOG.info("user=:" + user);

        String uploadIP = ip; //127.0.0.1
        String uploadUser = user; //abacus
        String uploadPassword = pass; //abacus
        InputStream inputStreamContent = null;
        ChannelSftp sftpChannel = null;
        Session jSession = null;
        boolean uploadStatus = false;

        try {
            // Get Session on Doc Destination Server
            JSch jsch = new JSch();
            jSession = jsch.getSession(uploadUser, uploadIP);
            jSession.setPassword(uploadPassword);
            jSession.setConfig("StrictHostKeyChecking", "no");
            LOG.info("Establishing Connection for Doc upload...");
            jSession.connect();

            LOG.info("Connection established.");
            LOG.info("Creating SFTP Channel.");
            sftpChannel = (ChannelSftp) jSession.openChannel("sftp");
            sftpChannel.connect();
            LOG.info("SFTP Channel created.");

            // InputStream of File content
            inputStreamContent = new ByteArrayInputStream(content);
            // Upload File
            sftpChannel.put(inputStreamContent, filePath + fileName); //filePath =   /app/UploadFiles/

            LOG.info("File Uploaded. " + filePath + fileName);

            uploadStatus = true;

        } catch (JSchException jex) {
            LOG.error("JSch exception while uploading Doc..." + jex.getLocalizedMessage());
            throw jex;
        } catch (SftpException e) {
            LOG.error("Sftp exception while uploading Doc..." + e.getLocalizedMessage());
            throw e;
        } finally {

            // Close inputStream and disconnect JSch session
            if (inputStreamContent != null)
                inputStreamContent.close();

            if (sftpChannel != null && sftpChannel.isConnected())
                sftpChannel.disconnect();

            if (jSession != null && jSession.isConnected())
                jSession.disconnect();
        }

        return uploadStatus;

    }

    public static byte[] readFileFromServer(String fileName) {

        byte[] file = null;
        Session jSession = null;
        ChannelSftp sftpChannel = null;

        try {

            String uploadIP = Constants.SFTP_SERVER_IP;
            String uploadUser = Constants.SFTP_USER_NAME;
            String uploadPassword = Constants.SFTP_USER_PASSWORD;
            // Get Session on Doc Destination Server
            JSch jsch = new JSch();
            jSession = jsch.getSession(uploadUser, uploadIP);
            jSession.setPassword(uploadPassword);
            jSession.setConfig("StrictHostKeyChecking", "no");
            LOG.info("Establishing Connection for Doc upload...");
            jSession.connect();

            LOG.info("Connection established.");
            LOG.info("Creating SFTP Channel.");
            sftpChannel = (ChannelSftp) jSession.openChannel("sftp");
            sftpChannel.connect();
            LOG.info("SFTP Channel created.");

            LOG.info("");

            String filePath = Constants.SFTP_FILE_UPLOAD_PATH + fileName; // + File.separator +
            LOG.info("filePath: " + filePath);

            InputStream is = sftpChannel.get(filePath);
            LOG.info("isFile:" + is);

            file = IOUtils.toByteArray(is);
            LOG.info("File Created bytes: " + file);

            //todo read these comments for assistance
            //1. Now you have received file form SFTP server
            //2. Set the file to a bean having byte []
            //3. You can also return the list of SFTP server filed upload files if you have the name
            //4. retrieve all the files one by one


        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}
