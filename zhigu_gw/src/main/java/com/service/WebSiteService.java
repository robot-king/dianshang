package com.service;

import java.util.List;
import com.pojo.WebSite;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface WebSiteService {
	
	// 添加
	public Map add(WebSite record,MultipartFile coverUrlFile,MultipartFile templateFile, String fileUploadPath);

	// 修改
	public Map update(WebSite record, MultipartFile coverUrlFile, MultipartFile templateFile, String fileUploadPath);

	// 所有数据
	public List<WebSite> list(WebSite record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<WebSite> listPage(int pageNum, int pageSize,WebSite record);
	
	// 查询单条数据
	public WebSite queryById(int id);
	
	// 批量修改数据
	int updateByIds(@Param("status") Integer status,@Param("ids") String[] ids);
    
    // 批量删除数据
    int deleteByIds(@Param("ids") String[] ids);
    
    // 总条数
    int getCount(WebSite record);

	public void unRarFile(String fileUploadPath);

	public void unReplace(String string);
}