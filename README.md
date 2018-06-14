# Backend App Ventas

## Prerequisites:
* Postgresql 9.6.9 
* IDE ( preffered IntelliJ) 
* JDK 1.8+
* Gradle

## Install and run the project 
1. Download/clone the project 
2. Prepare the database
  * import in Postgresql the self-contained file that comes with the project - [BackVentas / db.sql](https://github.com/CrisPj/BackVentas/blob/master/db.sql)
  * username/password - `ventas`/`123`
3. Change to the root folder of the project and execute the following command 
  * `gradle appRun`
  * The REST api is up and running with Tomcat on `localhost:8000/ventas/api` 
  
> **Note:** with `gradle war` command, you can deploy the generated __.war__ file in any web container like Tomcat for example. 

## License

[GLWTPL](https://github.com/me-shaon/GLWTPL/blob/master/LICENSE)


