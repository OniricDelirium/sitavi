create database sitavi;
use sitavi;

create user 'admin'@'%' identified by 'usuario_a';
grant all privileges on sitavi.* to 'admin'@'%';
flush privileges;

create table categoria (
  id_categoria int not null auto_increment,
  nombre varchar (40) not null,
  imagen varchar (2048),
  primary key (id_categoria));

create table producto (
  id_producto int not null auto_increment,
  id_categoria int not null,
  nombre varchar (40) not null,  
  descripcion varchar (500) not null, 
  precio double not null,
  existencias int not null,  
  imagen varchar(2048),
  primary key (id_producto),
  foreign key fk_producto_caregoria (id_categoria) references categoria(id_categoria)  
);

/*Se crea la tabla de clientes llamada cliente... igual que la clase Cliente */
create table usuario (
  id_usuario int not null auto_increment,
  username varchar(20) not null,
  password varchar(128) not null,
  nombre varchar(20) not null,
  apellidos varchar(30) not null,
  correo varchar(75) NULL,
  telefono varchar(15) NULL,
  primary key (id_usuario)
  );

create table factura (
  id_factura int not null auto_increment,
  id_usuario int not null,
  fecha date,  
  total double,
  estado int,
  primary key (id_factura),
  foreign key fk_factura_usuario (id_usuario) references usuario(id_usuario)  
);

create table venta (
  id_venta INT NOT NULL AUTO_INCREMENT,
  id_factura INT NOT NULL,
  id_producto INT NOT NULL,
  precio double, 
  cantidad int,
  PRIMARY KEY (id_venta),
  foreign key fk_ventas_factura (id_factura) references factura(id_factura),
  foreign key fk_ventas_producto (id_producto) references producto(id_producto) 
);
    
insert into categoria (id_categoria, nombre, imagen) values
('1', 'Granola', 'https://images.cookforyourlife.org/wp-content/uploads/2018/08/shutterstock_239599594-min.jpg'), 
('2', 'Dulce Artesanal', 'https://i.pinimg.com/736x/aa/e2/b0/aae2b0c7398bbaded0c7338383fac3a2.jpg');

insert into producto (id_producto, id_categoria, nombre, descripcion, precio, existencias, imagen) values
(1, 1, 'Granola Tradicional', 'Nuestra granola tradicional es 100% artesanal, elaborada con ingredientes naturales cuidadosamente seleccionados. Sin azúcar añadida y libre de preservantes, ofrece una mezcla equilibrada de avena, frutos secos y semillas, brindando un sabor auténtico y una textura crujiente. Ideal para un desayuno saludable o un snack nutritivo.', 3600, 9, 'imagenes_productos/Granola_Tradicional.jpg'),
(2, 1, 'Granola Frutal', 'Nuestra granola frutal es 100% artesanal, elaborada con ingredientes naturales y sin azúcar añadida ni preservantes. Una combinación perfecta de avena horneada, frutos secos y una selección de frutas deshidratadas que aportan un dulzor natural y una explosión de sabor en cada bocado. Crujiente, nutritiva y deliciosa, es ideal para disfrutar en el desayuno o como snack saludable a cualquier hora del día.', 3600, 2, 'imagenes_productos/Granola_Frutal.jpg'),
(3, 2, 'Leche Condensada', 'Nuestra leche condensada vegana es 100% artesanal, elaborada con ingredientes naturales y libre de preservantes. Su textura cremosa y su dulzura equilibrada la convierten en la alternativa perfecta para quienes buscan disfrutar del sabor tradicional en una versión completamente vegetal. Ideal para postres, bebidas o simplemente para endulzar tus momentos especiales de forma deliciosa y consciente.', 3600, 7, 'imagenes_productos/Leche_Condensada.jpeg'),
(4, 2, 'Leche Condensada Fresa', 'Nuestra leche condensada vegana sabor fresa es 100% artesanal, elaborada con ingredientes naturales y libre de preservantes. Su textura cremosa y su irresistible sabor a fresa la convierten en la opción perfecta para quienes buscan un toque frutal y delicioso en sus postres y recetas favoritas. Disfrútala como topping, en bebidas o directamente a cucharadas para un momento dulce y natural.', 3600, 6, 'imagenes_productos/Leche_Condensada_Fresa.jpg'),
(5, 2, 'Dulce de Leche', 'Nuestro dulce de leche vegano es 100% artesanal, elaborado con ingredientes naturales y libre de preservantes. Con una textura suave y cremosa, y un sabor intensamente dulce y delicioso, es la opción perfecta para quienes buscan disfrutar de este clásico en una versión completamente vegetal. Ideal para untar, rellenar postres o simplemente saborear a cucharadas.', 3600, 5, 'imagenes_productos/Dulce_de_Leche.jpg');