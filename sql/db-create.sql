USE extracurricular;

DROP TABLE IF EXISTS student_course;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS topic;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;

CREATE TABLE role (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name ENUM('ADMIN', 'TEACHER', 'STUDENT') NOT NULL
);

CREATE TABLE user (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name_en VARCHAR(255) NOT NULL,
    name_uk VARCHAR(255) NOT NULL,
    banned BIT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE topic (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name_en VARCHAR(255) NOT NULL UNIQUE,
    name_uk VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE course (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name_en VARCHAR(255) NOT NULL UNIQUE,
    name_uk VARCHAR(255) NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    duration_in_days INT NOT NULL,
    topic_id INT NOT NULL,
    teacher_id INT,
    FOREIGN KEY (topic_id) REFERENCES topic(id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES user(id) ON DELETE SET NULL
);

CREATE TABLE student_course (
	student_id INT NOT NULL,
    course_id INT NOT NULL,
    progress INT CHECK (progress >= 0 AND progress <= 100) NOT NULL,
    mark INT CHECK (mark >= 0 AND mark <= 100) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    PRIMARY KEY (student_id, course_id),
    UNIQUE (student_id, course_id)
);

INSERT INTO role (name) VALUES ('ADMIN'), ('TEACHER'), ('STUDENT');

INSERT INTO user (login, password, name_en, name_uk, role_id, banned) VALUES 
	('admin', '670506c9b67375007e1b50d698085e54bcd6e8bc6a7fece2a907ff42b83a92698147460dee66c1856ac9777875c7f0e5b1edac57486e7a841311ea6beeb235a2', 'Admin', 'Адмін', (SELECT id from role WHERE name = 'ADMIN'), 0),
    ('teacher', 'ae9416dded1da87a443eb3b8217bca75a5c1e1182d2201aef77bf185b6d080d41ff9906ddeb47d32ad99f01052cc64af2a62ab8465b445598fd52f1dabfbc013', 'Teacher', 'Викладача', (SELECT id from role WHERE name = 'TEACHER'), 0),
    ('student', '21eaa925971b38750802111167fb529998c8aa77e2087568585394eb68c98efaa9d3be95778af531ae69c3dfaecd5d502d10994bd003022caedf811be671f8e9', 'Student', 'Студент', (SELECT id from role WHERE name = 'STUDENT'), 0);

INSERT INTO topic (name_en, name_uk) VALUES ('History', 'Історія'), ('Programming', 'Програмування'), ('Math', 'Математика');

INSERT INTO course (name_en, name_uk, topic_id, start_date, duration_in_days) VALUES 
	('Roman Empire', 'Римська імперія', (SELECT id FROM topic WHERE name_en = 'History' AND name_uk = 'Історія'), '2021-02-25', 30),
    ('Java', 'Джава', (SELECT id FROM topic WHERE name_en = 'Programming' AND name_uk = 'Програмування'), '2021-02-27', 60);
