package cn.com.businessservicesplatform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.businessservicesplatform.common.util.BasePage;
import cn.com.businessservicesplatform.common.util.DateUtils;
import cn.com.businessservicesplatform.dao.mysql.BaseUserCompanyMapper;
import cn.com.businessservicesplatform.dao.mysql.BaseUserCompanyPicMapper;
import cn.com.businessservicesplatform.model.mysql.BaseUserCompany;
import cn.com.businessservicesplatform.model.mysql.BaseUserCompanyPic;
import cn.com.businessservicesplatform.model.vo.BaseUserCompanyVo;
import cn.com.businessservicesplatform.service.BaseUserCompanyService;

/**
 * Created by John on 2016/12/2.
 */
@Service
public class BaseUserCompanyServiceImpl implements BaseUserCompanyService {
	
	public static final Logger log = LoggerFactory.getLogger(BaseUserCompanyService.class);
	
	@Autowired
	BaseUserCompanyMapper baseUserCompanyMapper;
	@Autowired
	BaseUserCompanyPicMapper baseUserCompanyPicMapper;
	
    @Override
    public int insert(BaseUserCompanyVo vo) {
    	int result = 0;
    	if(null != vo && vo.getUserId() != null){
    		BaseUserCompany baseUserCompany = getUserCompany(new BaseUserCompanyVo(vo.getUserId()));
    		if(null != baseUserCompany && baseUserCompany.getId() != null){
    			vo.setId(baseUserCompany.getId());
    			baseUserCompany = baseUserCompany.make(baseUserCompany, vo);
    			try {
					if(!StringUtils.isBlank(vo.getCompanyRegisterTimeStr())){
						baseUserCompany.setCompanyRegisterTime(DateUtils.parseDate(vo.getCompanyRegisterTimeStr(), DateUtils.DEF_DATE_NO_TIME_FORMAT));
					}
				} catch (Exception e) {
					log.error("BaseUserCompanyService.insert.is.registerTime.error:"+vo.getCompanyRegisterTimeStr(),e);
				}
    			result = baseUserCompanyMapper.updateByPrimaryKey(baseUserCompany);
    			if(result > 0){
    				//保存公司图片
    				saveCompanyPic(vo);
    			}
    		}else{
    			baseUserCompany = new BaseUserCompany(vo);
    			baseUserCompany.setStatus(0);
    			baseUserCompany.setCreateTime(new Date());
    			baseUserCompany.setModifyTime(new Date());
    			try {
					if(!StringUtils.isBlank(vo.getCompanyRegisterTimeStr())){
						baseUserCompany.setCompanyRegisterTime(DateUtils.parseDate(vo.getCompanyRegisterTimeStr(), DateUtils.DEF_DATE_NO_TIME_FORMAT));
					}
				} catch (Exception e) {
					log.error("BaseUserCompanyService.insert.is.registerTime.error:"+vo.getCompanyRegisterTimeStr(),e);
				}
    			result = baseUserCompanyMapper.insert(baseUserCompany);
    			if(result > 0){
    				vo.setId(baseUserCompany.getId());
    				//保存公司图片
    				saveCompanyPic(vo);
    			}
    		}
    	}
        return result;
    }
    
    public void saveCompanyPic(BaseUserCompanyVo vo){
    	if(null != vo && vo.getId() != null && !StringUtils.isBlank(vo.getPicListStr())){
    		//全部删除
    		baseUserCompanyPicMapper.deleteAllPic(vo.getId());
    		if(vo.getPicListStr().indexOf("|")>0){
    			String [] picUrls = vo.getPicListStr().split("\\|");
    			if(null != picUrls && picUrls.length > 0){
    				for(int i=0;i<picUrls.length;i++){
    					if(!StringUtils.isBlank(picUrls[i])){
    						baseUserCompanyPicMapper.insert(new BaseUserCompanyPic(vo.getId(),picUrls[i]));
    					}
    				}
    			}
    		}else{
    			baseUserCompanyPicMapper.insert(new BaseUserCompanyPic(vo.getId(),vo.getPicListStr()));
    		}
    	}
    	
    }

