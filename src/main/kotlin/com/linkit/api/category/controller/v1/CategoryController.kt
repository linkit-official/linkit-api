package com.linkit.api.category.controller.v1

import com.linkit.api.category.dto.CategoryInfoListResponse
import com.linkit.api.category.dto.UserCategoryListRequest
import com.linkit.api.category.service.CategoryService
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/category")
class CategoryController(
    private val categoryService: CategoryService
) {
    @ApiOperation(
        value = "Category List API",
        notes = "Category List 를 보여주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping
    fun getCategoryList(): CategoryInfoListResponse = CategoryInfoListResponse(categoryService.getCategoryList())

    @ApiOperation(
        value = "User Category List API",
        notes = "User 가 등록한 Category List 를 보여주는 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/user")
    fun getUserCategoryList(): CategoryInfoListResponse = CategoryInfoListResponse(categoryService.getCategoryList())

    @ApiOperation(
        value = "User Category Register API",
        notes = "User 가 Category List 를 등록하 API"
    )
    @PreAuthorize("hasRole('BASIC')")
    @PostMapping("/user")
    fun registerUserCategoryList(request: UserCategoryListRequest): CategoryInfoListResponse =
        CategoryInfoListResponse(categoryService.registerUserCategoryList(request))
}