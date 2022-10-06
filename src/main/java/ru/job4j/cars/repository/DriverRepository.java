package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@AllArgsConstructor
@ThreadSafe
@Repository
public class DriverRepository {
    private final CrudRepository crudRepository;

    public Driver add(Driver driver) {
        crudRepository.run(session -> session.save(driver));
        return driver;
    }

    public Driver findById(int id) {
        return crudRepository.optional(
                "from Engine where id = :fId", Driver.class,
                Map.of("fId", id)).orElseThrow(NoSuchElementException::new);
    }

    public List<Driver> findAll() {
        return crudRepository.query("from Engine", Driver.class);
    }
}
