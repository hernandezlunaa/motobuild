USE motobuild;

INSERT INTO users (first_name, last_name, email, password_hash)
VALUES
    ('Test', 'User', 'test@motobuild.local', 'not_used_yet');

INSERT INTO motorcycles (make, model, year, engine_cc, bike_type, image_url)
VALUES
    ('Yamaha', 'YZF-R6', 2020, 600, 'Sport', 'r6.jpg'),
    ('Kawasaki', 'Ninja 400', 2023, 400, 'Sport', 'ninja400.jpg'),
    ('Honda', 'CBR600RR', 2021, 600, 'Sport', 'cbr600rr.jpg');

INSERT INTO part_categories (category_name, description)
VALUES
    ('Exhaust', 'Exhaust systems'),
    ('Tuning', 'ECU and tuning components'),
    ('Controls', 'Clip-ons, rearsets, handlebars'),
    ('Bodywork', 'Fairings and body parts'),
    ('Brakes', 'Brake systems and pads'),
    ('Suspension', 'Forks and shocks'),
    ('Drivetrain', 'Chains and sprockets'),
    ('Lighting', 'Lights and signals');

INSERT INTO parts (category_id, part_name, brand, part_number, description, price, install_difficulty, image_url)
VALUES
    (1, 'Full Titanium Exhaust System', 'Akrapovic', 'AKR-R6-FULL-TI', 'Full titanium performance exhaust system', 1299.99, 'Hard', 'akrapovic-full-exhaust.jpg'),
    (1, 'Slip-On Exhaust', 'Yoshimura', 'YOSH-SLIP-01', 'Slip-on exhaust for better sound and lighter weight', 599.99, 'Easy', 'slip-on-exhaust-yoshimura.png'),
    (1, 'Carbon Exhaust Heat Shield', 'Akrapovic', 'AKR-HEAT-01', 'Carbon fiber heat shield for exhaust protection', 179.99, 'Easy', 'carbon-exhaust-heat-shield-akrapovic.png'),
    (1, 'Mid Pipe Link', 'M4', 'M4-MID-01', 'Mid pipe link for exhaust conversion', 249.99, 'Medium', 'mid-pipe-link-m4.png'),

    (2, 'ECU Flash Tune', 'Dynojet', 'DJ-ECU-01', 'Performance ECU flash tune', 399.99, 'Medium', 'ecu-flash-tune-dynojet.png'),
    (2, 'Power Commander V', 'Dynojet', 'PCV-001', 'Fuel management controller', 349.99, 'Medium', 'power-commander-v-dynojet.png'),
    (2, 'Quickshifter', 'Dynojet', 'DJ-QS-01', 'Clutchless upshift system', 349.99, 'Medium', 'quickshifter-dynojet.png'),
    (2, 'Auto Blipper Kit', 'Woolich Racing', 'WR-BLIP-01', 'Auto blipper for smoother downshifts', 449.99, 'Hard', 'auto-blipper-kit-woolich-racing.png'),

    (3, 'Adjustable Rearsets', 'Woodcraft', 'WC-RS-01', 'CNC adjustable rearsets', 499.99, 'Medium', 'adjustable-rearsets-woodcraft.png'),
    (3, 'Clip-On Handlebars', 'Vortex', 'VX-CLIP-01', 'Race style clip-on handlebars', 199.99, 'Easy', 'clip-on-handlebars-vortex.png'),
    (3, 'Shorty Levers', 'ASV', 'ASV-LVR-01', 'Adjustable short brake and clutch levers', 179.99, 'Easy', 'shorty-levers-asv.png'),
    (3, 'Tank Grips', 'Stompgrip', 'STOMP-01', 'Tank grip pads for better rider control', 59.99, 'Easy', 'tank-grips-stompgrip.png'),

    (4, 'Race Fairing Kit', 'Hotbodies', 'HB-FAIR-01', 'Track-use race fairing kit', 699.99, 'Hard', 'race-fairing-kit-hotbodies.png'),
    (4, 'Fairing Stay', 'Woodcraft', 'WC-STAY-01', 'Aluminum fairing stay for race bodywork', 249.99, 'Medium', 'fairing-stay-woodcraft.png'),
    (4, 'Double Bubble Windscreen', 'Puig', 'PUIG-WS-01', 'Tinted double bubble windscreen', 119.99, 'Easy', 'double-bubble-windscreen-puig.jpg'),
    (4, 'Frame Sliders', 'Shogun', 'SHO-SLIDE-01', 'Frame slider crash protection kit', 109.99, 'Easy', 'frame-sliders-shogun.png'),

    (5, 'Performance Brake Pads', 'Brembo', 'BR-PAD-01', 'High performance front brake pads', 149.99, 'Easy', 'performance-brake-pads-brembo.png'),
    (5, 'Steel Braided Brake Lines', 'Galfer', 'GAL-LINE-01', 'Steel braided brake line kit', 129.99, 'Medium', 'steel-braided-brake-lines-galfer.png'),
    (5, 'Front Brake Rotors', 'EBC', 'EBC-ROT-01', 'Performance front brake rotor set', 299.99, 'Medium', 'front-brake-rotors-ebc.png'),
    (5, 'Brembo Master Cylinder', 'Brembo', 'BR-MC-01', 'Upgraded front brake master cylinder', 379.99, 'Hard', 'brembo-master-cylinder-brembo.png'),

    (6, 'Rear Shock', 'Ohlins', 'OHL-SHOCK-01', 'Adjustable rear shock absorber', 899.99, 'Hard', 'rear-shock-ohlins.png'),
    (6, 'Fork Cartridge Kit', 'Ohlins', 'OHL-FORK-01', 'Front fork cartridge suspension upgrade', 799.99, 'Hard', 'fork-cartridge-kit-ohlins.png'),
    (6, 'Steering Damper', 'Ohlins', 'OHL-DAMP-01', 'Adjustable steering stabilizer', 449.99, 'Medium', 'steering-damper-ohlins.png'),

    (7, '520 Chain Conversion Kit', 'DID', 'DID-520-01', 'Lightweight chain and sprocket conversion kit', 219.99, 'Medium', '520-chain-conversion-kit-did.png'),
    (7, 'Aluminum Rear Sprocket', 'Vortex', 'VX-SPROCKET-01', 'Lightweight aluminum rear sprocket', 89.99, 'Easy', 'aluminum-rear-sprocket-vortex.png'),
    (7, 'Heavy Duty Clutch Kit', 'Barnett', 'BAR-CLUTCH-01', 'Heavy duty clutch plate and spring kit', 249.99, 'Hard', 'heavy-duty-clutch-kit-barnett.png'),

    (8, 'LED Turn Signals', 'TST Industries', 'TST-LED-01', 'Sequential LED turn signals', 89.99, 'Easy', 'led-turn-signals-tst-industries.png'),
    (8, 'Integrated Tail Light', 'TST Industries', 'TST-TAIL-01', 'Tail light with built-in turn signals', 139.99, 'Easy', 'integrated-tail-light-tst-industries.png'),
    (8, 'Fender Eliminator Kit', 'TST Industries', 'TST-FE-01', 'Tidy tail fender eliminator kit', 119.99, 'Easy', 'fender-eliminator-kit-tst-industries.png'),
    (8, 'LED Headlight Bulbs', 'Cyclops', 'CYC-HEAD-01', 'Bright LED headlight bulb upgrade', 99.99, 'Easy', 'led-headlight-bulb-cyclops.png');

