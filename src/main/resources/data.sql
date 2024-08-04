-- Insert new contacts
INSERT INTO contacts (phone, email)
VALUES
    ('123-456-7890', 'user1@example.com'),
    ('234-567-8901', 'user2@example.com'),
    ('345-678-9012', 'user3@example.com'),
    ('456-789-0123', 'user4@example.com'),
    ('567-890-1234', 'user5@example.com'),
    ('678-901-2345', 'user6@example.com'),
    ('789-012-3456', 'user7@example.com'),
    ('890-123-4567', 'user8@example.com'),
    ('901-234-5678', 'user9@example.com'),
    ('012-345-6789', 'user10@example.com');

-- Insert new social media records
INSERT INTO social_media (github, twitter, instagram, facebook)
VALUES
    ('github.com/user1', 'twitter.com/user1', 'instagram.com/user1', 'facebook.com/user1'),
    ('github.com/user2', 'twitter.com/user2', 'instagram.com/user2', 'facebook.com/user2'),
    ('github.com/user3', 'twitter.com/user3', 'instagram.com/user3', 'facebook.com/user3'),
    ('github.com/user4', 'twitter.com/user4', 'instagram.com/user4', 'facebook.com/user4'),
    ('github.com/user5', 'twitter.com/user5', 'instagram.com/user5', 'facebook.com/user5'),
    ('github.com/user6', 'twitter.com/user6', 'instagram.com/user6', 'facebook.com/user6'),
    ('github.com/user7', 'twitter.com/user7', 'instagram.com/user7', 'facebook.com/user7'),
    ('github.com/user8', 'twitter.com/user8', 'instagram.com/user8', 'facebook.com/user8'),
    ('github.com/user9', 'twitter.com/user9', 'instagram.com/user9', 'facebook.com/user9'),
    ('github.com/user10', 'twitter.com/user10', 'instagram.com/user10', 'facebook.com/user10');

