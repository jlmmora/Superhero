# Superhero

Desarrollo de una API utilizando Maven, Spring Boot, y Java, que permita hacer un mantenimiento CRUD
de súper héroes. Este mantenimiento debe permitir:

• Consultar todos los súper héroes.

• Consultar un único súper héroe por id.

• Consultar todos los súper héroes que contienen, en su nombre, el valor de un parámetro enviado en la petición. 
Por ejemplo, si enviamos “man” devolverá “Spiderman”, “Superman”, “Manolito el fuerte”, etc.

• Crear un súper héroe.

• Modificar un súper héroe.

• Eliminar un súper héroe.

• Test unitarios de como mínimo un servicio.

## LogExecutionTime
    La interfaz LogExecutionTime contiene la anotación @Target que dice dónde se aplicará nuestra anotación. 
    ElementType.Method, significa que solo funcionará en métodos.
    
## LogExecutionTimeAspect
    Es la clase donde implementaremos la lógica que queremos que inyecte la anotación personalizada.
    Implementar una anotación personalizada que para medir cuánto tarda en ejecutarse una petición imprimiendo la duración en un log.    

## API superhero

Actualización de un Superheroe.
    
    PUT /api/superhero update. 

Creación de un Superheroe
    
    POST /api/superhero create.

Obtener el Superheroe que cumplen con el valor del id del parametro de entrada.

    GET /api/superhero/{id} findById. 

Borrado de un Superheroe.
    
    DELETE /api/superhero/{id} delete. 

Obtener el Superheroe que contiene, en su nombre, el valor del parámetro introducido.    
    
    GET /api/superhero/findByName findByName

Obtener el Superheroe que contiene, en su nombre, el valor del parámetro introducido utilizando la anotación @Query.

    GET /api/superhero/findByNameQuery findByNameQuery

Obtener todos los Superheroes.

    GET /api/superhero/findAll findAll

DTO
    
    SuperheroDTO {
        id	        integer($int64)
        name*	    string
        realName	string
        abilities*	string
        power	    number($double) 
                    maximum: 100 
                    exclusiveMaximum: false
                    minimum: 0
                    exclusiveMinimum: false
    }

## Links

h2-console

    http://localhost:8080/h2-console

Swagger-UI

    http://localhost:8080/swagger-ui-superhero.html
