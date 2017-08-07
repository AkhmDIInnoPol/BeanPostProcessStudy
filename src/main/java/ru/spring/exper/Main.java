package ru.spring.exper;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext appletContext = new ClassPathXmlApplicationContext("beans.xml");

        while (true) {
            Thread.sleep(100);
            appletContext.getBean(SayMessage.class).sayMessage();
        }
    }



}
