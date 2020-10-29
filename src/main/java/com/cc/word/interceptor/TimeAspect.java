package com.cc.word.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author liyc
 * @date 2020/9/24 16:11
 */
@Component
@Aspect
public class TimeAspect {
    @Pointcut("execution(* com.cc.word.service.impl..*.*(..))")
    public void aspect(){

    }

    /*@Before("aspect()")
    public void beforeLog(JoinPoint joinPoint){
        System.out.println("LogAdvice Before");
    }

    @After("aspect()")
    public void afterLog(JoinPoint joinPoint){
        System.out.println("LogAdvice After");
    }*/

    /**
     * 环绕通知
     * @param joinPoint
     */
    @Around("aspect()")
    public Object around(JoinPoint joinPoint){
        Object result=null;
       /* Object target = joinPoint.getTarget().getClass().getName();
        System.out.println("调用者="+target);

        //目标方法签名
        System.out.println("调用方法="+joinPoint.getSignature());

        //通过joinPoint获取参数
        Object [] args = joinPoint.getArgs();
        System.out.println("参数="+args[0]);*/


        long start = System.currentTimeMillis();
        System.out.println("执行"+joinPoint.getSignature()+"开始时间"+start);

        //执行连接点的方法
        try {
            result=((ProceedingJoinPoint)joinPoint).proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("调用"+joinPoint.getSignature()+"结束时间"+end
                +"总耗时 time = " + (end - start) +" ms");
        return result;
    }
}
