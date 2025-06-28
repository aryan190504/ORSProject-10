package com.rays.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rays.common.BaseServiceImpl;
import com.rays.common.UserContext;
import com.rays.dao.ProductDAOInt;
import com.rays.dto.ProductDTO;

@Service
@Transactional
public class ProductServiceImp extends BaseServiceImpl<ProductDTO, ProductDAOInt> implements ProductServiceInt {

	@Override
	public ProductDTO findByName(String name, UserContext context) {
		ProductDTO dto = baseDao.findByUniqueKey(name, name, context);
		return dto;
	}

}
