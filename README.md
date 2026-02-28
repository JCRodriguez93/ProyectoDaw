# Proyecto para el curso 2024 - 2025 "ProyectoDaw" sobre una tienda de tatuajes.
### Microservicio Monolítico

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)

---

## 📋 Descripción

Este proyecto es un microservicio **monolítico** desarrollado en IntelliJ IDEA para una tienda de productos de tatuajes. Su propósito es gestionar usuarios, productos y pedidos de manera eficiente, permitiendo una experiencia de compra fluida.

La plataforma admite registro, inicio y cierre de sesión de usuarios, gestión de roles con distintos niveles de acceso y funcionalidades para la administración de productos clasificados en categorías y subcategorías. Además, cada usuario dispone de un carrito de compras personal y la posibilidad de realizar pedidos.

---

## ✨ Características

- **Gestión de Usuarios**: Registro, autenticación segura y asignación de roles.
- **Gestión de Productos**: Creación, edición y eliminación de artículos organizados en categorías y subcategorías.
- **Carrito de Compras**: Permite a los usuarios añadir productos, modificar cantidades y proceder con la compra.
- **Gestión de Pedidos**: Creación, actualización y seguimiento de órdenes de compra.
- **Sistema de Roles**: Diferentes niveles de acceso para usuarios y administradores.

---

## 📦 Dependencias

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.2</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.2</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.2</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.2.Final</version>
</dependency>

<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.5.2.Final</version>
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.0.1.Final</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.12.6</version>
</dependency>

<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>3.1.0</version>
</dependency>

<dependency>
    <groupId>io.swagger.core.v3</groupId>
    <artifactId>swagger-annotations</artifactId>
    <version>2.0.9</version>
</dependency>

<dependency>
    <groupId>com.github.joschi.jackson</groupId>
    <artifactId>jackson-datatype-threetenbp</artifactId>
    <version>2.15.2</version>
</dependency>
```

## ⚙️ Configuración de Swagger Codegen

Permite generar API's y Modelos en base al fichero en  `/contract/api.yaml`. Se fixea también la generación de formato fecha a `LocalDateTime` de Java.

```xml
<plugin>
    <groupId>io.swagger.codegen.v3</groupId>
    <artifactId>swagger-codegen-maven-plugin</artifactId>
    <version>3.0.36</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/contract/api.yaml</inputSpec>
                <language>spring</language>
                <output>${project.build.directory}/generated-sources/</output>
                <configOptions>
                    <apiPackage>es.swagger.codegen.api</apiPackage>
                    <modelPackage>es.swagger.codegen.models</modelPackage>
                    <interfaceOnly>true</interfaceOnly>
                    <dateLibrary>custom</dateLibrary>
                </configOptions>
                <typeMappings>
                    <typeMapping>DateTime=java.time.LocalDateTime</typeMapping>
                </typeMappings>
                <importMappings>
                    <importMapping>LocalDateTime=java.time.LocalDateTime</importMapping>
                </importMappings>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## 🗄️ Base de datos del sistema

### Schema.sql

