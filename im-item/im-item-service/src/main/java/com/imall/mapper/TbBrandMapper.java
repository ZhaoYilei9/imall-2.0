package com.imall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.imall.pojo.TbBrand;
import com.imall.pojo.TbCategoryBrand;

import tk.mybatis.mapper.common.Mapper;

public interface TbBrandMapper extends Mapper<TbBrand> {

	@Insert("insert into tb_category_brand (category_id,brand_id) values (#{cid},#{bid})")
	int insertCategoryBrand(@Param("cid") Long cid,@Param("bid") Long bid);


}