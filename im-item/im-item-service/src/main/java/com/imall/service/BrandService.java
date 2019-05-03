package com.imall.service;

import java.util.List;

import com.imall.common.utils.ImallResult;
import com.imall.common.utils.PageResult;
import com.imall.pojo.TbBrand;
import com.imall.pojo.TbCategory;

public interface BrandService {
	
	PageResult<TbBrand> brandList(Integer page, Integer rows, String key, String sortBy, Boolean desc);

	int insertBrand(TbBrand brand, List<Long> cids);

	List<TbCategory> getCids(int id);

	int deleteBrandById(int id);
}
