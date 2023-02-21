package profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class Profiler {
    public static final Map<String, Integer> invocations = new HashMap<>();
    public static final Map<String, Double> durations = new HashMap<>();

    @Pointcut("execution(* *.*(..)) && if()")
    public static boolean pointcut(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringType().getPackage().getName().equals(System.getProperty("package"));
    }

    @Around("pointcut(joinPoint)")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "." + signature.getName();
        double startTime = System.nanoTime();

        Object result = joinPoint.proceed();

        double duration = System.nanoTime() - startTime;

        invocations.merge(method, 1, Integer::sum);
        durations.merge(method, duration, Double::sum);
        return result;
    }

    public static void printResults() {
        System.out.printf(
                " %-50s  %15s  %18s  %18s %n",
                "method", "invocations", "total duration", "average duration");

        for (Map.Entry<String, Integer> entry : invocations.entrySet()) {
            String method = entry.getKey();
            int invocationCount = entry.getValue();
            double totalDuration = durations.get(method);
            double averageDuration = totalDuration / invocationCount;
            System.out.printf(
                    " %-50s  %15d  %15.3f  %15.3f %n",
                    method, invocationCount, totalDuration, averageDuration);
        }
    }
}
