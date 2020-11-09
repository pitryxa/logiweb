package logiweb.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ModelAndView handleRuntimeException(Exception ex) {
        logger.error("Exception!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exceptionPage");
        modelAndView.addObject("ex", ex);
        return modelAndView;
    }
}