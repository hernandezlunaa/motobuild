USE motobuild;

-- =========================================================
-- MotoBuild Test User Data
-- Email: test@moto
-- Password: test123
-- Name: Test User
-- =========================================================

-- Create test user.
-- Password hash is SHA-256 for: test123
INSERT INTO users (first_name, last_name, email, password_hash)
VALUES
    ('Test', 'User', 'test@moto', 'ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae');

SET @test_user_id = LAST_INSERT_ID();

-- =========================================================
-- Build 1: Normal bike build
-- Yamaha R6 with a realistic mix of planned and installed parts.
-- =========================================================

INSERT INTO builds (user_id, motorcycle_id, build_name, description)
VALUES
    (@test_user_id, 1, 'Normal R6 Street Build', 'A normal realistic build with a few common upgrades.');

SET @normal_build_id = LAST_INSERT_ID();

INSERT INTO build_parts (build_id, part_id, status, quantity, notes)
VALUES
    (@normal_build_id, 2, 'installed', 1, 'Slip-on exhaust already installed.'),
    (@normal_build_id, 11, 'installed', 1, 'Shorty levers installed.'),
    (@normal_build_id, 12, 'installed', 1, 'Tank grips installed.'),
    (@normal_build_id, 17, 'planned', 1, 'Brake pad upgrade planned.'),
    (@normal_build_id, 18, 'planned', 1, 'Steel braided brake lines planned.'),
    (@normal_build_id, 29, 'planned', 1, 'Fender eliminator planned.');

-- =========================================================
-- Build 2: Empty build
-- Yamaha MT-07 with no parts.
-- =========================================================

INSERT INTO builds (user_id, motorcycle_id, build_name, description)
VALUES
    (@test_user_id, 4, 'Empty MT-07 Build', 'This build intentionally has no parts added yet.');

SET @empty_build_id = LAST_INSERT_ID();

-- No build_parts inserted for this build.

-- =========================================================
-- Build 3: Everything build
-- Kawasaki Ninja ZX-10R with every part added.
-- This is useful for testing total cost, cart cost, warnings, conflicts, and status behavior.
-- =========================================================

INSERT INTO builds (user_id, motorcycle_id, build_name, description)
VALUES
    (@test_user_id, 8, 'Everything ZX-10R Build', 'This build has every catalog part added for testing.');

SET @everything_build_id = LAST_INSERT_ID();

INSERT INTO build_parts (build_id, part_id, status, quantity, notes)
VALUES
    (@everything_build_id, 1, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 2, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 3, 'installed', 1, 'Everything test part.'),
    (@everything_build_id, 4, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 5, 'installed', 1, 'Everything test part.'),
    (@everything_build_id, 6, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 7, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 8, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 9, 'installed', 1, 'Everything test part.'),
    (@everything_build_id, 10, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 11, 'installed', 1, 'Everything test part.'),
    (@everything_build_id, 12, 'installed', 1, 'Everything test part.'),
    (@everything_build_id, 13, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 14, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 15, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 16, 'installed', 1, 'Everything test part.'),
    (@everything_build_id, 17, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 18, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 19, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 20, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 21, 'installed', 1, 'Everything test part.'),
    (@everything_build_id, 22, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 23, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 24, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 25, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 26, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 27, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 28, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 29, 'planned', 1, 'Everything test part.'),
    (@everything_build_id, 30, 'installed', 1, 'Everything test part.');

-- =========================================================
-- Build 4: Honda Rebel with incompatible parts
-- This should create multiple incompatibility warnings.
-- =========================================================

INSERT INTO builds (user_id, motorcycle_id, build_name, description)
VALUES
    (@test_user_id, 6, 'Rebel Incompatible Test Build', 'Honda Rebel build with intentionally incompatible parts added.');

SET @rebel_bad_build_id = LAST_INSERT_ID();

INSERT INTO build_parts (build_id, part_id, status, quantity, notes)
VALUES
    (@rebel_bad_build_id, 1, 'planned', 1, 'Incompatible test: R6-specific full exhaust.'),
    (@rebel_bad_build_id, 7, 'planned', 1, 'Incompatible test: quickshifter.'),
    (@rebel_bad_build_id, 8, 'planned', 1, 'Incompatible test: auto blipper.'),
    (@rebel_bad_build_id, 9, 'planned', 1, 'Incompatible test: sport rearsets.'),
    (@rebel_bad_build_id, 10, 'planned', 1, 'Incompatible test: clip-ons.'),
    (@rebel_bad_build_id, 13, 'planned', 1, 'Incompatible test: race fairing kit.'),
    (@rebel_bad_build_id, 14, 'planned', 1, 'Incompatible test: fairing stay.'),
    (@rebel_bad_build_id, 15, 'planned', 1, 'Incompatible test: sport windscreen.'),
    (@rebel_bad_build_id, 20, 'planned', 1, 'Incompatible test: master cylinder setup.'),
    (@rebel_bad_build_id, 22, 'planned', 1, 'Incompatible test: fork cartridge kit.');