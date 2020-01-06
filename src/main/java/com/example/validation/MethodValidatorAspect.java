package com.example.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.executable.ExecutableValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;

@Aspect
public class MethodValidatorAspect {

    @Before("execution(* *(.., @(@(javax.validation.* || javax.validation.constraints.*) *) (*), ..))")
    public void beforeMethod(JoinPoint point) {
        Method methodToValidate = ((MethodSignature) point.getSignature()).getMethod();
        ExecutableValidator executableValidator = ValidatorHolder.getValidator().forExecutables();
        Class<?>[] groups = determineValidationGroupsForMethod(point);
        Set<ConstraintViolation<Object>> result;
        try {
            result = executableValidator.validateParameters(
                point.getThis(), methodToValidate, point.getArgs(), groups);
        } catch (IllegalArgumentException e) {
            // Probably a generic type mismatch between interface and implementation.
            // Let's try to find the bridged method on the implementation class.
            methodToValidate = BridgeMethodResolver.findBridgedMethod(
                ClassUtils.getMostSpecificMethod(methodToValidate, point.getThis().getClass()));
            result = executableValidator.validateParameters(
                point.getThis(), methodToValidate, point.getArgs(), groups);
        }
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    @Before("preinitialization(*.new(.., @(@(javax.validation.* || javax.validation.constraints.*) *) (*), ..))")
    public void beforeConstructor(JoinPoint point) {
        Constructor<?> constructorToValidate = ((ConstructorSignature) point.getSignature()).getConstructor();
        Set<? extends ConstraintViolation<?>> result = ValidatorHolder.getValidator().forExecutables().validateConstructorParameters(
            constructorToValidate,
            point.getArgs(),
            determineValidationGroupsForConstructor(point));
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    @AfterReturning(pointcut = "execution(@(@(javax.validation.* || javax.validation.constraints.*) *) * *(..))", returning = "returnValue")
    public void afterMethod(JoinPoint point, Object returnValue) {
        Method methodToValidate = ((MethodSignature) point.getSignature()).getMethod();
        Set<ConstraintViolation<Object>> result = ValidatorHolder.getValidator().forExecutables()
            .validateReturnValue(point.getThis(), methodToValidate, returnValue, determineValidationGroupsForMethod(point));
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    @AfterReturning("execution((@(@(javax.validation.* || javax.validation.constraints.*) *) *).new(..))")
    public void afterConstructor(JoinPoint point) {
        Constructor<?> constructorToValidate = ((ConstructorSignature) point.getSignature()).getConstructor();
        Set<? extends ConstraintViolation<?>> result = ValidatorHolder.getValidator().forExecutables().validateConstructorReturnValue(
            constructorToValidate,
            point.getThis(),
            determineValidationGroupsForConstructor(point));
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    private Class<?>[] determineValidationGroupsForMethod(JoinPoint point) {
        Method methodToValidate = ((MethodSignature) point.getSignature()).getMethod();
        Validated validatedAnnotation = AnnotationUtils.findAnnotation(methodToValidate, Validated.class);
        return determineValidationGroups(validatedAnnotation, point.getSignature().getDeclaringType());
    }

    private Class<?>[] determineValidationGroupsForConstructor(JoinPoint point) {
        Constructor<?> constructorToValidate = ((ConstructorSignature) point.getSignature()).getConstructor();
        Validated validatedAnnotation = AnnotationUtils.findAnnotation(constructorToValidate, Validated.class);
        return determineValidationGroups(validatedAnnotation, point.getSignature().getDeclaringType());
    }

    private Class<?>[] determineValidationGroups(Validated validatedAnnotation, Class<?> clazz) {
        Validated annotation = Optional.ofNullable(validatedAnnotation)
            .orElseGet(() -> AnnotationUtils.findAnnotation(clazz, Validated.class));
        return Optional.ofNullable(annotation).map(Validated::value).orElse(new Class<?>[0]);
    }

}
