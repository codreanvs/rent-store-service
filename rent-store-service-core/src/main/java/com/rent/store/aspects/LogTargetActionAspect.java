package com.rent.store.aspects;

import com.rent.store.aspects.annotations.LogErrorAlertAfterThrowingTargetAction;
import com.rent.store.aspects.annotations.LogInfoAlertAfterTargetAction;
import com.rent.store.aspects.annotations.LogInfoAlertBeforeTargetAction;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "aop-logger-audit.log-target-action-aspect.enabled", havingValue = "true")
@Slf4j
@Aspect
@Component
public class LogTargetActionAspect {

    @ConditionalOnProperty(name = "aop-logger-audit.log-info-alert-before-target-action.enabled", havingValue = "true")
    @Before("@annotation(com.rent.store.aspects.annotations.LogInfoAlertBeforeTargetAction)")
    public void logInfoAlertBeforeTargetAction(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final LogInfoAlertBeforeTargetAction logInfoAlertBeforeTargetAction = methodSignature.getMethod()
                .getAnnotation(LogInfoAlertBeforeTargetAction.class);
        log.info(logInfoAlertBeforeTargetAction.value());
    }

    @ConditionalOnProperty(name = "aop-logger-audit.log-info-alert-after-target-action.enabled", havingValue = "true")
    @After("@annotation(com.rent.store.aspects.annotations.LogInfoAlertAfterTargetAction)")
    public void logInfoAlertAfterTargetAction(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final LogInfoAlertAfterTargetAction logInfoAlertAfterTargetAction = methodSignature.getMethod()
                .getAnnotation(LogInfoAlertAfterTargetAction.class);
        log.info(logInfoAlertAfterTargetAction.value());
    }

    @ConditionalOnProperty(name = "aop-logger-audit.log-error-alert-after-throwing-target-action.enabled", havingValue = "true")
    @AfterThrowing(
            value = "@annotation(com.rent.store.aspects.annotations.LogErrorAlertAfterThrowingTargetAction)",
            throwing = "exception"
    )
    public void logErrorAlertAfterThrowingTargetAction(final JoinPoint joinPoint, final Exception exception) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final LogErrorAlertAfterThrowingTargetAction logErrorAlertAfterThrowingTargetAction =
                methodSignature.getMethod().getAnnotation(LogErrorAlertAfterThrowingTargetAction.class);
        log.error(logErrorAlertAfterThrowingTargetAction.value(), exception);
    }

}
