package ttv.poltoraha.pivka.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger =  LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* ttv.poltoraha.pivka.serviceImpl.AuthorServiceImpl.*(..))")
    private void authorServicePointcut() {}

    @Pointcut("execution(* ttv.poltoraha.pivka.controller.AuthorController.*(..))")
    private void authorControllerPointcut() {}


    @Before("authorServicePointcut()")
    public void logAuthorServiceImplStart(JoinPoint joinPoint) {
        logger.info("Start method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning("authorServicePointcut()")
    public void logAuthorServiceImplEnd(JoinPoint joinPoint) {
        logger.info("End method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "authorServicePointcut()", throwing = "ex")
    public void logAuthorServiceImplError(JoinPoint jp, Throwable ex) {
        logger.error("Error in {}: {}", jp.getSignature().getName(), ex.toString(), ex);
    }

    @Before("authorControllerPointcut()")
    public void logAuthorControllerStart(JoinPoint joinPoint) {
        logger.info("Start method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning("authorControllerPointcut()")
    public void logAuthorControllerEnd(JoinPoint joinPoint) {
        logger.info("End method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "authorControllerPointcut()", throwing = "ex")
    public void logAuthorControllerException(JoinPoint joinPoint,  Throwable ex) {
        logger.error("Error in {}: {}", joinPoint.getSignature().getName(), ex.toString(), ex);
    }
}
