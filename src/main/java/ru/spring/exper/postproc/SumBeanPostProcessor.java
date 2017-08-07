package ru.spring.exper.postproc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import ru.spring.exper.annotations.SetSum;
import sun.reflect.misc.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SumBeanPostProcessor implements BeanPostProcessor {


    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {

        Field[] declaredFields = bean.getClass().getDeclaredFields();

        Arrays.stream(declaredFields).filter(field -> field.getAnnotation(SetSum.class) != null)
                                     .forEach(field -> {
                                         SetSum annotation = field.getAnnotation(SetSum.class);
                                         int val1 = annotation.val1();
                                         int val2 = annotation.val2();
                                         field.setAccessible(true);
                                         ReflectionUtils.setField(field, bean, val1 + val2);
                                     });

        return bean;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
