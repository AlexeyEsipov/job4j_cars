package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Engine;

public class HbnRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
             Session session = sf.openSession()) {
            session.beginTransaction();
            Engine engine = Engine.of(3);
            session.save(engine);
            Driver one = Driver.of("Petr");
            Driver two = Driver.of("Stas");
            Driver three = Driver.of("Alex");
            session.save(one);
            session.save(two);
            session.save(three);
            Car first = Car.of("bus", engine);
            first.getDrivers().add(one);
            first.getDrivers().add(two);
            Car second = Car.of("pickup", engine);
            second.getDrivers().add(three);
            second.getDrivers().add(one);
            session.persist(first);
            session.persist(second);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
