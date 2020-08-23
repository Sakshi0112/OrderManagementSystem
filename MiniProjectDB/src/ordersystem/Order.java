/*Problem Statement: Create a class called Order having collection of Item objects, order_qty and order_date as data members.
 *  Create getter and setter methods.*/

package ordersystem;

import java.util.ArrayList;
import java.util.Date;

public class Order 
{
	private ArrayList<Item> item_collection = new ArrayList<Item>();
	private int order_qty;
	private Date order_date;
	
	public ArrayList<Item> getItemCollection() {
		return item_collection;
	}
	public void setItemCollection(ArrayList<Item> item_collection) {
		this.item_collection = item_collection;
	}
	public int getOrderQty() {
		return order_qty;
	}
	public void setOrderQty(int order_qty) {
		this.order_qty = order_qty;
	}
	public Date getOrderDate() {
		return order_date;
	}
	public void setOrderDate(Date order_date) {
		this.order_date = order_date;
	}
	
	@Override
	public String toString() {
		return "Order [item_collection=" + item_collection + ", order_qty=" + order_qty + ", order_date=" + order_date
				+ "]";
	}
}