```sql
DROP DATABASE IF EXISTS dawproject;

-- Crear la base de datos
CREATE DATABASE dawproject;
USE dawproject;

-- Tabla de Roles (sigue igual)
CREATE TABLE Roles (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla de Usuarios (modificada + yaml)
CREATE TABLE Users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    user_surname VARCHAR(50) NOT NULL,
    birth_date DATETIME NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES Roles(id_role)
);

-- Tabla de Categorías
CREATE TABLE Category (
    id_category INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Tabla de Subcategorías
CREATE TABLE Subcategory (
    id_subcategory INT AUTO_INCREMENT PRIMARY KEY,
    id_category INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    UNIQUE(id_category, name),
    FOREIGN KEY (id_category) REFERENCES Category(id_category)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla de Productos
CREATE TABLE Products (
    id_product INT AUTO_INCREMENT PRIMARY KEY,
    id_subcategory INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(255),
    FOREIGN KEY (id_subcategory) REFERENCES Subcategory(id_subcategory)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla de Pedidos
CREATE TABLE Orders (
    id_order INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    total_quantity INT NOT NULL, -- Cantidad total de productos
    total_price DECIMAL(10, 2) NOT NULL, -- Precio total del pedido
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    order_status ENUM('PENDIENTE', 'PAGADO', 'CANCELADO', 'CONFIRMADO') DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_user) REFERENCES Users(id_user)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla intermedia para Pedidos y Productos
CREATE TABLE Order_Products (
    id_order_product INT AUTO_INCREMENT PRIMARY KEY,
    id_order INT NOT NULL,
    id_product INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0), -- Cantidad de un producto en este pedido
    FOREIGN KEY (id_order) REFERENCES Orders(id_order)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_product) REFERENCES Products(id_product)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Cart (
    id_cart INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    id_product INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (id_user) REFERENCES Users(id_user) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_product) REFERENCES Products(id_product) ON DELETE CASCADE ON UPDATE CASCADE
);
```

### Data.sql

