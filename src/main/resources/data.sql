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
('Anillos y Pendientes', 'Accesorios pequeños para perforaciones.'),
('Máquinas de Tatuar', 'Máquinas especializadas para realizar tatuajes.'),
('Mobiliario', 'Muebles para salones de tatuaje.'),
('Micropigmentación', 'Productos y equipos para micropigmentación.');

-- Insertar Subcategorías
INSERT INTO Subcategory (id_category, name, description) VALUES
(1, 'Anillos', 'Anillos ajustables y fijos para perforaciones.'),
(1, 'Pendientes', 'Pendientes de diferentes estilos y materiales.'),
(2, 'Bobinas', 'Máquinas de tatuar con bobinas.'),
(2, 'Rotativas', 'Máquinas rotativas de alta precisión.'),
(3, 'Carritos', 'Carros de almacenamiento para herramientas.'),
(3, 'Sillas', 'Sillas ergonómicas para clientes.'),
(4, 'Equipos', 'Máquinas específicas para micropigmentación.'),
(4, 'Pigmentos', 'Pigmentos de alta calidad para micropigmentación.');

-- Insertar Productos
INSERT INTO Products (id_subcategory, name, description, price) VALUES
(1, 'Anillo Oro', 'Anillo ajustable de oro para perforaciones.', 45.99),
(1, 'Anillo Plata', 'Anillo ajustable de plata para perforaciones.', 29.99),
(2, 'Pendientes de Acero', 'Pendientes de acero inoxidable para perforaciones.', 19.99),
(2, 'Pendientes de Plata', 'Pendientes de plata para perforaciones.', 35.50),
(3, 'Bobina para Máquina', 'Bobina de repuesto para máquinas de tatuar.', 22.75),
(4, 'Máquina Rotativa', 'Máquina rotativa para tatuar de alta precisión.', 199.99),
(5, 'Carrito de Herramientas', 'Carrito con múltiples compartimientos para herramientas de tatuaje.', 129.50),
(6, 'Silla Ergonómica', 'Silla ergonómica para clientes en el salón de tatuajes.', 150.00),
(7, 'Máquina Micropigmentación', 'Máquina especializada en micropigmentación.', 250.00),
(8, 'Pigmento Micropigmentación', 'Pigmentos de alta calidad para micropigmentación.', 50.00);

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