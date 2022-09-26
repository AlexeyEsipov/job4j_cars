package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@ThreadSafe
@Repository
public class DriverRepository {
    private final CrudRepository crudRepository;

    public Driver findById(int id) {
        return crudRepository.optional(
                "from Engine where id = :fId", Driver.class,
                Map.of("fId", id)).orElse(new Driver()
        );
    }

    public List<Driver> findAll() {
        return crudRepository.query("from Engine", Driver.class);
    }
}
