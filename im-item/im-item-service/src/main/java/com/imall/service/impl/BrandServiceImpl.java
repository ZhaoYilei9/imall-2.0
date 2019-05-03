package com.imall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imall.common.utils.ImallResult;
import com.imall.common.utils.PageResult;
import com.imall.mapper.TbBrandMapper;
import com.imall.mapper.TbCategoryBrandMapper;
import com.imall.mapper.TbCategoryMapper;
import com.imall.pojo.TbBrand;
import com.imall.pojo.TbCategory;
import com.imall.pojo.TbCategoryBrand;
import com.imall.service.BrandService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	@Autowired
	private TbCategoryMapper categoryMapper;
	@Autowired
	private TbCategoryBrandMapper categoryBrandMapper;

	@Override
	public PageResult<TbBrand> brandList(Integer page, Integer rows, String key, String sortBy, Boolean desc) {
		// 1.分页
		PageHelper.startPage(page, rows);
		// 2.排序
		Example example = new Example(TbBrand.class);
		if (StringUtils.isNotBlank(sortBy)) {
			example.setOrderByClause(sortBy + (desc ? " desc" : " asc"));
		}
		// 3.查询
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(key)) {
			criteria.andLike("name", key + "%").orEqualTo("letter", key.toUpperCase());
		}
		List<TbBrand> brands = brandMapper.selectByExample(example);
		if (brands != null && brands.size() > 0) {
			PageInfo<TbBrand> pageInfo = new PageInfo<>(brands);
			PageResult<TbBrand> pageResult = new PageResult<>();
			pageResult.setItems(pageInfo.getList());
			pageResult.setTotal(pageInfo.getPages());
			pageResult.setPage(pageInfo.getPageNum());
			pageResult.setRecords(pageInfo.getTotal());
			return pageResult;
		}
		return null;
	}

	@Override
	@Transactional
	public int insertBrand(TbBrand brand, List<Long> cids) {
		// 1,新增品牌
		int insertCount = brandMapper.insertSelective(brand);
		log.info("brand:{}"+ brand.getId());
		// 2,新增品牌分类关联表
		if (insertCount > 0) {
			for (Long cid : cids) {
				this.brandMapper.insertCategoryBrand(cid, brand.getId());
			}
		}

		return insertCount;
	}

	@Override
	public List<TbCategory> getCids(int id) {
		// TODO Auto-generated method stub
		List<TbCategory> categories = new ArrayList<TbCategory>();
		Example example = new Example(TbCategoryBrand.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("brandId", id);
		List<TbCategoryBrand> categoryBrands = categoryBrandMapper.selectByExample(example);
		
		if (categoryBrands != null && categoryBrands.size() > 0) {
			for (TbCategoryBrand tbCategoryBrand : categoryBrands) {
				log.info("tbCategoryBrand: {}",tbCategoryBrand);
				TbCategory category = categoryMapper.selectByPrimaryKey(tbCategoryBrand.getCategoryId());
				categories.add(category);
			}
		}	
		return categories;
		
	}

	@Override
	public int deleteBrandById(int id) {
		// TODO Auto-generated method stub
		int deleteCount = brandMapper.deleteByPrimaryKey(id);
		return deleteCount;
	}
	

}
