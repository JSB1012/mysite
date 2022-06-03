package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;

public class BoardRepository {

	public List<BoardVo> findAll(PageVo page, String kwd) {
		List<BoardVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = getConnection();

			String sql = "select a.no, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y-%m-%d %H:%i'), a.g_no, a.o_no, a.depth, a.user_no, b.name"
					+ " from board a, user b where a.user_no = b.no "
					+ " and a.title like ?"
					+ " order by a.g_no desc, "
					+ " a.o_no asc, a.depth asc"
					+ "  limit ?, 5";

			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setInt(2, page.getNum()-1);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);
				// boolean delete_check = rs.getBoolean("delete_check");

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = " insert into board" + " select null, ?, ?, 0, now(), ifnull(max(g_no)+1, 1),0,0,? from board";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public BoardVo findByNo(int sno) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		BoardVo vo = null;

		try {
			connection = getConnection();

			String sql = "select no,title,contents,hit,reg_date,g_no,o_no,depth,user_no" + " from board"
					+ " where no=?";

			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, sno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userNo = rs.getLong(9);
				// boolean delete_check = rs.getBoolean("delete_check");

				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);

			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vo;
	}

	public BoardVo countHit(int sno) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		BoardVo vo = null;

		try {
			conn = getConnection();

			String sql = "UPDATE board SET hit = hit + 1 WHERE no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, sno);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}

	public void delete(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "delete from board where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set title = ?, contents = ? where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public BoardVo boardInfo(int no) {
		BoardVo boardVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select no, g_no, o_no, depth from board where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				Long no2 = rs.getLong(1);
				Long g_no = rs.getLong(2);
				Long o_no = rs.getLong(3);
				Long depth = rs.getLong(4);

				boardVo = new BoardVo();

				boardVo.setNo(no2);
				boardVo.setGroupNo(g_no);
				boardVo.setOrderNo(o_no);
				boardVo.setDepth(depth);

			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return boardVo;
	}

	public boolean replyUpdate(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set o_no = o_no + 1 where g_no = ? and o_no > ?";

			pstmt = conn.prepareStatement(sql);

			// System.out.println(vo);

			pstmt.setLong(1, vo.getGroupNo());
			pstmt.setLong(2, vo.getOrderNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;

	}

	public boolean replyInsert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConnection();

			String sql = "INSERT INTO board VALUES (null, ?, ?, 0, now(), ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getGroupNo());
			pstmt.setLong(4, vo.getOrderNo() + 1);
			pstmt.setLong(5, vo.getDepth() + 1);
			pstmt.setLong(6, vo.getUserNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	public int getCount() {
		int rowCount = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection();

			String sql = "select * from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			rs.last();

			rowCount = rs.getRow();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowCount;
	}
	
	public int getCountSearch(String kwd) {
		int rowCount = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection();

			String sql = "select * from board where title like ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + kwd + "%");
			rs = pstmt.executeQuery();
			rs.last();

			rowCount = rs.getRow();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowCount;
	}

	private Connection getConnection() throws SQLException {
		Connection connection = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://192.168.10.43:3306/webdb?charset=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return connection;
	}
}
