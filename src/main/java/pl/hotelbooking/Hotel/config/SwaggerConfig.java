package pl.hotelbooking.Hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiRooms() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Rooms")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.app.controller"))
                .paths(PathSelectors.ant("/rooms/**"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket apiUsers() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Users")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.app.controller"))
                .paths(PathSelectors.ant("/user/**"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket apiReservations() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Reservations")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.app.controller"))
                .paths(PathSelectors.ant("/reservation/**"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket apiBookingStatus() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Booking status")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.app.controller"))
                .paths(PathSelectors.ant("/bookingStatus/**"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Booking Service documentation")
                .description("Booking service is an application intended to hotel on the internet. The application" +
                        "presents the hotel's offer and the availability of rooms at specified date for customers, as " +
                        "well as hotel management and reservations for hotel employees.")
                .contact(new Contact("Łukasz Bartoś", "https://www.linkedin.com/in/%C5%82ukasz-barto%C5%9B-5411731b3/", "b_lukasz.1994@wp.pl"))
                .version("1.0")
                .build();
    }

}
