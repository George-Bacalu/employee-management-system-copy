insert into roles(id, name) values (1, 'USER'), (2, 'ADMIN');

insert into users(id, name, email, password, mobile, address, birthday, role_id) values
(1, 'test_user_name1', 'test_user_email1@email.com', '#Test_user_password1', '+40700000001', 'test_user_address1', '1990-01-01', 1),
(2, 'test_user_name2', 'test_user_email2@email.com', '#Test_user_password2', '+40700000002', 'test_user_address2', '1990-01-02', 2);

insert into feedbacks(id, feedback_type, description, sent_at, user_id) values
(1, 'BUG', 'test_feedback_description1', '1990-01-01 00:00', 1),
(2, 'IMPROVEMENT', 'test_feedback_description2', '1990-01-02 00:00', 2);

insert into mentors (id, name, email, password, mobile, address, birthday, is_available, number_of_employees) values
(1, 'test_mentor_name1', 'test_mentor_email1@email.com', '#Test_mentor_password1', '+40700000001', 'test_mentor_address1', '1990-01-01', true, 5),
(2, 'test_mentor_name2', 'test_mentor_email2@email.com', '#Test_mentor_password2', '+40700000002', 'test_mentor_address2', '1990-01-02', true, 5);

insert into studies(id, university, faculty, major) values
(1, 'test_university1', 'test_faculty1', 'test_major1'),
(2, 'test_university2', 'test_faculty2', 'test_major2');

insert into experiences(id, title, organization, experience_type, started_at, finished_at) values
(1, 'test_experience_title1', 'test_experience_organization1', 'APPRENTICESHIP', '1990-01-01', '1991-01-02'),
(2, 'test_experience_title2', 'test_experience_organization2', 'INTERNSHIP', '1992-01-03', '1993-01-04'),
(3, 'test_experience_title3', 'test_experience_organization3', 'TRAINING', '1994-01-05', '1995-01-06'),
(4, 'test_experience_title4', 'test_experience_organization4', 'VOLUNTEERING', '1996-01-07', '1997-01-08');

insert into employees(id, name, email, password, mobile, address, birthday, job_type, position, grade, mentor_id, studies_id) values
(1, 'test_employee_name1', 'test_employee_email1@email.com', '#Test_employee_password1', '+40700000001', 'test_employee_address1', '1990-01-01', 'FULL_TIME', 'BACKEND', 'JUNIOR', 1, 1),
(2, 'test_employee_name2', 'test_employee_email2@email.com', '#Test_employee_password2', '+40700000002', 'test_employee_address2', '1990-01-02', 'FULL_TIME', 'FRONTEND', 'JUNIOR', 2, 2);

insert into employees_experiences(employee_id, experience_id) values
(1, 1), (1, 2),
(2, 3), (2, 4);