CREATE TABLE IF NOT EXISTS recipes (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         description VARCHAR(255) DEFAULT NULL,
                         created_date TIMESTAMP DEFAULT NULL,
                         recipe_parent_id BIGINT DEFAULT NULL,
                         FOREIGN KEY (recipe_parent_id) REFERENCES recipes(id)
);