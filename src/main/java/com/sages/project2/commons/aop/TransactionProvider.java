package com.sages.project2.commons.aop;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

// Don't work after some PR
@Aspect
@RequiredArgsConstructor
public class TransactionProvider {

    private final PlatformTransactionManager transactionManager;
    @Setter
    private TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();

    @Around("@annotation(Atomic)")
    public Object runWithTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        var transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            var result = joinPoint.proceed();
            transactionManager.commit(transaction);
            return result;
        } catch (Throwable throwable) {
            transaction.setRollbackOnly();
            throw throwable;
        }
    }
}
