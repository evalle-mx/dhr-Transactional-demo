package net.tce.admin.service.impl;

import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import net.tce.admin.service.JavaMailManagerService;
import net.tce.dto.CorreoTceDto;
import net.tce.util.Constante;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
/**
 * Clase (implementacion) diseñada para generar y enviar un objeto de correo
 * con valores de configuración en constantes, y valores de cuerpo obtenidos de un 
 * objeto CorreoTceDto
 * 
 * @author evalle
 *
 */
@Service("javaMailManagerService")
public class JavaMailManagerServiceImpl implements JavaMailManagerService{
	//Se leen las propiedades del archivo AppTCE.properties
	private  @Value("${MAIL_SERVER_HOST}")String MAIL_SERVER_HOST;	
	private  @Value("${MAIL_SERVER_PORT}")String MAIL_SERVER_PORT;	
	private  @Value("${MAIL_SERVER_PROTOCOL}")String MAIL_SERVER_PROTOCOL;
	private  @Value("${MAIL_SERVER_USERNAME}")String MAIL_SERVER_USERNAME;	
	private  @Value("${MAIL_SERVER_PASSWORD}")String MAIL_SERVER_PASSWORD;
	Logger log4j = Logger.getLogger( JavaMailManagerServiceImpl.class );

	@Autowired
	private SimpleAsyncTaskExecutor asynchTaskExecutor;
	
	/**
	 * Crea un hilo por cada llamada a sendMail
	 * @param correo, objeto con la informacion del correo a amndar
	 */
	public void threadMail(final CorreoTceDto correo) {
		if (this.asynchTaskExecutor != null) {
			this.asynchTaskExecutor.execute(new Runnable() {
				public void run() {
                	try {
                		sendMail( correo);
                	} catch (Exception e) {
						log4j.error("Error al mandar correo, numHilo:"+
									Thread.currentThread().getId()+"--> error:"+e.toString());
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	
	
	/**
	 * metodo que genera un correo electronico por medio de MimeMessage con
	 * información obtenida de un objeto CorreoTceDto
	 * @param correo
	 */
	private boolean sendMail(CorreoTceDto correo) {
		boolean sendit = false;
		if(correo!=null){
			
			/*/Prueba 2
			System.out.println("Adjuntando archivo..");
			FileSystemResource file = new FileSystemResource(correo.getAdjunto());
			helper.addAttachment("Adjunto.jpg", file);//*/
			
			/*/Prueba 3: agregar imagen in-Line
			// let's include the infamous windows Sample file (this time copied to c:/)
			FileSystemResource res = new FileSystemResource(new java.io.File("C:/Server/images/rss.png"));
			helper.addInline("identifier1234", res);//*/
			
	        try {
	        	JavaMailSenderImpl sender = new JavaMailSenderImpl();
				Properties javaMailProperties = new Properties(); 
				log4j.debug("$$  MAIL_SERVER_PORT="+MAIL_SERVER_PORT+
						" MAIL_SERVER_HOST="+MAIL_SERVER_HOST+
						" MAIL_SERVER_PROTOCOL="+MAIL_SERVER_PROTOCOL+
						" MAIL_SERVER_USERNAME="+MAIL_SERVER_USERNAME+
						" MAIL_SERVER_PASSWORD="+MAIL_SERVER_PASSWORD);
				sender.setHost(MAIL_SERVER_HOST); // "smtp.googlemail.com");
				sender.setPort(Integer.parseInt(MAIL_SERVER_PORT)); // 465);
				sender.setProtocol(MAIL_SERVER_PROTOCOL); // "smtps");
				sender.setUsername(MAIL_SERVER_USERNAME); //"mail@dotHr.net");
				sender.setPassword(MAIL_SERVER_PASSWORD); //"*******");
				javaMailProperties.put("mail.smtps.auth", true);
				sender.setJavaMailProperties(javaMailProperties);
				MimeMessage message = sender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);//Para adjuntar archivos
				helper.setFrom( new InternetAddress(correo.getRemitente()) );
				helper.setTo(new InternetAddress(correo.getDestinatario()));
				helper.setSubject(correo.getAsunto());
				 message.setText(correo.getCuerpo(),Constante.MAIL_MESSTEXT_CHARSET,
						 Constante.MAIL_MESSTEXT_SUBTYPE);
				 log4j.debug("Enviando E-mail -> getCuerpo: "+correo.getCuerpo());
	        	log4j.debug("Enviando E-mail: \"".concat(correo.getAsunto()).concat("\" a ").concat(correo.getDestinatario()) );
	        	sender.send(message);
	            sendit = true;
	            
	        }
	        catch (Exception ex) {
	        	log4j.error("Error al mandar correo");  
	        	ex.printStackTrace();
	        }
		 }
		return sendit;
	}

}
