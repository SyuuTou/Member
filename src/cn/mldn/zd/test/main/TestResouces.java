package cn.mldn.zd.test.main;

import cn.mldn.zd.util.MessageResource;

public class TestResouces {
	public static void main(String[] args) {
		MessageResource mr=new MessageResource("dao");
		System.out.println(mr.getMessage("member.dao"));
		MessageResource mr2=new MessageResource("service");
		System.out.println(mr2.getMessage("member.service"));
	}
}
