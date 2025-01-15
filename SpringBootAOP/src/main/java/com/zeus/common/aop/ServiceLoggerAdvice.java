package com.zeus.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class ServiceLoggerAdvice {
	
	//조인포인트(핵심코드에서) 실행전에 작동
	/*
	 * @Before("execution(* com.zeus.service.BoardService*.*(..))") public void
	 * startLog(JoinPoint jp) { log.info("ServiceLoggerAdvice.startLog");
	 * log.info("ServiceLoggerAdvice.startLog jp.signature"+jp.getSignature());
	 * log.info("ServiceLoggerAdvice.startLog jp.agrs"+Arrays.toString(jp.getArgs())
	 * );
	 * log.info("================================================================");
	 * }
	 */
	
	//조인포인트(핵심코드에서) 실행후에 작동
	/*
	 * @AfterReturning(pointcut =
	 * "execution(* com.zeus.service.BoardService*.*(..))", returning = "result")
	 * public void stopLog(JoinPoint jp, Object result) {
	 * log.info("ServiceLoggerAdvice.stopLog"); if(result != null) {
	 * log.info("ServiceLoggerAdvice.stopLog result"+result.toString()); }
	 * log.info("*****************************************************************")
	 * ; }
	 */
	//조인포인트(핵심코드에서)  예외가 발생했을때 작동
	@AfterThrowing(pointcut = "execution(* com.zeus.service.BoardService*.*(..))", throwing = "e")
	public void exceptionLog(JoinPoint jp, Exception e) {
		log.info("ServiceLoggerAdvice.exceptionLog");
		if(e != null) {
			log.info("ServiceLoggerAdvice.exceptionLog exception"+e.toString());
		}
		log.info("*****************************************************************"); 
	}

	//조인포인트(핵심코드) 전, 후 작동
	@Around("execution(* com.zeus.service.BoardService*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable{
		long startTime = System.currentTimeMillis(); 
		Object obj = pjp.proceed();
		long stopTime = System.currentTimeMillis(); 
		log.info(pjp.getSignature().getName()+":"+ (stopTime - startTime));
		log.info("--------------------------------------------------------");
		return obj; 
	}
}







