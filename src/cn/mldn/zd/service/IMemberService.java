package cn.mldn.zd.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mldn.zd.vo.Member;

public interface IMemberService {
	public boolean add(Member vo) throws Exception;
	
	public boolean edit(Member vo) throws Exception;
	
	public boolean delete(String id) throws Exception;
	
	public boolean delete(Set<String> ids) throws Exception;
	
	public Member get(String id) throws Exception;
	
	public List<Member> list() throws Exception;
	
	public Map<String,Object> list(Long currentPage,Integer lineSize,String column,String keyWord) throws Exception;
}
