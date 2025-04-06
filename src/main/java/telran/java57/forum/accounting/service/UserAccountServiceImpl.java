package telran.java57.forum.accounting.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java57.forum.accounting.dao.UserRepository;
import telran.java57.forum.accounting.dto.RolesDto;
import telran.java57.forum.accounting.dto.UpdateUserDto;
import telran.java57.forum.accounting.dto.UserDto;
import telran.java57.forum.accounting.dto.UserRegisterDto;
import telran.java57.forum.accounting.dto.exception.AccountAlreadyExistsException;
import telran.java57.forum.accounting.dto.exception.BadRequestException;
import telran.java57.forum.accounting.model.UserAccount;
import telran.java57.forum.accounting.dto.exception.AccountNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        String login = userRegisterDto.getLogin();
        if (login == null || login.isEmpty()) {
            throw new BadRequestException();
        }
        Optional<UserAccount> oldAccount = userRepository.findById(login);
        if (oldAccount.isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        UserAccount account = new UserAccount(login, userRegisterDto.getPassword(),
                userRegisterDto.getFirstName(), userRegisterDto.getLastName());
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
        UserAccount account = userRepository.findById(name).orElseThrow(AccountNotFoundException::new);
        account.setPassword(newPassword);
        userRepository.save(account);
    }

    @Override
    public UserDto removeUser(String login) {
        UserAccount account = userRepository.findById(login).orElseThrow(AccountNotFoundException::new);
        userRepository.delete(account);
        return modelMapper.map(account, UserDto.class);
    }

    @Override
    public UserDto updateUser(String login, UpdateUserDto updateUserDto) {
        UserAccount account = userRepository.findById(login).orElseThrow(AccountNotFoundException::new);
        account.setFirstName(updateUserDto.getFirstName());
        account.setLastName(updateUserDto.getLastName());
        userRepository.save(account);
        return modelMapper.map(account, UserDto.class);
    }

    @Override
    public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
        UserAccount account = userRepository.findById(login).orElseThrow(AccountNotFoundException::new);
        if (isAddRole) {
            account.addRole(role);
        } else {
            account.removeRole(role);
        }
        userRepository.save(account);
        Set<String> roles = new HashSet<>();
        account.getRoles().forEach(r -> roles.add(r.toString()));
        return new RolesDto(login, roles);
    }
}
