package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private final CarRepository carRepository = new CarRepository(new CrudRepository(sf));
    private final EngineRepository engineRepository = new EngineRepository(new CrudRepository(sf));
    private final DriverRepository driverRepository = new DriverRepository(new CrudRepository(sf));

    @Test
    void addAndFindById() {
        Engine engine = Engine.of("testEngine");
        engineRepository.add(engine);
        Driver owner = Driver.of("testOwner");
        driverRepository.add(owner);
        Car car = Car.of("testModel", engine);
        car.setOwner(owner);
        carRepository.add(car);
        int id = car.getId();
        String dbOwner = carRepository.findById(id).getOwner().getName();
        assertThat(dbOwner).isEqualTo(owner.getName());
        assertThat(carRepository.findById(id).getModel()).isEqualTo("testModel");
        assertThat(carRepository.findById(id).getEngine().getName()).isEqualTo(engine.getName());
        assertThatThrownBy(() -> carRepository.findById(11))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void add2CarsAndFindAll() {
        Engine engine1 = Engine.of("testEngine1");
        engineRepository.add(engine1);
        Driver owner1 = Driver.of("testOwner1");
        driverRepository.add(owner1);
        Car car1 = Car.of("testModel1", engine1);
        car1.setOwner(owner1);
        carRepository.add(car1);
        Engine engine2 = Engine.of("testEngine2");
        engineRepository.add(engine2);
        Driver owner2 = Driver.of("testOwner2");
        driverRepository.add(owner2);
        Car car2 = Car.of("testModel2", engine2);
        car2.setOwner(owner2);
        carRepository.add(car2);
        List<Car> list = carRepository.findAll();
        assertThat(list).hasSize(2)
                .contains(car1, car2);
    }

    @Test
    void add2CarsThenFindAllThenDelete1CarThenFindAll() {
        Engine engine1 = Engine.of("testEngine1");
        engineRepository.add(engine1);
        Driver owner1 = Driver.of("testOwner1");
        driverRepository.add(owner1);
        Car car1 = Car.of("testModel1", engine1);
        car1.setOwner(owner1);
        carRepository.add(car1);
        int id1 = car1.getId();
        Engine engine2 = Engine.of("testEngine2");
        engineRepository.add(engine2);
        Driver owner2 = Driver.of("testOwner2");
        driverRepository.add(owner2);
        Car car2 = Car.of("testModel2", engine2);
        car2.setOwner(owner2);
        carRepository.add(car2);
        int id2 = car2.getId();
        List<Car> list = carRepository.findAll();
        assertThat(list).hasSize(2)
                .contains(car1, car2);
        carRepository.delete(id1);
        assertThatThrownBy(() -> carRepository.findById(id1))
                .isInstanceOf(NoSuchElementException.class);
        list = carRepository.findAll();
        assertThat(list).hasSize(1)
                .contains(car2)
                .doesNotContain(car1);
        assertThat(list.get(0).getId()).isEqualTo(id2);
        assertThat(carRepository.findById(id2).getModel()).isEqualTo("testModel2");
    }

    @Test
    void updateCar() {
        Engine engine = Engine.of("testEngine1");
        engineRepository.add(engine);
        Driver owner = Driver.of("testOwner1");
        driverRepository.add(owner);
        Car car = Car.of("testModel1", engine);
        car.setOwner(owner);
        carRepository.add(car);
        assertThat(carRepository.findById(car.getId()).getModel()).isEqualTo("testModel1");
        car.setModel("newModel");
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).getModel()).isEqualTo("newModel");
    }
}