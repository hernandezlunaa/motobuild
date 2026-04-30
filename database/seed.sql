use
motobuild;

INSERT INTO motorcycles (make, model, year, engine_cc, bike_type)
VALUES ('Yamaha', 'YZF-R6', 2020, 600, 'Sport'),
       ('Kawasaki', 'Ninja 400', 2023, 400, 'Sport'),
       ('Honda', 'CBR600RR', 2021, 600, 'Sport');

select *
from motorcycles;

INSERT INTO part_categories (category_name, description)
VALUES ('Exhaust', 'Exhaust systems'),
       ('Tuning', 'ECU and tuning components'),
       ('Controls', 'Clip-ons, rearsets, handlebars'),
       ('Bodywork', 'Fairings and body parts'),
       ('Brakes', 'Brake systems and pads'),
       ('Suspension', 'Forks and shocks'),
       ('Drivetrain', 'Chains and sprockets'),
       ('Lighting', 'Lights and signals');

select *
from part_categories;

INSERT INTO parts (category_id, part_name, brand, part_number, description, price, install_difficulty)
VALUES
-- Exhaust
(1, 'Full Titanium Exhaust System', 'Akrapovic', 'AKR-R6-FULL-TI', 'Full titanium performance exhaust system', 1299.99,
 'Hard'),
(1, 'Slip-On Exhaust', 'Yoshimura', 'YOSH-SLIP-01', 'Slip-on exhaust for better sound and lighter weight', 599.99,
 'Easy'),
(1, 'Carbon Exhaust Heat Shield', 'Akrapovic', 'AKR-HEAT-01', 'Carbon fiber heat shield for exhaust protection', 179.99,
 'Easy'),
(1, 'Mid Pipe Link', 'M4', 'M4-MID-01', 'Mid pipe link for exhaust conversion', 249.99, 'Medium'),

-- Tuning
(2, 'ECU Flash Tune', 'Dynojet', 'DJ-ECU-01', 'Performance ECU flash tune', 399.99, 'Medium'),
(2, 'Power Commander V', 'Dynojet', 'PCV-001', 'Fuel management controller', 349.99, 'Medium'),
(2, 'Quickshifter', 'Dynojet', 'DJ-QS-01', 'Clutchless upshift system', 349.99, 'Medium'),
(2, 'Auto Blipper Kit', 'Woolich Racing', 'WR-BLIP-01', 'Auto blipper for smoother downshifts', 449.99, 'Hard'),

-- Controls
(3, 'Adjustable Rearsets', 'Woodcraft', 'WC-RS-01', 'CNC adjustable rearsets', 499.99, 'Medium'),
(3, 'Clip-On Handlebars', 'Vortex', 'VX-CLIP-01', 'Race style clip-on handlebars', 199.99, 'Easy'),
(3, 'Shorty Levers', 'ASV', 'ASV-LVR-01', 'Adjustable short brake and clutch levers', 179.99, 'Easy'),
(3, 'Tank Grips', 'Stompgrip', 'STOMP-01', 'Tank grip pads for better rider control', 59.99, 'Easy'),

-- Bodywork
(4, 'Race Fairing Kit', 'Hotbodies', 'HB-FAIR-01', 'Track-use race fairing kit', 699.99, 'Hard'),
(4, 'Fairing Stay', 'Woodcraft', 'WC-STAY-01', 'Aluminum fairing stay for race bodywork', 249.99, 'Medium'),
(4, 'Double Bubble Windscreen', 'Puig', 'PUIG-WS-01', 'Tinted double bubble windscreen', 119.99, 'Easy'),
(4, 'Frame Sliders', 'Shogun', 'SHO-SLIDE-01', 'Frame slider crash protection kit', 109.99, 'Easy'),

-- Brakes
(5, 'Performance Brake Pads', 'Brembo', 'BR-PAD-01', 'High performance front brake pads', 149.99, 'Easy'),
(5, 'Steel Braided Brake Lines', 'Galfer', 'GAL-LINE-01', 'Steel braided brake line kit', 129.99, 'Medium'),
(5, 'Front Brake Rotors', 'EBC', 'EBC-ROT-01', 'Performance front brake rotor set', 299.99, 'Medium'),
(5, 'Brembo Master Cylinder', 'Brembo', 'BR-MC-01', 'Upgraded front brake master cylinder', 379.99, 'Hard'),

-- Suspension
(6, 'Rear Shock', 'Ohlins', 'OHL-SHOCK-01', 'Adjustable rear shock absorber', 899.99, 'Hard'),
(6, 'Fork Cartridge Kit', 'Ohlins', 'OHL-FORK-01', 'Front fork cartridge suspension upgrade', 799.99, 'Hard'),
(6, 'Steering Damper', 'Ohlins', 'OHL-DAMP-01', 'Adjustable steering stabilizer', 449.99, 'Medium'),

-- Drivetrain
(7, '520 Chain Conversion Kit', 'DID', 'DID-520-01', 'Lightweight chain and sprocket conversion kit', 219.99, 'Medium'),
(7, 'Aluminum Rear Sprocket', 'Vortex', 'VX-SPROCKET-01', 'Lightweight aluminum rear sprocket', 89.99, 'Easy'),
(7, 'Heavy Duty Clutch Kit', 'Barnett', 'BAR-CLUTCH-01', 'Heavy duty clutch plate and spring kit', 249.99, 'Hard'),

