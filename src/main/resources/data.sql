INSERT INTO groups (id, name)
VALUES (1, '3530904/80101'),
       (2, '3530904/80102'),
       (3, '3530904/80103');

INSERT INTO users (id, login, password, role,
                   first_name, middle_name, last_name, group_id, email, phone, enabled)
VALUES (1, 'user1', '$2y$10$2IB5lJezDEoyKRmT5YO9qOdD6pRwSMsnj6PyEszl9kAj7xFYlLo3.', 'USER',
        'Иван', 'Иванович', 'Иванов', 1, 'user1@mail.ru', '89999999999', true),
       (2, 'dima881', '$2y$10$oGxqreQzyptA8i67VtMWE.Vjpdt.NqIfbWqkc6jj2bEgVxwb4npbi', 'USER',
        'Дмитрий', 'Андреевич', 'Богданов', 3, 'dima881@mail.ru', '89999999888', true),
       (3, 'grisha007', '$2y$10$AQfzYyL6.o3XDDEps4ecq.8fpL70CgwtP0wx1/l2yb4vBQRb.xvBa', 'USER',
        'Григорий', 'Николаевич', 'Толстиков', 3, 'grisha007@mail.ru', '89999999777', true),
       (4, 'alina98', '$2y$10$DvalkbAqtdYYjsdjBkwjp.vS6A7GuQWTgG5XR2cg4O/hX5/xqcR4C', 'USER',
        'Алина', 'Юрьевна', 'Шестакова', 3, 'alina98@mail.ru', '89999999887', true),
       (5, 'danila777', '$2y$10$SbhO5flZ8hshcSiTIkfRjei9HoL0yo6ocQxJGbk5IbP0/JEdWdHXe', 'USER',
        'Данила', 'Даниилович', 'Даниилов', 1, 'danila777@mail.ru', '89999999555', true),
       (6, 'lexa52', '$2y$10$R0EuIGneWudlVOvbX7gTlegJbiXG9Jjg1YtozgkFWPQqH.Ry4b3u.', 'USER',
        'Алексей', 'Алексеевич', 'Алексеев', 2, 'lexa52@mail.ru', '89999999444', true);

INSERT INTO chats (id, name, initiator_id)
VALUES (1, '3530904/80101', 1),
       (2, '3530904/80102', 6),
       (3, '3530904/80103', 4),
       (4, 'Проект по экономике', 4);

INSERT INTO messages (id, content, to_id, from_id, chat_id)
VALUES (1, 'Пишу сообщение', null, 1, 1),
       (2, 'Доброго времени суток', null, 2, 3),
       (3, 'И тебе привет', 2, 3, 3),
       (4, 'Какие завтра пары?', null, 4, 3),
       (5, 'Что задали на завтра?', null, 2, 3),
       (6, 'Я завтра не приду', null, 4, 4),
       (7, 'Передайте, что я опоздаю', null, 6, 4);

INSERT INTO users_chats (user_id, chat_id)
VALUES (1, 1),
       (5, 1),
       (6, 2),
       (2, 3),
       (3, 3),
       (4, 3),
       (4, 4),
       (6, 4);

INSERT INTO subjects (id, name)
VALUES (),
       (),
       ();

INSERT INTO teachers (id, first_name, middle_name, last_name, email, phone)
VALUES (),
       (),
       ();

INSERT INTO lessons (id, semester, week_day, lesson_time)
VALUES (),
       (),
       (),
       (),
       (),
       (),
       ();

INSERT INTO schedule (id, group_id, subject_id, teacher_id, lesson_id)
VALUES (),
       (),
       (),
       (),
       (),
       ();

INSERT INTO tasks (id, schedule_id, name, context, deadline)
VALUES (),
       (),
       (),
       (),
       (),
       ();

INSERT INTO users_tasks (id, user_id, task_id, done, score)
VALUES (),
       (),
       (),
       (),
       ();

INSERT INTO comments (id, content, task_id, to_id , from_id, created_at, updated_at)
VALUES (),
       (),
       (),
       (),
       ();