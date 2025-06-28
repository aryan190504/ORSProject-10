package com.rays.form;

import javax.validation.constraints.NotEmpty;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;
import com.rays.dto.ProductDTO;
import com.rays.dto.SubjectDTO;

public class ProductForm extends BaseForm {

	@NotEmpty(message = "please enter product name")
	private String name;

	@NotEmpty(message = "please enter description")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public BaseDTO getDto() {

		ProductDTO dto = initDTO(new ProductDTO());

		dto.setName(name);
		dto.setDescription(description);

		return dto;
	}

}
