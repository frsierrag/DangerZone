# DangerZone

SUMMARY
A project to Android to refer places with geolocalized maps.

DESCRIPCIÓN
Aplicación desarrollada (en Flask-Python como back-end y HTML, CSS y jQuery para el front-end) para administrar elementos de una tienda por catalogo en la cual se pueden crear, mostrar, acatualizar y borrar accesorios. Adicionalmente la aplicación cuenta con un sistema de registro de usuarios a la cabeza de una cuenta de Administrador.

SERVICIOS
Login: (GET/POST) Servicio de autenticación de usuario la cual compara la entrada con la información almacenada en base de datos.

Recuerpación de contraseña: (GET/POST) Servicio de recuperación de contraseña para usuarios registrados por medio de el correo electrónico registrado.

Cambio de contraseña: (GET/POST) Servicio para recibir contraseña, confirmarla y actualizarla en la base de datos.

Registro de usuario: (GET/POST) Servicio para registro de usuarios con nombre, email y constraseña.

Creación de producto: (GET/POST) Servicio para crear producto con datos de entrada: Nombre de producto, cantidad, precio e imagen.

Actualización de producto: (GET/POST) Servicio para actualizar los productos en base de datos a través del ID generado por la base.

Eliminación de producto: (GET/POST/DELETE) Servicio para eliminar los productos en base de datos a través del ID generado por la base.

Búsqueda de producto: (GET/POST) Servicio para listar los productos relacionados con una entrada producida por el administrador o el usuario.

Actualización de inventarios: (GET/POST) Servicio para actualizar los inventarios de un producto en base de datos a través del ID generado por la base.

DESCARGAS
Descarga este repositorio a través de: https://github.com/frsierrag/accesoriesStoreApp.git

INDEX
https://localhost:443/index

INSTALACIONES
pip install Flask-SQLAlchemy pip install Flask-JWT pip install Flask-Migrate

INFO
frsierrag@gmailcom fabiansierra@uninorte.edu.co silviamm@uninorte.edu.co