-- Insert new users
INSERT INTO users (username, password, first_name, last_name, contact_id, age, picture_profile_url, is_banned, city, registered_on, is_active, social_medias_id)
VALUES
    ('User1', 'password1', 'John', 'Doe', 2, 30, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700524/premium_photo-1671656349322-41de944d259b_bu4tyb.avif', false, 'New York', NOW(), true, 2),
    ('User2', 'password2', 'Jane', 'Doe', 3, 28, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735493/photo-1494790108377-be9c29b29330_f9nmdx.avif', false, 'Los Angeles', NOW(), true, 3),
    ('User3', 'password3', 'Alice', 'Smith', 4, 35, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735494/photo-1532074205216-d0e1f4b87368_aslpmp.avif', false, 'Chicago', NOW(), true, 4),
    ('User4', 'password4', 'Bob', 'Johnson', 5, 40, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735493/photo-1474176857210-7287d38d27c6_xsgihe.avif', false, 'Houston', NOW(), true, 5),
    ('User5', 'password5', 'Charlie', 'Brown', 6, 32, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735493/photo-1492562080023-ab3db95bfbce_hekrvr.avif', false, 'San Francisco', NOW(), true, 6),
    ('User6', 'password6', 'Daisy', 'Williams', 7, 27, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735495/photo-1633332755192-727a05c4013d_luwaq3.avif', false, 'Miami', NOW(), true, 7),
    ('User7', 'password7', 'Ella', 'Jones', 8, 29, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735666/photo-1502823403499-6ccfcf4fb453_hwqrya.avif', false, 'Seattle', NOW(), true, 8),
    ('User8', 'password8', 'Frank', 'Garcia', 9, 33, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735495/photo-1573740144655-bbb6e88fb18a_tyzcwj.avif', false, 'Dallas', NOW(), true, 9),
    ('User9', 'password9', 'Grace', 'Martinez', 10, 25, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735497/photo-1639149888905-fb39731f2e6c_ur7zff.avif', false, 'Philadelphia', NOW(), true, 10),
    ('User10', 'password10', 'Henry', 'Wilson', 11, 38, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722735497/photo-1535713875002-d1d0cf377fde_tibvsn.avif', false, 'San Diego', NOW(), true, 11);

INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('Kawasaki', 'Z750', 140, 8000, '2021-06-01 00:00:00', 'GREEN', 'Plovdiv', NOW(), NULL, 'MANUAL', 'EURO_4', 636, 'PETROL', 15000.00, 'SPORT', false, false, true, 3);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('Audi', 'RSQ3', 200, 100, '2022-05-01 00:00:00', 'BLACK', 'Sofia', NOW() + INTERVAL 1 SECOND, NULL, 'AUTOMATIC', 'EURO_6', 2500, 'PETROL', 30000.00, 'JEEP', true, true, true, 2);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('Audi', 'TT', 2280, 1000, '2021-06-01 00:00:00', 'BLACK', 'Plovdiv', NOW() + INTERVAL 2 SECOND, NULL, 'MANUAL', 'EURO_6', 2000, 'PETROL', 22000.00, 'COUPE', false, true, true, 2);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('Yamaha', 'R1', 120, 5000, '2023-03-15 00:00:00', 'BLACK', 'Varna', NOW() + INTERVAL 3 SECOND, NULL, 'MANUAL', 'EURO_5', 600, 'PETROL', 12000.00, 'SPORT', true, false, true, 2);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('Yamaha', 'R6', 200, 1000, '2022-02-15 00:00:00', 'WHITE', 'Burgas', NOW() + INTERVAL 4 SECOND, NULL, 'AUTOMATIC', 'EURO_5', 1000, 'PETROL', 20000.00, 'SPORT', true, false, true, 2);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('Honda', 'CBR 250R', 214, 2000, '2022-05-01 00:00:00', 'BLACK', 'Shumen', NOW() + INTERVAL 5 SECOND, NULL, 'AUTOMATIC', 'EURO_5', 1100, 'PETROL', 25000.00, 'SPORT', true, false, true, 3);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('Mercedes', 'C63', 150, 25000, '2020-01-01 00:00:00', 'BLUE', 'Plovdiv', NOW() + INTERVAL 6 SECOND, NULL, 'AUTOMATIC', 'EURO_5', 1800, 'PETROL', 18000.00, 'COUPE', true, true, false, 2);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('Yamaxa', 'T-max', 205, 3000, '2023-01-01 00:00:00', 'BLUE', 'Kazanlak', NOW() + INTERVAL 7 SECOND, NULL, 'MANUAL', 'EURO_5', 999, 'PETROL', 24000.00, 'SCOOTER', true, false, true, 4);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('Volkswagen', 'Golf 7', 190, 8000, '2022-07-01 00:00:00', 'RED', 'Varna', NOW() + INTERVAL 8 SECOND, NULL, 'AUTOMATIC', 'EURO_6', 2200, 'PETROL', 25000.00, 'HATCHBACK', true, true, true, 3);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('Ducati','Panigale V4', 217, 4000, '2021-09-01 00:00:00', 'RED', 'Shumen', NOW() + INTERVAL 9 SECOND, NULL, 'AUTOMATIC', 'EURO_5', 1000, 'PETROL', 1000.00, 'SPORT', false, false, true, 4);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('Yamaha ', 'YZF R1', 202, 1500, '2022-03-01 00:00:00', 'RED', 'Shumen', NOW() + INTERVAL 10 SECOND, NULL, 'MANUAL', 'EURO_1', 999, 'PETROL', 22000.00, 'SPORT', true, false, true, 5);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('Lamborgini', 'Urus', 200, 20000, '2020-09-01 00:00:00', 'YELLOW', 'Varna', NOW(), NULL, 'MANUAL', 'EURO_1', 2000, 'PETROL', 22000.00, 'JEEP', true, true, false, 5);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('VW', 'Passat B8', 160, 12000, '2020-08-01 00:00:00', 'BLACK', 'Stara Zagora', NOW() + INTERVAL 12 SECOND, NULL, 'AUTOMATIC', 'EURO_5', 1800, 'PETROL', 19000.00, 'STATION_WAGON', true, true, false, 3);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('Volkswagen', 'Jetta', 250, 10000, '2022-01-01 00:00:00', 'GRAY', 'Plovdiv', NOW() + INTERVAL 13 SECOND, NULL, 'AUTOMATIC', 'EURO_6', 3000, 'PETROL', 35000.00, 'SEDAN', true, true, true, 4);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('Toyota', 'Yaris', 220, 8000, '2021-03-01 00:00:00', 'WHITE', 'Varna', NOW() + INTERVAL 14 SECOND, NULL, 'AUTOMATIC', 'EURO_6', 2500, 'PETROL', 32000.00, 'SEDAN', true, true, true, 4);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('KTM', '790 Duke', 123, 4000, '2023-03-01 00:00:00', 'BLUE', 'Burgas', NOW() + INTERVAL 15 SECOND, NULL, 'MANUAL', 'EURO_5', 1200, 'PETROL', 18000.00, 'SPORT', true, false, true, 6);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('Indian', 'Scout', 121, 5000, '2022-11-01 00:00:00', 'GRAY', 'Sofia', NOW() + INTERVAL 16 SECOND, NULL, 'AUTOMATIC', 'EURO_5', 1250, 'PETROL', 17000.00, 'TOURER', true, false, true, 6);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('Subaru', 'Impreza', 240, 6000, '2022-06-01 00:00:00', 'RED', 'Sofia', NOW() + INTERVAL 17 SECOND, NULL, 'AUTOMATIC', 'EURO_6', 2800, 'PETROL', 38000.00, 'SEDAN', true, true, true, 4);
INSERT INTO cars (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has4x4, has_air_Conditioner, hasGPS, owner_id)
VALUES
    ('BMW', 'X6', 170, 1200, '2021-05-01 00:00:00', 'BLUE', 'Ruse', NOW() + INTERVAL 19 SECOND, NULL, 'MANUAL', 'EURO_6', 2000, 'PETROL', 50000.00, 'JEEP', true, true, true, 3);
INSERT INTO motorcycles (brand, model, power, mileage, production_date, color, location, registered_on, modified, transmission, euro_standard, cubic_capacity, engine_type, price, category, has_luggage_case, has_sidecar, has_stability_control, owner_id)
VALUES
    ('BMW', 'S1000RR', 123, 6000, '2020-12-01 00:00:00', 'WHITE', 'Kazanlak', NOW() + INTERVAL 19 SECOND, NULL, 'MANUAL', 'EURO_4', 675, 'PETROL', 50000.00, 'TOURER', true, false, true, 5);

-- Insert into my_cars
INSERT INTO my_cars (user_id, car_id)
VALUES
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 4),
    (6, 5),
    (7, 6),
    (8, 7),
    (9, 8),
    (10, 9),
    (10, 10);

-- Insert into my_motorcycles
INSERT INTO my_motorcycles (user_id, motorcycle_id)
VALUES
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 4),
    (6, 5),
    (7, 6),
    (8, 7),
    (9, 8),
    (10, 9),
    (10, 10);

-- Insert comments
INSERT INTO comments (user_id, commented_at, comment)
VALUES
    (2, NOW(), 'Great performance and design.'),
    (3, NOW(), 'Reliable and comfortable ride.'),
    (4, NOW(), 'Fuel efficiency could be better.'),
    (5, NOW(), 'Excellent handling and speed.'),
    (6, NOW(), 'Good value for the price.'),
    (7, NOW(), 'Very stylish and modern.'),
    (8, NOW(), 'Amazing acceleration and braking.'),
    (9, NOW(), 'Smooth and quiet ride.'),
    (10, NOW(), 'Not very spacious, but fun to drive.'),
    (2, NOW(), 'Perfect for city driving.'),
    (2, NOW(), 'Could use more advanced features.'),
    (3, NOW(), 'Comfortable seats and interior.'),
    (4, NOW(), 'Good for long-distance trips.'),
    (5, NOW(), 'Lacks in cargo space.'),
    (6, NOW(), 'Great for weekend getaways.'),
    (7, NOW(), 'Excellent fuel economy.'),
    (8, NOW(), 'Sleek and elegant design.'),
    (9, NOW(), 'Top-notch safety features.'),
    (10, NOW(), 'Decent sound system.'),
    (3, NOW(), 'Affordable maintenance costs.'),
    (2, NOW(), 'Not suitable for off-road driving.');

-- -- Insert into cars_comments
-- INSERT INTO cars_comments (car_id, comments_id)
-- VALUES
--     (1, 1),
--     (2, 2),
--     (3, 3),
--     (4, 4),
--     (5, 5),
--     (6, 6),
--     (7, 7),
--     (8, 8),
--     (9, 9),
--     (10, 10);

-- Insert into motorcycles_comments
INSERT INTO motorcycles_comments (motorcycle_id, comments_id)
VALUES
    (1, 11),
    (2, 12),
    (3, 13),
    (4, 14),
    (5, 15),
    (6, 16),
    (7, 17),
    (8, 18),
    (9, 19),
    (10, 20);

-- Insert car photos
INSERT INTO car_photos_urls (car_id, photos_urls)
VALUES
    (1, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700266/3_cl8jsx.avif'),
    (1, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700261/2_zumwhx.avif'),
    (1, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722727960/rs_q3_054_pee169.webp'),
    (1, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700255/1_q9mubx.avif'),
    (2, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700712/photo-1577696467903-bee9f5ee9fe9_a0epuq.avif'),
    (2, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700673/photo-1569606986963-ae4085fde160_l1kxab.avif'),
    (2, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700703/photo-1569606996717-fc7e520e34b8_tykjno.avif'),
    (2, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700707/photo-1573502574024-1c8413ee012f_xdluqe.avif'),
    (3, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701009/photo-1638731007181-fb9b4620510e_jgerlz.avif'),
    (3, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701012/photo-1638731006999-332d2ca5cdff_ejxmbw.avif'),
    (3, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701017/photo-1638731006100-6b8229e93488_gsdy1l.avif'),
    (3, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701021/photo-1638303430125-5de682b5aff6_kndhoc.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701228/photo-1572634303017-48a1618ab4fd_tlikb8.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701231/photo-1630019209880-6b53557d0a8b_qbpoal.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701235/photo-1630019210269-d0ebeee405f0_tokoz1.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701237/photo-1630019499081-50ed7838a1cd_nqedfp.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701241/photo-1630019636119-6f3b65ce35dd_tekolt.avif'),
    (5, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717633/photo-1549288830-f95a7354fea8_klz0yw.avif'),
    (5, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717633/photo-1549288830-f4746d294441_wjach2.avif'),
    (5, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717635/photo-1549458395-e14f2e6d39c7_npg9ak.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722714916/photo-1611845128924-afdf01f4bb49_wiunza.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722714919/photo-1611845128941-b7797d028d1e_frb7ik.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722714928/photo-1696705389662-cf9880eef7f6_cb4vmz.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722714916/photo-1611845128924-afdf01f4bb49_wiunza.avif'),
    (7, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717132/photo-1635706966476-7722dc4295d3_cowpap.avif'),
    (7, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717134/photo-1634330902803-94db39ce8c8d_gbiot0.avif'),
    (7, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717142/photo-1608583213272-4f3b6e17c5b3_robj1n.avif'),
    (7, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717140/photo-1634330902518-f201fc36e0f4_c3pa48.avif'),
    (8, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717248/photo-1647269896060-953d56d0badb_xpu9fb.avif'),
    (8, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717253/photo-1647269890231-169d454f8df4_i0otu1.avif'),
    (8, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717256/photo-1647269867560-4f8046eb50d0_ovsvpx.avif'),
    (8, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717259/photo-1647269855378-6ecf52eb9a8c_sh0r0s.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717480/photo-1611609054272-1c25b307a218_er7c0v.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717486/photo-1611609054281-0802f1f58b95_anha0j.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717491/photo-1611609054395-246e4000ad36_r7nyop.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717494/photo-1611609054471-93477faca9e1_qxjiuu.avif'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701403/mate-vanyek-amyO34qYZj4-unsplash_levw7u.jpg'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701371/photo-1676409429433-503d95c21ac4_dcsjgx.avif'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701375/photo-1676409429427-6e23e64a2752_w3qned.avif'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701378/photo-1676409429142-179800faa044_al55sv.avif'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701381/photo-1652967786818-b25edb1d5298_c9oapu.avif'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722701385/photo-1652967786714-040438afe93f_rv3bqs.avif');

-- Insert motorcycle photos
INSERT INTO motorcycle_photos_urls (motorcycle_id, photos_urls)
VALUES
    (1, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700884/photo-1691054748918-b29056042279_facrv0.avif'),
    (1, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722700888/photo-1691065016001-75d836802f09_z1f68k.avif'),
    (2, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715240/photo-1571646036117-8015cc02547c_f3js96.avif'),
    (2, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715243/photo-1571646059462-99317ec8d1bf_q3cq10.avif'),
    (2, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715245/photo-1571646078462-3a00bdd5eb73_vv9ind.avif'),
    (3, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715362/photo-1653403743632-d43c36d2a1f9_hloeeq.avif'),
    (3, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715372/photo-1626606934861-9f6522c3d3a9_eq4ofd.avif'),
    (3, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715382/photo-1626607087458-76b214ecc417_voj7x1.avif'),
    (3, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715377/photo-1653403743601-3a65530d7482_d8y9k0.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715492/photo-1647242250685-1a0b058a1e3f_x3b2kz.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715496/photo-1647242274782-fcade4bfcd74_ouijby.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715502/photo-1647242408394-4167fcd03900_n67hto.avif'),
    (4, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715503/photo-1693522108924-58b38d8f0a00_rzz6od.avif'),
    (5, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715665/photo-1625639360043-eeb7effa77dd_rpnkk1.avif'),
    (5, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715668/photo-1625639529689-a379d2e97f3e_cuzybp.avif'),
    (5, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715672/photo-1625639557555-0c7c18ffb997_kcrugm.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717777/photo-1673357688556-d392bb5bf80e_lwk8i7.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717776/photo-1673357688094-b8224b1b8200_qqqcc8.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717775/photo-1643618070684-b2c782b5f7da_jvcckf.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717774/photo-1632157257704-8b1edff239ea_llkgdd.avif'),
    (6, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722717773/photo-1632157256884-9ab327461a01_fuyelo.avif'),
    (7, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718452/photo-1656766500535-3b2ff1f15082_kshfqg.avif'),
    (7, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718452/photo-1656767112164-f9f59766357d_hvr74a.avif'),
    (7, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718454/photo-1656767330683-5798c42acc18_utfbl5.avif'),
    (7, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718455/photo-1665760361803-8fd164dae794_cp2ost.avif'),
    (8, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718175/photo-1706825633005-91945b801661_il0248.avif'),
    (8, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718174/photo-1706825632933-69fdd2f9eb40_gqeaph.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718321/photo-1653342117420-e0ad84deff9c_c0oijp.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718323/photo-1653554919017-fb4a40f7364b_kwtbwj.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722718327/photo-1653554933059-38638b993b40_xvlbxg.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722728266/EEQPGHHOCZBT3KNAJBPXRQFY4M_q7wxap.avif'),
    (9, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722728131/scout-bobber-lg_pqp9yi.jpg'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715768/photo-1635073908681-b4dfbd6015e8_bamh34.avif'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715772/photo-1635073943212-f34dfbfcc3b0_f4nc8c.avif'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715775/photo-1635073948138-df07998b9135_qrtbk0.avif'),
    (10, 'https://res.cloudinary.com/db1rc9fon/image/upload/v1722715779/photo-1635073953217-aba3cbba7dce_tsefgm.avif');