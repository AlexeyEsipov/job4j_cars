CREATE TABLE participates (
    id SERIAL PRIMARY KEY,
    auto_user_id int not null references auto_user(id),
    auto_post_id int not null references auto_post(id)
);