CREATE TABLE `tipo_usuario` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `nombre` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `genero` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `nombre` varchar(45) NOT NULL,
    `descripcion` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `usuario` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `nombre` varchar(45) NOT NULL,
    `correo` varchar(45) NOT NULL,
    `contrasena` varchar(45) NOT NULL,
    `activo` tinyint(1) NOT NULL DEFAULT '1',
    `id_tipo_usuario` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_usuario_tipo_usuario_idx` (`id_tipo_usuario`),
    CONSTRAINT `fk_usuario_tipo_usuario` FOREIGN KEY (`id_tipo_usuario`) REFERENCES `tipo_usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `cancion` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `nombre` varchar(45) NOT NULL,
    `duracion` int(11) NOT NULL,
    `autor` varchar(45) NOT NULL,
    `album` varchar(45) NOT NULL,
    `num_likes` int(11) NOT NULL,
    `valoracion` int(11) NOT NULL,
--     `sonido` blob NOT NULL,
--     `portada` blob,
    `id_genero` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_cancion_genero_idx` (`id_genero`),
    CONSTRAINT `fk_cancion_genero` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `likes` (
    `id_usuario` int(11) NOT NULL,
    `id_cancion` int(11) NOT NULL,
    PRIMARY KEY (`id_usuario`, `id_cancion`),
    KEY `fk_likes_usuario_idx` (`id_usuario`),
    KEY `fk_likes_cancion_idx` (`id_cancion`),
    CONSTRAINT `fk_likes_cancion` FOREIGN KEY (`id_cancion`) REFERENCES `cancion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_likes_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);