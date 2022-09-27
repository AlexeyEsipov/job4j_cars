package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@ThreadSafe
@Repository
public class PostRepository {
    private static final String PART_QUERY = "select distinct post from Post post "
            + "join fetch post.priceHistory history "
            + "join fetch post.car car "
            + "join fetch post.participates participates ";
    private static final String CREATED_DESC = "order by post.created desc";

    private final CrudRepository crudRepository;

    public List<Post> findPostLastDay() {
        return crudRepository.query(PART_QUERY
                        + "where post.created between :aYesterday and :aToday "
                        + CREATED_DESC, Post.class,
                Map.of("aYesterday", Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                        "aToday", Timestamp.valueOf(LocalDateTime.now()))
        );
    }

    public List<Post> findPostIsPhoto() {
        return crudRepository.query(
                PART_QUERY
                        + "where a.photo.size > 0 "
                        + CREATED_DESC, Post.class
        );
    }

    public List<Post> findPostWithModel(String model) {
        return crudRepository.query(
                PART_QUERY
                        + "where car.model = :fModel "
                        + CREATED_DESC, Post.class,
                Map.of("fModel", model)
        );
    }
}
