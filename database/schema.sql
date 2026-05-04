CREATE DATABASE IF NOT EXISTS motobuild;
USE motobuild;

DROP TABLE IF EXISTS part_conflicts;
DROP TABLE IF EXISTS part_dependencies;
DROP TABLE IF EXISTS bike_part_incompatibility;
DROP TABLE IF EXISTS build_parts;
DROP TABLE IF EXISTS builds;
DROP TABLE IF EXISTS parts;
DROP TABLE IF EXISTS part_categories;
DROP TABLE IF EXISTS motorcycles;
DROP TABLE IF EXISTS account_activity;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(50)  NOT NULL,
    last_name     VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE account_activity
(
    activity_id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id          INT NOT NULL,
    activity_type    VARCHAR(50) NOT NULL,
    activity_message VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_activity_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE CASCADE
);

CREATE TABLE motorcycles
(
    motorcycle_id INT AUTO_INCREMENT PRIMARY KEY,
    make          VARCHAR(50)  NOT NULL,
    model         VARCHAR(80)  NOT NULL,
    year          INT          NOT NULL,
    engine_cc     INT,
    bike_type     VARCHAR(50),
    image_url     VARCHAR(255),
    UNIQUE (make, model, year)
);

CREATE TABLE part_categories
(
    category_id   INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(75) NOT NULL UNIQUE,
    description   TEXT
);

CREATE TABLE parts
(
    part_id            INT AUTO_INCREMENT PRIMARY KEY,
    category_id        INT            NOT NULL,
    part_name          VARCHAR(120)   NOT NULL,
    brand              VARCHAR(80)    NOT NULL,
    part_number        VARCHAR(80),
    description        TEXT,
    price              DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    install_difficulty ENUM('Easy', 'Medium', 'Hard') DEFAULT 'Medium',
    image_url          VARCHAR(255),

    CONSTRAINT fk_part_category
        FOREIGN KEY (category_id)
            REFERENCES part_categories (category_id)
            ON DELETE RESTRICT
);

CREATE TABLE builds
(
    build_id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT          NOT NULL,
    motorcycle_id INT          NOT NULL,
    build_name    VARCHAR(100) NOT NULL,
    description   TEXT,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_build_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_build_motorcycle
        FOREIGN KEY (motorcycle_id)
            REFERENCES motorcycles (motorcycle_id)
            ON DELETE RESTRICT
);

