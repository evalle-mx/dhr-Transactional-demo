//package net.tce.app.aws.impl;
//
//import java.io.File;
//import java.io.InputStream;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.regions.Region;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectResult;
//import com.amazonaws.services.s3.model.S3ObjectSummary;
//
//@Service("clientAWSServer")
//public class ClientAWSImpl {
//	
//	Logger log4j = Logger.getLogger( ClientAWSImpl.class );
//	  AmazonS3 s3 ;
////	  private  @Value("${aws_region}")String aws_region;
////	  private  @Value("${aws_name_bucket}")String aws_name_bucket;
////	  private  @Value("${files_repository_local}")boolean files_repository_local;
//	  private String aws_region="US_EAST_1";
//	  private String aws_name_bucket="dothr";
//	  private boolean files_repository_local= false;
//	
//	  
//	  @PostConstruct
//	  public void initIt()  {
//	  	log4j.debug(" initIt() -> aws_name_bucket="+aws_name_bucket+" aws_region="+aws_region+" files_repository_local="+files_repository_local+" s3="+s3);								 
//		if(!files_repository_local){
//			s3 = new AmazonS3Client(new ProfileCredentialsProvider("default").getCredentials());
//			s3.setRegion(Region.getRegion(Regions.valueOf(aws_region)));
//			log4j.info("Se crea la instancia ClientAWSImpl y AmazonS3 ="+s3);
//		}
//	  }
//	  
//	
//	/**
//	 * Pone un archivo en el Bucketb dado un objeto File
//	 * @param path, ubicacion del archivo en el bucket
//	 * @param file, el archivo que se pone en el bucket
//	 * @return  PutObjectResult
//	 */
//	public PutObjectResult putObject(String path, File file) {
//		 return s3.putObject(new PutObjectRequest(aws_name_bucket, path, file));
//	}
//
//	/**
//	 * Pone un archivo en el Bucketb dado un objeto InputStream
//	 * @param path, ubicacion del archivo en el bucket
//	 * @param inputStream, el archivo que se pone en el bucket
//	 * @param objectMetadata, informacion adicional en el request
//	 * @return  PutObjectResult
//	 */
//	public PutObjectResult putObject(String path, InputStream  inputStream, ObjectMetadata objectMetadata){
//		log4j.debug("#$ putObject ="+s3+" path="+path+" InputStream="+inputStream+" objectMetadata="+objectMetadata);
//		 return s3.putObject(new PutObjectRequest(aws_name_bucket, path, inputStream,objectMetadata));
//	}
//	
//	
//	/**
//	 * se elimina un objeto
//	 * @param folderPath, ubicacion del objeto 
//	 */
//	public void deleteObject(String path) {
//		 s3.deleteObject(aws_name_bucket, path);
//	}
//
//	/**
//	 * se eliminan todos los objetos  de un folder, menos el folder
//	 * @param folderPath, ubicacion del folder
//	 */
//	public void deleteObjectsInFolder(String folderPath) {
//		//se borran todos los objetos del folder, menos el folder
//		for (S3ObjectSummary file : s3.listObjects(aws_name_bucket, folderPath).getObjectSummaries()){
//			if(!file.getKey().equals(folderPath)){
//				log4j.info("Se borra del repositorio el objeto temporal="+file.getKey());
//				s3.deleteObject(aws_name_bucket, file.getKey());				
//			}	
//		}
//	}
//
//}
