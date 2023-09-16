package com.example.demo.repostories;

import com.example.demo.models.Cancion;
import org.springframework.data.repository.CrudRepository;

public interface CancionRepository extends CrudRepository<Cancion, Long> {
}
