package telran.java57.forum.accounting.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java57.forum.accounting.dao.UserRepository;
import telran.java57.forum.accounting.dto.RolesDto;
import telran.java57.forum.accounting.dto.UpdateUserDto;
import telran.java57.forum.accounting.dto.UserDto;
import telran.java57.forum.accounting.dto.UserRegisterDto;
import telran.java57.forum.accounting.model.UserAccount;
import telran.java57.forum.accounting.dto.exception.AccountNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        Optional<UserAccount> oldAccount = userRepository.findById(userRegisterDto.getLogin());
        if (oldAccount.isPresent()) {
            return null;
        }
        UserAccount account = new UserAccount(userRegisterDto.getLogin(),
                userRegisterDto.getPassword(), userRegisterDto.getFirstName(), userRegisterDto.getLastName());
        userRepository.save(account);
        return modelMapper.map(account, UserDto.class);
    }

    @Override
    public UserDto getUser(String name) {
        UserAccount account = userRepository.findById(name).orElseThrow(AccountNotFoundException::new);
        return modelMapper.map(account, UserDto.class);
    }

    @Override
    public void changePassword(String name, String newPassword) {

    }

    @Override
    public UserDto removeUser(String login) {
        return null;
    }

    @Override
    public UserDto updateUser(String login, UpdateUserDto updateUserDto) {
        return null;
    }

    @Override
    public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
        return null;
    }
}
