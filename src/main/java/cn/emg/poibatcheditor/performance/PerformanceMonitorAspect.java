package cn.emg.poibatcheditor.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

@Aspect
@Component
public class PerformanceMonitorAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

	/**
	 * 创建一个日志信息
	 *
	 * @param methodName
	 *            方法名
	 * @param methodDuration
	 *            执行时间
	 * @return
	 */
	private static String buildLogMessage(String clazzName, String methodName, double methodDuration) {
		StringBuilder message = new StringBuilder();
		message.append("[ ");
		message.append(clazzName);
		message.append(" ] ");
		message.append(methodName);
		message.append(" --- ");
		message.append("<");
		message.append(methodDuration);
		message.append(">");
		return message.toString();
	}

	@Pointcut(value = "@annotation(PerformanceMonitor)")
	public void pointcut() {
		
	}

	/**
	 * 在截获的目标方法体开始执行时刚进入该方法实体时调用
	 *
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean hasMethodAnnotation = false;
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = methodSignature.getName();
		if (null != method) {
			hasMethodAnnotation = method.isAnnotationPresent(PerformanceMonitor.class);
		}
		Object result;
		if (hasMethodAnnotation) {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			result = joinPoint.proceed();
			stopWatch.stop();
			String runInfo = buildLogMessage(className, methodName, stopWatch.getTotalTimeSeconds());
			logger.debug(runInfo);
		} else {
			result = joinPoint.proceed();
		}
		return result;
	}
}
