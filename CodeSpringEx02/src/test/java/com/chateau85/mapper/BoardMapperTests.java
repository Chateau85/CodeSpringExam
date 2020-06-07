package com.chateau85.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chateau85.domain.BoardVO;
import com.chateau85.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}

	@Test
	public void testInsert() {
		BoardVO b = new BoardVO();
		b.setTitle("새로 작성하는 글");
		b.setContent("새로 작성하는 내용");
		b.setWriter("newbie");

		mapper.insert(b);
		log.info(b);
	}

	@Test
	public void testInsertSelectKey() {
		BoardVO b = new BoardVO();
		b.setTitle("새로 작성하는 글 select key");
		b.setContent("새로 작성하는 내용 select key");
		b.setWriter("newbie");

		mapper.insertSelectKey(b);
		log.info(b);
	}

	@Test
	public void testRead() {
		BoardVO b = mapper.read(5L);
		log.info(b);
	}

	@Test
	public void testDelete() {
		log.info("DELETE COUNT : " + mapper.delete(3L));
	}

	@Test
	public void testUpdate() {
		BoardVO b = new BoardVO();
		b.setBno(5L);
		b.setTitle("수정된 제목");
		b.setContent("수정된 내용");
		b.setWriter("user00");

		int count = mapper.update(b);
		log.info("UPDATE COUNT : " + count);
	}

	@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		cri.setPageNum(3);
		cri.setAmount(10);
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}

	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("새로");
		cri.setType("TC");
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
}
