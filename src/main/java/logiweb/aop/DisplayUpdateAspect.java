package logiweb.aop;

import logiweb.topic.Sender;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DisplayUpdateAspect {
    private final String UPDATE = "update";

    @Autowired
    private Sender topicSender;

    @Pointcut("@annotation(logiweb.aop.SendUpdate)")
    public void annotated() {}

    @AfterReturning("annotated()")
    public void sendsMethodCall(){
        topicSender.send(UPDATE);
    }
}
