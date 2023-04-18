INSERT INTO roles(name) VALUES ('USER'), ('ADMIN');

INSERT INTO users(name, email, password, mobile, address, birthday, role_id) VALUES
('test_user_name1', 'test_user_email1@email.com', '#Test_user_password1', '+40700000001', 'test_user_address1', '1990-01-01', 2),
('test_user_name2', 'test_user_email2@email.com', '#Test_user_password2', '+40700000002', 'test_user_address2', '1990-01-02', 2),
('test_user_name3', 'test_user_email3@email.com', '#Test_user_password3', '+40700000003', 'test_user_address3', '1990-01-03', 2),
('test_user_name4', 'test_user_email4@email.com', '#Test_user_password4', '+40700000004', 'test_user_address4', '1990-01-04', 2),
('test_user_name5', 'test_user_email5@email.com', '#Test_user_password5', '+40700000005', 'test_user_address5', '1990-01-05', 1),
('test_user_name6', 'test_user_email6@email.com', '#Test_user_password6', '+40700000006', 'test_user_address6', '1990-01-06', 1),
('test_user_name7', 'test_user_email7@email.com', '#Test_user_password7', '+40700000007', 'test_user_address7', '1990-01-07', 1),
('test_user_name8', 'test_user_email8@email.com', '#Test_user_password8', '+40700000008', 'test_user_address8', '1990-01-08', 1),
('test_user_name9', 'test_user_email9@email.com', '#Test_user_password9', '+40700000009', 'test_user_address9', '1990-01-09', 1),
('test_user_name10', 'test_user_email10@email.com', '#Test_user_password10', '+40700000010', 'test_user_address10', '1990-01-10', 1),
('test_user_name11', 'test_user_email11@email.com', '#Test_user_password11', '+40700000011', 'test_user_address11', '1990-01-11', 1),
('test_user_name12', 'test_user_email12@email.com', '#Test_user_password12', '+40700000012', 'test_user_address12', '1990-01-12', 1),
('test_user_name13', 'test_user_email13@email.com', '#Test_user_password13', '+40700000013', 'test_user_address13', '1990-01-13', 1),
('test_user_name14', 'test_user_email14@email.com', '#Test_user_password14', '+40700000014', 'test_user_address14', '1990-01-14', 1),
('test_user_name15', 'test_user_email15@email.com', '#Test_user_password15', '+40700000015', 'test_user_address15', '1990-01-15', 1),
('test_user_name16', 'test_user_email16@email.com', '#Test_user_password16', '+40700000016', 'test_user_address16', '1990-01-16', 1),
('test_user_name17', 'test_user_email17@email.com', '#Test_user_password17', '+40700000017', 'test_user_address17', '1990-01-17', 1),
('test_user_name18', 'test_user_email18@email.com', '#Test_user_password18', '+40700000018', 'test_user_address18', '1990-01-18', 1),
('test_user_name19', 'test_user_email19@email.com', '#Test_user_password19', '+40700000019', 'test_user_address19', '1990-01-19', 1),
('test_user_name20', 'test_user_email20@email.com', '#Test_user_password20', '+40700000020', 'test_user_address20', '1990-01-20', 1),
('test_user_name21', 'test_user_email21@email.com', '#Test_user_password21', '+40700000021', 'test_user_address21', '1990-01-21', 1),
('test_user_name22', 'test_user_email22@email.com', '#Test_user_password22', '+40700000022', 'test_user_address22', '1990-01-22', 1),
('test_user_name23', 'test_user_email23@email.com', '#Test_user_password23', '+40700000023', 'test_user_address23', '1990-01-23', 1),
('test_user_name24', 'test_user_email24@email.com', '#Test_user_password24', '+40700000024', 'test_user_address24', '1990-01-24', 1);

