package com.imall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imall.common.utils.ImallResult;
import com.imall.common.utils.PageResult;
import com.imall.pojo.TbBrand;
import com.imall.pojo.TbCategory;
import com.imall.service.BrandService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("brand")
public class BrandController {

	@Autowired
	private BrandService brandService;

	/**
	 * 	key: 
		page: 1
		rows: 5
		sortBy: id
		desc: 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public PageResult<TbBrand> brandList  (@RequestParam("page") Integer page, 
									@RequestParam("rows") Integer rows,
									@RequestParam("sortBy") String sortBy,
									@RequestParam("desc") Boolean desc,
									@RequestParam("key") String key) {
		PageResult<TbBrand> brandList = brandService.brandList(page,rows,key,sortBy,desc);
		return brandList;
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Void> addBrand(TbBrand brand, @RequestParam("cids") List<Long> cids) {
		log.info("brand: {}" , brand);
		int count = brandService.insertBrand(brand,cids);
		if (count > 0) {
			return ResponseEntity.ok().build();
		}
		return null;
	}
	@GetMapping("/bid/{id}")
	@ResponseBody
	public  ImallResult updateBrand(@PathVariable("id") int id) {
		log.info("update:bid: {}", id);
		List<TbCategory> categories = brandService.getCids(id);
		log.info("category-size:{}",categories.size());
		if (categories != null && categories.size() > 0) {
			return ImallResult.ok(categories);
			
		}
		return null;
	}
	@DeleteMapping("/bid/{id}")
	@ResponseBody
	public ImallResult deleteBrand(@PathVariable("id") int id) {
		log.info("delete:id : {}", id);
		int deleteCount = brandService.deleteBrandById(id);
		if (deleteCount > 0) {
			return ImallResult.ok();
		}
		return null;
	}
	
}
