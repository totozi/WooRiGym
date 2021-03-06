package woorigym.search.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import woorigym.common.jdbcTemplate;
import woorigym.product.model.vo.ProductTable;

public class SearchListDao {
	// 2021.10.23 μΆκ°μμ
	public int getProductPageListCount(Connection conn, ProductTable searchKeyVo) {
		int result = 0;
		String sql = "select count(*) from product p "; // 

		String parentCategory = searchKeyVo.getParentCategory();
		String childCategory = searchKeyVo.getChildCategory();	
		boolean flag = false;
		
		if (parentCategory != null && !parentCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.parent_category like '%" + parentCategory + "%'";
		}
		if (childCategory != null && !childCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.child_category like '%" + childCategory + "%'";
		}
		
		System.out.println("getProductPageListCount sql:"+sql);
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rset);
			jdbcTemplate.close(pstmt);
		}
		return result;
	}
	
	public ArrayList<ProductTable> productPageList(Connection conn, ProductTable searchKeyVo, int start, int end) {
		System.out.println("ProductpageList 1");
		ArrayList<ProductTable> productlist = null;
		String sql = "select *"
				+ " from (select rownum r, t1.*"
				+ "      from(select p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
				+ "           from product p";

		String parentCategory = searchKeyVo.getParentCategory();
		String childCategory = searchKeyVo.getChildCategory();	
		boolean flag = false;
		
		if (parentCategory != null && !parentCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.parent_category like '%" + parentCategory + "%'";
		}
		if (childCategory != null && !childCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.child_category like '%" + childCategory + "%'";
		}
		sql += " )t1) t2 where r between ? and ?";
		
		System.out.println("sql:"+sql);
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			System.out.println("ProductpageList executeQuery 1");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			System.out.println("ProductpageList executeQuery 2");
			if (rset.next()) {
				System.out.println("ProductpageList executeQuery over 1");
				productlist = new ArrayList<ProductTable>();
				do {
					ProductTable vo = new ProductTable();
					vo.setProductInfoUrl(rset.getString("product_info_url"));
					vo.setProductName(rset.getString("product_name"));
					vo.setProductOption(rset.getString("product_option"));
					vo.setPrice(rset.getInt("price"));
					productlist.add(vo);			
					System.out.println("ProductpageList executeQuery over 2");
				} while (rset.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rset);
			jdbcTemplate.close(pstmt);
		}
		System.out.println("ProductpageList 2" + productlist);
		return productlist;
	}
	// 2021.10.23 μΆκ°μλ£
	
	// 2021.10.12 1μ°¨ μΆκ°μμ
	public ProductTable getProductNo(Connection conn, String productNo) {
		System.out.println("getProductNo 1");
		ProductTable vo = null;
		String sql = "select productNo"
				+ " from product where productNo = ?";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			System.out.println("getProductNo executeQuery 1");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productNo);
			rset = pstmt.executeQuery();
			System.out.println("getProductNo executeQuery 2");
			if (rset.next()) {
				vo = new ProductTable();
				vo.setProductNo(rset.getString("product_No"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rset);
			jdbcTemplate.close(pstmt);
		}
		return vo;
	}
	
	// 2021.10.15 1μ°¨ λ΄μ©μμ  μμ
	public ArrayList<ProductTable> searchProductList(Connection conn, ProductTable searchKeyVo, int start, int end) {
		System.out.println("searchProductList 1");
		ArrayList<ProductTable> productlist = null;
		String sql = "select *"
				+ " from (select rownum r, t1.*"
				+ "      from(select p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
				+ "           from product p";
//				+ "           inner join order_detail o on p.product_no = o.product_no"
//				+ "           inner join review r on o.order_detail_no = r.order_detail_no"
//				+ "           where  p.product_name like '%?%'"
//				+ "           and  p.parent_category like '%?%'"
//				+ "           and  p.price between ? and ?"
//				+ "           group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
//				+ "           order by count(o.buy_quantity) desc" // μ‘°κ±΄μ λ°λΌ λ°λμ΄μΌν¨
//				+ "           )"
//				+ "      t1) t2"
//				+ " where r between ? and ?";

		String productName = searchKeyVo.getProductName();
		String parentCategory = searchKeyVo.getParentCategory();
		String selectRank = searchKeyVo.getSelectRank();
		String childCategory = searchKeyVo.getChildCategory();	
		int minPrice = searchKeyVo.getMinPrice();
		int maxPrice = searchKeyVo.getMaxPrice();
		boolean flag = false;
		boolean flag1 = false;
		
		if (selectRank != null && !selectRank.equals("")) {
			if (!flag1) {
				sql += " inner join order_detail o on p.product_no = o.product_no ";
				sql += " inner join review r on o.order_detail_no = r.order_detail_no ";
				flag1 = true;
			}
		}
		if (productName != null && !productName.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.product_name like '%" + productName + "%'";
		}
		if (parentCategory != null && !parentCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.parent_category like '%" + parentCategory + "%'";
		}
		if (childCategory != null && !childCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.child_category like '%" + childCategory + "%'";
		}
		if (minPrice >= 0 || maxPrice >= 0) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.price between " + minPrice + " and " + maxPrice;
		}
		if (selectRank != null && selectRank.equals("μΈκΈ°μ")) {
				sql += " group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
						+ " order by count(o.buy_quantity) desc";
		}
		else if (selectRank != null && selectRank.equals("νμ μ")) {
				sql += " group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
						+ " order by sum(r.score) desc";
		}
		sql += " )t1) t2 where r between ? and ?";
		
		System.out.println("sql:"+sql);
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			System.out.println("searchProductList executeQuery 1");
			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, productName);
