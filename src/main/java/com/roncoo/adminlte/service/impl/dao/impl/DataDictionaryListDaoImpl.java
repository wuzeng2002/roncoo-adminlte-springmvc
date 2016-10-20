package com.roncoo.adminlte.service.impl.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.roncoo.adminlte.bean.entity.RcDataDictionaryList;
import com.roncoo.adminlte.bean.entity.RcDataDictionaryListExample;
import com.roncoo.adminlte.bean.entity.RcDataDictionaryListExample.Criteria;
import com.roncoo.adminlte.service.impl.dao.DataDictionaryListDao;
import com.roncoo.adminlte.service.impl.dao.impl.mybatis.RcDataDictionaryListMapper;
import com.roncoo.adminlte.util.base.Page;
import com.roncoo.adminlte.util.base.SqlUtil;

/**
 * 
 * 作用功能：数据字典明细接口实现类 作者： LYQ 时间：2016年10月18日
 */
@Repository
public class DataDictionaryListDaoImpl implements DataDictionaryListDao {

	@Autowired
	private RcDataDictionaryListMapper mapper;

	@Override
	public int countByExample(RcDataDictionaryListExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int delete(RcDataDictionaryList dList) {
		RcDataDictionaryListExample example = new RcDataDictionaryListExample();
		Criteria criteria = example.createCriteria();
		criteria.andFieldCodeEqualTo(dList.getFieldCode());
		return mapper.deleteByExample(example);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public int save(RcDataDictionaryList dList) {
		Date date = new Date();
		dList.setCreateTime(date);
		dList.setUpdateTime(date);
		return mapper.insertSelective(dList);
	}

	@Override
	public Page<RcDataDictionaryList> listForPage(RcDataDictionaryList dList, int pageCurrent, int pageSize) {
		RcDataDictionaryListExample example = new RcDataDictionaryListExample();
		example.setOrderByClause("sort desc");
		int totalCount = mapper.countByExample(example);
		pageSize = SqlUtil.checkPageSize(pageSize);
		pageCurrent = SqlUtil.checkPageCurrent(totalCount, pageSize, pageCurrent);
		int totalPage = SqlUtil.countTotalPage(totalCount, pageSize);
		example.setLimitStart(SqlUtil.countOffset(pageCurrent, pageSize));
		example.setPageSize(pageSize);
		Criteria criteria = example.createCriteria();
		criteria.andFieldCodeEqualTo(dList.getFieldCode());
		List<RcDataDictionaryList> list = mapper.selectByExample(example);
		Page<RcDataDictionaryList> page = new Page<>(totalCount, totalPage, pageCurrent, pageSize, list);
		return page;
	}

	@Override
	public RcDataDictionaryList selectById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(RcDataDictionaryList record, RcDataDictionaryListExample example) {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public List<RcDataDictionaryList> listForFieldCode(String fieldCode) {
		RcDataDictionaryListExample example = new RcDataDictionaryListExample();
		example.setOrderByClause("sort desc");
		Criteria criteria = example.createCriteria();
		criteria.andFieldCodeEqualTo(fieldCode);
		return mapper.selectByExample(example);
	}

}
