package kdg.be.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class Exception {


    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler()
        public String handleNullPointerException(Exception ex) {
           return  "errorPagina.html";

        }
    }








}