-- Lighting
(8, 'LED Turn Signals', 'TST Industries', 'TST-LED-01', 'Sequential LED turn signals', 89.99, 'Easy'),
(8, 'Integrated Tail Light', 'TST Industries', 'TST-TAIL-01', 'Tail light with built-in turn signals', 139.99, 'Easy'),
(8, 'Fender Eliminator Kit', 'TST Industries', 'TST-FE-01', 'Tidy tail fender eliminator kit', 119.99, 'Easy'),
(8, 'LED Headlight Bulbs', 'Cyclops', 'CYC-HEAD-01', 'Bright LED headlight bulb upgrade', 99.99, 'Easy');

select *
from parts;

INSERT INTO part_dependencies (part_id, required_part_id, notes)
VALUES (5, 1, 'ECU flash tune is recommended with a full exhaust system.'),
       (6, 1, 'Power Commander is commonly used with a full exhaust setup.'),
       (7, 5, 'Quickshifter requires ECU tuning support.'),
       (8, 5, 'Auto blipper requires ECU tuning support.'),
       (13, 14, 'Race fairing kit requires a fairing stay for mounting.'),
       (20, 18, 'Upgraded master cylinder works best with steel braided brake lines.');

select *
from part_dependencies;

INSERT INTO part_conflicts (part_id, conflicting_part_id, notes)
VALUES (1, 2, 'A full exhaust system replaces the slip-on exhaust setup.'),
       (2, 1, 'A slip-on exhaust cannot be used with a full exhaust system.'),
       (27, 28, 'Separate LED turn signals conflict with an integrated tail light.'),
       (28, 27, 'Integrated tail light already includes turn signal functionality.');

select *
from part_conflicts;

-- UPDATES

ALTER TABLE motorcycles
    ADD COLUMN image_url VARCHAR(255);

UPDATE motorcycles
SET image_url = 'r6.jpg'
WHERE make = 'Yamaha';

UPDATE motorcycles
SET image_url = 'ninja400.jpg'
WHERE make = 'Kawasaki';

UPDATE motorcycles
SET image_url = 'cbr600rr.jpg'
WHERE make = 'Honda';


ALTER TABLE parts
    ADD COLUMN image_url VARCHAR(255);

UPDATE parts
SET image_url = 'akrapovic-full-exhaust.jpg'
WHERE part_id = 1;
UPDATE parts
SET image_url = 'slip-on-exhaust-yoshimura.png'
WHERE part_id = 2;
UPDATE parts
SET image_url = 'carbon-exhaust-heat-shield-akrapovic.png'
WHERE part_id = 3;
UPDATE parts
SET image_url = 'mid-pipe-link-m4.png'
WHERE part_id = 4;
UPDATE parts
SET image_url = 'ecu-flash-tune-dynojet.png'
WHERE part_id = 5;
UPDATE parts
SET image_url = 'power-commander-v-dynojet.png'
WHERE part_id = 6;
UPDATE parts
SET image_url = 'quickshifter-dynojet.png'
WHERE part_id = 7;
UPDATE parts
SET image_url = 'auto-blipper-kit-woolich-racing.png'
WHERE part_id = 8;
UPDATE parts
SET image_url = 'adjustable-rearsets-woodcraft.png'
WHERE part_id = 9;
UPDATE parts
SET image_url = 'clip-on-handlebars-vortex.png'
WHERE part_id = 10;
UPDATE parts
SET image_url = 'shorty-levers-asv.png'
WHERE part_id = 11;
UPDATE parts
SET image_url = 'tank-grips-stompgrip.png'
WHERE part_id = 12;
UPDATE parts
SET image_url = 'race-fairing-kit-hotbodies.png'
WHERE part_id = 13;
UPDATE parts
SET image_url = 'fairing-stay-woodcraft.png'
WHERE part_id = 14;
UPDATE parts
SET image_url = 'double-bubble-windscreen-puig.jpg'
WHERE part_id = 15;
UPDATE parts
SET image_url = 'frame-sliders-shogun.png'
WHERE part_id = 16;
UPDATE parts
SET image_url = 'performance-brake-pads-brembo.png'
WHERE part_id = 17;
UPDATE parts
SET image_url = 'steel-braided-brake-lines-galfer.png'
WHERE part_id = 18;
UPDATE parts
SET image_url = 'front-brake-rotors-ebc.png'
WHERE part_id = 19;
UPDATE parts
SET image_url = 'brembo-master-cylinder-brembo.png'
WHERE part_id = 20;
UPDATE parts
SET image_url = 'rear-shock-ohlins.png'
WHERE part_id = 21;
UPDATE parts
SET image_url = 'fork-cartridge-kit-ohlins.png'
WHERE part_id = 22;
UPDATE parts
SET image_url = 'steering-damper-ohlins.png'
WHERE part_id = 23;
UPDATE parts
SET image_url = '520-chain-conversion-kit-did.png'
WHERE part_id = 24;
UPDATE parts
SET image_url = 'aluminum-rear-sprocket-vortex.png'
WHERE part_id = 25;
UPDATE parts
SET image_url = 'heavy-duty-clutch-kit-barnett.png'
WHERE part_id = 26;
UPDATE parts
SET image_url = 'led-turn-signals-tst-industries.png'
WHERE part_id = 27;
UPDATE parts
SET image_url = 'integrated-tail-light-tst-industries.png'
WHERE part_id = 28;
UPDATE parts
SET image_url = 'fender-eliminator-kit-tst-industries.png'
WHERE part_id = 29;
UPDATE parts
SET image_url = 'led-headlight-bulb-cyclops.png'
WHERE part_id = 30;