-- Test builds for the default test user
INSERT INTO builds (user_id, motorcycle_id, build_name, description)
VALUES
    (1, 1, 'R6 Track Build', 'Starter track build for testing parts and compatibility.'),
    (1, 2, 'Ninja 400 Street Build', 'Simple street build for testing multiple builds.');

INSERT INTO build_parts (build_id, part_id, status, quantity, notes)
VALUES
    (1, 1, 'planned', 1, 'Full exhaust planned for track setup.'),
    (1, 5, 'planned', 1, 'Tune needed for exhaust setup.'),
    (1, 9, 'installed', 1, 'Rearsets already installed.'),
    (1, 12, 'installed', 1, 'Tank grips already installed.'),
    (1, 17, 'planned', 1, 'Brake pad upgrade.'),
    (1, 21, 'planned', 1, 'Rear shock upgrade.'),

    (2, 2, 'planned', 1, 'Slip-on exhaust for sound.'),
    (2, 11, 'planned', 1, 'Short levers.'),
    (2, 16, 'planned', 1, 'Crash protection.'),
    (2, 27, 'planned', 1, 'LED signal upgrade.');

-- Compatibility:
-- If a part is listed here for a motorcycle, the app can treat it as compatible.
-- If it is not listed, the app can warn the user that it may not fit.

-- Yamaha R6 compatibility
INSERT INTO bike_part_compatibility (motorcycle_id, part_id, notes)
VALUES
    (1, 1, 'Fits Yamaha R6.'),
    (1, 2, 'Fits Yamaha R6.'),
    (1, 3, 'Fits Yamaha R6.'),
    (1, 4, 'Fits Yamaha R6.'),
    (1, 5, 'Fits Yamaha R6.'),
    (1, 6, 'Fits Yamaha R6.'),
    (1, 7, 'Fits Yamaha R6.'),
    (1, 8, 'Fits Yamaha R6.'),
    (1, 9, 'Fits Yamaha R6.'),
    (1, 10, 'Fits Yamaha R6.'),
    (1, 11, 'Fits Yamaha R6.'),
    (1, 12, 'Fits Yamaha R6.'),
    (1, 13, 'Fits Yamaha R6.'),
    (1, 14, 'Fits Yamaha R6.'),
    (1, 15, 'Fits Yamaha R6.'),
    (1, 16, 'Fits Yamaha R6.'),
    (1, 17, 'Fits Yamaha R6.'),
    (1, 18, 'Fits Yamaha R6.'),
    (1, 19, 'Fits Yamaha R6.'),
    (1, 20, 'Fits Yamaha R6.'),
    (1, 21, 'Fits Yamaha R6.'),
    (1, 22, 'Fits Yamaha R6.'),
    (1, 23, 'Fits Yamaha R6.'),
    (1, 24, 'Fits Yamaha R6.'),
    (1, 25, 'Fits Yamaha R6.'),
    (1, 26, 'Fits Yamaha R6.'),
    (1, 27, 'Fits Yamaha R6.'),
    (1, 28, 'Fits Yamaha R6.'),
    (1, 29, 'Fits Yamaha R6.'),
    (1, 30, 'Fits Yamaha R6.');

