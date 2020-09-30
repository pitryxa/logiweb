package logiweb.service;

import logiweb.dao.api.UserDao;
import logiweb.dto.UserDto;
import logiweb.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        User user = userDao.getByEmail(email);

        if (user != null) {
            UserDto userDto = mapper.map(user, UserDto.class);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            grantedAuthorities.add(new SimpleGrantedAuthority(userDto.getRole().toString()));

            return new org.springframework.security.core.userdetails.User(userDto.getEmail(), userDto.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User is not found.");
        }
    }
}
