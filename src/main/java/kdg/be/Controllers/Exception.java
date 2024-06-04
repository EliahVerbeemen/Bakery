package kdg.be.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


    @ControllerAdvice
    public class Exception {

        @ExceptionHandler(RuntimeException.class)
        public String handleNullPointerException(Exception ex) {
           return  "errorPagina.html";

        }
    }