INSERT INTO feedbacks(feedback_type, description, sent_at, user_id) VALUES
('BUG', 'test_feedback_description1', '1990-01-01 00:00', 1),
('OPTIMIZATION', 'test_feedback_description2', '1990-01-02 00:00', 2),
('IMPROVEMENT', 'test_feedback_description3', '1990-01-03 00:00', 3),
('BUG', 'test_feedback_description4', '1990-01-04 00:00', 4),
('OPTIMIZATION', 'test_feedback_description5', '1990-01-05 00:00', 5),
('IMPROVEMENT', 'test_feedback_description6', '1990-01-06 00:00', 6);

INSERT INTO mentors (name, email, password, mobile, address, birthday, is_available, number_of_employees) VALUES
('test_mentor_name1', 'test_mentor_email1@email.com', '#Test_mentor_password1', '+40700000001', 'test_mentor_address1', '1990-01-01', true, 5),
('test_mentor_name2', 'test_mentor_email2@email.com', '#Test_mentor_password2', '+40700000002', 'test_mentor_address2', '1990-01-02', true, 5),
('test_mentor_name3', 'test_mentor_email3@email.com', '#Test_mentor_password3', '+40700000003', 'test_mentor_address3', '1990-01-03', true, 5),
('test_mentor_name4', 'test_mentor_email4@email.com', '#Test_mentor_password4', '+40700000004', 'test_mentor_address4', '1990-01-04', true, 5);

INSERT INTO studies(university, faculty, major) VALUES
('test_university1', 'test_faculty1', 'test_major1'),
('test_university2', 'test_faculty2', 'test_major2'),
('test_university3', 'test_faculty3', 'test_major3'),
('test_university4', 'test_faculty4', 'test_major4');

INSERT INTO experiences(title, organization, experience_type, started_at, finished_at) VALUES
('test_experience_title1', 'test_experience_organization1', 'APPRENTICESHIP', '1990-01-01', '1991-01-01'),
('test_experience_title2', 'test_experience_organization2', 'INTERNSHIP', '1992-01-01', '1993-01-01'),
('test_experience_title3', 'test_experience_organization3', 'TRAINING', '1994-01-01', '1995-01-01'),
('test_experience_title4', 'test_experience_organization4', 'VOLUNTEERING', '1996-01-01', '1997-01-01');

