package pl.hotelbooking.Hotel.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hotelbooking.Hotel.domain.User;
import pl.hotelbooking.Hotel.domain.dto.UserDTO;
import pl.hotelbooking.Hotel.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveNewUser(UserDTO userDTO) {
        userRepository.save(userDTO.toUser());
    }

    @Transactional
    public void updateUser(UserDTO userDTO) {
        userRepository.save(userDTO.toUser());
    }

    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }
}
