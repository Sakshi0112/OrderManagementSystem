package ordersystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Operation {
	/*
	 * validateCategory is a method used to validate if the category entered by the
	 * user belongs to one of the enumeration object. It loops through all the
	 * enumeration objects and if the match is found returns true else returns
	 * false.
	 */
	public boolean validateCategory(String item_category) {
		Category[] category = Category.values();
		for (Category c : category) {
			String s = c.toString();
			if (s.equalsIgnoreCase(item_category)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * insertItem is a method that directly communicates with the database and adds
	 * the item object to the database by accessing values of the item object using
	 * getters
	 */
	public int insertItem(Item items, String category, Connection conn) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into item_XXXX values(?,?,?,?,?)");
			pstmt.setInt(1, items.getItemNo());
			pstmt.setString(2, items.getDescription());
			pstmt.setString(3, items.getCategory().toString());
			pstmt.setDouble(4, items.getPrice());
			pstmt.setInt(5, items.getQuantity());
			int count = pstmt.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * serachItem method is used to validate whether the given item exists in the
	 * database if not it return false else true. It uses item_no for searching
	 */
	public boolean searchItem(Connection conn, int item_no) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select item_no, description from item_XXXX where item_no=" + item_no);
			if (rset.isBeforeFirst())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/* retrieveItem method is used to retrieve the item if present in database */
	public Item retrieveItem(Connection conn, int item_no) {
		try {
			Item item = new Item();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from item_XXXX where item_no=" + item_no);
			while (rset.next()) {
				item.setItemNo(rset.getInt(1));
				item.setDescription(rset.getString(2));
				String category = rset.getString(3);
				item.setCategory(Category.valueOf(category));
				item.setPrice(rset.getInt(4));
				item.setQuantity(rset.getInt(5));
			}
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * updateItem is a method used to update the description and price of the item
	 * if it exists in the database. It uses separate update queries for description
	 * and price modification respectively.
	 */
	public int updateItem(Connection conn, Item item, int item_no) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("update item_XXXX set description =? where item_no=?");
			pstmt.setString(1, item.getDescription());
			pstmt.setInt(2, item_no);
			pstmt.executeUpdate();
			PreparedStatement pstmt1 = conn.prepareStatement("update item_XXXX set price =? where item_no=?");
			pstmt1.setDouble(1, item.getPrice());
			pstmt1.setInt(2, item_no);
			int count = pstmt1.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * deleteItem is a method used to deleted the requested item from the database.
	 */
	public int deleteItem(Connection conn, int item_no) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete item_XXXX where item_no=?");
			pstmt.setInt(1, item_no);
			int count = pstmt.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * selectItem is a method used to retrieve the items from ITEM_XXX table based
	 * on the item_no given by user.
	 */
	public void selectItem(Connection conn, int item_no) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from item_XXXX where item_no=" + item_no);
			System.out.println("Item No\t\tDescription\t\tCategory\t\tPrice\t\tQuantity");
			System.out.println(
					"_______________________________________________________________________________________________________");
			while (rset.next()) {
				System.out.println(rset.getInt(1) + "\t\t" + rset.getString(2) + "\t\t" + rset.getString(3) + "\t\t"
						+ rset.getInt(4) + "\t\t" + rset.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * selectItem method is used to retrieve the items from ITEM_XXX table based on
	 * the category given by user. This method is overloaded
	 */
	public boolean selectItem(Connection conn, String category) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from item_XXXX where category=?");
			pstmt.setString(1, category);
			ResultSet rset = pstmt.executeQuery();
			if (!rset.isBeforeFirst())
				return true;
			else {
				System.out.println("Item No\t\ttDescription\t\tCategory\t\tPrice\t\tQuantity");
				System.out.println(
						"_______________________________________________________________________________________________________");
				while (rset.next()) {
					System.out.println(rset.getInt(1) + "\t\t" + rset.getString(2) + "\t\t" + rset.getString(3) + "\t\t"
							+ rset.getInt(4) + "\t\t" + rset.getInt(5));
				}
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * selectDesc method is used to retrieve the description for the requested
	 * item_no public void selectDesc(Connection conn, int item_no) { try {
	 * Statement stmt = conn.createStatement (); ResultSet rset = stmt.executeQuery
	 * ("select description from item_XXXX where item_no="+item_no); while
	 * (rset.next()) {
	 * System.out.println("Description of item selected: "+rset.getString (1)); } }
	 * catch (SQLException e) { e.printStackTrace(); } }
	 */

	/*
	 * checkQuantity method is a used to check whether the quantity requested during
	 * order placement is more than the quantity of the item in items_XXXX table. It
	 * return true if quantity is more in the items table than requested else return
	 * false.
	 */
	public boolean checkQuantity(Connection conn, int item_no, int quantity) {
		try {
			int dbquantity = 0;
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select qty from item_XXXX where item_no=" + item_no);
			while (rset.next()) {
				dbquantity = rset.getInt(1);
			}
			if (dbquantity >= quantity)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * checkCustId method checks whether the cust_no given by user for order
	 * placement is valid and exists in the CUSTOMER_XXXX table if not it return
	 * false else return true
	 */
	public boolean checkCustId(Connection conn, String cust_no) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select custno from customer_XXXX");
			while (rset.next()) {
				if (cust_no == rset.getString(1))
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * placeOrder is used to insert the order into ORDER_XXXX table after the
	 * mentioned validations are done. It also reduces the quantity in the item_xxxx
	 * table depending on the quantity of item in the order placed.
	 */
	public int placeOrder(Order order, String cust_no, Connection conn) {
		try {
			int item_no=0;
			ArrayList<Item> item_collection = new ArrayList<Item>();
			item_collection = order.getItemCollection(); 
			for(Item i : item_collection)
			{
				System.out.println(i);
				item_no = i.getItemNo();
			}
			int order_no = 0;
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select max(orderno) from order_XXXX");
			while (rset.next()) {
				order_no = rset.getInt(1);
				order_no++;
			}
			PreparedStatement pstmt = conn
					.prepareStatement("insert into order_XXXX(orderno,item_no,ord_qty,custno) values(?,?,?,?)");
			pstmt.setInt(1, order_no);
			pstmt.setInt(2, item_no);
			pstmt.setInt(3, order.getOrderQty());
			pstmt.setString(4, cust_no);
			int count = pstmt.executeUpdate();
			PreparedStatement pstmt1 = conn.prepareStatement("update item_XXXX set qty=qty-? where item_no = ?");
			pstmt1.setInt(1, order.getOrderQty());
			pstmt1.setInt(2, item_no);
			pstmt1.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * showOrders method is used to retrieve the data from order_xxxx table and
	 * display all the existing orders to the user.
	 */
	public boolean showOrders(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from order_XXXX");

			// Print the no and name
			System.out.println("Order No\t\t\tItem No\t\t\tOrder Quantity\t\t\tOrder Date\t\t\tCustomer No");
			System.out.println("_________________________________________________________________");
			if (!rset.isBeforeFirst())
				return true;
			else 
			{
				while (rset.next()) {
					System.out.println(rset.getInt(1) + "\t\t\t" + rset.getInt(2) + "\t\t\t" + rset.getInt(3) + "\t\t\t"
							+ rset.getString(4) + "\t\t\t" + rset.getString(5));
				}
			  return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * showOrders method is used to retrieve the data from order_xxxx table and
	 * display the order according to the order number specified by the user. It is
	 * an overloaded method
	 */
	public boolean showOrders(Connection conn, int order_no) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from order_XXXX where orderno=" + order_no);

			// Print the no and name
			System.out.println("Order No\t\t\tItem No\t\t\tOrder Quantity\t\t\tOrder Date\t\t\tCustomer No");
			System.out.println("_________________________________________________________________");
			if (!rset.isBeforeFirst())
				return true;
			else {
				while (rset.next()) {
					System.out.println(rset.getInt(1) + "\t\t\t" + rset.getInt(2) + "\t\t\t" + rset.getInt(3) + "\t\t\t"
							+ rset.getString(4) + "\t\t\t" + rset.getString(5));
				}
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}