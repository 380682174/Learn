package com.fish.learn.demo.utils;
import com.data.util.MapToObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBUtil {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConn(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3309/dbname?useUnicode=true&characterEncoding=utf8", "root", "123456");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return conn;
	}

	/**
	 * 关闭连接
	 * @param pstmt
	 * @param conn
	 * @param rs
	 */
	public static void closeAll(PreparedStatement pstmt, Connection conn,
								ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行单条增，删，改sql语句
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public int doUpdate(String sql, List<Object> params) throws SQLException,
			IOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			// 设置参数
			setParams(pstmt, params);
			result = pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, conn, null);
		}
		return result;
	}

	/**
	 * 插入多条信息
	 * @param sqls
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int doUpdate(List<String> sqls, List<List<Object>> params)
			throws Exception {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConn();
			conn.setAutoCommit(false);
			if (sqls != null && sqls.size() > 0) {
				for (int i = 0; i < sqls.size(); i++) {
					pstmt = conn.prepareStatement(sqls.get(i));
					if(params!=null && params.size()>0){
						setParams(pstmt, params.get(i));
					}
					result += pstmt.executeUpdate();
				}
			}
			conn.commit();// 提交事务
		} catch (Exception e) {
			conn.rollback();// 回滚事务
			result=0;
			throw e;
		} finally {
			conn.setAutoCommit(true);
			closeAll(pstmt, conn, null);
		}
		return result;
	}

	/**
	 * 查询：返回一个map对象，只能查看单个对象，如果查看多个对象，则不能用这个方法
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Map<String, String> findSingleObject(String sql, List<Object> params)
			throws SQLException, IOException {
		Map<String, String> map = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs = pstmt.executeQuery();
			List<String> columnNames = getAllColumnNames(rs);
			if (rs.next()) {
				map = new HashMap<String, String>();
				for (String cn : columnNames) {
					map.put(cn, rs.getString(cn));
				}
			}
		} finally {
			closeAll(pstmt, conn, rs);
		}
		return map;
	}

	public <T> T findSingleObject(String sql,List<Object> params,Class<T> c){
		T t = null;
		try {
			Map<String,String> map = findSingleObject(sql, params);
			if(map==null){
				return null;
			}
			t = MapToObject.convert(map, c);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return t;
	}

	/**
	 * 查询出所有对象
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Map<String, String>> findAllObject(String sql,
												   List<Object> params) throws SQLException, IOException {
		Connection conn = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			if (params != null && params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					pstmt.setString(i + 1, params.get(i).toString());
				}
			}
			rs = pstmt.executeQuery();
			List<String> columnNames = getAllColumnNames(rs);
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (String cn : columnNames) {
					map.put(cn, rs.getString(cn));
				}
				list.add(map);
			}
		} finally {
			closeAll(pstmt, conn, rs);
		}
		return list;

	}

	public <T> List<T> findAllObject(String sql, List<Object> params , Class<T> c){

		List<T> list = new ArrayList<>();
		try {
			List<Map<String,String>> listmap = findAllObject(sql, params);
			if(listmap!=null && listmap.size()>0){
				for(Map<String,String> m:listmap){
					T t = MapToObject.convert(m, c);
					list.add(t);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return list;
	}

	/**
	 * 设置参数到预处理对象中
	 * @param pstmt
	 * @param params
	 * @throws SQLException
	 */
	private static void setParams(PreparedStatement pstmt, List<Object> params)
			throws SQLException {
		if (null != params && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setString(i + 1, params.get(i).toString());
			}
		}
	}

	/**
	 * 获取结果集中所有列名存在一个list集合中：技术点 jdbc2.0取元数据
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<String> getAllColumnNames(ResultSet rs) throws SQLException {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
			list.add(rs.getMetaData().getColumnLabel(i + 1));
		}
		return list;

	}

	/**
	 * 聚合函数查询：例如 select count(*) from testjava;
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public double getCount(String sql, List<Object> params)
			throws SQLException, IOException {
		double result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getDouble(1);
			}
		} finally {
			closeAll(pstmt, conn, rs);
		}
		return result;
	}

	public int insertWithKey(String sql, List<Object> params)
			throws SQLException {
		Connection conn = getConn();
		PreparedStatement pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		setParams(pstmt, params);
		pstmt.executeUpdate();
		int autoIncKeyFromApi=-1;
		//得到主键
		ResultSet rs=pstmt.getGeneratedKeys();
		if(rs.next()){
			autoIncKeyFromApi=rs.getInt(1);
		}else{
			throw new RuntimeException("无法插入新数据，序列号无法生成");
		}
		closeAll(pstmt, conn, rs);
		return autoIncKeyFromApi;
	}

	public void insert(String sql, List<Object> params)
			throws SQLException {
		Connection conn = getConn();
		PreparedStatement pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		setParams(pstmt, params);
		pstmt.executeUpdate();
		int autoIncKeyFromApi=-1;
		closeAll(pstmt, conn, null);
	}
		
}