CREATE TABLE build_parts
(
    build_part_id INT AUTO_INCREMENT PRIMARY KEY,
    build_id      INT NOT NULL,
    part_id       INT NOT NULL,
    status        ENUM('planned', 'installed', 'removed') NOT NULL DEFAULT 'planned',
    quantity      INT NOT NULL DEFAULT 1,
    custom_price  DECIMAL(10, 2),
    notes         TEXT,
    added_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_build_part_build
        FOREIGN KEY (build_id)
            REFERENCES builds (build_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_build_part_part
        FOREIGN KEY (part_id)
            REFERENCES parts (part_id)
            ON DELETE RESTRICT,

    CONSTRAINT uq_build_part
        UNIQUE (build_id, part_id),

    CONSTRAINT chk_build_part_quantity
        CHECK (quantity > 0)
);

CREATE TABLE bike_part_incompatibility
(
    incompatibility_id INT AUTO_INCREMENT PRIMARY KEY,
    motorcycle_id      INT NOT NULL,
    part_id            INT NOT NULL,
    reason             TEXT,

    CONSTRAINT fk_incompat_motorcycle
        FOREIGN KEY (motorcycle_id)
            REFERENCES motorcycles (motorcycle_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_incompat_part
        FOREIGN KEY (part_id)
            REFERENCES parts (part_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_bike_part_incompatibility
        UNIQUE (motorcycle_id, part_id)
);

CREATE TABLE part_dependencies
(
    dependency_id    INT AUTO_INCREMENT PRIMARY KEY,
    part_id          INT NOT NULL,
    required_part_id INT NOT NULL,
    dependency_group VARCHAR(80) NOT NULL DEFAULT 'default',
    notes            TEXT,

    CONSTRAINT fk_dependency_part
        FOREIGN KEY (part_id)
            REFERENCES parts (part_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_dependency_required_part
        FOREIGN KEY (required_part_id)
            REFERENCES parts (part_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_part_dependency
        UNIQUE (part_id, required_part_id),

    CONSTRAINT chk_no_self_dependency
        CHECK (part_id <> required_part_id)
);

CREATE TABLE part_conflicts
(
    conflict_id         INT AUTO_INCREMENT PRIMARY KEY,
    part_id             INT NOT NULL,
    conflicting_part_id INT NOT NULL,
    notes               TEXT,

    CONSTRAINT fk_conflict_part
        FOREIGN KEY (part_id)
            REFERENCES parts (part_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_conflict_conflicting_part
        FOREIGN KEY (conflicting_part_id)
            REFERENCES parts (part_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_part_conflict
        UNIQUE (part_id, conflicting_part_id),

    CONSTRAINT chk_no_self_conflict
        CHECK (part_id <> conflicting_part_id)
);

CREATE INDEX idx_activity_user ON account_activity (user_id);
CREATE INDEX idx_activity_created_at ON account_activity (created_at);

CREATE INDEX idx_parts_category ON parts (category_id);
CREATE INDEX idx_builds_user ON builds (user_id);
CREATE INDEX idx_builds_motorcycle ON builds (motorcycle_id);
CREATE INDEX idx_build_parts_build ON build_parts (build_id);
CREATE INDEX idx_build_parts_part ON build_parts (part_id);
CREATE INDEX idx_incompat_motorcycle ON bike_part_incompatibility (motorcycle_id);
CREATE INDEX idx_incompat_part ON bike_part_incompatibility (part_id);
CREATE INDEX idx_dependency_part ON part_dependencies (part_id);
CREATE INDEX idx_dependency_required_part ON part_dependencies (required_part_id);
CREATE INDEX idx_conflict_part ON part_conflicts (part_id);
CREATE INDEX idx_conflict_conflicting_part ON part_conflicts (conflicting_part_id);

CREATE TRIGGER trg_user_created
    AFTER INSERT ON users
    FOR EACH ROW
    INSERT INTO account_activity (user_id, activity_type, activity_message)
    VALUES (
               NEW.user_id,
               'ACCOUNT_CREATED',
               CONCAT('Account created for ', NEW.first_name, ' ', NEW.last_name)
           );

CREATE TRIGGER trg_build_created
    AFTER INSERT ON builds
    FOR EACH ROW
    INSERT INTO account_activity (user_id, activity_type, activity_message)
    VALUES (
               NEW.user_id,
               'BUILD_CREATED',
               CONCAT('Created build: ', NEW.build_name)
           );

CREATE TRIGGER trg_build_deleted
    AFTER DELETE ON builds
    FOR EACH ROW
    INSERT INTO account_activity (user_id, activity_type, activity_message)
    VALUES (
               OLD.user_id,
               'BUILD_DELETED',
               CONCAT('Deleted build: ', OLD.build_name)
           );

CREATE TRIGGER trg_part_added
    AFTER INSERT ON build_parts
    FOR EACH ROW
    INSERT INTO account_activity (user_id, activity_type, activity_message)
    SELECT
        b.user_id,
        'PART_ADDED',
        CONCAT('Added part: ', p.part_name, ' to ', b.build_name)
    FROM builds b
             JOIN parts p ON p.part_id = NEW.part_id
    WHERE b.build_id = NEW.build_id;

CREATE TRIGGER trg_part_removed
    AFTER DELETE ON build_parts
    FOR EACH ROW
    INSERT INTO account_activity (user_id, activity_type, activity_message)
    SELECT
        b.user_id,
        'PART_REMOVED',
        CONCAT('Removed part: ', p.part_name, ' from ', b.build_name)
    FROM builds b
             JOIN parts p ON p.part_id = OLD.part_id
    WHERE b.build_id = OLD.build_id;