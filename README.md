# Real Estate
## _Proyecto 2ª DAM (Acceso a datos / Programación de servicios y procesos / Diseño de interfaces / Programación multimedia y dispositivos móviles)_

[![N|Solid](logo.png)](https://triana.salesianos.edu/colegio//nsolid)



Este proyecto ha sido desarrollado por Francisco Gallego Gordillo
Programas empleados:

- API: IntelliJ idea
- Postman
- Angular: VisualStudio code

>## Funcionalidad
  Crear una aplicación de la gestión y visualización de los datos referentes a un conjunto de inmobiliarias y sus asociacines.
  Creando peticiones (usando API REST) desde las entidades, asociaciones y aplicando cambios en la forma de mostrar su estructura a través de Dtos para luego mostrar y modificar esos datos realizando a través de angular.

  Como innovación el programa debe de implementar seguridad mediante roles.
  

>## Repositorio de Angular.
  [Angular RealState](https://github.com/miguelcamposedu/g4-realstate-frontend/tree/master/)
  
>##Instrucciones de arranque
  Para ejecutar esta aplicación tras clonar el repositorio, debes ejecutar en la consola de tu IDE, "spring-boot:run", con la configuración de Maven.


>## Entidades
  Contamos con 4 entidades que son:
  - Inmobiliaria
  - Vivienda
  - Usuario 
  - Interesa

  
>## Asociaciones
## ManytoMany con atributos extra ( Vivienda -> Interesa <- Usuario )

#### Vivienda
```sh
@Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vivienda")
    private List<Interesa> intereses = new ArrayList<>();
```
#### Interesado
```sh
@Builder.Default
    @OneToMany(mappedBy = "interesado")
    private List<Interesa> intereses = new ArrayList<>();
```
#### Interesa
```sh
@Builder.Default
    @EmbeddedId
    private InteresaPK id = new InteresaPK();
```
#### InteresaPK
La clase interesaPK es la clave compuesta y a su vez foranea, que através de la anotacion @Embeddable y mappedBy conseguimos rescatar los identificadores de las tablas de la asociación requeridas
```sh
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InteresaPK implements Serializable {

    private Long interesado_id;

    private Long vivienda_id;

}
```
## ManyToOne bidireccional (Inmobiliaria -> Vivienda)
#### Inmobiliaria
```sh
 @OneToMany(mappedBy = "inmobiliaria", fetch = FetchType.LAZY)
    private List<Vivienda> viviendas = new ArrayList<>();   
```
#### Vivienda
```sh
@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inmobiliaria_id", foreignKey = @ForeignKey(name = "FK_VIVIENDA_INMOBILIARIA"))
    private Inmobiliaria inmobiliaria;
```

## ManyToOne bidireccional (Propietario -> Vivienda)
#### Propietario
```sh
@OneToMany(mappedBy = "propietario")
    private List<Vivienda> viviendas = new ArrayList<>();
```
#### Vivienda
```sh
@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id", foreignKey = @ForeignKey(name = "FK_VIVIENDA_PROPIETARIO"))
    private Propietario propietario;
```
>## Herencia
Hemos creido conveniente que la clase Persona sea abstracta porque dadas las peticiones y el esquema proporcionado por el profesorado, nunca se va a instanciar la clase Persona, simplemente esta extiende a las otras dos clases (propietario y interesado). Para ello utilizaremos la anotacion @MappedSuperclass con la cual definimos que sera usada como clase base y no se trata como una entidad en si en la bbdd.
```sh
@MappedSuperclass
public abstract class Persona 
```


## Seguridad 

Para implementar la seguridad hemos creado un atributo ENUM llamado rol en la clase usuario, y definimos dentro de este los roles:
  - ADMIN
  - PROPIETARIO
  - GESTOR

