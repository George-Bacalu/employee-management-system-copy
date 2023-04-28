package com.project.ems.user;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto saveUser(UserDto userDto);

    UserDto updateUserById(UserDto userDto, Long id);

    void deleteUserById(Long id);

    User getUserEntityById(Long id);

    Page<UserDto> getAllUsersPaginatedSortedFiltered(Pageable pageable, String key);
}
