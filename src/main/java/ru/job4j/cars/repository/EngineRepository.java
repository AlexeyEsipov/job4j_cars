package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@ThreadSafe
@Repository
public class EngineRepository {
    private final CrudRepository crudRepository;

    public Engine add(Engine engine) {
        crudRepository.run(session -> session.save(engine));
        return engine;
    }

    public Engine findById(int id) {
        return crudRepository.optional(
                "from Engine where id = :fId", Engine.class,
                Map.of("fId", id)).orElse(new Engine()
        );
    }

    public List<Engine> findAll() {
        return crudRepository.query("from Engine", Engine.class);
    }
}
