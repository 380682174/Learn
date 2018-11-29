package com.fish.learn.demo.annotation;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/7/10 15:22
 */
public class PasswordUtils {
    public boolean validatePassword(String password){
        return (password.matches("\\w*\\d\\w*"));
    }

    @UseCase(id="48")
    public String encryptPassword(String password){
        return new StringBuilder(password).reverse().toString();
    }
}
