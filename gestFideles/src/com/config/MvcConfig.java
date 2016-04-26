package com.config;


@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.*" })
public class MvcConfig extends ZKWebMvcConfigurerAdapter {

    @Bean
    public ViewResolver getViewResolver() {
        ZKUrlBasedViewResolver resolver = new ZKUrlBasedViewResolver();
        resolver.setViewClass(new ZKView().getClass());
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix("");
        return resolver;
    }

}
