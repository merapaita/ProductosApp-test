package com.app.productos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductoTest {

	@Autowired
	private ProductoRepositorio repositorio;
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testGuardarProducto() {
		Producto producto = new Producto("Laptop HP", 2000);
		Producto productoGuardado = repositorio.save(producto);
		
		assertNotNull(productoGuardado);
		
	}
	
	@Test
	@Order(2)
	public void buscarProductoPorNombre() {
		String nombre = "Televisor Sangung HD";
		Producto producto = repositorio.findByNombre(nombre);
		
		assertThat(producto.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Order(3)
	public void buscarProductoPorNombreNoExistente() {
		String nombre = "Televisor Sangun HD";
		Producto producto = repositorio.findByNombre(nombre);
		
		assertNull(producto);
	}
	
	@Test
	@Rollback(false)
	@Order(4)
	public void testActualizarProducto() {
		String nombre = "Televisor HD";
		Producto producto = new Producto(nombre, 2000);
		producto.setId(2);
		repositorio.save(producto);
		
		Producto productoActualizado = repositorio.findByNombre(nombre);
		assertThat(productoActualizado.getNombre()).isEqualTo(nombre);
		
	}
	
	@Test
	@Order(5)
	public void listarProducto() {
		List<Producto> productos = (List<Producto>)repositorio.findAll();
		
		for (Producto producto : productos) {
			System.out.println(producto);
		}

		assertThat(productos).size().isGreaterThan(0);
	}
	
	@Test
	@Order(6)
	public void eliminarProducto() {
		Integer id = 2;
		
		boolean esExistenteAntesDeEliminar = repositorio.findById(id).isPresent();
		repositorio.deleteById(id);
		boolean noExistenteDespuesDeEliminar = repositorio.findById(id).isPresent();
		
		assertTrue(esExistenteAntesDeEliminar);
		assertFalse(noExistenteDespuesDeEliminar);
	}
	
}