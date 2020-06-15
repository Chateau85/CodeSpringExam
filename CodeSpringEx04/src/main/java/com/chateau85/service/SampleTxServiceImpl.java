package com.chateau85.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chateau85.mapper.Sample2Mapper;
import com.chateau85.mapper.SampleMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService {
	@Setter(onMethod_ = { @Autowired })
	private SampleMapper mapper1;

	@Setter(onMethod_ = { @Autowired })
	private Sample2Mapper mapper2;

	@Transactional
	@Override
	public void addData(String value) {
		log.info("mapper1...................");
		mapper1.insertCol1(value);

		log.info("mapper2...................");
		mapper2.insertCol2(value);

		log.info("end.......................");
	}

}
