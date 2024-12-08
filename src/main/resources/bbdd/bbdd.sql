-- Create the database
CREATE DATABASE IF NOT EXISTS dawproject;
USE dawproject;

-- Tabla de Roles
CREATE TABLE Roles (
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Users Table
CREATE TABLE Users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,    
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES Roles(id_role)
);


-- Categories Table
CREATE TABLE Category (
    id_category INT AUTO_INCREMENT PRIMARY KEY,   
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Subcategories Table
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

-- Products Table
CREATE TABLE Products (
    id_product INT AUTO_INCREMENT PRIMARY KEY,    
    id_subcategory INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,                  
    FOREIGN KEY (id_subcategory) REFERENCES Subcategory(id_subcategory)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Orders Table
CREATE TABLE Orders (
    id_order INT AUTO_INCREMENT PRIMARY KEY,       
    id_user INT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,        
    status ENUM('pendiente', 'pagado', 'cancelado') DEFAULT 'pendiente',  
    FOREIGN KEY (id_user) REFERENCES Users(id_user)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Order Details Table
CREATE TABLE Order_Details (
    id_detail INT AUTO_INCREMENT PRIMARY KEY,      
    id_order INT NOT NULL,
    id_product INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),     
    FOREIGN KEY (id_order) REFERENCES Orders(id_order)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_product) REFERENCES Products(id_product)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- crear datos de ejemplo

INSERT INTO Roles (role_name) VALUES
('admin'),
('usuario');


-- Insertar categorías
INSERT INTO Category (name, description) VALUES
('Tatuaje', 'Productos relacionados con tatuajes'),
('Piercing', 'Productos relacionados con piercings'),
('Cicatrización', 'Productos para el cuidado y cicatrización'),
('Muebles y Equipo', 'Mobiliario y equipos para estudios de tatuaje'),
('Consumibles e Higiene', 'Productos de consumo e higiene');

-- Insertar subcategorías
INSERT INTO Subcategory (id_category, name, description) VALUES
(1, 'Máquinas de Tatuaje', 'Máquinas especializadas para tatuar'),
(1, 'Agujas', 'Agujas para tatuajes de diferentes tamaños'),
(1, 'Pigmentos', 'Tintas y colores para tatuajes'),
(2, 'Joyas', 'Joyas y adornos para piercings'),
(2, 'Microdermal', 'Implantes y accesorios microdermales'),
(3, 'Tatuaje', 'Productos para cicatrización de tatuajes'),
(4, 'Mobiliario', 'Sillas y camillas para estudios de tatuaje'),
(5, 'Protección Personal', 'Guantes, mascarillas, etc.');


-- Insertar productos
INSERT INTO Products (id_subcategory, name, description, price) VALUES
(1, 'Máquina de Tatuaje Pro', 'Máquina profesional de tatuajes', 150.00),
(2, 'Agujas 5RL', 'Paquete de agujas 5RL', 12.50),
(3, 'Pigmento Negro', 'Tinta negra de alta calidad', 25.00),
(4, 'Piercing de Titanio', 'Piercing de alta calidad en titanio', 8.99),
(5, 'Microdermal Básico', 'Microdermal para principiantes', 15.99),
(7, 'Camilla Ajustable', 'Camilla ajustable para tatuajes', 300.00),
(8, 'Guantes Desechables', 'Caja de 100 guantes desechables', 10.00);


-- Insertar usuarios
INSERT INTO Users (user_name, email, password, role_id) VALUES
('admin1', 'admin1@tatuajes.com', 'admin123', 1),  -- El 'admin' tiene role_id 1
('usuario1', 'usuario1@tatuajes.com', 'user123', 2),  -- El 'usuario' tiene role_id 2
('usuario2', 'usuario2@tatuajes.com', 'user456', 2);  -- El 'usuario' tiene role_id 2




-- Insertar órdenes (asociadas a los usuarios)
INSERT INTO Orders (id_user, status) VALUES
(1, 'pendiente'),
(2, 'pagado'),
(3, 'cancelado');


