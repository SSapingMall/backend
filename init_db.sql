DROP TABLE IF EXISTS `Post`;
DROP TABLE IF EXISTS `PostReply`;
DROP TABLE IF EXISTS `Product`;
DROP TABLE IF EXISTS `ProductImage`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `User_Info`;

CREATE TABLE IF NOT EXISTS `user` (
                        `id` INT AUTO_INCREMENT NOT NULL,
                        `type` INT NOT NULL,
                        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        `username` VARCHAR(255) NULL,
                        `password` VARCHAR(255) NOT NULL,
                        PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `product` (
                           `id` INT AUTO_INCREMENT NOT NULL,
                           `image_url` VARCHAR(255) NULL,
                           `name` VARCHAR(255) NULL,
                           `price` INT NULL,
                           `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `description` VARCHAR(255) NULL,
                           `category` INT NULL,
                           `stock` INT NULL,
                           `user_id` INT, -- User 테이블의 id를 참조하는 외래키 필드 추가
                           PRIMARY KEY (`id`),
                           FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) -- 외래키 제약 조건 정의
);

CREATE TABLE IF NOT EXISTS `post` (
                        `id` INT AUTO_INCREMENT NOT NULL,
                        `contents` VARCHAR(255) NULL,
                        `title` VARCHAR(255) NULL,
                        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `likes` INT NULL,
                        `dislikes` INT NULL,
                        `views` INT NULL,
                        `user_id` INT, -- User 테이블의 id를 참조하는 외래키 필드 추가
                        PRIMARY KEY (`id`),
                        FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) -- 외래키 제약 조건 정의
);

CREATE TABLE IF NOT EXISTS `user_info` (
                             `id` INT AUTO_INCREMENT NOT NULL,
                             `address` VARCHAR(255) NULL,
                             `is_default` BOOLEAN DEFAULT TRUE NULL,
                             PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `post_reply` (
                             `id` INT AUTO_INCREMENT NOT NULL,
                             `contents` VARCHAR(255) NULL,
                             `created_at` DATETIME NULL,
                             `updated_at` DATETIME NULL,
                             `likes` INT NULL,
                             `post_id` INT, -- user 테이블의 id를 참조하는 외래키 필드 추가
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`post_id`) REFERENCES `post`(`id`) -- 외래키 제약 조건 정의
);

CREATE TABLE IF NOT EXISTS `product_image` (
                                `id` INT AUTO_INCREMENT NOT NULL,
                                `url` VARCHAR(255) NULL,
                                `product_id` INT, -- User 테이블의 id를 참조하는 외래키 필드 추가
                                PRIMARY KEY (`id`),
                                FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) -- 외래키 제약 조건 정의
);
