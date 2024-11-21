package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import controller.RegisterRequestValidator;
import interceptor.AuthCheckInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@EnableWebMvc//OptionalValidatorFactoryBean을 글로벌 범위 Validator로 등록
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }//컨트롤러에서 다룰 수 있는 요청을 제외한 다른 요청들을 디폴트 서블릿으로 보낸다는거 였던 것 같음(my)

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
    }//viewResolover가 jsp파일을 찾을 수 있도록 설정(my)

    @Override//별 기능 없는 main Controller를 구현할 때, 이렇게 간편하게 끝
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
    }

//    @Override
//    public Validator getValidator() {
//        return new RegisterRequestValidator();
//    } OptionalValidatorFactoryBean을 글로벌 범위 Validator로 사용하기 위해 이 코드를 삭제

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor()).addPathPatterns("/edit/**");
    }

    @Override//JsonFormat을 매번 붙이기 힘드니깐, 변환하는 설정을 바꾸는거지
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder//objectMapper 생성 용이하게 해주는
//                .json()
//                //.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)//유닉스 timestamp로 못바꾸게
//                .simpleDateFormat("yyyyMMddHHmmss")//위에는 ISO-8601, 이렇게 하면 java.util.Date 타입을 내가 원하는 형식
//                .build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter))
                .build();
        converters.add(0, new MappingJackson2HttpMessageConverter(objectMapper));
        //@EnableWebMvc가 미리 등록해둔 HttpMessageConverter에는 Jackson을 이용하는 것도 등록되어 있음
        //그래서 내가 원하는게 적용되려면 해당 Converter을 맨 앞으로 옮겨야 한다
    }

    @Bean
    public AuthCheckInterceptor authCheckInterceptor() {
        return new AuthCheckInterceptor();
    }
    @Bean
    public MessageSource messageSource() {//공통된 메세지 처리
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasenames("message.label");
        ms.setDefaultEncoding("utf-8");
        return ms;
    }
}
