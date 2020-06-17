package net.tce.assembler;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

public class TrackingAssembler extends CommonAssembler  {
	
	Logger log4j = Logger.getLogger( this.getClass());

	@Autowired
	private ConversionService converter;
}
