package pl.hotelbooking.Hotel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.exceptions.UserServiceException;
import pl.hotelbooking.Hotel.repository.UserRepository;
import pl.hotelbooking.Hotel.services.mapper.EntityDtoMapper;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Transactional
    public UserDTO saveNewUser(UserDTO userDTO) throws UserServiceException {
        if (Objects.isNull(userDTO)) throw new UserServiceException("Given user object is null");

        userRepository.save(entityDtoMapper.toUser(userDTO));
        return userDTO;
    }

    @Transactional
    public UserDTO updateUser(UserDTO userDTO) throws UserServiceException {
        if (Objects.isNull(userDTO)) throw new UserServiceException("Given user object is null");

        userRepository.save(entityDtoMapper.toUser(userDTO));
        return userDTO;
    }

    public Long removeUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    public UserDTO getUser(Long userId) throws UserServiceException {
        return entityDtoMapper.toUserDTO(userRepository.findById(userId).orElseThrow(() -> new UserServiceException("User not found")));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
