CREATE TABLE posts (
    post_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     VARCHAR(255)  NOT NULL,
    content     TEXT,
    post_type   ENUM('TEXT_ONLY', 'IMAGE_ONLY', 'TEXT_IMAGE') NOT NULL,
    is_deleted  BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME      ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_posts_user_id (user_id),
    INDEX idx_posts_created_at (created_at DESC)
);

CREATE TABLE post_images (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id     BIGINT        NOT NULL,
    image_url   VARCHAR(2048) NOT NULL,

    CONSTRAINT fk_post_images_post
        FOREIGN KEY (post_id) REFERENCES posts(post_id)
        ON DELETE CASCADE,

    INDEX idx_post_images_post_id (post_id)
);

-- ...existing code...

CREATE TABLE comments (
    comment_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id     BIGINT        NOT NULL,
    user_id     VARCHAR(255)  NOT NULL,
    text        TEXT          NOT NULL,
    is_deleted  BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME      ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_comments_post
        FOREIGN KEY (post_id) REFERENCES posts(post_id)
        ON DELETE CASCADE,

    INDEX idx_comments_post_id (post_id),
    INDEX idx_comments_user_id (user_id),
    INDEX idx_comments_created_at (created_at DESC)
);