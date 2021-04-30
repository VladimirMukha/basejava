package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {


    public static void main(String[] args) throws IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("name");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        field.set(resume, "new_Resume");
        System.out.println(resume);

        Annotation[] annotations = resume.getClass().getAnnotations();
        System.out.println(annotations);
        Method method = resume.getClass().getDeclaredMethod("toString");
        System.out.println(method.invoke(resume));
    }
}