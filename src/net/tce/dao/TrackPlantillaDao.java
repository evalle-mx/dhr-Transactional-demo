package net.tce.dao;

import java.util.List;

import net.tce.dto.TrackingDto;

public interface TrackPlantillaDao extends PersistenceGenericDao {

	List<TrackingDto> getByPlantillaRol(Long idPlantillaRol);

	void deleteByPlantillaRol(Long idPlantillaRol);
}
