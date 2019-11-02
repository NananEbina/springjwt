package pers.missp.springjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.missp.springjwt.domin.User;
import pers.missp.springjwt.resp.UserMapper;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("can not find this user in database!");
        }
        return user;
    }
}
