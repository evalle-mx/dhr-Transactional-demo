package net.tce.admin.service;

import net.tce.dto.PermisoDto;

public interface PermissionService {

	String update(PermisoDto permisoDto);
	Object get(PermisoDto permisoDto);
	
	String create(PermisoDto permisoDto);
	String delete(PermisoDto permisoDto);
}
