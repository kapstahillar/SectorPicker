package com.kapsta.sectorpicker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@ControllerAdvice
public class WebExceptionHandling {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionHandling.class);

    @ExceptionHandler({ConstraintViolationException.class})
    public String handleConstraintViolationException(
            RedirectAttributes redirectAttrs,
            ConstraintViolationException ex
    ) {
        LOGGER.error(ex.getMessage(), ex);
        redirectAttrs.addFlashAttribute("errors", ex.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessage).toList());
        return "redirect:/?";
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public String handleBindException(
            IllegalArgumentException ex,
            RedirectAttributes redirectAttrs
    ) {
        LOGGER.error(ex.getMessage(), ex);
        redirectAttrs.addFlashAttribute("errors", new ArrayList<String>().add("An Error occured."));
        return "redirect:/?";
    }


}
