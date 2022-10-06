package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@AllArgsConstructor
@ThreadSafe
@Repository
public class CarRepository {
    private final CrudRepository crudRepository;

    public Car findById(int id) {
        return crudRepository.optional(
                "from Car where id = :fId", Car.class,
                Map.of("fId", id)).orElseThrow(NoSuchElementException::new);
    }

    public List<Car> findAll() {
        return crudRepository.query(
                "select distinct c from Car c "
                        + "join fetch c.engine e "
                        + "join fetch c.owner owner ", Car.class
        );
    }

    public Car add(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    public void delete(int id) {
        crudRepository.run("delete Car c where c.id = :fId",
                Map.of("fId", id));
    }
}
