INSERT INTO concerts (band, concert_date, description, image_url) VALUES ('Lake Malawi',
                                                                          'July 15th',
                                                                          'Lake Malawi is an indie-pop band formed by Albert Cerny in September 2013, based in UK and the Czech Republic.',
                                                                          'Lake_Malawi_music_band.jpg');
INSERT INTO concerts (band, concert_date, description, image_url) VALUES ('The Players',
                                                                          'May 22nd',
                                                                          'Re-discover The Players on their second tour at the City Concert Hall. Includes extraordinary new compositions and jazz classics.',
                                                                          'jazz-band.jpg');

INSERT INTO users (username, email, password, role) VALUES ('admin', 'admin_ticket@mailinator.com', '$2a$10$zpnfXD7Av9YUhG3obNrtI.QHTDXEhqes.PHlavIsoiUjnswGgrxwS', 'ADMIN');


INSERT INTO users (username, email, password, role) VALUES ('mark', 'mark@mailinator.com', '$2a$10$QxtktyF086GMuYLYIraGgOGOI.EZtPLI.eG75D7EiZjcn2u.GUWnu', 'ADMIN');

INSERT INTO users (username, email, password, role) VALUES ('user1', 'user1@mailinator.com', '$2a$10$fG1O1/pM5lBaCHvgMd9g.uF2iOET4s8WievtIZQyRhC1V0YEEV6H6', 'USER');
INSERT INTO users (username, email, password, role) VALUES ('user2', 'user2@mailinator.com', '$2a$10$O4WGQnp2yJBbKBPcvCbB.ulyYQ55nmbUaBN0N75ipEKUt4u2Tek4O', 'USER');
