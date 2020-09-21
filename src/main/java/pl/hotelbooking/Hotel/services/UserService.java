package pl.hotelbooking.Hotel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.MyUserPrincipal;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.domain.enums.Role;
import pl.hotelbooking.Hotel.exceptions.UserServiceException;
import pl.hotelbooking.Hotel.repository.UserRepository;
import pl.hotelbooking.Hotel.services.mapper.EntityDtoMapper;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LoginAttemptService loginAttemptService;

    @Transactional
    public UserDTO saveNewUser(UserDTO userDTO) throws UserServiceException {
        if (Objects.isNull(userDTO)) throw new UserServiceException("Given user object is null");
        validateUsername(userDTO.getUsername());
        validateEmail(userDTO.getEmail());
        userDTO.setPassword(encodePassword(userDTO.getPassword()));
        userDTO.setIsNotLocked(true);

        userRepository.save(entityDtoMapper.toUser(userDTO));
        return userDTO;
    }

    @Transactional
    public UserDTO updateUser(UserDTO newUserDTO) throws UserServiceException {
        if (Objects.isNull(newUserDTO)) throw new UserServiceException("Given user object is null");
        UserDTO oldUserDTO = entityDtoMapper
                .toUserDTO(userRepository
                        .findByUsername(newUserDTO.getUsername())
                        .orElseThrow(() -> new UserServiceException("User not found")));

        if (newUserDTO.getName() != null && !newUserDTO.getName().equals(oldUserDTO.getName())) oldUserDTO.setName(newUserDTO.getName());
        if (newUserDTO.getSurname() != null && !newUserDTO.getSurname().equals(oldUserDTO.getSurname())) oldUserDTO.setSurname(newUserDTO.getSurname());
        if (newUserDTO.getEmail() != null && !newUserDTO.getEmail().equals(oldUserDTO.getEmail())) oldUserDTO.setEmail(newUserDTO.getEmail());
        if (newUserDTO.getPhone() != null && !newUserDTO.getPhone().equals(oldUserDTO.getPhone())) oldUserDTO.setPhone(newUserDTO.getPhone());
        if (newUserDTO.getAddress() != null && !newUserDTO.getAddress().equals(oldUserDTO.getAddress())) oldUserDTO.setAddress(newUserDTO.getAddress());
        if (newUserDTO.getCity() != null && !newUserDTO.getCity().equals(oldUserDTO.getCity())) oldUserDTO.setCity(newUserDTO.getCity());
        if (newUserDTO.getZipCode() != null && !newUserDTO.getZipCode().equals(oldUserDTO.getZipCode())) oldUserDTO.setZipCode(newUserDTO.getZipCode());
        if (newUserDTO.getCountry() != null && !newUserDTO.getCountry().equals(oldUserDTO.getCountry())) oldUserDTO.setCountry(newUserDTO.getCountry());
        if (newUserDTO.getIsNotLocked() != null && !newUserDTO.getIsNotLocked().equals(oldUserDTO.getIsNotLocked())) oldUserDTO.setIsNotLocked(newUserDTO.getIsNotLocked());

        userRepository.save(entityDtoMapper.toUser(oldUserDTO));
        return oldUserDTO;
    }

    // wysyłanie emaila z jakims nowym haslem, czy cos
    @Transactional
    public UserDTO changePassword(String username, String email, String newPassword) throws UserServiceException {
        var userDTO = entityDtoMapper.toUserDTO(userRepository.findByUsername(username).orElseThrow(() ->  new UserServiceException("User not found")));
        if (!userDTO.getEmail().equals(email)) throw new UserServiceException("Given email is incorrect");
        else userDTO.setPassword(encodePassword(newPassword));
        userRepository.save(entityDtoMapper.toUser(userDTO));
        return userDTO;
    }

    @Transactional
    public UserDTO changeRoleOnAdmin(String username) throws UserServiceException {
        var userDTO = entityDtoMapper.toUserDTO(userRepository.findByUsername(username).orElseThrow(() -> new UserServiceException("User not found")));
        userDTO.setRole(Role.ROLE_ADMIN);
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = entityDtoMapper.toUserDTO(userRepository.findByUsername(username).get());
        if (userDTO == null) throw new UsernameNotFoundException("No user found by given username " + username);
        else {
            validateLoginAttempt(userDTO);
            return new MyUserPrincipal(userDTO);
        }
    }

    private void validateLoginAttempt(UserDTO userDTO) {
        if (userDTO.getIsNotLocked()) {
            if (loginAttemptService.hasExceededMaxAttempts(userDTO.getUsername())) userDTO.setIsNotLocked(false);
            else userDTO.setIsNotLocked(true);
        } else loginAttemptService.evictUserFromLoginAttemptCache(userDTO.getUsername());
    }

    public UserDTO findUserByUsername(String username) throws UserServiceException {
        return entityDtoMapper.toUserDTO(userRepository.findByUsername(username).orElseThrow(() -> new UserServiceException("User not found")));
    }

    // validacja reszty pól na froncie!
    @Transactional
    public UserDTO register(UserDTO userDTO) throws UserServiceException {
        validateUsername(userDTO.getUsername());
        validateEmail(userDTO.getEmail());
        userDTO.setPassword(encodePassword(userDTO.getPassword()));
        userDTO.setRole(Role.ROLE_USER);
        userDTO.setIsNotLocked(true);
        userRepository.save(entityDtoMapper.toUser(userDTO));
        return userDTO;
    }

    private void validateUsername(String username) throws UserServiceException {
        if (userRepository.findByUsername(username).isPresent())
            throw new UserServiceException("Given username already exists");
    }

    private void validateEmail(String email) throws UserServiceException {
        if (userRepository.findByEmail(email).isPresent())
            throw new UserServiceException("Given email already exists");
    }

    private String encodePassword(String password) throws UserServiceException {
        if (password.isBlank() || password.length() < 6)
            throw new UserServiceException("You should enter valid password to create account");

        return bCryptPasswordEncoder.encode(password);
    }
}
