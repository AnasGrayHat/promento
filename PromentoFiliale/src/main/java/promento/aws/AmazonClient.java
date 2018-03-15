package promento.aws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    
    
    @SuppressWarnings("deprecation")
	@PostConstruct
    
    private void initializeAmazon() {
    	
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       this.s3client = new AmazonS3Client(credentials);
}

    //this method return the name of the file not the url !
	public String uploadFile(MultipartFile multipartFile , String fileName) {
		
		
		  String newfileName = "";
		    try {
		        File file = convertMultiPartToFile(multipartFile);
		         newfileName = generateFileName(fileName);
//		        fileUrl = endpointUrl + File.separator + bucketName + File.separator + newfileName;
		        uploadFileTos3bucket(newfileName, file);
		        file.delete();
		    } catch (Exception e) {
		       e.printStackTrace();
		    }
		    
		    return newfileName;
	}


	private void uploadFileTos3bucket(String fileName, File file) {
		
		 s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
		            .withCannedAcl(CannedAccessControlList.PublicRead));		
	}


	private String generateFileName(String fileName) {
	    return new Date().getTime() + "-" + fileName.replace(" ", "_");
	}


	private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
	    File convFile = new File(multipartFile.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(multipartFile.getBytes());
	    fos.close();
	    return convFile;
	}
    
//	public String deleteFileFromS3Bucket(String fileUrl) {
//	    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//	    s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
//	    return "Successfully deleted";
//	}
	
	
}
