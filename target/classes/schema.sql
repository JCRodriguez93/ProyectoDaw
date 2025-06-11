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