INSERT INTO employees(name, email, password, mobile, address, birthday, job_type, position, grade, mentor_id, studies_id) VALUES
('test_employee_name1', 'test_employee_email1@email.com', '#Test_employee_password1', '+40700000001', 'test_employee_address1', '1990-01-01', 'FULL_TIME', 'BACKEND', 'JUNIOR', 1, 1),
('test_employee_name2', 'test_employee_email2@email.com', '#Test_employee_password2', '+40700000002', 'test_employee_address2', '1990-01-02', 'FULL_TIME', 'BACKEND', 'JUNIOR', 2, 2),
('test_employee_name3', 'test_employee_email3@email.com', '#Test_employee_password3', '+40700000003', 'test_employee_address3', '1990-01-03', 'FULL_TIME', 'BACKEND', 'SENIOR', 3, 3),
('test_employee_name4', 'test_employee_email4@email.com', '#Test_employee_password4', '+40700000004', 'test_employee_address4', '1990-01-04', 'FULL_TIME', 'FRONTEND', 'JUNIOR', 4, 4),
('test_employee_name5', 'test_employee_email5@email.com', '#Test_employee_password5', '+40700000005', 'test_employee_address5', '1990-01-05', 'FULL_TIME', 'FRONTEND', 'JUNIOR', 1, 1),
('test_employee_name6', 'test_employee_email6@email.com', '#Test_employee_password6', '+40700000006', 'test_employee_address6', '1990-01-06', 'FULL_TIME', 'FRONTEND', 'SENIOR', 2, 2),
('test_employee_name7', 'test_employee_email7@email.com', '#Test_employee_password7', '+40700000007', 'test_employee_address7', '1990-01-07', 'FULL_TIME', 'TESTING', 'JUNIOR', 3, 3),
('test_employee_name8', 'test_employee_email8@email.com', '#Test_employee_password8', '+40700000008', 'test_employee_address8', '1990-01-08', 'FULL_TIME', 'TESTING', 'SENIOR', 4, 4),
('test_employee_name9', 'test_employee_email9@email.com', '#Test_employee_password9', '+40700000009', 'test_employee_address9', '1990-01-09', 'FULL_TIME', 'DEVOPS', 'JUNIOR', 1, 1),
('test_employee_name10', 'test_employee_email10@email.com', '#Test_employee_password10', '+40700000010', 'test_employee_address10', '1990-01-10', 'FULL_TIME', 'DEVOPS', 'SENIOR', 2, 2),
('test_employee_name11', 'test_employee_email11@email.com', '#Test_employee_password11', '+40700000011', 'test_employee_address11', '1990-01-11', 'FULL_TIME', 'DESIGN', 'JUNIOR', 3, 3),
('test_employee_name12', 'test_employee_email12@email.com', '#Test_employee_password12', '+40700000012', 'test_employee_address12', '1990-01-12', 'FULL_TIME', 'DESIGN', 'SENIOR', 4, 4),
('test_employee_name13', 'test_employee_email13@email.com', '#Test_employee_password13', '+40700000013', 'test_employee_address13', '1990-01-13', 'FULL_TIME', 'DATA_ANALYST', 'JUNIOR', 1, 1),
('test_employee_name14', 'test_employee_email14@email.com', '#Test_employee_password14', '+40700000014', 'test_employee_address14', '1990-01-14', 'FULL_TIME', 'DATA_ANALYST', 'SENIOR', 2, 2),
('test_employee_name15', 'test_employee_email15@email.com', '#Test_employee_password15', '+40700000015', 'test_employee_address15', '1990-01-15', 'FULL_TIME', 'ML_ENGINEER', 'JUNIOR', 3, 3),
('test_employee_name16', 'test_employee_email16@email.com', '#Test_employee_password16', '+40700000016', 'test_employee_address16', '1990-01-16', 'FULL_TIME', 'ML_ENGINEER', 'SENIOR', 4, 4),
('test_employee_name17', 'test_employee_email17@email.com', '#Test_employee_password17', '+40700000017', 'test_employee_address17', '1990-01-17', 'FULL_TIME', 'BUSINESS_ANALYST', 'JUNIOR', 1, 1),
('test_employee_name18', 'test_employee_email18@email.com', '#Test_employee_password18', '+40700000018', 'test_employee_address18', '1990-01-18', 'FULL_TIME', 'BUSINESS_ANALYST', 'SENIOR', 2, 2),
('test_employee_name19', 'test_employee_email19@email.com', '#Test_employee_password19', '+40700000019', 'test_employee_address19', '1990-01-19', 'FULL_TIME', 'SCRUM_MASTER', 'SENIOR', 3, 3),
('test_employee_name20', 'test_employee_email20@email.com', '#Test_employee_password20', '+40700000020', 'test_employee_address20', '1990-01-20', 'FULL_TIME', 'SCRUM_MASTER', 'SENIOR', 4, 4);

INSERT INTO employees_experiences(employee_id, experience_id) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 1), (3, 2),
(4, 3), (4, 4),
(5, 1), (5, 2),
(6, 3), (6, 4),
(7, 1), (7, 2),
(8, 3), (8, 4),
(9, 1), (9, 2),
(10, 3), (10, 4),
(11, 1), (11, 2),
(12, 3), (12, 4),
(13, 1), (13, 2),
(14, 3), (14, 4),
(15, 1), (15, 2),
(16, 3), (16, 4),
(17, 1), (17, 2),
(18, 3), (18, 4),
(19, 1), (19, 2),
(20, 3), (20, 4);