package kz.oj.tinkoffhw5.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@Aspect
@Component
public class MethodTimedAspect {

    private static final Logger logger = LoggerFactory.getLogger(MethodTimedAspect.class);

    @Pointcut("@annotation(kz.oj.tinkoffhw5.aop.Timed)")
    public void timedMethod() {}

    @Around("timedMethod()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getMethod(joinPoint);
        Timed timedAnnotation = method.getAnnotation(Timed.class);

        String metricName = timedAnnotation.value().isEmpty() ? getMethodName(joinPoint) : timedAnnotation.value();

        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("Exception in method: {} :: {} ms", metricName, System.currentTimeMillis() - start, throwable);
            throw throwable;
        } finally {
            long executionTime = System.currentTimeMillis() - start;
            logger.info("Execution time of {} :: {} ms", metricName, executionTime);
        }
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getDeclaringTypeName() + "." + signature.getName();
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.getDeclaringClass().isInterface()) {
            method = joinPoint.getTarget().getClass()
                    .getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        }
        return method;
    }
}
