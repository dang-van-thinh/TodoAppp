package com.td.todoapp.services.servicesImp;

import com.td.todoapp.entity.Users;
import com.td.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    // ghi đè lại phương thức này , khi đăng nhập phương thức này sẽ được gọi
    // PHẦN NÀY THUỘC PHÂN ĐĂNG NHẬP THÔNG THƯỜNG

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không có user phù hợp"));

//        List<SimpleGrantedAuthority> authorities = Collections.singletonList(  // tạo danh sách có 1 phần tử
//                new SimpleGrantedAuthority(user.getRole().toString()));

        return new User(
                user.getName(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_"+user.getRole().toString()))
        );
    }


}
