USE motobuild;

INSERT INTO motorcycles (make, model, year, engine_cc, bike_type, image_url)
VALUES
    ('Yamaha', 'YZF-R6', 2020, 600, 'Sport', 'r6.jpg'),
    ('Kawasaki', 'Ninja ZX-10R', 2023, 998, 'Sport', 'ninja-zx10r.png'),
    ('Honda', 'CBR600RR', 2021, 600, 'Sport', 'cbr600rr.jpg'),
    ('Kawasaki', 'Ninja 400', 2023, 400, 'Sport', 'ninja400.jpg'),
    ('Yamaha', 'MT-07', 2023, 689, 'Naked', 'mt07.png'),
    ('Kawasaki', 'Ninja 650', 2023, 649, 'Sport Touring', 'ninja650.png'),
    ('Honda', 'Rebel 500', 2023, 471, 'Cruiser', 'rebel.png'),
    ('Harley-Davidson', 'Street Glide', 2023, 1923, 'Touring', 'street-glide.png');

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

INSERT INTO build_parts (build_id, part_id, status, quantity, notes)
VALUES
    (1, 1, 'planned', 1, 'Full exhaust planned for track setup.'),
    (1, 5, 'planned', 1, 'Tune option selected for exhaust setup.'),
    (1, 9, 'installed', 1, 'Rearsets already installed.'),
    (1, 12, 'installed', 1, 'Tank grips already installed.'),
    (1, 17, 'planned', 1, 'Brake pad upgrade.'),
    (1, 21, 'planned', 1, 'Rear shock upgrade.'),

    (2, 2, 'planned', 1, 'Slip-on exhaust for sound.'),
    (2, 11, 'planned', 1, 'Short levers.'),
    (2, 16, 'planned', 1, 'Crash protection.'),
    (2, 27, 'planned', 1, 'LED signal upgrade.');

INSERT INTO bike_part_incompatibility (motorcycle_id, part_id, reason)
VALUES
-- Kawasaki Ninja 400
(2, 1, 'The full titanium exhaust system is designed for larger supersport fitment and does not fit the Ninja 400.'),
(2, 8, 'The auto blipper kit is not listed for this Ninja 400 setup.'),
(2, 13, 'The race fairing kit is not made for the Ninja 400.'),
(2, 14, 'The fairing stay is not made for the Ninja 400.'),
(2, 20, 'The Brembo master cylinder setup is not recommended for this basic Ninja 400 configuration.'),

-- Honda CBR600RR
(3, 1, 'The full titanium exhaust system in this catalog is R6-specific and does not fit the CBR600RR.'),

-- Yamaha MT-07
(4, 1, 'The full titanium exhaust system in this catalog is R6-specific and does not fit the MT-07.'),
(4, 8, 'The auto blipper kit is not listed for this MT-07 setup.'),
(4, 13, 'The race fairing kit is not made for the MT-07.'),
(4, 14, 'The fairing stay is not made for the MT-07.'),
(4, 20, 'The Brembo master cylinder setup is not recommended for this MT-07 configuration.'),

-- Kawasaki Ninja 650
(5, 1, 'The full titanium exhaust system in this catalog is R6-specific and does not fit the Ninja 650.'),
(5, 8, 'The auto blipper kit is not listed for this Ninja 650 setup.'),

-- Honda Rebel 500
(6, 1, 'The full titanium exhaust system in this catalog is R6-specific and does not fit the Rebel 500.'),
(6, 7, 'The quickshifter is not listed for this Rebel 500 setup.'),
(6, 8, 'The auto blipper kit is not listed for this Rebel 500 setup.'),
(6, 9, 'Adjustable rearsets are not made for this Rebel 500 cruiser setup.'),
(6, 10, 'Clip-on handlebars are not appropriate for this Rebel 500 cruiser setup.'),
(6, 13, 'Race fairing kit is not made for the Rebel 500.'),
(6, 14, 'Fairing stay is not made for the Rebel 500.'),
(6, 15, 'Double bubble sport windscreen is not made for the Rebel 500.'),
(6, 20, 'The Brembo master cylinder setup is not recommended for this Rebel 500 configuration.'),
(6, 22, 'Fork cartridge kit is not listed for this Rebel 500 setup.'),

-- Harley-Davidson Street Glide
(7, 1, 'The full titanium exhaust system in this catalog is R6-specific and does not fit the Street Glide.'),
(7, 7, 'The quickshifter is not listed for this Street Glide touring setup.'),
(7, 8, 'The auto blipper kit is not listed for this Street Glide touring setup.'),
(7, 9, 'Adjustable rearsets are not made for this Street Glide touring setup.'),
(7, 10, 'Clip-on handlebars are not appropriate for this Street Glide touring setup.'),
(7, 13, 'Race fairing kit is not made for the Street Glide.'),
(7, 14, 'Fairing stay is not made for the Street Glide.'),
(7, 15, 'Double bubble sport windscreen is not made for the Street Glide.'),
(7, 24, '520 chain conversion kit does not apply to this Street Glide touring setup.'),
(7, 25, 'Aluminum rear sprocket does not apply to this Street Glide touring setup.'),

-- Kawasaki Ninja ZX-10R
(8, 1, 'The full titanium exhaust system in this catalog is R6-specific and does not fit the Ninja ZX-10R.'),
(8, 2, 'The slip-on exhaust in this catalog is not listed for the Ninja ZX-10R.'),
(8, 4, 'The mid pipe link in this catalog is not listed for the Ninja ZX-10R.');

INSERT INTO part_dependencies (part_id, required_part_id, dependency_group, notes)
VALUES
    (1, 5, 'fuel_tuning', 'Full exhaust needs at least one fuel tuning option.'),
    (1, 6, 'fuel_tuning', 'Full exhaust needs at least one fuel tuning option.'),

    (7, 5, 'quickshifter_tuning', 'Quickshifter requires ECU tuning support.'),
    (8, 5, 'blipper_tuning', 'Auto blipper requires ECU tuning support.'),
    (13, 14, 'fairing_mounting', 'Race fairing kit requires a fairing stay for mounting.'),
    (20, 18, 'brake_line_upgrade', 'Upgraded master cylinder works best with steel braided brake lines.');

INSERT INTO part_conflicts (part_id, conflicting_part_id, notes)
VALUES
    (1, 2, 'A full exhaust system replaces the slip-on exhaust setup.'),
    (2, 1, 'A slip-on exhaust cannot be used with a full exhaust system.'),

    (27, 28, 'Separate LED turn signals conflict with an integrated tail light.'),
    (28, 27, 'Integrated tail light already includes turn signal functionality.');