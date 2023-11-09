    USE obscuro_db;

    INSERT INTO users (id, name, password, profile_picture, username) 
    VALUES (
        (1, 'Mary Poppins', '$2a$10$/y2qyKVFuV8sqrqka0E67u2UU.hJvg97qMGYzXGXwGoJEScSdfiXS', '/img/image.png', 'witchlady'),
        (2, 'Freddy Krueger', '$2a$10$/y2qyKVFuV8sqrqka0E67u2UU.hJvg97qMGYzXGXwGoJEScSdfiXS', '/img/image.png',  'nightmareguy'),
        (3, 'Michael Myers ', '$2a$10$/y2qyKVFuV8sqrqka0E67u2UU.hJvg97qMGYzXGXwGoJEScSdfiXS', '/img/image.png', 'holloweenboy'),
        (4, 'Jason Voorhees', '$2a$10$/y2qyKVFuV8sqrqka0E67u2UU.hJvg97qMGYzXGXwGoJEScSdfiXS', '/img/image.png', 'fridaythe13th'),
        (5, 'Carrie White', '$2a$10$/y2qyKVFuV8sqrqka0E67u2UU.hJvg97qMGYzXGXwGoJEScSdfiXS', '/img/image.png', 'promsucked')
        );


INSERT INTO sweetdreams (id, level, user_id)
VALUES( 
    (1, '3', 1), 
    (2, '2', 2), 
    (3, '1', 3), 
    (4, '2', 4), 
    (5, '5', 5)
);

INSERT INTO nightmare (id, level, user_id)
VALUES (
    (1, '1', 1), 
    (2, '5', 2), 
    (3, '2', 3), 
    (4, '3', 4), 
    (5, '4', 5)
);

INSERT INTO sleepparalysis (id, level, user_id)
VALUES (
    (1, '5', 1), 
    (2, '2', 2), 
    (3, '1', 3), 
    (4, '1', 4), 
    (5, '3', 5);
);
