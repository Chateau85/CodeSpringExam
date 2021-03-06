package com.chateau85.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chateau85.domain.Criteria;
import com.chateau85.domain.ReplyVO;

public interface ReplyMapper {
	public int insert(ReplyVO vo);

	public ReplyVO read(Long bno);

	public int delete(Long rno);

	public int update(ReplyVO reply);

	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);

	public int getCountByBno(Long bno);
}
