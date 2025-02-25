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

INSERT INTO Products (id_subcategory, name, description, price) VALUES
(1, 'Anillo de Oro', 'Anillo de oro macizo de 18 quilates.', 120.00),
(1, 'Anillo de Plata', 'Anillo de plata esterlina 925 con acabado pulido.', 45.00),
(1, 'Anillo de Titanio', 'Anillo resistente de titanio, ideal para perforaciones.', 55.00),
(1, 'Anillo de Acero Quirúrgico', 'Anillo de acero inoxidable hipoalergénico.', 30.00),
(1, 'Anillo de Tungsteno', 'Anillo de tungsteno ultra resistente con acabado mate.', 65.00),
(1, 'Anillo de Zirconio', 'Anillo de zirconio negro con diseño elegante.', 50.00),
(1, 'Anillo de Niobio', 'Anillo hipoalergénico de niobio, ideal para pieles sensibles.', 58.00),
(1, 'Anillo de Cerámica', 'Anillo ligero de cerámica con detalles grabados.', 40.00),
(1, 'Anillo de Madera', 'Anillo artesanal de madera con incrustaciones naturales.', 35.00),
(1, 'Anillo de Silicona', 'Anillo flexible de silicona, perfecto para deporte y trabajo.', 20.00);


-- Insertar productos pendientes (corrección: usar subcategoría 2)
INSERT INTO Products (id_subcategory, name, description, price) VALUES
(2, 'Pendientes de Oro', 'Pendientes de oro para perforaciones.', 60.00),
(2, 'Pendientes de Titanio', 'Pendientes de titanio para perforaciones.', 40.00),
(2, 'Pendientes de Zirconio', 'Pendientes de zirconio para perforaciones.', 45.00),
(2, 'Pendientes con Diamantes', 'Pendientes de lujo con diamantes.', 150.00),
(2, 'Pendientes de Perla', 'Pendientes elegantes de perla.', 55.00),
(2, 'Pendientes de Cristal', 'Pendientes con cristal Swarovski.', 35.00),
(2, 'Pendientes de Niobio', 'Pendientes hipoalergénicos de niobio.', 42.00),
(2, 'Pendientes de Cerámica', 'Pendientes ligeros de cerámica.', 30.00),
(2, 'Pendientes de Bambú', 'Pendientes ecológicos de bambú.', 25.00),
(2, 'Pendientes de Cuero', 'Pendientes de cuero para un estilo alternativo.', 28.00);


-- Insertar máquinas de tatuar
INSERT INTO Products (id_subcategory, name, description, price) VALUES
(3, 'Máquina de Tatuar Bobina 8 Wrap', 'Máquina de tatuar con bobina de 8 wraps.', 100.00),
(3, 'Máquina de Tatuar Bobina 10 Wrap', 'Máquina de tatuar con bobina de 10 wraps.', 120.00),
(3, 'Máquina de Tatuar Bobina 12 Wrap', 'Máquina de tatuar con bobina de 12 wraps.', 150.00),
(4, 'Máquina Rotativa Pen', 'Máquina rotativa tipo pen para tatuajes.', 180.00),
(4, 'Máquina Rotativa Direct Drive', 'Máquina rotativa con sistema de transmisión directa.', 200.00),
(4, 'Máquina Rotativa de Cartucho', 'Máquina rotativa compatible con cartuchos.', 220.00),
(3, 'Máquina de Tatuar Híbrida', 'Máquina de tatuar híbrida compatible con bobinas y rotativas.', 250.00),
(3, 'Máquina de Tatuar de Hierro', 'Máquina de tatuar de hierro con bobina.', 130.00),
(4, 'Máquina Rotativa Inalámbrica', 'Máquina rotativa inalámbrica para mayor libertad.', 300.00),
(3, 'Máquina de Tatuar de Latón', 'Máquina de tatuar de latón con diseño clásico.', 140.00);



-- Insertar mobiliario para tienda de tatuajes (corrección: usar subcategoría 5, por ejemplo)
INSERT INTO Products (id_subcategory, name, description, price) VALUES
(5, 'Lámpara de Pie Ajustable', 'Lámpara de pie con brazo ajustable para iluminar estaciones de trabajo.', 75.00),
(5, 'Mesa de Trabajo con Ruedas', 'Mesa de trabajo móvil con ruedas y espacio de almacenamiento.', 150.00),
(5, 'Taburete Ajustable', 'Taburete ajustable en altura para tatuadores.', 85.00),
(5, 'Camilla Plegable', 'Camilla plegable para mayor comodidad y ahorro de espacio.', 200.00),
(5, 'Armario de Almacenamiento', 'Armario con múltiples compartimentos para almacenar suministros.', 180.00),
(5, 'Mesa Auxiliar', 'Mesa auxiliar pequeña para herramientas y equipos.', 60.00),
(5, 'Parasol de Ventana', 'Parasol para ventanas que proporciona privacidad y control de luz.', 40.00),
(5, 'Sillón Reclinable', 'Sillón reclinable para mayor comodidad de los clientes.', 250.00),
(5, 'Estantería de Pared', 'Estantería de pared para exhibir productos y materiales.', 95.00),
(5, 'Espejo de Cuerpo Entero', 'Espejo de cuerpo entero para revisión de tatuajes.', 120.00);

-- Insertar productos de micropigmentación (corrección: usar subcategoría 7, por ejemplo)
INSERT INTO Products (id_subcategory, name, description, price) VALUES
(7, 'Agujas de Micropigmentación', 'Agujas desechables para micropigmentación.', 10.00),
(7, 'Anestésico Tópico', 'Crema anestésica para reducir el dolor durante la micropigmentación.', 25.00),
(7, 'Lápiz de Diseño', 'Lápiz especializado para diseño previo a la micropigmentación.', 5.00),
(7, 'Capa Protectora', 'Capa protectora para clientes durante el procedimiento.', 15.00),
(7, 'Guantes Desechables', 'Guantes de nitrilo desechables para procedimientos de micropigmentación.', 12.00),
(7, 'Almohadillas para Cejas', 'Almohadillas adhesivas para diseño de cejas.', 8.00),
(7, 'Pigmento Orgánico', 'Pigmento orgánico para micropigmentación.', 30.00),
(7, 'Fijador de Pigmento', 'Producto para fijar el pigmento en la piel.', 20.00),
(7, 'Cinta Métrica para Cejas', 'Cinta métrica para medir y diseñar cejas.', 7.00),
(7, 'Removedor de Pigmento', 'Producto para corregir errores de micropigmentación.', 18.00);

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
