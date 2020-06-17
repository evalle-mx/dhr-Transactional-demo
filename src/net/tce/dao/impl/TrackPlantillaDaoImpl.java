package net.tce.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import net.tce.dao.TrackPlantillaDao;
import net.tce.dto.TrackingDto;
import net.tce.util.Constante;

@Repository("trackPlantillaDao")
public class TrackPlantillaDaoImpl extends 
PersistenceGenericDaoImpl implements TrackPlantillaDao {

	Logger log4j = Logger.getLogger( this.getClass());
	String uriRoot = Constante.URI_TRACK_MODELO_RSC_POSICION;
	
	@Override
	public List<TrackingDto> getByPlantillaRol(Long idPlantillaRol) {
		log4j.debug("<getByPlantillaRol> obteniendo listado para idPlantillaRol: " + idPlantillaRol );
		String uriService = uriRoot+Constante.URI_GET;
		try {
			String resp = getJsonFile(uriService);
			log4j.debug("<getByPlantillaRol> resp: " + resp );
			//TODO CODIFICAR SIMILAR A PRODUCTIVO
			return null;
		} catch (Exception e) {
			log4j.fatal("<getByPlantillaRol> Fatal: ", e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteByPlantillaRol(Long idPlantillaRol) {
		log4j.debug("<getByPlantillaRol> Borrando listado para idPlantillaRol: " + idPlantillaRol );
		
	}

}
