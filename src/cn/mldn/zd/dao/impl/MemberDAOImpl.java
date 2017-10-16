package cn.mldn.zd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.mldn.zd.dao.IMemberDAO;
import cn.mldn.zd.dao.abs.AbstarctDAO;
import cn.mldn.zd.dbc.DatabaseConnection;
import cn.mldn.zd.vo.Member;

public class MemberDAOImpl extends AbstarctDAO implements IMemberDAO{
	private PreparedStatement pstmt=null;
	@Override
	public boolean doCreate(Member vo) throws SQLException {
		String sql="insert into member(mid,name,age,birthday,salary,note) values (?,?,?,?,?,?)";
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setString(1, vo.getMid());
		this.pstmt.setString(2, vo.getName());
		this.pstmt.setInt(3, vo.getAge());
		this.pstmt.setDate(4, new java.sql.Date(vo.getBirthday().getTime()));
		this.pstmt.setDouble(5, vo.getSalary());
		this.pstmt.setString(6, vo.getNote());
		return this.pstmt.executeUpdate()>0;
	}

	@Override
	public boolean doEdit(Member vo) throws SQLException {
		String sql="update member set name=?,age=?,birthday=?,salary=?,note=? where mid=?";
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setString(1, vo.getName());
		this.pstmt.setInt(2, vo.getAge());
		this.pstmt.setDate(3, new java.sql.Date(vo.getBirthday().getTime()));
		this.pstmt.setDouble(4, vo.getSalary());
		this.pstmt.setString(5, vo.getNote());
		this.pstmt.setString(6, vo.getMid());
		return this.pstmt.executeUpdate()>0;
	}

	@Override
	public boolean doRemove(String id) throws SQLException {
		String sql="delete from member where mid=?";
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setString(1, id);
		return this.pstmt.executeUpdate()>0;
	}

	@Override
	public boolean doRemove(Set<String> ids) throws SQLException {
		StringBuffer buf=new StringBuffer("delete from member where mid in (");
		Iterator<String> ite = ids.iterator();
		while(ite.hasNext()) {
			buf.append("'").append(ite.next()).append("',");
		}
		buf.delete(buf.length()-1, buf.length()).append(")");
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(buf.toString());
		return this.pstmt.executeUpdate()>0;
	}

	@Override
	public Member findById(String id) throws SQLException {
		String sql="select mid,name,age,birthday,salary,note from member where mid=?";
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setString(1, id);
		ResultSet rs = this.pstmt.executeQuery();
		Member mem=null;
		while(rs.next()) {
			mem=new Member();
			mem.setMid(rs.getString(1));
			mem.setName(rs.getString(2));
			mem.setAge(rs.getInt(3));
			mem.setBirthday(rs.getDate(4));
			mem.setSalary(rs.getDouble(5));
			mem.setNote(rs.getString(6));
		}
		return mem;
	}

	@Override
	public List<Member> findAll() throws SQLException {
		String sql="select mid,name,age,birthday,salary,note from member";
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(sql);
		ResultSet rs = this.pstmt.executeQuery();
		List<Member> all=new ArrayList<Member>();
		while(rs.next()) {
			Member mem=new Member();
			mem.setMid(rs.getString(1));
			mem.setName(rs.getString(2));
			mem.setAge(rs.getInt(3));
			mem.setBirthday(rs.getDate(4));
			mem.setSalary(rs.getDouble(5));
			mem.setNote(rs.getString(6));
			all.add(mem);
		}
		return all;
	}

	@Override
	public List<Member> findAll(Long currentPage, Integer lineSize) throws SQLException {
		List<Member> all = new ArrayList<Member>();
		String sql = "SELECT * FROM ( " + " SELECT mid,name,age,birthday,salary,note,ROWNUM rn "
				+ " FROM member WHERE ROWNUM<=?) temp WHERE temp.rn>?";
		this.pstmt = DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setLong(1, currentPage * lineSize);
		this.pstmt.setLong(2, (currentPage - 1) * lineSize);
		ResultSet rs = this.pstmt.executeQuery();
		while (rs.next()) { // 现在有数据
			Member mem = new Member(); // 实例化Member对象
			mem.setMid(rs.getString(1));
			mem.setName(rs.getString(2));
			mem.setAge(rs.getInt(3));
			mem.setBirthday(rs.getDate(4));
			mem.setSalary(rs.getDouble(5));
			mem.setNote(rs.getString(6));
			all.add(mem); // 将查询的结果追加到List集合之中
		}
		return all;
	}

	@Override
	public List<Member> findSplit(Long currentPage, Integer lineSize, String column, String keyWord)
			throws SQLException {
		String sql="selct * from (select mid,name,age,birthday,salary,note,rownum rn from member where "+column+" like ? and rownum <= ? ) "+
				" tmp where tmp.rn > ?";
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setString(1, "%"+keyWord+"%");
		this.pstmt.setLong(2, currentPage*lineSize);
		this.pstmt.setLong(3, currentPage-1*lineSize);
		ResultSet rs = this.pstmt.executeQuery();
		List<Member> all=new ArrayList<Member>();
		while(rs.next()) {
			Member mem=new Member();
			mem.setMid(rs.getString(1));
			mem.setName(rs.getString(2));
			mem.setAge(rs.getInt(3));
			mem.setBirthday(rs.getDate(4));
			mem.setSalary(rs.getDouble(5));
			mem.setNote(rs.getString(6));
			all.add(mem);
		}
		return all;
	}

	@Override
	public Long getAllCount() throws SQLException {
		String sql="selct count(*) from member";
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(sql);
		ResultSet rs = this.pstmt.executeQuery();
		while(rs.next()) {
			return rs.getLong(1);
		}
		return 0L;
	}

	@Override
	public Long getSplitCount(String column, String keyWord) throws Exception {
		String sql="selct count(*) from member where "+column+" like ?";
		this.pstmt=DatabaseConnection.getConnection().prepareStatement(sql);
		this.pstmt.setString(1, "%"+keyWord+"%");
		ResultSet rs = this.pstmt.executeQuery();
		while(rs.next()) {
			return rs.getLong(1);
		}
		return 0L;
	}

}