```sql
-- Insertar Roles
INSERT INTO Roles (role_name) VALUES
('Admin'),
('Usuario');

-- Insertar Usuarios
INSERT INTO Users (user_name, user_surname, birth_date, email, password, role_id) VALUES
('Juan', 'Pérez', '1985-03-15', 'juan.perez@example.com', 'password123', 1),  -- Admin
('Ana', 'Gómez', '1990-07-22', 'ana.gomez@example.com', 'password456', 2);  -- Usuario

-- Insertar Categorías
INSERT INTO Category (name, description) VALUES
('Pearcings y Pendientes', 'Accesorios pequeños para perforaciones.'),
('Máquinas de Tatuar', 'Máquinas especializadas para realizar tatuajes.'),
('Mobiliario', 'Muebles para salones de tatuaje.'),
('Micropigmentación', 'Productos y equipos para micropigmentación.');

-- Insertar Subcategorías
INSERT INTO Subcategory (id_category, name, description) VALUES
(1, 'Pearcings', 'Anillos ajustables y fijos para perforaciones.'),  -- poner una frase con sentido
(1, 'Pendientes', 'Pendientes de diferentes estilos y materiales.'),
(2, 'Bobinas', 'Máquinas de tatuar con bobinas.'),
(2, 'Rotativas', 'Máquinas rotativas de alta precisión.'),
(3, 'Carritos', 'Carros de almacenamiento para herramientas.'),
(3, 'Sillas', 'Sillas ergonómicas para clientes.'),
(4, 'Equipos', 'Máquinas específicas para micropigmentación.'),
(4, 'Pigmentos', 'Pigmentos de alta calidad para micropigmentación.');

-- las rutas están bien y funcionan
INSERT INTO Products (id_subcategory, name, description, price, image_url) VALUES
(2, 'Dilatador Acrílico', 'Dilatador acrílico negro de 10 mm para lóbulo.', 12.00, 'img/pearcings/01-dilatador-acrilico.png'),
(2, 'Dilatador de Acero', 'Dilatador de acero quirúrgico de 8 mm.', 15.00, 'img/pearcings/01-dilatador-acero.png'),
(2, 'Aro Segmentado', 'Aro segmentado de acero quirúrgico para septum o cartílago.', 18.00, 'img/pearcings/03-aro-segmentado.png'),
(1, 'Piercing Industrial', 'Barra industrial de titanio con diseño de flecha.', 25.00, 'img/pearcings/04-pearcing-industrial.png'),
(1, 'Piercing Tragus', 'Piercing de tragus con bola de zirconia.', 20.00, 'img/pearcings/05-pearcing-tragus.png'),
(1, 'Piercing Labret', 'Piercing labret de titanio con rosca interna.', 22.00, 'img/pearcings/06-pearcing-labret.png'),
(2, 'Dilatador de Silicona', 'Dilatador flexible de silicona de 12 mm.', 10.00, 'img/pearcings/07-dilatador-silicona.png'),
(2, 'Clicker para Septum', 'Clicker de acero con diseño tribal para septum.', 28.00, 'img/pearcings/08-clicker-septum.png'),
(1, 'Piercing para Ceja', 'Barra curva de acero quirúrgico con bolas de titanio.', 16.00, 'img/pearcings/09-pearcing-ceja.png'),
(2, 'Horseshoe para Labio', 'Piercing horseshoe de acero con puntas cónicas.', 19.00, 'img/pearcings/02-horseshoe-labio.png');



-- máquinas de tatuar también correctos
INSERT INTO Products (id_subcategory, name, description, price, image_url) VALUES
(3, 'Máquina de Tatuar Bobina 8 Wrap', 'Máquina de tatuar con bobina de 8 wraps.', 100.00, 'img/maquinas/29-maquina-de-tatuar-8-wrap.png'),
(3, 'Máquina de Tatuar Bobina 10 Wrap', 'Máquina de tatuar con bobina de 10 wraps.', 120.00, 'img/maquinas/28-maquina-bobina-10-wrap.png'),
(3, 'Máquina de Tatuar Bobina 12 Wrap', 'Máquina de tatuar con bobina de 12 wraps.', 150.00, 'img/maquinas/27-maquina-bobina-12-wrap.png'),
(4, 'Máquina Rotativa Pen', 'Máquina rotativa tipo pen para tatuajes.', 180.00, 'img/maquinas/26-maquina-rotativa-pen.png'),
(4, 'Máquina Rotativa Direct Drive', 'Máquina rotativa con sistema de transmisión directa.', 200.00, 'img/maquinas/25-maquina-rotativa-direct-drive.png'),
(4, 'Máquina Rotativa de Cartucho', 'Máquina rotativa compatible con cartuchos.', 220.00, 'img/maquinas/24-maquina-rotativa-cartucho.png'),
(3, 'Máquina de Tatuar Híbrida', 'Máquina de tatuar híbrida compatible con bobinas y rotativas.', 250.00, 'img/maquinas/23-maquina-tatuar-hibrida.png'),
(3, 'Máquina de Tatuar de Hierro', 'Máquina de tatuar de hierro con bobina.', 130.00, 'img/maquinas/22-maquina-tatuar-hierro.png'),
(4, 'Máquina Rotativa Inalámbrica', 'Máquina rotativa inalámbrica para mayor libertad.', 300.00, 'img/maquinas/21-maquina-rotativa-inalambrica.png'),
(3, 'Máquina de Tatuar de Latón', 'Máquina de tatuar de latón con diseño clásico.', 140.00, 'img/maquinas/20-maquina-laton.png');


-- el mobiliario también está correcto
INSERT INTO Products (id_subcategory, name, description, price, image_url) VALUES
(5, 'Lámpara de Pie Ajustable', 'Lámpara de pie con brazo ajustable para iluminar estaciones de trabajo.', 75.00, 'img/mobiliario/30-lampara-de-pie.png'),
(5, 'Mesa de Trabajo con Ruedas', 'Mesa de trabajo móvil con ruedas y espacio de almacenamiento.', 150.00, 'img/mobiliario/31-mesa-trabajo-ruedas.png'),
(5, 'Taburete Ajustable', 'Taburete ajustable en altura para tatuadores.', 85.00, 'img/mobiliario/32-taburete-ajustable.png'),
(5, 'Camilla Plegable', 'Camilla plegable para mayor comodidad y ahorro de espacio.', 200.00, 'img/mobiliario/33-camilla-plegable.png'),
(5, 'Armario de Almacenamiento', 'Armario con múltiples compartimentos para almacenar suministros.', 180.00, 'img/mobiliario/34-armario-almacenamiento.png'),
(5, 'Mesa Auxiliar', 'Mesa auxiliar pequeña para herramientas y equipos.', 60.00, 'img/mobiliario/35-mesa-auxiliar.png'),
(5, 'Parasol de Ventana', 'Parasol para ventanas que proporciona privacidad y control de luz.', 40.00, 'img/mobiliario/36-parasol-ventana.png'),
(5, 'Estantería de Pared', 'Estantería de pared para exhibir productos y materiales.', 95.00, 'img/mobiliario/37-estanteria-pared.png'),
(5, 'Espejo de Cuerpo Entero', 'Espejo de cuerpo entero para revisión de tatuajes.', 120.00, 'img/mobiliario/38-espejo-cuerpo.png'),
(6, 'Sillón Reclinable', 'Sillón reclinable para mayor comodidad de los clientes.', 250.00, 'img/mobiliario/39-sillon-reclinable.png');

-- Insertar productos de micropigmentación está correcto
INSERT INTO Products (id_subcategory, name, description, price, image_url) VALUES
(7, 'Agujas de Micropigmentación', 'Agujas desechables para micropigmentación.', 10.00, 'img/micropigmentacion/40-aguja-micropigmentacion.png'),
(7, 'Anestésico Tópico', 'Crema anestésica para reducir el dolor durante la micropigmentación.', 25.00, 'img/micropigmentacion/42-anestesico-topico.png'),
(7, 'Lápiz de Diseño', 'Lápiz especializado para diseño previo a la micropigmentación.', 5.00, 'img/micropigmentacion/47-lapiz-disenio.png'),
(7, 'Capa Protectora', 'Capa protectora para clientes durante el procedimiento.', 15.00, 'img/micropigmentacion/43-capa-protectora.png'),
(7, 'Guantes Desechables', 'Guantes de nitrilo desechables para procedimientos de micropigmentación.', 12.00, 'img/micropigmentacion/46-guantes-desechables.png'),
(7, 'Almohadillas para Cejas', 'Almohadillas adhesivas para diseño de cejas.', 8.00, 'img/micropigmentacion/41-almohadilla-cejas.png'),
(8, 'Pigmento Orgánico', 'Pigmento orgánico para micropigmentación.', 30.00, 'img/micropigmentacion/48-pigmento-organico.png'),
(8, 'Fijador de Pigmento', 'Producto para fijar el pigmento en la piel.', 20.00, 'img/micropigmentacion/45-fijador-pigmentacion.png'),
(7, 'Cinta Métrica para Cejas', 'Cinta métrica para medir y diseñar cejas.', 7.00, 'img/micropigmentacion/44-cinta-metrica-cejas.png'),
(8, 'Removedor de Pigmento', 'Producto para corregir errores de micropigmentación.', 18.00, 'img/micropigmentacion/49-removedor-pigmentacion.png');


-- Insertar Pedidos
INSERT INTO Orders (id_user, total_quantity, total_price, order_status) VALUES
(1, 5, 199.95, 'PENDIENTE'),  -- Pedido de Juan
(2, 3, 95.00, 'PAGADO');      -- Pedido de Ana

-- Insertar Order_Products
INSERT INTO Order_Products (id_order, id_product, quantity) VALUES
(1, 1, 2),   -- Juan compra 2 Anillos Oro
(1, 2, 1),   -- Juan compra 1 Anillo Plata
(1, 5, 1),   -- Juan compra 1 Bobina para Máquina
(1, 6, 1),   -- Juan compra 1 Máquina Rotativa
(2, 3, 1),   -- Ana compra 1 Pendiente de Acero
(2, 7, 2);   -- Ana compra 2 Sillas Ergonómicas
```

