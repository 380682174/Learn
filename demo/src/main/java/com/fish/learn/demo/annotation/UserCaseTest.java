package com.fish.learn.demo.annotation;

import lombok.extern.log4j.Log4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/7/10 15:25
 */
@Log4j
public class UserCaseTest {


    public static void main(String[] args) {
        List<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases,47,48,49,50);
        trackUseCcase(useCases,PasswordUtils.class);
    }

    public static void trackUseCcase(List<Integer> useCases,Class<?> cl){
        for(Method m : cl.getDeclaredMethods()){
            UseCase uc = m.getAnnotation(UseCase.class);
            if(uc != null){
                log.info("found use case :" + uc.id()+"   "+uc.description());
                useCases.remove(new Integer(uc.id()));
            }
        }

        for(int i : useCases){
            log.info("warning :missing use case-" + i);
        }
    }
}
