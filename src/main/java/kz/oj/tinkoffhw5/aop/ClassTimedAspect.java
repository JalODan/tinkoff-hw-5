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
public class ClassTimedAspect {

    private static final Logger logger = LoggerFactory.getLogger(ClassTimedAspect.class);

    @Pointcut("@within(kz.oj.tinkoffhw5.aop.Timed)")
    public void timedClass() {}

    @Around("timedClass() && execution(* *(..)) && !@annotation(kz.oj.tinkoffhw5.aop.Timed)")
    public Object measureClassMethodsExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = getMethod(joinPoint);

        if (method.isAnnotationPresent(Timed.class)) {
            return joinPoint.proceed();
        }

        Timed timedAnnotation = joinPoint.getTarget().getClass().getAnnotation(Timed.class);

        String metricName = timedAnnotation.value().isEmpty() ? getMethodName(joinPoint) : timedAnnotation.value() + "." + method.getName();

        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - start;
            logger.error("Exception in method: {} :: {} ms", metricName, executionTime, throwable);
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