---

## 🏗️ Estructura del microservicio (back-end)

* **Controller**
  * Contiene las clases que gestionan las solicitudes HTTP.
  * Se encarga de recibir peticiones, procesarlas y devolver respuestas apropiadas.
  * Llama a los servicios correspondientes para obtener datos o realizar operaciones.

* **Entity**
  * Define las clases que representan las tablas en la base de datos.
  * Usa anotaciones de JPA como `@Entity`, `@Table`, `@Column`, etc.
  * Define relaciones entre entidades con `@OneToMany`, `@ManyToOne`, etc.

* **Security**
  * Implementa la autenticación y autorización del microservicio.
  * Usa **JWT** para la autenticación basada en tokens.
  * Contiene filtros como `JwtAuthenticationFilter` y la configuración en `SecurityConfig`.

* **Service**
  * Contiene la lógica de negocio del microservicio.
  * Se encarga de la manipulación y procesamiento de datos antes de enviarlos al `Controller`.
  * Usa anotaciones como `@Service` y se comunica con el `Repository`.

* **Error**
  * Contiene clases que manejan excepciones personalizadas.
  * Extienden `RuntimeException` y permiten lanzar errores específicos.

* **Mapper**
  * Usa **MapStruct** para transformar objetos de una capa a otra.
  * La conversión se realiza entre las entidades (ejemplo: UsersEntity) hacia el modelo autogenerado Users y viceversa.

