package com.krakdev.peliculas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krakdev.peliculas.entidades.Pelicula;
import com.krakdev.peliculas.services.PeliculaService;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
	private final PeliculaService peliculaService;

	public PeliculaController(PeliculaService peliculaService) {
		super();
		this.peliculaService = peliculaService;
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Pelicula pelicula) {
		try {
			Pelicula nuevaPelicula = peliculaService.crear(pelicula);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPelicula);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la pelicula");
		}
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Pelicula> peliculas = peliculaService.listar();
			return ResponseEntity.ok(peliculas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar las peliculas");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		try {
			Pelicula pelicula = peliculaService.buscarPorId(id);
			if (pelicula == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("La pelicula con el id '" + id + "' no fue encontrada.");
			}
			return ResponseEntity.ok(pelicula);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar la pelicula con el id '" + id + "'");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pelicula peliculaActualizada) {
		try {
			Pelicula existente = peliculaService.buscarPorId(id);
			if (existente == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("La pelicula con el id '" + id + "' no fue encontrada.");
			}
			peliculaActualizada.setId(id);
			Pelicula actualizada = peliculaService.actualizar(peliculaActualizada);
			return ResponseEntity.ok(actualizada);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la pelicula");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		try {
			boolean eliminado = peliculaService.eliminar(id);
			if (!eliminado) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("La pelicula con el id '" + id + "' no fue encontrada.");
			}
			return ResponseEntity.ok("Pelicula eliminada");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la pelicula");
		}
	}

	@GetMapping("/genero")
	public ResponseEntity<?> buscarPorGenero(@RequestParam String genero) {
		try {
			List<Pelicula> peliculas = peliculaService.buscarPorGenero(genero);
			return ResponseEntity.ok(peliculas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar las peliculas por el genero de '" + genero + "'");
		}
	}

	@GetMapping("/disponible")
	public ResponseEntity<?> buscarPorDisponible(@RequestParam boolean disponible) {
		try {
			List<Pelicula> peliculas = peliculaService.buscarPorDisponible(disponible);
			return ResponseEntity.ok(peliculas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al buscar las peliculas por disponibilidad");
		}
	}
}