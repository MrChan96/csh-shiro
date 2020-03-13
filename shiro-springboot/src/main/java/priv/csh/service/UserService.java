package priv.csh.service;

import priv.csh.pojo.User;

public interface UserService {
    public User queryUserByName(String name);
}
