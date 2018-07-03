package java.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer{

	private static final Charset UTF8=Charset.forName("UTF-8");
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter=new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text","plain",UTF8)));
		
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(3500000);
		registry.addResourceHandler("/img/**").addResourceLocations("/img").setCachePeriod(3500000);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/**").setCachePeriod(35000);
		
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
