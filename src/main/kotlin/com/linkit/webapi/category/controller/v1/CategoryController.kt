package com.linkit.webapi.category.controller.v1

import com.linkit.webapi.category.dto.CategoryInfoListResponse
import com.linkit.webapi.category.dto.UserCategoryListRequest
import com.linkit.webapi.category.service.CategoryService
import com.linkit.webapi.commons.dto.ApiResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(tags = [CategoryController.API_TAG])
@RestController
@RequestMapping(CategoryController.BASE_URL)
class CategoryController(
    private val categoryService: CategoryService
) {
    @ApiOperation(
        nickname = "getCategoryList",
        value = "Category List API",
        notes = "Category List 를 보여주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping
    fun getCategoryList(): ApiResponse<CategoryInfoListResponse> =
        ApiResponse.of(CategoryInfoListResponse(categoryService.getCategoryList()))

    @ApiOperation(
        nickname = "getUserCategoryList",
        value = "User Category List API",
        notes = "User 가 등록한 Category List 를 보여주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/user")
    fun getUserCategoryList(): ApiResponse<CategoryInfoListResponse> =
        ApiResponse.of(CategoryInfoListResponse(categoryService.getUserCategoryList()))

    @ApiOperation(
        nickname = "registerUserCategoryList",
        value = "User Category Register API",
        notes = "User 가 Category List 를 등록하 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @PostMapping("/user")
    fun registerUserCategoryList(@RequestBody request: UserCategoryListRequest): ApiResponse<CategoryInfoListResponse> =
        ApiResponse.of(CategoryInfoListResponse(categoryService.registerUserCategoryList(request)))

    companion object {
        const val API_TAG = "Category"
        const val BASE_URL = "/v1/category"
    }
}