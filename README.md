# Challenge Tecnico - MELI

# Detector Mutante
## Objetivo
Crear una funcion que permita detectar mutantes a traves de una cadena de ADN, 
representada en una Matriz de NxN, en la cual para detectar si es mutante debe cumplir con mas de una secuencia de 4 caracteres iguales, de forma horizontal, vertical y obliqua.

Los caracteres permitidos en la cadena a analizar son A, T, G, C.

### Stack tecnologico utilizado
- Java 11
- Spring boot 2.4.3
- Mongo DB 4.4.4
- Jacoco Junit libs
- Gradle.
###Despliegue
- Heroku Cloud
- Mongo Atlas en AWS
- Amazon Api gateway
- Amazon BeansTalk.
###Instrucciones para despliegue local
- Descargar en instalar jdk 11
- Descargar Gradle
- Instalar mongodb o utilizar algun servicio en la nube como mongo atlas o cosmos DB
- Clonar el repositorio de GitHub de [este repositorio](https://github.com/jairoteran6/mutants-meli-teran.git)
- Para compilar el aplicativo y generar el desplegable ejecute ./gradlew build
- En la carpeta dist/libs/ se encuentra el .jar generado
- Para el despliegue el aplicativo requiere de dos variables de entorno:
  * APPPORT -> ej APPPORT=8080
  * MONGODBURI -> String de coneccion a base de datos Mongo 
    ej: mongodb://{userDB}:{passDB}@clusterdllotest-shard-00-00.ekpm1.mongodb.net:27017,clusterdllotest-shard-00-01.ekpm1.mongodb.net:27017,clusterdllotest-shard-00-02.ekpm1.mongodb.net:27017/{nameDB}?replicaSet=atlas-ghaa6m-shard-0&ssl=true&keepAlive=false&authSource=admin
    
- Para desplegar el aplicativo ejecutar en la ruta dist/libs/ java -jar mutants-0.0.1.jar.

##Uso
Despues de desplegar la aplicacion tenemos los siguientes servicios disponibles.
###Api mutant
El primer servicio con el que contamos es el que detecta si de una secuencia de ADN humana es de un mutante o no.

Para probar el funcionamiento tenemos dos escenarios:

#### Opcion 1: Secuencia de un mutante

```
POST localhost:8080/mutant
{
    "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```
o si queremos usar el servicio desplegado en la nube
```
curl -v -X POST https://api-mutants-teran.herokuapp.com/mutant -H 'Content-Type: application/json' -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'
```
```
Response: 200 - OK
```

#### Opcion 2: Secuencia de un humano
```
POST localhost:8080/mutant
{
	"dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGACGG", "GACCTA", "TCACTG"]
}
```
o
```
curl -v -X POST https://api-mutants-teran.herokuapp.com/mutant -H 'Content-Type: application/json' -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGACGG","GACCTA","TCACTG"]}'
```
```
Response: 403 - Forbidden
```

En caso de ingresar una secuencia que no cumpla con la estructura definida de NxN y que solo contenga los caracteres (A,T,C,G) el servicio respondera un error con status 400, ej:
```
POST localhost:8080/mutant
{
	"dna": ["CAGTGC", "TTATGT", "AGACGG", "GACCTA", "TCACTG"]
}
```
o
```
curl -v -X POST https://api-mutants-teran.herokuapp.com/mutant -H 'Content-Type: application/json' -d '{"dna":["ATYCGA","CAGTGC","TTATGT","AGACGG","GACCTA","TCACTG"]}'
```
```
Response: 400 - Bad Request
```
###Api stats
Este servicio nos permite obtener estadisticas de identificacion de mutantes, estas estadisticas se otienen de de una base de datos donde se almacena cada secuencia identificada y su clasificacion
```
GET localhost:8080/stats
``` 
o
```
curl -v https://api-mutants-teran.herokuapp.com/stats -H 'Content-Type: application/json'
```
```
Response 200 - OK 
{
    "count_mutant_dna": 2,
    "count_human_dna": 2,
    "ratio": 1
}
```
##Vistas arquitectonicas
###Vista Logica
![Alt text](https://github.com/jairoteran6/mutants-meli-teran/blob/master/Vistas%20arquitectonicas/Logical%20View.png?raw=true "Title")
###Vista Fisica
![Alt text](https://github.com/jairoteran6/mutants-meli-teran/blob/master/Vistas%20arquitectonicas/Fisical%20View.png?raw=true "Title")