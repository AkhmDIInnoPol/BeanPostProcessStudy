package ru.spring.exper;

import ru.spring.exper.annotations.Profiling;
import ru.spring.exper.annotations.SetSum;

import javax.annotation.PostConstruct;


@Profiling
public class Messager implements SayMessage {



    private String message;

    @SetSum(val1 = 25,val2 = 15)
    private int sum;

    @PostConstruct
    public void init(){
        System.out.println("Phase2");
    }

    public Messager() {
        System.out.println("Phase1");
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    @Override
    public void sayMessage()
    {
        System.out.println(this.message + "\n" + "sum = " + sum);
    }




}
