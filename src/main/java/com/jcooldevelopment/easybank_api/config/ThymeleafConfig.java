package com.jcooldevelopment.easybank_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

// https://www.arquitecturajava.com/spring-thymeleaf-un-motor-de-plantillas/
// https://www.thymeleaf.org/doc/articles/standardurlsyntax.html
@Configuration
public class ThymeleafConfig {

    @Bean(name ="templateResolver") 
    public ITemplateResolver getTemplateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("XHTML");
        return templateResolver;
    }

    @Bean(name ="templateEngine") 
    public SpringTemplateEngine getTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(getTemplateResolver());
        return templateEngine;
    }

    @Bean(name="viewResolver")
    public ThymeleafViewResolver getViewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver(); 
        viewResolver.setTemplateEngine(getTemplateEngine());
        return viewResolver;
    }

}