	@Override
	public BaseUserCompanyVo getBaseUserAllCompany(Integer userId) {
		BaseUserCompanyVo result = null;
		if(null != userId){
			BaseUserCompany baseUserCompany = getUserCompany(new BaseUserCompanyVo(userId));
			if(null != baseUserCompany && baseUserCompany.getId() != null){
				result = new BaseUserCompanyVo(baseUserCompany);
				List<BaseUserCompanyPic> picList = baseUserCompanyPicMapper.queryList(baseUserCompany.getId());
				if(null != picList && picList.size() > 0){
					result.setPicList(picList);
					StringBuffer picListStr = new StringBuffer();
					for(BaseUserCompanyPic pic : picList){
						if(null != pic && !StringUtils.isBlank(pic.getCompanyPicUrl())){
							if(!StringUtils.isBlank(picListStr.toString())){
								picListStr.append("|");
							}
							picListStr.append(pic.getCompanyPicUrl());
						}
					}
					result.setPicListStr(picListStr.toString());
				}
				try {
					if(null != baseUserCompany.getCompanyRegisterTime()){
						result.setCompanyRegisterTimeStr(DateUtils.getString(baseUserCompany.getCompanyRegisterTime(), DateUtils.DEF_DATE_NO_TIME_FORMAT));
					}
				} catch (Exception e) {
					log.error("BaseUserCompanyService.getBaseUserAllCompany.is.registerTime.error:"+baseUserCompany.getCompanyRegisterTime(),e);
				}
			}
		}
		return result;
	}
	@Override
	public BaseUserCompanyVo getUserAllCompany(Integer id){
		BaseUserCompanyVo result = null;
		if(null != id){
			BaseUserCompany baseUserCompany = baseUserCompanyMapper.selectByPrimaryKey(id);
			if(null != baseUserCompany && baseUserCompany.getId() != null){
				result = new BaseUserCompanyVo(baseUserCompany);
				result = makeBaseUser(result);
			}
		}
		return result;
	}
	
	private BaseUserCompanyVo makeBaseUser(BaseUserCompanyVo vo){
		if(null != vo && null != vo.getId()){
			List<BaseUserCompanyPic> picList = baseUserCompanyPicMapper.queryList(vo.getId());
			if(null != picList && picList.size() > 0){
				vo.setPicList(picList);
				StringBuffer picListStr = new StringBuffer();
				for(BaseUserCompanyPic pic : picList){
					if(null != pic && !StringUtils.isBlank(pic.getCompanyPicUrl())){
						if(!StringUtils.isBlank(picListStr.toString())){
							picListStr.append("|");
						}
						picListStr.append(pic.getCompanyPicUrl());
					}
				}
				vo.setPicListStr(picListStr.toString());
			}
			try {
				if(null != vo.getCompanyRegisterTime()){
					vo.setCompanyRegisterTimeStr(DateUtils.getString(vo.getCompanyRegisterTime(), DateUtils.DEF_DATE_NO_TIME_FORMAT));
				}
			} catch (Exception e) {
				log.error("BaseUserCompanyService.makeBaseUser.is.registerTime.error:"+vo.getCompanyRegisterTime(),e);
			}
		}
		return vo;
	}

	/**
	 * 获取所有的企业
	 *
	 * @param baseUserCompanyVo
	 * @return
	 */
	@Override
	public List<BaseUserCompanyVo> getAllUserCompanys(BaseUserCompanyVo baseUserCompanyVo) {
		List<BaseUserCompany> comLst = baseUserCompanyMapper.getAllUserCompanys(baseUserCompanyVo);
		List<BaseUserCompanyVo> comVoLst = new ArrayList<BaseUserCompanyVo>();

		for (BaseUserCompany com:comLst) {
			BaseUserCompanyVo comVo = new BaseUserCompanyVo(com);
			comVoLst.add(comVo);
		}

		return comVoLst;
	}

	@Override
	public BaseUserCompany getUserCompany(BaseUserCompanyVo baseUserCompanyVo) {
		return baseUserCompanyMapper.getUserCompany(baseUserCompanyVo);
	}
	
	@Override
	public List<BaseUserCompanyVo> queryPage(BasePage basePage,BaseUserCompanyVo vo){
		List<BaseUserCompanyVo> list = baseUserCompanyMapper.queryPage(basePage, vo);
		if(null != list && list.size() > 0){
			for(BaseUserCompanyVo baseUserCompanyVo :list){
				if(null != baseUserCompanyVo && baseUserCompanyVo.getId() != null){
					baseUserCompanyVo = makeBaseUser(baseUserCompanyVo);
				}
			}
		}
		return list;
	}
	@Override
	public List<BaseUserCompanyVo> queryList(BaseUserCompanyVo vo){
		List<BaseUserCompanyVo> list = baseUserCompanyMapper.queryList(vo);
		if(null != list && list.size() > 0){
			for(BaseUserCompanyVo baseUserCompanyVo :list){
				if(null != baseUserCompanyVo && baseUserCompanyVo.getId() != null){
					baseUserCompanyVo = makeBaseUser(baseUserCompanyVo);
				}
			}
		}
		return list;
	}
}
