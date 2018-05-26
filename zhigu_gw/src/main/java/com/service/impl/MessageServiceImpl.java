package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.MessageMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.Message;
import com.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageMapper messageMapper;

	@Override
	public int add(Message record) {
		if (record.getType().equals(Message.TYPE_SIXIN)) {
			String no = "";
			if (record.getSendId().intValue() < record.getReceivedId().intValue()) {
				no = record.getSendId() + "" + record.getReceivedId();
			} else {
				no = record.getReceivedId() + "" + record.getSendId();
			}
			record.setDialogNo(no);
		}
		return messageMapper.insertSelective(record);
	}

	@Override
	public int update(Message record) {
		return messageMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Message> list(Message record) {
		return messageMapper.queryList(record);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return messageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Message> listPage(int pageNum, int pageSize, Message record) {
		PageHelper.startPage(pageNum, pageSize);
		return messageMapper.queryList(record);
	}

	@Override
	public List<Message> queryListByDialog(int pageNum, int pageSize, Message record) {
		PageHelper.startPage(pageNum, pageSize);
		return messageMapper.queryListByDialog(record);
	}

	@Override
	public Message queryById(int id) {
		return messageMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByIds(Integer status, String[] ids) {
		return messageMapper.updateByIds(status, ids);
	}

	@Override
	public int deleteByIds(String[] ids) {
		return messageMapper.deleteByIds(ids);
	}
}
