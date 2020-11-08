package logiweb.aop;

import logiweb.topic.Sender;
import logiweb.topic.SenderQueue;
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

    @Autowired
    private SenderQueue queueSender;

    @Pointcut("execution(public * logiweb.service.OrderServiceImpl.*(..))")
    public void orderServiceMethods() {}

    @Pointcut("execution(public * logiweb.service.DriverServiceImpl.*(..))")
    public void driverServiceMethods() {}

    @Pointcut("execution(public * logiweb.service.TruckServiceImpl.*(..))")
    public void truckServiceMethods() {}

    @Pointcut("@within(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {}

    @Pointcut("@annotation(logiweb.aop.SendUpdate)")
    public void annotated() {}

    @Pointcut("orderServiceMethods() && annotated()")
    public void ordersSends() {}

    @Pointcut("driverServiceMethods() && annotated()")
    public void driversSends() {}

    @Pointcut("truckServiceMethods() && annotated()")
    public void trucksSends() {}

    @Pointcut("ordersSends() || driversSends() || trucksSends()")
    public void sendsMethods() {}

    @AfterReturning("annotated()")
    public void sendsMethodCall(){
//        System.out.println("---*** ENTRY POINT 1 ***---");
        topicSender.send(UPDATE);
        //queueSender.send(UPDATE);
//        System.out.println("---*** ENTRY POINT 2 ***---");
    }

//    @AfterReturning("annotated()")
//    public void testCall() {
//        System.out.println("---*** ENTRY POINT ***---");
//    }


}
