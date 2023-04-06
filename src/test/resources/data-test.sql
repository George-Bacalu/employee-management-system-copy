INSERT INTO mentors (name, email, password, mobile, address, birthday, is_available, number_of_employees) VALUES
('test_mentor_name1', 'test_mentor_email1@email.com', '#Test_mentor_password1', '+40700000001', 'test_mentor_address1', '1990-01-01', true, 5),
('test_mentor_name2', 'test_mentor_email2@email.com', '#Test_mentor_password2', '+40700000002', 'test_mentor_address2', '1990-01-02', true, 5);

INSERT INTO studies(university, faculty, major) VALUES
('test_university1', 'test_faculty1', 'test_major1'),
('test_university2', 'test_faculty2', 'test_major2');

INSERT INTO experiences(title, organization, experience_type, started_at, finished_at) VALUES
('test_experience_title1', 'test_experience_organization1', 'APPRENTICESHIP', '1990-01-01', '1991-01-01'),
('test_experience_title2', 'test_experience_organization2', 'INTERNSHIP', '1992-01-01', '1993-01-01'),
('test_experience_title3', 'test_experience_organization3', 'TRAINING', '1994-01-01', '1995-01-01'),
('test_experience_title4', 'test_experience_organization4', 'VOLUNTEERING', '1996-01-01', '1997-01-01');

INSERT INTO roles(name) VALUES ('USER'), ('ADMIN');

INSERT INTO users(name, email, password, mobile, address, birthday, role_id) VALUES
('test_user_name1', 'test_user_email1@email.com', '#Test_user_password1', '+40700000001', 'test_user_address1', '1990-01-01', 1),
('test_user_name2', 'test_user_email2@email.com', '#Test_user_password2', '+40700000002', 'test_user_address2', '1990-01-02', 2);

INSERT INTO feedbacks(feedback_type, description, sent_at, user_id) VALUES
('BUG', 'test_feedback_description1', '1990-01-01 00:00', 1),
('OPTIMIZATION', 'test_feedback_description2', '1990-01-02 00:00', 2);

INSERT INTO employees(name, email, password, mobile, address, birthday, job_type, position, grade, mentor_id, studies_id) VALUES
('test_employee_name1', 'test_employee_email1@email.com', '#Test_employee_password1', '+40700000001', 'test_employee_address1', '1990-01-01', 'FULL_TIME', 'BACKEND', 'JUNIOR', 1, 1),
('test_employee_name2', 'test_employee_email2@email.com', '#Test_employee_password2', '+40700000002', 'test_employee_address2', '1990-01-02', 'FULL_TIME', 'BACKEND', 'JUNIOR', 2, 2);

INSERT INTO employees_experiences(employee_id, experience_id) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4);