//			pstmt.setString(2, parentCategory);
//			pstmt.setInt(3, minprice);
//			pstmt.setInt(4, maxprice);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			System.out.println("searchProductList executeQuery 2");
			if (rset.next()) {
				System.out.println("searchProductList executeQuery over 1");
				productlist = new ArrayList<ProductTable>();
				do {
					ProductTable vo = new ProductTable();
					vo.setProductInfoUrl(rset.getString("product_info_url"));
					vo.setProductName(rset.getString("product_name"));
					vo.setProductOption(rset.getString("product_option"));
					vo.setPrice(rset.getInt("price"));
					productlist.add(vo);			
					System.out.println("searchProductList executeQuery over 2");
				} while (rset.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rset);
			jdbcTemplate.close(pstmt);
		}
		System.out.println("searchProductList 2" + productlist);
		return productlist;
	}
	// 2021.10.15 1μ°¨ λ΄μ©μμ  μλ£
	// 2021.10.12 1μ°¨ μΆκ°μλ£
	
	// 2021.10.11 μΆκ°μμ
	public int getProductListCount(Connection conn) {
		int result = 0;
		String sql = "select count(product_no) from product";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rset);
			jdbcTemplate.close(pstmt);
		}
		return result;
	}
	// 2021.10.11 μΆκ°μλ£	
	// 2021.10.22 μΆκ°μμ
	public int getProductListCount(Connection conn, ProductTable searchKeyVo) {
		int result = 0;
		//String sql = "select count(product_no) from product";
		//String sql = "select * from (select rownum r, t1.*      from(select p.product_info_url, p.product_name, p.product_option, p.price, p.product_no           from product p where  p.parent_category like '%κ·Όλ ₯κΈ°κ΅¬%' )t1) t2 where r between ? and ?"
		String sql = "select count(*) from product p "; // 

		String productName = searchKeyVo.getProductName();
		String parentCategory = searchKeyVo.getParentCategory();
		String selectRank = searchKeyVo.getSelectRank();
		String childCategory = searchKeyVo.getChildCategory();	
		int minPrice = searchKeyVo.getMinPrice();
		int maxPrice = searchKeyVo.getMaxPrice();
		boolean flag = false;
		boolean flag1 = false;
		
		if (selectRank != null && !selectRank.equals("")) {
			if (!flag1) {
				sql += " inner join order_detail o on p.product_no = o.product_no ";
				sql += " inner join review r on o.order_detail_no = r.order_detail_no ";
				flag1 = true;
			}
		}
		if (productName != null && !productName.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.product_name like '%" + productName + "%'";
		}
		if (parentCategory != null && !parentCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.parent_category like '%" + parentCategory + "%'";
		}
		if (childCategory != null && !childCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.child_category like '%" + childCategory + "%'";
		}
		if (minPrice >= 0 || maxPrice >= 0) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.price between " + minPrice + " and " + maxPrice;
		}
		if (selectRank != null && selectRank.equals("μΈκΈ°μ")) {
				sql += " group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
						+ " order by count(o.buy_quantity) desc";
		}
		else if (selectRank != null && selectRank.equals("νμ μ")) {
				sql += " group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
						+ " order by sum(r.score) desc";
		}
		
		System.out.println("getProductListCount sql:"+sql);
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rset);
			jdbcTemplate.close(pstmt);
		}
		return result;
	}
	// 2021.10.22 μΆκ°μλ£	
	
	
	
	
	// 2021.10.07 μΆκ°
	public ArrayList<ProductTable> productSearch(Connection conn, ProductTable searchKeyVo) {
		System.out.println("productSearch 1");
		System.out.println(searchKeyVo);

		ArrayList<ProductTable> productlist = null;
		// 2021.10.11 1μ°¨ λ΄μ©μμ  μμ
		String sql = "select p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
				+ " from product p";
//				+ " inner join order_detail o on p.product_no = o.product_no"
//				+ " inner join review r on o.order_detail_no = r.order_detail_no";
		// 2021.10.11 1μ°¨ λ΄μ©μμ  μλ£
		
		String productName = searchKeyVo.getProductName();
		String parentCategory = searchKeyVo.getParentCategory();
		String selectRank = searchKeyVo.getSelectRank(); // 2021.10.11 1μ°¨ λ΄μ©μΆκ°
		String childCategory = searchKeyVo.getChildCategory();	
		int minPrice = searchKeyVo.getMinPrice();
		int maxPrice = searchKeyVo.getMaxPrice();
		boolean flag = false;
		boolean flag1 = false;

		// 2021.10.11 1μ°¨ λ΄μ©μμ  μμ
		if (selectRank != null && !selectRank.equals("")) {
			if (!flag1) {
				sql += " inner join order_detail o on p.product_no = o.product_no ";
				sql += " inner join review r on o.order_detail_no = r.order_detail_no ";
				flag1 = true;
			}
		}
		if (productName != null && !productName.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.product_name like '%" + productName + "%'";
		}
		if (parentCategory != null && !parentCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.parent_category like '%" + parentCategory + "%'";
		}
		if (childCategory != null && !childCategory.equals("")) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.child_category like '%" + childCategory + "%'";
		}
		if (minPrice >= 0 || maxPrice >= 0) {
			if (!flag) {
				sql += " where ";
				flag = true;
			} else {
				sql += " and ";
			}
			sql += " p.price between " + minPrice + " and " + maxPrice;
		}
		if (selectRank != null && selectRank.equals("μΈκΈ°μ")) {
				sql += " group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
						+ " order by count(o.buy_quantity) desc";
		}
		else if (selectRank != null && selectRank.equals("νμ μ")) {
				sql += " group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
						+ " order by sum(r.score) desc";
		}
		// 2021.10.11 1μ°¨ λ΄μ©μμ  μλ£
		System.out.println(sql);

		//
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		try {
			System.out.println("productSearch executeQuery 1");
			pstmt = conn.prepareStatement(sql);
			rest = pstmt.executeQuery();
			System.out.println("productSearch executeQuery 2");
			if (rest.next()) {
				System.out.println("productSearch executeQuery over 1");
				productlist = new ArrayList<ProductTable>();
				do {
					ProductTable vo = new ProductTable();
					vo.setProductInfoUrl(rest.getString("product_info_url"));
					vo.setProductName(rest.getString("product_name"));
					vo.setProductOption(rest.getString("product_option"));
					vo.setPrice(rest.getInt("price"));
					productlist.add(vo);
					System.out.println("productSearch executeQuery over 1++");
				} while (rest.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rest);
			jdbcTemplate.close(pstmt);
		}
		System.out.println("productlist 2" + productlist);
		return productlist;
	}
	// 2021.10.07 μΆκ°μλ£

	// TODO
	// μνλͺκ²μ λ©μλ
	public ArrayList<ProductTable> productSearch(Connection conn, String productName) {
		ArrayList<ProductTable> productlist = null;
		String sql = "select product_info_url, product_name, product_option, price"
				+ " from product where product_name like '% ? %'";
		// ?λ searchpage.jspμμ νμ€νΈλ₯Ό μλ ₯λ°μμμΌν¨
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productName);
			rest = pstmt.executeQuery();
			if(rest.next()) {
				productlist = new ArrayList<ProductTable>();
				do {
					ProductTable vo = new ProductTable();
					vo.setProductInfoUrl(rest.getString("product_info_url"));
					vo.setProductName(rest.getString("product_name"));
					vo.setProductOption(rest.getString("product_option"));
					vo.setPrice(rest.getInt("price"));
					productlist.add(vo);
				} while(rest.next());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rest);
			jdbcTemplate.close(pstmt);
		}
		System.out.println("productlist λ¦¬ν΄μ " + productlist);
		return productlist;
	}

	// 2021-10-07 μΆκ°
	// κ°κ²© λ²μ κ²μ λ©μλ
	public ArrayList<ProductTable> priceSearch(Connection conn, int minprice, int maxprice) {
		ArrayList<ProductTable> productlist = null;
		String sql = "select product_info_url, product_name, product_option, price"
				+ " from product where price between ? and ?";
		// ?λ searchpage.jspμμ μ«μλ₯Ό μλ ₯λ°μμμΌν¨(μ²«λ²μ§Έλ μ΅μκΈμ‘ λλ²μ§Έλ μ΅λκΈμ‘ μ«μλ‘ μλ ₯νκ±Έ λ°μμμΌν¨)
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, minprice);
			pstmt.setInt(2, maxprice);
			rest = pstmt.executeQuery();
			if(rest.next()) {
				productlist = new ArrayList<ProductTable>();
				do {
					ProductTable vo = new ProductTable();
					vo.setProductInfoUrl(rest.getString("product_info_url"));
					vo.setProductName(rest.getString("product_name"));
					vo.setProductOption(rest.getString("product_option"));
					vo.setPrice(rest.getInt("price"));
					productlist.add(vo);
				} while(rest.next());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rest);
			jdbcTemplate.close(pstmt);
		}
		System.out.println("productlist λ¦¬ν΄μ " + productlist);
		return productlist;
	}
	
	// μΈκΈ°(μ£Όλ¬Έ)μμλ³ κ²μ λ©μλ
	public ArrayList<ProductTable> buyRankSearch(Connection conn) {
		ArrayList<ProductTable> productlist = null;
		String sql = "select p.product_info_url, p.product_name, p.product_option, p.price, p.product_no, count(o.buy_quantity)"
				+ " from product p join order_detail o"
				+ " on p.product_no = o.product_no"
				+ " group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
				+ " order by count(o.buy_quantity) desc";
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rest = pstmt.executeQuery();
			if(rest.next()) {
				productlist = new ArrayList<ProductTable>();
				do {
					ProductTable vo = new ProductTable();
					vo.setProductInfoUrl(rest.getString("product_info_url"));
					vo.setProductName(rest.getString("product_name"));
					vo.setProductOption(rest.getString("product_option"));
					vo.setPrice(rest.getInt("price"));
					productlist.add(vo);
				} while(rest.next());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(rest);
			jdbcTemplate.close(pstmt);
		}
		System.out.println("productlist λ¦¬ν΄μ " + productlist);
		return productlist;
	}
	
	// νμ μμλ³ κ²μ λ©μλ
			public ArrayList<ProductTable> scoreRankSearch(Connection conn) {
				ArrayList<ProductTable> productlist = null;
				String sql = "select p.product_info_url, p.product_name, p.product_option, p.price, p.product_no, sum(r.score)"
						+ " from product p inner join order_detail o"
						+ " on p.product_no = o.product_no"
						+ " inner join review r"
						+ " on o.order_detail_no = r.order_detail_no"
						+ " group by p.product_info_url, p.product_name, p.product_option, p.price, p.product_no"
						+ " order by sum(r.score) desc";
				PreparedStatement pstmt = null;
				ResultSet rest = null;
				try {
					pstmt = conn.prepareStatement(sql);
					rest = pstmt.executeQuery();
					if(rest.next()) {
						productlist = new ArrayList<ProductTable>();
						do {
							ProductTable vo = new ProductTable();
							vo.setProductInfoUrl(rest.getString("product_info_url"));
							vo.setProductName(rest.getString("product_name"));
							vo.setProductOption(rest.getString("product_option"));
							vo.setPrice(rest.getInt("price"));
							productlist.add(vo);
						} while(rest.next());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					jdbcTemplate.close(rest);
					jdbcTemplate.close(pstmt);
				}
				System.out.println("productlist λ¦¬ν΄μ " + productlist);
				return productlist;
			}
	
	// μΉ΄νκ³ λ¦¬λ³ κ²μ λ©μλ
		public ArrayList<ProductTable> categorySearch(Connection conn) {
			ArrayList<ProductTable> productlist = null;
			String sql = "select product_info_url, product_name, product_option, price"
					+ " from product"
					+ " where parent_category like ' ? '";
			// ?λ searchpage.jspμμ μ νλ μ΅μκ°μ λ°μμμΌν¨
			PreparedStatement pstmt = null;
			ResultSet rest = null;
			try {
				pstmt = conn.prepareStatement(sql);
				rest = pstmt.executeQuery();
				if(rest.next()) {
					productlist = new ArrayList<ProductTable>();
					do {
						ProductTable vo = new ProductTable();
						vo.setProductInfoUrl(rest.getString("product_info_url"));
						vo.setProductName(rest.getString("product_name"));
						vo.setProductOption(rest.getString("product_option"));
						vo.setPrice(rest.getInt("price"));
						productlist.add(vo);
					} while(rest.next());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jdbcTemplate.close(rest);
				jdbcTemplate.close(pstmt);
			}
			System.out.println("productlist λ¦¬ν΄μ " + productlist);
			return productlist;
		}
	// 2021-10-07 μΆκ°μλ£
	
}