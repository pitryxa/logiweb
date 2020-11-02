package logiweb.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DisplayUpdateAspect {
    @Pointcut
    public void ordersPointcut() {}


}
