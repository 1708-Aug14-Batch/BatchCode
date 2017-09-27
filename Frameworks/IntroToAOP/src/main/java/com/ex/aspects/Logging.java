package com.ex.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// Aspect - unit of modularity for crosscutting concerns
@Aspect
@Component
public class Logging {
	
	long numMiliseconds;
	
	// Advice - the block of code that runs based on the pointcut definition
	@Before("execution(* com.ex.beans.Student.* (..))")
	public void logBefore(){
		System.out.println("log called before");
	}
	
	/*
	 * pointcut - join point quieries where advice executes
	 * join point - well-defined points in the program flow
	 */
	// Set of methods where the advice can be applied
	@Pointcut("execution(* com.ex.beans.*.* (..))")
	public void everywhere() {}
	
	@After("everywhere()")
	public void logAfter() {
		System.out.println("log called after");
	}
	
	@Around("everywhere()")
	public void count(ProceedingJoinPoint pjp) {
		System.out.println("1...2...3...");
		
		// Actually run the method
		try {
			pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println("4...5...");
	}
	
	@Before("execution(* com.ex.beans.Student.takeTime(..))")
	public void startTimer() {
		numMiliseconds = System.currentTimeMillis();
	}
	@After("execution(* com.ex.beans.Student.takeTime(..))")
	public void endTimer() {
		numMiliseconds -= System.currentTimeMillis();
		numMiliseconds = Math.abs(numMiliseconds);
		System.out.println("takeTime too : " + numMiliseconds + " miliseconds");
	}
}