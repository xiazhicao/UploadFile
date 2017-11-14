package Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
//	record and output any exception message
	@ExceptionHandler(value=Exception.class)
	public String defaultErrorHandler(Exception e, RedirectAttributes redirect) throws Exception {
			redirect.addFlashAttribute("exception", e.getCause().getMessage());
			return "error";
	}
}
