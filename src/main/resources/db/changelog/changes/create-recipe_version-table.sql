CREATE TABLE IF NOT EXISTS recipe_versions (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 description VARCHAR(255) DEFAULT NULL,
                                 created_date TIMESTAMP DEFAULT NULL,
                                 recipe_id BIGINT DEFAULT NULL,
                                 FOREIGN KEY (recipe_id) REFERENCES recipes(id)
);