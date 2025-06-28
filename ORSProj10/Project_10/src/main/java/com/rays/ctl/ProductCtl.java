package com.rays.ctl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.dto.ProductDTO;
import com.rays.form.ProductForm;
import com.rays.service.ProductServiceInt;

@RestController
@RequestMapping(value = "product")
public class ProductCtl extends BaseCtl<ProductForm, ProductDTO, ProductServiceInt> {

}
