package com.example.demo.repostories;

import com.example.demo.models.Genero;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GeneroRepository extends CrudRepository<Genero, Long> {

    List<Genero> findByActivoTrue();
}
