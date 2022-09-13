package ru.job4j.cars.repository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;

public class UserUsageNew {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            UserRepositoryNew userRepository = new UserRepositoryNew(new CrudRepository(sf));
            User userOne = new User();
            userOne.setLogin("admin");
            userOne.setPassword("admin");
            userRepository.create(userOne);
            User userTwo = new User();
            userTwo.setLogin("moder");
            userTwo.setPassword("moder");
            userRepository.create(userTwo);
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            userRepository.findByLikeLogin("t")
                    .forEach(System.out::println);
            userRepository.findById(userOne.getId())
                    .ifPresent(System.out::println);
            userRepository.findByLogin("admin")
                    .ifPresent(System.out::println);
            userOne.setPassword("password");
            userRepository.update(userOne);
            userRepository.findById(userOne.getId())
                    .ifPresent(System.out::println);
            userRepository.delete(userOne.getId());
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

