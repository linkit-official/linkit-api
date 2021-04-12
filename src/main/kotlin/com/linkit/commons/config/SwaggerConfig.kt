package com.linkit.commons.config

import com.fasterxml.classmate.TypeResolver
import com.linkit.commons.dto.ErrorResponse
import io.swagger.annotations.ApiOperation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.Parameter
import springfox.documentation.service.ResponseMessage
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .globalOperationParameters(this.globalParameters())
            .globalResponseMessage(RequestMethod.GET, this.globalResponseMessages())
            .globalResponseMessage(RequestMethod.POST, this.globalResponseMessages())
            .globalResponseMessage(RequestMethod.PATCH, this.globalResponseMessages())
            .additionalModels(typeResolver.resolve(ErrorResponse::class.java))
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(this.apiInfo())

    private fun apiInfo() =
        ApiInfoBuilder()
            .title("Linkit API")
            .description("Linkit API 입니다")
            .build()

    private fun globalResponseMessages() =
        listOf<ResponseMessage>(
            ResponseMessageBuilder().code(401).message("Unauthorized").responseModel(errorModel).build(),
            ResponseMessageBuilder().code(403).message("Forbidden").responseModel(errorModel).build(),
            ResponseMessageBuilder().code(404).message("NotFound").responseModel(errorModel).build()
        )

    private fun globalParameters() =
        listOf<Parameter>(
            ParameterBuilder().name("Authorization")
                .description("Access Token")
                .parameterType("header")
                .required(false)
                .defaultValue("Bearer ")
                .modelRef(ModelRef("string"))
                .build()
        )

    companion object {
        val errorModel = ModelRef("ErrorResponse")
        val typeResolver = TypeResolver()
    }
}