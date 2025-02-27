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

-- las rutas están bien y funcionan
INSERT INTO Products (id_subcategory, name, description, price, image_url) VALUES
(2, 'Dilatador Acrílico', 'Dilatador acrílico negro de 10 mm para lóbulo.', 12.00, 'img/pearcings/01-dilatador-acrilico.png'),
(2, 'Dilatador de Acero', 'Dilatador de acero quirúrgico de 8 mm.', 15.00, 'img/pearcings/01-dilatador-acero.png'),
(2, 'Aro Segmentado', 'Aro segmentado de acero quirúrgico para septum o cartílago.', 18.00, 'img/pearcings/03-aro-segmentado.png'),
(2, 'Piercing Industrial', 'Barra industrial de titanio con diseño de flecha.', 25.00, 'img/pearcings/04-pearcing-industrial.png'),
(2, 'Piercing Tragus', 'Piercing de tragus con bola de zirconia.', 20.00, 'img/pearcings/05-pearcing-tragus.png'),
(2, 'Piercing Labret', 'Piercing labret de titanio con rosca interna.', 22.00, 'img/pearcings/06-pearcing-labret.png'),
(2, 'Dilatador de Silicona', 'Dilatador flexible de silicona de 12 mm.', 10.00, 'img/pearcings/07-dilatador-silicona.png'),
(2, 'Clicker para Septum', 'Clicker de acero con diseño tribal para septum.', 28.00, 'img/pearcings/08-clicker-septum.png'),
(2, 'Piercing para Ceja', 'Barra curva de acero quirúrgico con bolas de titanio.', 16.00, 'img/pearcings/09-pearcing-ceja.png'),
(2, 'Horseshoe para Labio', 'Piercing horseshoe de acero con puntas cónicas.', 19.00, 'img/pearcings/02-horseshoe-labio.png');





-- Insertar máquinas de tatuar
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




-- Insertar mobiliario para tienda de tatuajes (corrección: usar subcategoría 5, por ejemplo)
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
(5, 'Sillón Reclinable', 'Sillón reclinable para mayor comodidad de los clientes.', 250.00, 'img/mobiliario/39-sillon-reclinable.png');


-- Insertar productos micropigmentación correctos
INSERT INTO Products (id_subcategory, name, description, price, image_url) VALUES
(7, 'Agujas de Micropigmentación', 'Agujas desechables para micropigmentación.', 10.00, 'img/micropigmentacion/40-aguja-micropigmentacion.png'),
(7, 'Anestésico Tópico', 'Crema anestésica para reducir el dolor durante la micropigmentación.', 25.00, 'img/micropigmentacion/42-anestesico-topico.png'),
(7, 'Lápiz de Diseño', 'Lápiz especializado para diseño previo a la micropigmentación.', 5.00, 'img/micropigmentacion/47-lapiz-disenio.png'),
(7, 'Capa Protectora', 'Capa protectora para clientes durante el procedimiento.', 15.00, 'img/micropigmentacion/43-capa-protectora.png'),
(7, 'Guantes Desechables', 'Guantes de nitrilo desechables para procedimientos de micropigmentación.', 12.00, 'img/micropigmentacion/46-guantes-desechables.png'),
(7, 'Almohadillas para Cejas', 'Almohadillas adhesivas para diseño de cejas.', 8.00, 'img/micropigmentacion/41-almohadilla-cejas.png'),
(7, 'Pigmento Orgánico', 'Pigmento orgánico para micropigmentación.', 30.00, 'img/micropigmentacion/48-pigmento-organico.png'),
(7, 'Fijador de Pigmento', 'Producto para fijar el pigmento en la piel.', 20.00, 'img/micropigmentacion/45-fijador-pigmentacion.png'),
(7, 'Cinta Métrica para Cejas', 'Cinta métrica para medir y diseñar cejas.', 7.00, 'img/micropigmentacion/44-cinta-metrica-cejas.png'),
(7, 'Removedor de Pigmento', 'Producto para corregir errores de micropigmentación.', 18.00, 'img/micropigmentacion/49-removedor-pigmentacion.png');


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
