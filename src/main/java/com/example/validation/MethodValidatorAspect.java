package com.example.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;

@Aspect
public class MethodValidatorAspect {

    @Before("execution(* *(.., @(javax.validation.* || javax.validation.constraints.*) (*), ..))")
    public void beforeMethod(JoinPoint point) {
        Method methodToValidate = ((MethodSignature) point.getSignature()).getMethod();
        // Avoid Validator invocation on FactoryBean.getObjectType/isSingleton
        if (isFactoryBeanMetadataMethod(methodToValidate)) {
            return;
        }
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

    @Before("preinitialization(*.new(.., @(javax.validation.* || javax.validation.constraints.*) (*), ..))")
    public void beforeConstructor(JoinPoint point) {
        Constructor constructorToValidate = ((ConstructorSignature) point.getSignature()).getConstructor();
        ExecutableValidator executableValidator = ValidatorHolder.getValidator().forExecutables();
        Class<?>[] groups = determineValidationGroupsForConstructor(point);
        Set<ConstraintViolation<Object>> result = executableValidator.validateConstructorParameters(constructorToValidate, point.getArgs(), groups);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    @AfterReturning(pointcut = "execution(@(javax.validation.* || javax.validation.constraints.*) * *(..))", returning = "returnValue")
    public void afterMethod(JoinPoint point, Object returnValue) {
        ExecutableValidator executableValidator = ValidatorHolder.getValidator().forExecutables();
        Class<?>[] groups = determineValidationGroupsForMethod(point);
        Method methodToValidate = ((MethodSignature) point.getSignature()).getMethod();
        Set<ConstraintViolation<Object>> result = executableValidator
            .validateReturnValue(point.getThis(), methodToValidate, returnValue, groups);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    private boolean isFactoryBeanMetadataMethod(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        // Call from interface-based proxy handle, allowing for an efficient check?
        if (clazz.isInterface()) {
            return ((FactoryBean.class .equals(clazz)|| SmartFactoryBean.class.equals(clazz)) &&
                !method.getName().equals("getObject"));
        }
        // Call from CGLIB proxy handle, potentially implementing a FactoryBean method?
        Class<?> factoryBeanType = null;
        if (SmartFactoryBean.class.isAssignableFrom(clazz)) {
            factoryBeanType = SmartFactoryBean.class;
        } else if (FactoryBean.class.isAssignableFrom(clazz)) {
            factoryBeanType = FactoryBean.class;
        }
        return (factoryBeanType != null && !method.getName().equals("getObject") &&
            ClassUtils.hasMethod(factoryBeanType, method.getName(), method.getParameterTypes()));
    }

    protected Class<?>[] determineValidationGroupsForMethod(JoinPoint point) {
        Method methodToValidate = ((MethodSignature) point.getSignature()).getMethod();
        Validated validatedAnnotation = AnnotationUtils.findAnnotation(methodToValidate, Validated.class);
        if (validatedAnnotation == null) {
            validatedAnnotation = AnnotationUtils.findAnnotation(point.getSignature().getDeclaringType(), Validated.class);
        }
        return (validatedAnnotation != null ? validatedAnnotation.value() : new Class<?>[0]);
    }

    protected Class<?>[] determineValidationGroupsForConstructor(JoinPoint point) {
        Constructor constructorToValidate = ((ConstructorSignature) point.getSignature()).getConstructor();
        Validated validatedAnnotation = AnnotationUtils.findAnnotation(constructorToValidate, Validated.class);
        if (validatedAnnotation == null) {
            validatedAnnotation = AnnotationUtils.findAnnotation(point.getSignature().getDeclaringType(), Validated.class);
        }
        return (validatedAnnotation != null ? validatedAnnotation.value() : new Class<?>[0]);
    }

}
