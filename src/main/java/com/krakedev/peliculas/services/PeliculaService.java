package com.krakedev.peliculas.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.krakedev.peliculas.entidades.Pelicula;
import com.krakedev.peliculas.repository.PeliculaRepository;

@Service
public class PeliculaService {
	private PeliculaRepository peliculaRepository;
	
	public PeliculaService(PeliculaRepository peliculaRepository) {
		super();
		this.peliculaRepository = peliculaRepository;
	}

	public Pelicula crear(Pelicula pelicula) {
		return peliculaRepository.save(pelicula);
	}

	public List<Pelicula> listar() {
		return peliculaRepository.findAll();
	}

	public Pelicula buscarPorId(Long id) {
		Optional<Pelicula> pelicula = peliculaRepository.findById(id);
		return pelicula.orElse(null);
	}

	public Pelicula actualizar(Pelicula pelicula) {
		return peliculaRepository.save(pelicula);
	}

	public boolean eliminar(Long id) {
		if (peliculaRepository.existsById(id)) {
			peliculaRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public List<Pelicula> buscarPorGenero(String genero) {
		return peliculaRepository.findByGenero(genero);
	}

	public List<Pelicula> buscarPorDisponible(boolean disponible) {
		return peliculaRepository.findByDisponible(disponible);
	}
}