-- Kawasaki Ninja 400 compatibility
INSERT INTO bike_part_compatibility (motorcycle_id, part_id, notes)
VALUES
    (2, 2, 'Fits Ninja 400.'),
    (2, 3, 'Universal exhaust accessory.'),
    (2, 5, 'Tune option for Ninja 400.'),
    (2, 6, 'Fuel controller option.'),
    (2, 7, 'Quickshifter option.'),
    (2, 9, 'Rearset option.'),
    (2, 10, 'Clip-on option.'),
    (2, 11, 'Lever option.'),
    (2, 12, 'Universal tank grip style part.'),
    (2, 15, 'Windscreen option.'),
    (2, 16, 'Frame slider option.'),
    (2, 17, 'Brake pad option.'),
    (2, 18, 'Brake line option.'),
    (2, 19, 'Brake rotor option.'),
    (2, 21, 'Rear shock option.'),
    (2, 22, 'Fork upgrade option.'),
    (2, 23, 'Steering damper option.'),
    (2, 24, 'Chain conversion option.'),
    (2, 25, 'Rear sprocket option.'),
    (2, 26, 'Clutch kit option.'),
    (2, 27, 'LED signal option.'),
    (2, 28, 'Integrated tail light option.'),
    (2, 29, 'Fender eliminator option.'),
    (2, 30, 'LED headlight option.');

-- Honda CBR600RR compatibility
INSERT INTO bike_part_compatibility (motorcycle_id, part_id, notes)
VALUES
    (3, 2, 'Fits CBR600RR.'),
    (3, 3, 'Universal exhaust accessory.'),
    (3, 5, 'Tune option for CBR600RR.'),
    (3, 6, 'Fuel controller option.'),
    (3, 7, 'Quickshifter option.'),
    (3, 8, 'Auto blipper option.'),
    (3, 9, 'Rearset option.'),
    (3, 10, 'Clip-on option.'),
    (3, 11, 'Lever option.'),
    (3, 12, 'Universal tank grip style part.'),
    (3, 13, 'Race fairing option.'),
    (3, 14, 'Fairing stay option.'),
    (3, 15, 'Windscreen option.'),
    (3, 16, 'Frame slider option.'),
    (3, 17, 'Brake pad option.'),
    (3, 18, 'Brake line option.'),
    (3, 19, 'Brake rotor option.'),
    (3, 20, 'Master cylinder option.'),
    (3, 21, 'Rear shock option.'),
    (3, 22, 'Fork upgrade option.'),
    (3, 23, 'Steering damper option.'),
    (3, 24, 'Chain conversion option.'),
    (3, 25, 'Rear sprocket option.'),
    (3, 26, 'Clutch kit option.'),
    (3, 27, 'LED signal option.'),
    (3, 28, 'Integrated tail light option.'),
    (3, 29, 'Fender eliminator option.'),
    (3, 30, 'LED headlight option.');

-- Dependencies:
-- part_id = the part being added
-- required_part_id = the part it needs/recommends
INSERT INTO part_dependencies (part_id, required_part_id, notes)
VALUES
    (1, 5, 'Full exhaust is recommended with an ECU flash tune.'),
    (1, 6, 'Full exhaust can also use a fuel controller.'),
    (7, 5, 'Quickshifter requires ECU tuning support.'),
    (8, 5, 'Auto blipper requires ECU tuning support.'),
    (13, 14, 'Race fairing kit requires a fairing stay for mounting.'),
    (20, 18, 'Upgraded master cylinder works best with steel braided brake lines.');

-- Conflicts:
-- These are entered in both directions to make querying easier.
INSERT INTO part_conflicts (part_id, conflicting_part_id, notes)
VALUES
    (1, 2, 'A full exhaust system replaces the slip-on exhaust setup.'),
    (2, 1, 'A slip-on exhaust cannot be used with a full exhaust system.'),
    (27, 28, 'Separate LED turn signals conflict with an integrated tail light.'),
    (28, 27, 'Integrated tail light already includes turn signal functionality.');