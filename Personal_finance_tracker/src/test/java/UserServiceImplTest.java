import com.example.personal_finance_tracker.DTOs.UserDTO;
import com.example.personal_finance_tracker.Exceptions.InvalidUserException;
import com.example.personal_finance_tracker.Models.Role;
import com.example.personal_finance_tracker.Models.User;
import com.example.personal_finance_tracker.Repositories.RoleRepository;
import com.example.personal_finance_tracker.Repositories.UserRepository;

import com.example.personal_finance_tracker.Services.impl.JWTService;
import com.example.personal_finance_tracker.Services.impl.UserServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private UserServiceimpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {

        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void getUserById_shouldThrowException_whenUserDoesNotExist() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> userService.getUserById(userId));
    }

    @Test
    void addUser_shouldCreateAndReturnUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        Role role = new Role("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        User result = userService.addUser(userDTO);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {

        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("updateduser");
        userDTO.setEmail("updated@example.com");

        User existingUser = new User();
        existingUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.updateUser(userId, userDTO);

        assertEquals("updateduser", result.getUsername());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser_shouldDeleteAndReturnUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.deleteUser(userId);

        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void verifyUser_shouldReturnToken_whenCredentialsAreValid() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(mock(UserDetails.class));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("mocked-jwt-token");


        String token = userService.VerifyUser(userDTO);


        assertEquals("mocked-jwt-token", token);
    }

    @Test
    void verifyUser_shouldReturnError_whenAuthenticationFails() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("invaliduser");
        userDTO.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));


        String result = userService.VerifyUser(userDTO);


        assertTrue(result.startsWith("Failed to login user: invaliduser"));
    }

    @Test
    void getUserByUsername_shouldReturnUser_whenUserExists() {

        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));


        User result = userService.getUserByUsername(username);


        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    void getUserByUsername_shouldThrowException_whenUserDoesNotExist() {

        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());


        assertThrows(InvalidUserException.class, () -> userService.getUserByUsername(username));
    }
}
