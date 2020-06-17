package net.tce.admin.adapter.rest;


import net.tce.exception.SystemTCEException;
import net.tce.dto.MensajeDto;
import net.tce.exception.FileException;
import net.tce.util.Mensaje;
import net.tce.util.UtilsTCE;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.hibernate.PropertyValueException;
//import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Clase que gestiona excepciones y maneja mensajes 
 * @author Goyo
 *
 */
public class ErrorMessageAdapterRest {
	Logger log4j = Logger.getLogger( this.getClass());

	@Autowired
	protected Gson gson;
	
	/**
	 * Maneja excepciones de tipo SystemTCEException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(SystemTCEException.class)
	public @ResponseBody String handleSystemTCEException(final SystemTCEException e)
	{
		log4j.error("&---& SystemTCEException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeErrorPolitica(); 
	}
	
	
//	/**
//	 * Maneja excepciones de tipo GenericJDBCException
//	 * @param e, objeto de la excepcion correspondiente
//	 * @return un mensaje JSON
//	 */
//	@ExceptionHandler(PropertyValueException.class)
//	public @ResponseBody String handlePropertyValueException(final PropertyValueException e)
//	{
//		log4j.error("&---&  handlePropertyValueException ERROR: "+e.toString(), e);
//		e.printStackTrace();
//	    return mensajeErrorPolitica(); 
//	}
		
	/**
	 * Maneja excepciones de tipo UncategorizedSQLException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(UncategorizedSQLException.class)
	public @ResponseBody String handleUncategorizedSQLException(final UncategorizedSQLException e)
	{
		log4j.error("&---&  UncategorizedSQLException ERROR: "+e.getSQLException().getMessage()+" --(bueno) "+
				e.getSQLException().getErrorCode(), e);
		e.printStackTrace();
	    return mensajeErrorPolitica(); 
	}
	
	/**
	 * Maneja excepciones de tipo DataIntegrityViolationException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public @ResponseBody String handleDataIntegrityViolationException(final DataIntegrityViolationException e)
	{
		log4j.error("&---&  DataIntegrityViolationException ERROR: "+e.getMostSpecificCause().getMessage(), e);
		e.printStackTrace();
	    return mensajeErrorPolitica(); 
	}
	
//	/**
//	 * Maneja excepciones de tipo ConstraintViolationException
//	 * @param e, objeto de la excepcion correspondiente
//	 * @return un mensaje JSON
//	 */
//	@ExceptionHandler(ConstraintViolationException.class)
//	public @ResponseBody String handleConstraintViolationException(final ConstraintViolationException e)
//	{
//		log4j.error("&---& ConstraintViolationException ERROR:"+e.getSQLState()+" -- "+e.getSQLException().getErrorCode()+
//				" --"+e.getSQLException().getMessage(), e);
//		e.printStackTrace();
//	    return mensajeErrorPolitica(); 
//	}
	
	/**
	 * Maneja excepciones de tipo JsonSyntaxException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(JsonSyntaxException.class)
	public @ResponseBody String handleJsonSyntaxException(final JsonSyntaxException e)
	{
		log4j.error("&---& JsonSyntaxException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeErrorPolitica(); 
	}
	
	/**
	 * Maneja excepciones de tipo JsonSyntaxException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(NumberFormatException.class)
	public @ResponseBody String handleNumberFormatException(final NumberFormatException e)
	{
		log4j.error("&---& NumberFormatException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeErrorPolitica(); 
	}
	
	/**
	 * Maneja excepciones de tipo ClassNotFoundException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(ClassNotFoundException.class)
	public @ResponseBody String handleClassNotFoundException(final ClassNotFoundException e)
	{
		log4j.error("&---& ClassNotFoundException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeErrorPolitica(); 
	}
	
	/**
	 * Maneja excepciones de tipo CannotCreateTransactionException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(CannotCreateTransactionException.class)
	public @ResponseBody String handleCannotCreateTransactionException(final CannotCreateTransactionException e)
	{
		log4j.error("&---& CannotCreateTransactionException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeErrorsistema(); 
	}
	
	/**
	 * Maneja excepciones de tipo CannotCreateTransactionException
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(FileException.class)
	public @ResponseBody String handleFileException(final FileException e)
	{
		log4j.error("&---& FileException ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeErrorsistema(); 
	}
	
	
	/**
	 * Maneja cualquier otra excepcion
	 * @param e, objeto de la excepcion correspondiente
	 * @return un mensaje JSON
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody String Exception(final Exception e)
	{
	    log4j.error("&---& Exception ERROR: "+e.toString(), e);
		e.printStackTrace();
	    return mensajeErrorsistema(); 
	}
	
	/**
	 * Crea un mensaje de error en general, para las politicas de negocio
	 * @return mensaje de error en JSON
	 */
	private String mensajeErrorPolitica(){
	    return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
						Mensaje.SERVICE_CODE_006,Mensaje.SERVICE_TYPE_FATAL,
						Mensaje.MSG_ERROR)); 
	}
	
	/**
	 * Crea un mensaje de error si hay falla en el sistema
	 * @return mensaje de error en JSON
	 */
	private String mensajeErrorsistema(){
		return UtilsTCE.getJsonMessageGson(null, new MensajeDto(null,null,
				Mensaje.SERVICE_CODE_000,Mensaje.SERVICE_TYPE_FATAL,
				Mensaje.MSG_ERROR)); 
	}
	
	/**
	 * Controlador expuesto que ejecuta la funcion ping para verificar conectividad con Aplicacion 
	 * @param json, mensaje JSON 
	 * @return,  mensaje JSON 
	 */
	@RequestMapping(value=net.tce.util.Constante.URI_PING, method=RequestMethod.POST,headers = net.tce.util.Constante.ACEPT_REST_JSON)
	public @ResponseBody String ping(@RequestBody String json) {
		return UtilsTCE.getJsonMessageGson(null, new MensajeDto("app","TransactionalMock",
				Mensaje.SERVICE_CODE_010, Mensaje.SERVICE_TYPE_INFORMATION,
				Mensaje.MSG_WARNING + " -ping OK-"));
	  } 
}
