package cn.mldn.zd.test.junit;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;

import cn.mldn.zd.fatory.Factory;
import cn.mldn.zd.service.IMemberService;
import cn.mldn.zd.vo.Member;
import junit.framework.TestCase;


@FixMethodOrder
public class IMemberServiceTest {

	@Test
	public void test1Add() throws Exception {
		IMemberService service=Factory.getInstance("member.service");
		System.out.println(service.getClass());
		Member vo=new Member();
		vo.setMid("denis2");
		vo.setName("denis");
		vo.setAge(312);
		vo.setBirthday(new Date());
		vo.setSalary(12.32);
		vo.setNote("i am denis");
		TestCase.assertTrue(service.add(vo));
	}

	@Test
	public void test5Edit() throws Exception {
		IMemberService service=Factory.getInstance("member.service");
		Member vo=new Member();
		vo.setMid("denis2");
		vo.setName("denis2222");
		vo.setAge(312);
		vo.setBirthday(new Date());
		vo.setSalary(12.32);
		vo.setNote("i am denis");
		
		TestCase.assertTrue(service.edit(vo));
	}

	@Test
	public void test6DeleteString() throws Exception {
		IMemberService service=Factory.getInstance("member.service");
		
		TestCase.assertTrue(service.delete("denis2"));
	}

	@Test
	public void test7DeleteSetOfString() throws Exception {
		IMemberService service=Factory.getInstance("member.service");
		Set<String> ids=new HashSet<String>();
		ids.add("denis");
		ids.add("denis2");
		TestCase.assertTrue(service.delete(ids));
	}

	@Test
	public void test2Get() throws Exception {
//		IMemberService service=Factory.getInstance("member.service");
//		Member mem=service.get("denis");
////		System.out.println(mem);//此处注释掉测试可以正常执行完成，但是一旦加上这句代码就会报错，错误信息给您图片发过去，还请参考
//		TestCase.assertNotNull(service.get("denis"));
		
		IMemberService memberService = Factory.getInstance("member.service") ;
		System.out.println(memberService.get("a"));
		//System.out.println(memberService.get("a"));
		TestCase.assertNotNull(memberService.get("a"));
	}

	@Test
	public void test3List() throws Exception {
		IMemberService service=Factory.getInstance("member.service");
		
		List<Member> all=service.list();
		Iterator<Member> ite = all.iterator();
		while(ite.hasNext()) {
			Member mem=ite.next();
			System.out.println(mem);
		}
		TestCase.assertTrue(all.size()>0);
	}

	@Test
	public void test4ListLongIntegerStringString() throws Exception {
		IMemberService service=Factory.getInstance("member.service");
		
//		Map<String,Object> map=service.list(1L,5,null,null);
//		List<Member> all = (List<Member>) map.get("allMembers");
//		Long memberCount = (Long) map.get("memberCount");
//		
//		System.out.println(memberCount+":");
//		Iterator<Member> ite = all.iterator();
//		while(ite.hasNext()) {
//			System.out.println(ite.next());
//		}
//		TestCase.assertTrue(all.size()>0);
		
		
		Map<String, Object> map = service.list(1L, 10, null, null);
		System.out.println(map);
		Long count = (Long) map.get("memberCount");
		List<Member> all = (List<Member>) map.get("allMembers");
		TestCase.assertTrue(count > 0 && all.size() > 0);
		

	}

}
