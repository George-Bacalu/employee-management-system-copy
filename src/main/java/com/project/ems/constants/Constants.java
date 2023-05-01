package com.project.ems.constants;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String RESOURCE_NOT_FOUND = "Resource not found: %s";
    public static final String INVALID_REQUEST = "Invalid request: %s";
    public static final String HTTP_REQUEST_METHOD_NOT_SUPPORTED = "Http request method not supported: %s";
    public static final String HTTP_MEDIA_TYPE_NOT_SUPPORTED = "Http media type not supported: %s";
    public static final String METHOD_ARGUMENT_NOT_VALID = "Method argument not valid: %s";

    public static final String EMPLOYEE_NOT_FOUND = "Employee with id %s not found";
    public static final String EXPERIENCE_NOT_FOUND = "Experience with id %s not found";
    public static final String FEEDBACK_NOT_FOUND = "Feedback with id %s not found";
    public static final String MENTOR_NOT_FOUND = "Mentor with id %s not found";
    public static final String ROLE_NOT_FOUND = "Role with id %s not found";
    public static final String STUDIES_NOT_FOUND = "Studies with id %s not found";
    public static final String USER_NOT_FOUND = "User with id %s not found";

    public static final Long VALID_ID = 1L;
    public static final Long INVALID_ID = 999L;
    public static final List<Long> EXPERIENCE1_IDS = List.of(1L, 2L);
    public static final List<Long> EXPERIENCE2_IDS = List.of(3L, 4L);

    public static final String API_EMPLOYEES = "/api/employees";
    public static final String API_EXPERIENCES = "/api/experiences";
    public static final String API_FEEDBACKS = "/api/feedbacks";
    public static final String API_MENTORS = "/api/mentors";
    public static final String API_ROLES = "/api/roles";
    public static final String API_STUDIES = "/api/studies";
    public static final String API_USERS = "/api/users";

    public static final String TEXT_HTML_UTF8 = "text/html;charset=UTF-8";
    public static final String EMPLOYEES_VIEW = "employee/employees";
    public static final String EMPLOYEE_DETAILS_VIEW = "employee/employee-details";
    public static final String SAVE_EMPLOYEE_VIEW = "employee/save-employee";
    public static final String REDIRECT_EMPLOYEES_VIEW = "redirect:/employees";
    public static final String EXPERIENCES_VIEW = "experience/experiences";
    public static final String EXPERIENCE_DETAILS_VIEW = "experience/experience-details";
    public static final String SAVE_EXPERIENCE_VIEW = "experience/save-experience";
    public static final String REDIRECT_EXPERIENCES_VIEW = "redirect:/experiences";
    public static final String FEEDBACKS_VIEW = "feedback/feedbacks";
    public static final String FEEDBACK_DETAILS_VIEW = "feedback/feedback-details";
    public static final String SAVE_FEEDBACK_VIEW = "feedback/save-feedback";
    public static final String REDIRECT_FEEDBACKS_VIEW = "redirect:/feedbacks";
    public static final String MENTORS_VIEW = "mentor/mentors";
    public static final String MENTOR_DETAILS_VIEW = "mentor/mentor-details";
    public static final String SAVE_MENTOR_VIEW = "mentor/save-mentor";
    public static final String REDIRECT_MENTORS_VIEW = "redirect:/mentors";
    public static final String ROLES_VIEW = "role/roles";
    public static final String ROLE_DETAILS_VIEW = "role/role-details";
    public static final String SAVE_ROLE_VIEW = "role/save-role";
    public static final String REDIRECT_ROLES_VIEW = "redirect:/roles";
    public static final String STUDIES_VIEW = "studies/studies";
    public static final String STUDIES_DETAILS_VIEW = "studies/studies-details";
    public static final String SAVE_STUDIES_VIEW = "studies/save-studies";
    public static final String REDIRECT_STUDIES_VIEW = "redirect:/studies";
    public static final String USERS_VIEW = "user/users";
    public static final String USER_DETAILS_VIEW = "user/user-details";
    public static final String SAVE_USER_VIEW = "user/save-user";
    public static final String REDIRECT_USERS_VIEW = "redirect:/users";

    public static final Pageable pageable = PageRequest.of(0, 4, Sort.Direction.ASC, "id");
    public static final String EMPLOYEE_FILTER_KEY = "front";
    public static final String EXPERIENCE_FILTER_KEY = "intern";
    public static final String FEEDBACK_FILTER_KEY = "prove";
    public static final String MENTOR_FILTER_KEY = "john";
    public static final String ROLE_FILTER_KEY = "adm";
    public static final String STUDIES_FILTER_KEY = "harvard";
    public static final String USER_FILTER_KEY = "john";
    public static final String EMPTY_FILTER_KEY = "";

    public static final String EMPLOYEE_FILTER_QUERY = "select e from Employee e where lower(concat(e.name, '', e.email, '', e.mobile, '', e.address, '', e.birthday, '', e.jobType, '', e.position, '', e.grade, '', e.mentor, '', e.studies)) like %:key%";
    public static final String EXPERIENCE_FILTER_QUERY = "select e from Experience e where lower(concat(e.title, '', e.organization, '', e.experienceType, '', e.startedAt, '', e.finishedAt)) like %:key%";
    public static final String FEEDBACK_FILTER_QUERY = "select f from Feedback f where lower(concat(f.feedbackType, '', f.description, '', f.sentAt, '', f.user)) like %:key%";
    public static final String MENTOR_FILTER_QUERY = "select m from Mentor m where lower(concat(m.name, '', m.email, '', m.mobile, '', m.address, '', m.birthday, '', m.isAvailable, '', m.numberOfEmployees)) like %:key%";
    public static final String ROLE_FILTER_QUERY = "select r from Role r where lower(r.name) like %:key%";
    public static final String STUDIES_FILTER_QUERY = "select s from Studies s where lower(concat(s.university, '', s.faculty, '', s.major)) like %:key%";
    public static final String USER_FILTER_QUERY = "select u from User u where lower(concat(u.name, '', u.email, '', u.mobile, '', u.address, '', u.birthday, '', u.role)) like %:key%";
}
