INSERT IGNORE INTO car (id, image_url, model, price, car_type, status)
VALUES
(1, '/images/cars/bmw7series.jpg', 'BMW 7 Series', 5500, 'Luxury sedan', 'AVAILABLE'),
(2, '/images/cars/mercedes_sclass.jpeg', 'Mercedes-Benz S-Class', 7000, 'Luxury sedan', 'AVAILABLE'),
(3, '/images/cars/porsche_cayenne.jpeg', 'Porsche Cayenne', 6500, 'Luxury SUV', 'AVAILABLE'),
(4, '/images/cars/lamborghini_urus.jpeg', 'Lamborghini Urus', 12000, 'Luxury SUV', 'AVAILABLE'),
(5, '/images/cars/range_rover_autobiography.jpeg', 'Range Rover Autobiography', 8500, 'Luxury SUV', 'AVAILABLE'),
(6, '/images/cars/audi.jpeg', 'Audi', 3000, 'Luxury sedan', 'AVAILABLE'),
(7, '/images/cars/audi_2.jpeg', 'Audi', 1200, 'Luxury sedan', 'AVAILABLE'),
(8, '/images/cars/Audi-1.png', 'Audi', 2000, 'Luxury', 'AVAILABLE'),
(9, '/images/cars/ferrari_purosangue.jpg', 'Ferrari Purosangue', 5000, 'SUV', 'AVAILABLE'),
(10, '/images/cars/bentley_bentayga.jpeg', 'Bentley Bentayga', 11000, 'Luxury SUV', 'AVAILABLE'),
(11, '/images/cars/maserati_levante.jpeg', 'Maserati Levante', 9500, 'Luxury SUV', 'AVAILABLE'),
(12, '/images/cars/rolls_royce_cullinan.jpeg', 'Rolls-Royce Cullinan', 20000, 'Luxury SUV', 'AVAILABLE'),
(13, '/images/cars/aston_martin_dbx.jpeg', 'Aston Martin DBX', 15000, 'Luxury SUV', 'AVAILABLE'),
(14, '/images/cars/jeep_grand_cherokee.jpeg', 'Jeep Grand Cherokee', 4500, 'SUV', 'AVAILABLE'),
(15, '/images/cars/audi_q7.jpeg', 'Audi Q7', 7500, 'Luxury SUV', 'AVAILABLE'),
(16, '/images/cars/volvo_xc90.jpeg', 'Volvo XC90', 4800, 'Luxury SUV', 'AVAILABLE'),
(17, '/images/cars/toyota_landcruiser.jpeg', 'Toyota Land Cruiser', 6500, 'SUV', 'AVAILABLE'),
(18, '/images/cars/chevrolet_suburban.jpg', 'Chevrolet Suburban', 4200, 'SUV', 'AVAILABLE'),
(19, '/images/cars/cadillac_escalade.jpg', 'Cadillac Escalade', 9500, 'Luxury SUV', 'AVAILABLE'),
(20, '/images/cars/nissan_patrol.jpeg', 'Nissan Patrol', 6000, 'SUV', 'AVAILABLE'),
(21, '/images/cars/ford_expedition.jpg', 'Ford Expedition', 4800, 'SUV', 'AVAILABLE'),
(22, '/images/cars/landrover_defender.jpg', 'Land Rover Defender', 8500, 'Luxury SUV', 'AVAILABLE'),
(23, '/images/cars/hummer_h2.jpg', 'Hummer H2', 10000, 'SUV', 'AVAILABLE'),
(24, '/images/cars/mercedes_gls.jpg', 'Mercedes-Benz GLS', 12000, 'Luxury SUV', 'AVAILABLE');



