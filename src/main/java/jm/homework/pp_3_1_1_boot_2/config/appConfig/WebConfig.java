//package jm.homework.pp_3_1_1_boot_2.config.appConfig;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    private final ApplicationContext applicationContext;
//
//    public WebConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//
//}