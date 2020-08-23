/*Problem Statement: Create a class called Item having - item_no, description, category, price and quantity as its data members.             
 * a. Create a parameterized constructor to assign appropriate values to the data members.  
 * b. Create appropriate getter and setter methods*/

package ordersystem;


public class Item 
{
	private int item_no;
	private String description;
	private Category category;
	private double price;
	private int quantity;
	
	public Item() {
		super();
		this.item_no = 0;
		this.description = "";
		this.category = Category.valueOf("GROCERY");
		this.price = 0.0;
		this.quantity = 0;
	}
	
	public Item(int item_no, String description, Category category, double price, int quantity) {
		super();
		this.item_no = item_no;
		this.description = description;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}
	
	public int getItemNo() {
		return item_no;
	}
	public void setItemNo(int item_no) {
		this.item_no = item_no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Item [item_no=" + item_no + ", description=" + description + ", category=" + category + ", price="
				+ price + ", quantity=" + quantity + "]";
	}
}