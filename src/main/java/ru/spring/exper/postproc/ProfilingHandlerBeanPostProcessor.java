package ru.spring.exper.postproc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ru.spring.exper.Messager;
import ru.spring.exper.ProfilingController;
import ru.spring.exper.SayMessage;
import ru.spring.exper.annotations.Profiling;


import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {


    private Map<String, Class> beanMap = new HashMap<>();
    private ProfilingController profilingController =  new ProfilingController();

    @Override
    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class))
            beanMap.put(beanName, beanClass);
        return bean;
    }

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(profilingController, new ObjectName("profiling", "parameter", "controller"));
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {

        Class beanClass = beanMap.get(beanName);

        if (beanClass != null){


            Object object =  Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    Object returnObj;
                    if (profilingController.isEnable()) {
                        System.out.println("Begin profiling.");
                        long startTime = System.nanoTime();
                        returnObj = method.invoke(bean, args);
                        long endTime = System.nanoTime();
                        System.out.println("Time: " + (endTime - startTime));
                        System.out.println("End profiling.");
                        return returnObj;
                    } else {
                        returnObj = method.invoke(bean, args);
                        return returnObj;
                    }
                }
            });

             return object;
        }



        return bean;
    }
}