INSERT IGNORE INTO user (id, username, password, email, phno, role, enabled, photo, driving_License_No)
VALUES 
(1, 'Isha Gupta', '$2a$10$LsyKt4kl93ggFKdAEWfuW.nPjzIn8.Rs9euXLAu36kmG03kA3I8mW', 'isha.gupta@example.com', '9876543210', 'ADMIN', true, '/images/users/1738494530646_p1.jpg', 'DL100200300'),
(2, 'Meera Sharma', '$2a$10$In3c5QNkI4qmjShBx95hc.E34srJJNfXP3oUkZgmYnsbRowphq0G6', 'meera.sharma@example.com', '8765432109', 'USER', true, '/images/users/1738494530646_p2.jpg', 'DL200300400'),
(3, 'Ananya Reddy', '$2a$10$PhoMrRw9Wed08W5DlhDUx.SzWuYpRBLltDeg6I0mB1EOcAF0ZQzBG', 'ananya.reddy@example.com', '7654321098', 'USER', true, '/images/users/1738494530646_p3.jpg', 'DL300400500'),
(4, 'Anupama Parameshwaran', '$2a$10$g72O0G9bMCiU8U6UAAiQ0.F0zkN2M7uHH.3eNRmTo5wbvJwmN4eGS', 'anupama@example.com', '7543210987', 'USER', true, '/images/users/1738494530646_p4.jpg', 'DL400500600'),
(5, 'Samantha', '$2a$10$yQuLuebORV79SzBhLrmEGuV0qPF.v82LIw3Wh4I5MmYn.uZo2seei', 'samantha@example.com', '7432109876', 'USER', true, '/images/users/1738494530646_p5.jpg', 'DL500600700'),
(6, 'Rohit Verma', '$2a$10$R.BE.KQkkIx6tau8l3cg8eYhoBb92XZoEv6cDUWAX3cp9yJ/ZvK7e', 'rohit.verma@example.com', '7321098765', 'USER', true, '/images/users/1738494530646_p6.jpg', 'DL600700800'),
(7, 'Ram Pothineni', '$2a$10$eckn3TIjI1kWt8BA/X0Emuf4648NLx0SYtvSCFynsHesA5jjBINom', 'ram@example.com', '7210987654', 'USER', true, '/images/users/1738494530646_p7.jpg', 'DL700800900'),
(8, 'Vijay Thalapathi', '$2a$10$00M3SLVx6iOpl1PF5H7Pc.zPACro14Al5m5pL46mw/u5AodDmLT9.', 'vijay@example.com', '7109876543', 'USER', true, '/images/users/1738494530646_p8.jpg', 'DL800900100'),
(9, 'Manoj Kumar', '$2a$10$w7AmnocpjIgXvfvaW8RQhOiLjlgCBi2wFb.3G5v48oR83pfy4am/u', 'manoj.kumar@example.com', '7098765432', 'USER', true, '/images/users/1738494530646_p9.jpg', 'DL900100200'),
(10, 'Priyanka Nair', '$2a$10$WjhMKYvHFP6v3Xy.AF2DmeTIBtH2G5v.f9v.RqUG/6cFL79/XQFqa', 'priyanka.nair@example.com', '6987654321', 'USER', true, '/images/users/1738494530646_p10.jpg', 'DL100200300'),
(11, 'Akash Patel', '$2a$10$OUqKu6JICH.fYesCOPStdOTrvljziG2wRGdSQsaR/jRt0hZ6CzJMO', 'akash.patel@example.com', '6876543210', 'USER', true, '/images/users/1738494530646_p11.jpg', 'DL200300400'),
(12, 'Sandeep Menon', '$2a$10$nUS3dOzKZkOEQs0GsLgVbuyDgnWqZaGZW5gaZvygKB9Il8yDT3Kxe', 'sandeep.menon@example.com', '6765432109', 'USER', true, '/images/users/1738494530646_p12.jpg', 'DL300400500'),
(13, 'Rhea Kapoor', '$2a$10$Rxv3o7YvzO9Y9uCtLb4/oOaXuYjtCFOqxGu5hrfX.ceVJVlk3Qa8.', 'rhea.kapoor@example.com', '6654321098', 'USER', true, '/images/users/1738494530646_p13.jpg', 'DL400500600'),
(14, 'Neha Mishra', '$2a$10$qiczk4OEqD1Fl9SqaGX2S.L9AD.D.TIR226gOMRkJ7oSXf3b09BxS', 'neha.mishra@example.com', '6543210987', 'USER', true, '/images/users/1738494530646_p14.jpg', 'DL500600700'),
(15, 'Shruti Pandey', '$2a$10$DAuuTUnwVvP3MWyMSgMxxesezG1RbYIgqG8MqDHCrfXQP48RCJ.Rq', 'shruti.pandey@example.com', '6432109876', 'USER', true, '/images/users/1738494530646_p15.jpg', 'DL600700800');

