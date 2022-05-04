SET search_path TO studmess;

-- Группы студентов
CREATE TABLE IF NOT EXISTS groups
(
    id          	SERIAL			PRIMARY KEY,
    name   			VARCHAR(20)		NOT NULL,

    CONSTRAINT unique_group_name UNIQUE (name)
);

-- Пользователи (ученики)
CREATE TABLE IF NOT EXISTS users
(
    id               	SERIAL			PRIMARY KEY,
    login            	TEXT      		NOT NULL,
    password         	TEXT   	   		NOT NULL,
    role             	TEXT	      	NOT NULL,
    first_name   	 	VARCHAR(50) 	NOT NULL,
    middle_name  	 	VARCHAR(50) 	NOT NULL,
    last_name    	 	VARCHAR(50) 	NOT NULL,
    group_id			INT				NOT NULL,
    email			 	VARCHAR(50) 	NOT NULL,
    phone            	VARCHAR(20)		NULL,
    enabled          	BOOLEAN   		NOT NULL DEFAULT FALSE,
    verification_code	TEXT      		NULL,

    CONSTRAINT unique_login 	UNIQUE (login),
    CONSTRAINT unique_email 	UNIQUE (email),
    CONSTRAINT unique_phone 	UNIQUE (phone),
    CONSTRAINT id_fk_users_groups FOREIGN KEY (group_id) REFERENCES groups (id)
);

CREATE TABLE IF NOT EXISTS chats
(
    id					SERIAL			PRIMARY KEY,
    name				VARCHAR(60)		NULL,
    initiator_id		INT 			NOT NULL,

    CONSTRAINT id_fk_chats__initiator_id__users FOREIGN KEY (initiator_id) REFERENCES users (id)
);

-- Сообщения
CREATE TABLE IF NOT EXISTS messages
(
    id         			SERIAL			PRIMARY KEY,
    content    			TEXT			NOT NULL,
    to_id      			INT				NULL,
    from_id    			INT				NOT NULL,
    chat_id				INT				NOT NULL,
    created_at 			TIMESTAMP 		NOT NULL DEFAULT NOW(),
    updated_at 			TIMESTAMP		NULL,

    CONSTRAINT id_fk_messages__to_id__users   FOREIGN KEY (to_id)   REFERENCES users (id),
    CONSTRAINT id_fk_messages__from_id__users FOREIGN KEY (from_id) REFERENCES users (id),
    CONSTRAINT id_fk_messages__chat_id__chats FOREIGN KEY (chat_id) REFERENCES chats (id)
);

CREATE TABLE IF NOT EXISTS users_chats
(
    user_id 			INT				NOT NULL,
    chat_id				INT				NOT NULL,
    PRIMARY KEY	(user_id, chat_id),

    CONSTRAINT id_fk_users_chats__user_id__users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT id_fk_users_chats__chat_id__chats FOREIGN KEY (chat_id) REFERENCES chats (id)
);

-- Предметы
CREATE TABLE IF NOT EXISTS subjects
(
    id				SERIAL		  	PRIMARY KEY,
    name 			VARCHAR(50)		NOT NULL,

    CONSTRAINT unique_subjects_name UNIQUE (name)
);

-- Учителя
CREATE TABLE IF NOT EXISTS teachers
(
    id           	SERIAL 			PRIMARY KEY,
    first_name   	VARCHAR(50) 	NOT NULL,
    middle_name  	VARCHAR(50) 	NOT NULL,
    last_name    	VARCHAR(50) 	NOT NULL
);

-- Календарь. Хранит ID семестра, дни недели и время лекций
CREATE TABLE IF NOT EXISTS lessons
(
    id          	SERIAL			PRIMARY KEY,
    semester	  	INT				NOT NULL,
    week_day     	INT				NOT NULL,
    lesson_time  	TIME 			NOT NULL,

    CONSTRAINT ck_schedule_weekday CHECK (week_day BETWEEN 1 AND 6)
);

-- Расписание. Хранит пересечение всех всех таблиц
CREATE TABLE IF NOT EXISTS schedule
(
    id          	SERIAL			PRIMARY KEY,
    group_id		INT 			NOT NULL,
    subject_id   	INT 			NOT NULL,
    teacher_id		INT 			NOT NULL, -- будет в таблице lesson
    lesson_id		INT				NOT NULL,

    CONSTRAINT id_fk_schedule_groups	FOREIGN KEY (group_id) REFERENCES groups (id),
    CONSTRAINT id_fk_schedule_subjects 	FOREIGN KEY (subject_id) REFERENCES subjects (id),
    CONSTRAINT id_fk_schedule_teachers 	FOREIGN KEY (teacher_id) REFERENCES teachers (id),
    CONSTRAINT id_fk_schedule_lessons 	FOREIGN KEY (lesson_id) REFERENCES lessons (id)
);

-- Задания
CREATE TABLE IF NOT EXISTS tasks
(
    id					SERIAL			PRIMARY KEY,
    schedule_id			INT				NOT NULL,
    name				VARCHAR(60) 	NOT NULL,
    context				TEXT			NULL,
    deadline			TIMESTAMP		NULL,

    CONSTRAINT id_fk_tasks__schedule_id__schedule FOREIGN KEY (schedule_id) REFERENCES schedule (id)
);

CREATE TABLE IF NOT EXISTS users_tasks
(
    id 					SERIAL			PRIMARY KEY,
    user_id				INT				NOT NULL,
    task_id				INT				NOT NULL,
    done				BOOLEAN			NOT NULL DEFAULT FALSE,
    score				INT2			NULL,

    CONSTRAINT id_fk_users_tasks__user_id__users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT id_fk_users_tasks__task_id__tasks FOREIGN KEY (task_id) REFERENCES tasks (id)
);

-- Комментарии
CREATE TABLE IF NOT EXISTS comments
(
    id         			SERIAL			PRIMARY KEY,
    content    			TEXT			NOT NULL,
    task_id				INT				NOT NULL,
    to_id      			INT				NULL,
    from_id    			INT				NOT NULL,
    created_at 			TIMESTAMP 		NOT NULL DEFAULT NOW(),
    updated_at 			TIMESTAMP		NULL,

    CONSTRAINT id_fk_comments__task_id__tasks FOREIGN KEY (task_id) REFERENCES tasks (id),
    CONSTRAINT id_fk_comments__to_id__users   FOREIGN KEY (to_id)   REFERENCES users (id),
    CONSTRAINT id_fk_comments__from_id__users FOREIGN KEY (from_id) REFERENCES users (id)
);
