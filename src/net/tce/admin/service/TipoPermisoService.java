package net.tce.admin.service;

import net.tce.dto.TipoPermisoDto;

public interface TipoPermisoService {

	Object get(TipoPermisoDto tipoPermisoDto) ;
	TipoPermisoDto dataConf(TipoPermisoDto tipoPermisoDto) ;
	TipoPermisoDto filtrosDataConf(TipoPermisoDto tipoPermisoDto) throws Exception;
}
