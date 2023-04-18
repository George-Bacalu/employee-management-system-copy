package com.project.ems.constants;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
}
