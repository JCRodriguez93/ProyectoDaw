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
