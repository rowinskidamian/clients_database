package pl.damianrowinski.clients_database.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.damianrowinski.clients_database.exceptions.CompanyNotFoundException;

@ControllerAdvice(basePackages = "pl.damianrowinski.clients_database.rest")
@Slf4j
public class ExceptionHandlerRestController {
    @ResponseBody
    @ExceptionHandler({CompanyNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String companyNotFound(CompanyNotFoundException ex) {
        log.error("Raised CompanyNotFoundException");
        return ex.getMessage();
    }
}
