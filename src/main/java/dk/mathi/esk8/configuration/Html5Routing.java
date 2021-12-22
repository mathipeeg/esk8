package dk.mathi.esk8.configuration;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

/**
 * This class sets up the "redirect 404 to index.html" which is needed for HTML5 routing as used by, say, the Angular
 * router.
 */
@Configuration
public class Html5Routing {
  @Bean
  ErrorViewResolver redirect404ToIndex() {
    return (request, status, model) -> status == HttpStatus.NOT_FOUND
                                       ? new ModelAndView("index.html", Collections.emptyMap(), HttpStatus.OK)
                                       : null;
  }
}
