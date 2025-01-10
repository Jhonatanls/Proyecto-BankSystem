# Proyecto BankSystem
Este repositorio consiste inicialmente en guardar los distintos cambios a lo largo del tiempo del proyecto de básico de sistema bancario que al final deberá contar con funcionalidades tales como:

- **Gestión de usuarios:** Creación, actualización, eliminación, autenticación, y autorización de usuarios.

- **Gestión de cuentas bancarias:** Creación y manejo de cuentas bancarias asociadas a los usuarios y consultas de saldo.

- **Operaciones bancarias:** Depósito de fondos en cuentas específicas y retiro de fondos con validación de fondos disponibles.

## Organización por commits del proyecto
1. Creación inicial del README.md para la documentación técnica y de usuario que se irá actualizando a medida que se vayan agregando funcionalidades.
2. Creación de diagramas UML y ER:

### Diagrama Entidad-Relación:

![image](src/main/resources/static/Diagrama-ER-BankSystem.png)

### Diagrama UML:

![image](src/main/resources/static/UML-BankSystem.png)

3. Creación de modelos para las entidades de User, Account y Transaction con sus distintas relaciones
    - **Relaciones:** 
      - **OneToMany:** User -> Account
      - **ManyToOne:** Account -> User
      - **OneToMany:** Account -> Transaction

4. Implementación inicial de la arquitectura MVC con las 3 capas de repository, service y controller, además de la generación 
de las primeras funciones CRUD para los usuarios:
    - Crear Usuario
    - Obtener Usuario
    - Editar Usuario
    - Eliminar Usuario
    - Listar todos los usuarios

5. - Implementación de la lógica para gestión de cuentas bancarias creadas en función de un usuario en específico,
      se implementa patrón DTO para que la solicitud de creación de la cuenta bancaria sea mucho más escalable y segura.
      - Crear cuenta
      - Obtener Cuenta
      - Listar todas las cuentas
      - Editar cuenta
      - Eliminar cuenta
   - Se agrega la anotación @JsonIgnore en la entidad Account para que no se genere un loop a la hora de listar los usuarios y las cuentas.
   - Se agrega el enum TransactionType para delimitar los dos tipos de transacciones que tendremos
   - Se le da un mejor orden a las entidades ya creadas, se les agrega propiedades a ciertos campos para no aceptar nulos y que sean campos únicos.

6. - Implementación de la lógica CRUD para las transacciones de las cuentas en función de una cuenta específica, se usa el patrón DTO para manejar la solicitud
   de creación.
     - Crear transacción
     - Obtener transacción
     - Listar todas las transacciones
     - Listar transacción por accountId
   - Se crea un Enum para manejar los estados de la transacción: EXITOSO o DECLINADO
   - Se actualizan los diagramas UML y ER para incluir los cambios realizados