########### Configuracion inicial

##  I. Instalar glassfish tools para configurar el servidor en eclipse

-Go to Help > Install New Software...
-Add the GlassFish Tools Update Site
  http://download.eclipse.org/glassfish-tools/1.0.0/repository
- as repository (or add the URL under Work with: directly) and install the tools via the wizard.

##  II. verificar que todas las librerias esten contenidas en Properties > Deployment assembly

##  III. Configurar Glassfish (Segun parametros de AppIni.properties)
http://localhost:4848/
https://javaeesquad.github.io/tutorials/glassfishDatasource/glassFishDatasource.html

## IV. Modificar e incluir archivos json para Constante.java JSONFILES_ROOT_DIR 

# Crear Connection Pool
psqPool

#Crear JDBC 
ds/tcePsg


=============================================================================================


















Para Agregar un Nuevo Servicio en TransactionalMock:

* Elemental (Básico solo ClientRest.Tester )
- Generar Adapter en: net.tce.admin.adapter.rest [extendiendo de ErrorMessageAdapterRest]
- Agregar nuevo módulo (EndPoint Root) en Constante.java [/module/subModule] y 
	agregarlo al Adapter @RequestMapping
- Agregar metodos deseados en el adapter (create-004-, update, delete-007-,etc) con respuesta una cadena fija
- Crear un nuevo Tester 

* Fase Uno Cliente Web
[- Agregar EndPoint en los archivos de Validación, configuración y permisos
	= ./JsonUI/admin/validateParameters.json
	= ./JsonUI/module/enterpriseParameter/get.json & getExtend.json
	= ./JsonUI/module/curriculumManagement/uricodes.json
	]
- Actualizar archivos PermisoMenu.csv & PermisoUricode.csv (Separador ;) 
	[ TransactionalMock/src/net/tce/resources/]
- Ejecutar MockJsonGenerator para agregar EndPoints al archivo uricodes.json y generar archivos de control
- Actualizar archivo permByUser.json Por medio de query por Role 
- Verificar uriCodesSys.json [./JsonUI/admin/]
- Agregar formulario en la vista.html requerida con los id's y uriCodes respectivos en prueba

*Fase Dos (Respuesta emulada)
- Generar Interface y clase Impl del Servicio en: net.tce.admin.service & net.tce.admin.service.impl
- Generar/Modificar Dto relativo al objeto en Base de Datos
- Agregar funcionamiento elemental dependiendo de las reglas de Negocio
	- Agregar endPoints (metodos) a AppEndPoints 
- Modificar Adapter para implementar el Service
- Crear nuevos Json de Respuesta en ./JsonUI/module si es requerido


++++++++++++++++++++++++++++++++

En producción
* Replicar y configurar el Código BASICO
* Agregar EndPoint(s) de Servicio en la tabla de Permiso
INSERT INTO permiso (id_permiso,contexto,valor,descripcion,id_tipo_permiso)
VALUES (107,'LANGUAGE.D','/module/language/delete','URI para Eliminar un registro de Idioma en Persona/Posicion','1');
* Agregar los valores para cada Tipo de Usuario  (Por cada Rol en tipo_relacion_permiso)
* Modificar la implementacion del Servicio relativo a las reglas de Negocio REAL

* Para empatar los datos de Permiso con producción se usa la clase net.utils.TablaPermisoEdit en ClientRest


########################################################################
#  permiso.csv
