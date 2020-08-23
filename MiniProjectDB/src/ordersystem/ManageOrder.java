/*Create ManageOrder Class having main method and tasks are carried out according to a menu. This menu is as follows: 
 * a. Item details  In item details ask for: (Use Item class. All operations are on database) 
 *   i. Add Item – accept item details. Use enumeration for item category (electronic, grocery and garments) check if the user has entered appropriate category.  
 *   ii. Modify item – Only description and price can be modified. Accept item no and data to be modified. 
 *   iii. Delete item – Accept item no 
 *   iv. Search Item – Search an item based on item no OR search items based on category. 
 * b. Order details: In order details accept item_no, order_qty and custNo.         
 *    Check if the item exists in the table. If item exists display its description,otherwise display message that item does not exist.          
 *    Check if the qty assigned to order_qty is less than quantity in item table.          
 *    Check if the CustNo exists in the table, display message if the Customer does not exist 
      Check the validity of item, quantity, CustNo and only then insert order data into the OrderXXX table in database/collections 
   c. Show Order Details 
      i. Show All Orders : Should display all orders in a tabular format 
      ii. Show Order by Order No : Accept Order No and display order details  
Each of the above operation success/failure messages should be logged into a text file which is user defined. */

package ordersystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class ManageOrder 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		int choice=0,choice1=0,choice2=0;
        boolean append = true;
        try {
			FileHandler handler = new FileHandler("log_file.txt", append);  
			Logger logger = Logger.getLogger("pgs.miniproject");
			logger.addHandler(handler);
			do
			{
			  try
			  {
				DBConnection connect = new DBConnection();
				Connection conn = connect.connectionCreation();
			    Operation op = new Operation();
			    Item items;
			    Order order;
			    ArrayList<Item> item_collection = new ArrayList<Item>(); 
				System.out.println("Menu :");
				System.out.println(" 1.Items Details"+"\n 2.Order Details"+"\n 3.Show Order Details"+"\n 4.Exit");
				System.out.println("Enter your choice of operation to be performed :");
				choice = sc.nextInt();
				switch(choice)
				{	
				  case 1 :
					  	   System.out.println("Choose an option you want to perform :");
					       System.out.println(" 1. Add Item"+"\n 2. Modify Item"+"\n 3. Delete Item" +"\n 4. Search Item");
					       choice1 = sc.nextInt();
					       switch(choice1)
					       {
						       case 1 : System.out.println("Enter the itemNo: ");
					   			        int item_no = sc.nextInt();
					   			        sc.nextLine();
					   			        System.out.println("Enter the item description: ");
					   			        String description = sc.nextLine();
					   			        System.out.println("Enter the item category out of the following list only: \nElectonic \nGrocery \nGarments");
						                String item_category = sc.next();
						                boolean status=false;
						                status = op.validateCategory(item_category);
						 	            if(!status)
						 	            {
						 	      	       System.out.println("Enter Valid Item Category from the list specified");
						 	      	       logger.warning("Invalid Category specified hence failed to add item");
						 	            }
						 	            else
						 	            {
						 	    	        System.out.println("Enter the price for the item: ");
						 	    	        double price = sc.nextDouble();
						 	    	        System.out.println("Enter the quantity of the items: ");
						 	    	        int quantity = sc.nextInt();
						 	    	        items = new Item(item_no,description,Category.valueOf(item_category.toUpperCase()),price,quantity);
						 	    	        int count = op.insertItem(items,item_category.toUpperCase(),conn);
						 	    	        logger.info("Successfully added "+count+" item to the database");
						 	            }
								        break;
							
					            case 2 :  System.out.println("Enter the item no of the item whose details you want to modify: ");
					   			          item_no = sc.nextInt();
					   			          boolean flag = false;
					   			          flag = op.searchItem(conn, item_no);	 
					   			          System.out.println(flag);
					   			          if(!flag)
					   			          {
					   				         System.out.println("The item no specified does not exist in the database.");
					   				         logger.warning("The item no specified does not exist in the database hence can't modify the item.");
					   			          }
					   			          else
					   			          {
					   			        	  items = op.retrieveItem(conn, item_no);
					   			        	  sc.nextLine();
					   				          System.out.println("Enter the new description of the item: ");
					   				          items.setDescription(sc.nextLine());
				   					          System.out.println("Enter the new price of the item: ");
				   					          items.setPrice(sc.nextDouble());
				   					          int count = op.updateItem(conn, items, item_no);
				   					          logger.info("The item description and price for "+count+" item are modified successfully");
					   			          }
					   			          break;
						   		 
					            case 3 :  System.out.println("Enter the item no of the item whom you want to delete: ");
			   			 		 		  item_no = sc.nextInt();
			   			 		 		  flag = false;
			   			 		 		  flag = op.searchItem(conn, item_no);	 
			   			 		 		  System.out.println(flag);
			   			 		 		  if(!flag)
			   			 		 		  {
			   			 		 			  System.out.println("The item no specified does not exist in the database.");
			   			 		 			  logger.warning("The item no specified does not exist in the database thus can't delete the item");
			   			 		 		  }
			   			 		 		  else
			   			 		 		  {
			   			 		 			  int count = op.deleteItem(conn,item_no);
			   			 		 			  logger.info("Successfully deleted "+count+" item from the table");
			   			 		 		  }
			   			 		 		  break;
			   			 		 
					            case 4 : System.out.println("Choose an option :");
				       					 System.out.println(" 1. Search items based on item no"+"\n 2. Search items based on Category");
				       					 choice2 = sc.nextInt();
				       					 switch(choice2)
				       					 {
				       					 	case 1 : System.out.println("Enter the item no of the item whom you want to search: ");
				       					 			 item_no = sc.nextInt();
				       					 			 flag = false;
				       					 			 flag = op.searchItem(conn, item_no);	 
				       					 			 System.out.println(flag);
				       					 			 if(!flag)
				       					 			 {
				       					 				 System.out.println("The item no specified does not exist in the database.");
				       					 				 logger.warning("The item doesn't exist in the database thus the item can't be displayed");
				       					 			 }
				       					 			 else
				       					 			 {
				       					 				 op.selectItem(conn, item_no);
				       					 				 logger.info("Sucessfully displayed the items based on item number");
				       					 			 }
				       					 			 break;
					
				       					 	case 2 : System.out.println("Enter the category you want search according: ");
				       					 			 item_category = sc.next();
				       					 			 status = false;
				       					 			 flag=false;
				       					 			 status = op.validateCategory(item_category);
				       					 			 if(!status)
				       					 			 {	
				       					 				 System.out.println("Enter Valid Item Category from the list specified");
				       					 				 logger.warning("The category specified is invalid thus can't display the items");
				       					 			 }
				       					 			 else
				       					 			 {
				       					 				flag=op.selectItem(conn, item_category.toUpperCase());
				       					 				if(!flag)
				       					 					logger.info("Sucessfully displayed the items based on category");
				       					 				else
				       					 					logger.warning("No items present in the database for this category");
				       					 			 }
				       					 			 break;
				       					 }
				       					 break;
					       }
					      break;
					       
				  		  case 2 : System.out.println("Enter the item no of the item you want to order: ");
				  		  		   int item_no = sc.nextInt();
				  		  		   boolean status = false;
				  		  		   status = op.searchItem(conn, item_no);
				  		  		   if(!status)
				  		  		   {
				  		  			   System.out.println("The item no does not exist in the database so you can't place an order");
				  		  			   logger.warning("Since the item number doesn't exist in the database order can't be placed.");
				  		  		   }
				  		  		   else
				  		  		   {
				  		  			   items = op.retrieveItem(conn, item_no);
				  		  			   System.out.println("Description of item selected for placing order: "+items.getDescription());
				  		  			   System.out.println("Enter the quantity of item for the order: ");
				  		  			   int quantity = sc.nextInt();
				  		  			   boolean flag = false;
				  		  			   flag = op.checkQuantity(conn,item_no,quantity);
				  		  			   if(!flag)
				  		  			   {
				  		  				   System.out.println("The quantity of the item requested is above the current quantity for item so you can't place an order");
				  		  				   logger.warning("The quanity requested is greater than the quantity of items present so order can't be placed");
				  		  			   }
				  		  			   else
				  		  			   {
				  		  				   System.out.println("Enter the customer no in order to confirm your order: ");
				  		  				   String cust_no = sc.next();
				  		  				   flag = false;
				  		  				   flag = op.checkCustId(conn,cust_no);
				  		  				   if(!flag)
				  		  				   {
				  		  					   System.out.println("The customer no is invalid and thus order can't be placed");
				  		  					   logger.warning("Since the customer number doesn't exist in the database order can't be placed.");
				  		  				   }
				  		  				   else
				  		  				   {
				  		  					   order = new Order();
				  		  					   item_collection.add(items);
				  		  					   order.setItemCollection(item_collection);
				  		  					   order.setOrderQty(quantity);
				  		  					   int count = op.placeOrder(order,cust_no,conn);
				  		  					   logger.info("Successfully placed an order for "+count+" requested item");
				  		  				   }
				  		  			    }
				  		  		    }
				  		  		    break;

				  		  	 case 3 : System.out.println("Choose an option :");
				  		  	 		  System.out.println(" 1. Show all orders"+"\n 2. Display order by order no");
				  		  	 		  choice1 = sc.nextInt();
				  		  	 		  switch(choice1)
				  		  	 		  {
				  		  	 		  	  case 1 : status=false;
				  		  	 		  	  		   status = op.showOrders(conn);
				  		  	 		  	  		   if(!status)
				  		  	 		  	  		   {
				  		  	 		  	  			   logger.info("Sucessfully displayed the all the order");
				  		  	 		  	  		   }
				  		  	 		  	  		   else
				  		  	 		  	  		   {
				  		  	 		  	  			   logger.warning("No orders are present in the database in order to display");
				  		  	 		  	  		   }
				  		  	 		  	  		   break;
					   				
				  		  	 		  	  case 2 : System.out.println("Enter the order no: ");
				  		  	 		  	  		   int order_no = sc.nextInt();
				  		  	 		  	  		   status = false;
				  		  	 		  	  		   status = op.showOrders(conn,order_no);
				  		  	 		  	  		   if(!status)
				  		  	 		  	  		   {
				  		  	 		  	  			   logger.info("Sucessfully displayed the order based on order number");
				  		  	 		  	  		   }
				  		  	 		  	  		   else
				  		  	 		  	  		   {
				  		  	 		  	  			   logger.warning("No orders are present in the database in order to display");
				  		  	 		  	  		   }
				  		  	 		  	  		   break;
				  		  	 		  }
				  		  	 		  break;
					 }
			  	}
				catch(InputMismatchException ime)
				{
				  System.out.println("Make sure input is in valid format");
				  sc.next();
				}
			}while(choice>0 && choice<4);
			System.out.println("Thank you for using the application!");
			sc.close();
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
}