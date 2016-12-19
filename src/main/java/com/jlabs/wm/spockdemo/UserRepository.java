package com.jlabs.wm.spockdemo;


import java.util.List;
import com.jlabs.wm.spockdemo.dto.User;

public interface UserRepository {

	List<User> findAll();
}
