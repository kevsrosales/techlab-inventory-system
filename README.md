TechLab Inventory System 🚀

Sistema modular de gestión de inventario desarrollado en Java
. Este proyecto aplica conceptos avanzados de Programación Orientada a Objetos (POO) y una arquitectura desacoplada por responsabilidades
.

✨ Características Principales

CRUD Modular: Gestión completa de Artículos y Categorías con menús independientes
.

Repositorio Genérico: Implementación de un patrón de repositorio (Repositorio<T extends Identificable>) para centralizar el manejo de datos en memoria
.

Memoria de Identificadores: Lógica personalizada en la clase Secuencias que permite reciclar códigos de categorías eliminadas basándose en su historial de nombres.
Cálculo Automático de Descuentos: Uso de la API java.time para calcular precios dinámicos en artículos alimenticios según su cercanía a la fecha de vencimiento.
Validaciones Avanzadas: Clase utilitaria centralizada para garantizar la integridad de los datos (nombres no vacíos, precios positivos y formatos de fecha)
.

🏗️ Arquitectura y Tecnologías

El sistema está organizado en paquetes según su responsabilidad para asegurar la escalabilidad
:
model: Jerarquía de clases con herencia y polimorfismo (Articulo, ArticuloElectronico, ArticuloAlimenticio, Categoria)
.
repository: Capa de persistencia en memoria usando tipos genéricos
.
menu: Interfaz de usuario por consola con limpieza de pantalla mediante códigos ANSI
.
interfaces: Contratos para comportamiento común (Calculable, Identificable)
.
utils: Herramientas transversales de validación y secuencias
.