* **Repository**
  * Implementa la capa de acceso a datos mediante **JPA**.
  * Usa interfaces que extienden `JpaRepository` para facilitar operaciones CRUD.
  * Permite definir consultas personalizadas con anotaciones como `@Query`.

* **Config**
  * Contiene configuraciones globales del microservicio.
  * **Ejemplo: `SecurityConfig`**
    * Configura la seguridad del microservicio con `SecurityFilterChain`.
    * Implementa la autenticación con **JWT** y BCrypt para el cifrado de contraseñas.
    * Define qué rutas requieren autenticación y cuáles son públicas.
  * **Ejemplo: `SwaggerConfig`**
    * Habilita la documentación de la API con **Swagger**.
    * Permite acceder a la documentación en `http://localhost:8080/swagger-ui/index.html`.
    * Define información sobre la API, como el título, versión y contacto.

---

## 🎨 Recursos estáticos del microservicio (front-end)

* **css**
  * Contiene los ficheros de estilos que definen la apariencia de la web.
  * Aplica estilos globales y específicos para distintas páginas HTML.
  * Mejora la experiencia del usuario con diseño responsivo y animaciones.

* **js**
  * Contiene los scripts que manejan la lógica del lado del cliente.
  * Se encarga de interactuar con los endpoints del backend para obtener y enviar datos.
  * Implementa funcionalidades dinámicas como botones de añadir/disminuir productos, carruseles e inserción de contenido con `innerHTML`.

* **img**
  * Carpeta que almacena las imágenes utilizadas en la web.
  * Puede incluir logotipos, banners, imágenes de productos y otros elementos visuales.

* **Páginas HTML**
  * **index.html** - Página principal de la tienda.
  * **header.html** - Cabecera común de la web, incluye navegación y logotipo.
  * **footer.html** - Pie de página con información adicional y enlaces.
  * **catalog.html** - Página donde se listan los productos disponibles.
  * **product.html** - Página de detalles de un producto específico.
  * **cart.html** - Página del carrito de compras donde se gestionan los productos añadidos.
  * **login.html** - Página de autenticación para que los usuarios inicien sesión.

---

## © Derechos de autor

Se atribuye a Jorge Campos Rodríguez la titularidad de los derechos de autor sobre este producto conforme a la Ley de Propiedad Intelectual, Real Decreto Legislativo 1/1996, de 12 de abril, que establece que los derechos de autor sobre cualquier obra original de la mente, como software, textos, imágenes, música y otros, pertenecen automáticamente al autor desde el momento de su creación. Esto incluye los derechos exclusivos de reproducción, distribución, comunicación pública y transformación, los cuales son intransferibles sin la autorización explícita del titular de los derechos.

Este producto se encuentra protegido bajo las leyes de propiedad intelectual españolas y está sujeto a los derechos exclusivos de su autor.
