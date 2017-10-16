package cn.mldn.zd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mldn.zd.dao.IMemberDAO;
import cn.mldn.zd.fatory.Factory;
import cn.mldn.zd.service.IMemberService;
import cn.mldn.zd.vo.Member;

public class MemberServiceImpl implements IMemberService {
	private IMemberDAO memdao=Factory.getInstance("member.dao");
	@Override
	public boolean add(Member vo) throws Exception {
		if(vo!=null && memdao.findById(vo.getMid())==null) {
			return memdao.doCreate(vo);
		}
		return false;
	}

	@Override
	public boolean edit(Member vo) throws Exception {
		if(vo!=null && memdao.findById(vo.getMid())!=null) {
			return memdao.doEdit(vo);
		}
		return false;
	}

	@Override
	public boolean delete(String id) throws Exception {
		if(memdao.findById(id)!=null) {
			return memdao.doRemove(id);
		}
		return true;
	}

	@Override
	public boolean delete(Set<String> ids) throws Exception {
		if(ids==null||ids.size()==0) {
			return memdao.doRemove(ids);
		}
		return true;
	}

	@Override
	public Member get(String id) throws Exception {
		return this.memdao.findById(id);
	}

	@Override
	public List<Member> list() throws Exception {
		return this.memdao.findAll();
	}

	@Override
	public Map<String, Object> list(Long currentPage, Integer lineSize, String column, String keyWord)
			throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		if(column==null||"".equals(column)||keyWord==null||"".equals(keyWord)) {
			map.put("allMembers",this.memdao.findAll(currentPage, lineSize));
			map.put("memberCount", this.memdao.getAllCount());
		}else {
			map.put("allMembers",this.memdao.findSplit(currentPage, lineSize, column, keyWord));
			map.put("memberCount", this.memdao.getSplitCount(column, keyWord));
		}
		return map;
	}
	
}
