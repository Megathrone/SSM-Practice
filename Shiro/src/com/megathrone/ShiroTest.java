package com.megathrone;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.ArrayList;
import java.util.List;

public class ShiroTest {
    private static boolean hasRole(User user, String role){
        Subject subject = getSubject(user);
        return subject.hasRole(role);
    }

    private static boolean isPermiited(User user,String permit){
        Subject subject = getSubject(user);
        return subject.isPermitted(permit);
    }

    private static Subject getSubject(User user) {
        Factory<SecurityManager> factory = new
                IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager sm = factory.getInstance();
        SecurityUtils.setSecurityManager(sm);

        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    private static boolean login(User user){
        Subject subject = getSubject(user);

        if(subject.isAuthenticated())
            subject.logout();

        UsernamePasswordToken token = new UsernamePasswordToken(
                user.getName(),
                user.getPassword()
        );

        try {
            subject.login(token);
        }catch (AuthenticationException e){
            return false;
        }

        return subject.isAuthenticated();
    }

    public static void main(String[] args) {
        User zhang3,li4,wang5;
        zhang3 = new User();
        zhang3.setName("zhang3");
        zhang3.setPassword("12345");

        li4 = new User();
        li4.setName("li4");
        li4.setPassword("abcde");

        wang5 = new User();
        wang5.setName("wang5");
        wang5.setPassword("wang5password");

        List<User> users = new ArrayList<>();
        users.add(zhang3);
        users.add(li4);
        users.add(wang5);


        String roleAdmin = "admin";
        String roleProductManager ="productManager";

        List<String> roles = new ArrayList<>();
        roles.add(roleAdmin);
        roles.add(roleProductManager);


        String permitAddProduct = "addProduct";
        String permitAddOrder = "addOrder";

        List<String> permits = new ArrayList<>();
        permits.add(permitAddProduct);
        permits.add(permitAddOrder);


        for (User user : users) {
            if(login(user)){
                System.out.printf("%s \t Login successfully \t, the password is %s \n",
                        user.getName(),user.getPassword());
            }else{
                System.out.printf("%s \t Login failed \t, the password is %s \n",
                        user.getName(),user.getPassword());
            }
        }

        System.out.println("---------------------------------------------");
        for (User user: users){
            for(String role: roles){
                if(login(user)){
                    if(hasRole(user, role)){
                        System.out.printf("%s \t has a role as %s \n",
                                user.getName(),role);
                    }else{
                        System.out.printf("%s \t does not have a role as %s \n",
                                user.getName(),role);
                    }
                }
            }
        }
        System.out.println("---------------------------------------------");

        for(User user: users){
            for (String permit: permits){
                if(login(user)){
                    if(isPermiited(user,permit)){
                        System.out.printf("%s \t has permission of  %s \n",
                                user.getName(),permit);
                    }else{
                        System.out.printf("%s \t does not have permission of  %s \n",
                                user.getName(),permit);
                    }
                }
            }
        }
    }
}
