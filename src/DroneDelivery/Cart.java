package DroneDelivery;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
public class Cart {
	ArrayList<Integer> cartItems;	//list of product's code purchased by a customer
	ArrayList<Integer> quantity;	//list of quantity of each item purchased
	ArrayList<Integer> wh;	//list of warehouse where purchased products are stored
	int totalPrice;					//total price of purchased goods
	float totalWeight;				//total weight of purchased goods
	int id;							//id of cart


	static BufferedReader ob = new BufferedReader(new InputStreamReader(System.in));

	public Cart(int id)	
	{
		this.id = id;
		cartItems = new ArrayList<Integer>();
		quantity = new ArrayList<Integer>();
		wh = new ArrayList<Integer>();
		totalPrice = 0;
		totalWeight = 0.0f;
	}


	//this function allows user to shop by category by displaying category and product (listed by category)
	//and lets user select the product to purchase
	void ShopByCategory(Connection con) throws SQLException, NumberFormatException, IOException
	{
		int choice = 0, selectedProdCode = 0, i = 0, qty = 0;
		Statement state = con.createStatement();
		ArrayList<String> category = new ArrayList<String>();

		String Query = "SELECT DISTINCT Category FROM Products ORDER BY WarehouseNo";
		ResultSet res = state.executeQuery(Query);
		while (res.next())						//add all categories to the list category
		{
			category.add(res.getString("Category"));	
		}

		do
		{
			choice = categoryMenu(category);	//display category menu
			if(choice != 0)						//choice = 0, if user wants to go back to main menu
			{
				String query = "SELECT id, Product, Price, Stock FROM Products WHERE Category='"+category.get(choice-1)+"'ORDER BY id";
				res = state.executeQuery(query);
				ArrayList<Integer> product = new ArrayList<Integer>();		//to display the product under a selected category

				i = 0;
				System.out.print("\n-----------------------------------------------------------------------");
				System.out.print("\n\n\t\t***** PRODUCTs LIST *****");
				System.out.print("\n-----------------------------------------------------------------------");
				System.out.printf("\n SR. NO. \t\t ITEM NAME \t\t\t PRICE");
				System.out.print("\n-----------------------------------------------------------------------");
				while (res.next())
				{
					if(res.getInt("Stock") > 0)			//add available product's id to the list product
					{
						System.out.printf("\n "+(++i)+"%40s %18d", res.getString("Product"), res.getInt("Price"));
						product.add(res.getInt("id"));
					}
				}
				System.out.print("\n Enter '0' to go back to CATEGORIES.");
				System.out.print("\n-----------------------------------------------------------------------");

				do
				{
					System.out.print("\n\n\tSELECT A PRODUCT TO ADD TO CART : ");
					do
					{					
						selectedProdCode = Integer.parseInt(ob.readLine());		// select a product from available product list

						if(selectedProdCode<0 || selectedProdCode>product.size())
							System.out.print("\n\t***Enter a valid choice : ");

					}while(selectedProdCode<0 || selectedProdCode>product.size());

					if(selectedProdCode != 0)								//if selectedProdCode=0, then user wants to written to the category menu
					{
						selectedProdCode = product.get(selectedProdCode - 1);
						Statement state2 = con.createStatement();
						ResultSet prod = state2.executeQuery("SELECT Product, Price, Weight, Stock, WarehouseNo FROM Products WHERE id='"+selectedProdCode+"';");

						System.out.print("\n\n\t Quantity to be purchased = ");
						do
						{
							qty = Integer.parseInt(ob.readLine());

							if(qty > prod.getInt("Stock"))
								System.out.print("\n Only "+prod.getInt("Stock")+" items available");
						}while(qty > prod.getInt("Stock"));				//qty should be less than or equal to available stock

						if(qty > 0)
						{
							totalPrice += qty * prod.getInt("Price");
							totalWeight += qty * prod.getFloat("Weight");
							int curStock = prod.getInt("Stock") - qty;  
							int warehouseNo = prod.getInt("WarehouseNo");

							if(!wh.isEmpty())		//to add warehouses from where products purchased are stored
							{					
								if(!wh.contains(warehouseNo))
								{
									wh.add(warehouseNo);
								}
							}
							else
							{
								wh.add(warehouseNo);
							}
							
							state.execute("UPDATE Products SET Stock = '"+curStock+"' WHERE id='"+selectedProdCode+"';"); //update stock after purchased in 
																														  //Products table structure of our database
							cartItems.add(selectedProdCode);		//adding purchased item's and quantity to cartItems and quantity list resp
							quantity.add(qty);
							System.out.print("\n\t***Item added successfully to cart.");
						}

						System.out.print("\n-----------------------------------------------------------------------");
						System.out.print("\n\n Enter '0' to go back to CATEGORIES.");
						prod.close();
					}
				}while(selectedProdCode != 0);		//go back to category list
			}
		}while(choice != 0); 					// to end shopping

		if(totalPrice != 0)
		{
			addCustomer(con);	// add customer to the database
			generateBill(con);		//generate purchased goods bill
		}

		state.close();
		res.close();

	}


	//displays list of categories in Products table structure and returns selected category by user
	int categoryMenu(ArrayList<String> category) throws NumberFormatException, IOException 
	{
		int choice = 0;
		System.out.print("\n------------------------------------------------------");
		System.out.print("\n\n\t\t***** SHOP-BY-CATEGORY *****");
		for(int i=0;i< category.size();i++)			//display list
		{
			System.out.print("\n "+(i+1)+". "+category.get(i));
		}
		System.out.print("\n Enter '0' to go back to MAIN MENU.");
		System.out.print("\n\n\tCHOOSE CATEGORY : ");  	//choose category from displayed list
		do
		{
			choice = Integer.parseInt(ob.readLine());

			if(choice<0 || choice>category.size())
				System.out.print("\n\t***Enter a valid choice : ");

		}while(choice<0 || choice>category.size());
		System.out.print("\n------------------------------------------------------");

		return choice;
	}


	//adds Customer details to the Customer table structure
	void addCustomer(Connection con) throws SQLException, IOException
	{
		Statement state = con.createStatement();
		System.out.print("\n\t ENTER DETAILS BELOW : ");
		System.out.print("\n\t\t Name :  ");
		String name= ob.readLine();
		System.out.print("\n\t\tContact No. :  ");
		long contact = Long.parseLong(ob.readLine());
		System.out.print("\n\t\tFor Delivery Address, Enter => ");
		System.out.print("\n\t\tCoordinate X :  ");
		int x = Integer.parseInt(ob.readLine());
		System.out.print("\n\t\tCoordinate Y :  ");
		int y = Integer.parseInt(ob.readLine());
		ResultSet res = state.executeQuery("select *from Customer ORDER BY id DESC LIMIT 1;");
		int id = res.getInt(1) + 1;

		
		//for warehouse string
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < wh.size(); i++) {
			s.append(Integer.toString(wh.get(i)) + " ");
		}
		
		String warehouse = s.toString().trim();
		String insQuery  = "INSERT into Customer(id,name,contact,X,Y,cartAmount,cartWeight,Warehouse) "
				+ "VALUES('"+id+"','"+name+"','"+contact+"','"+x+"','"+y+"','"+totalPrice+"','"+totalWeight+"','"+warehouse+"');";
		state.executeUpdate(insQuery);
		state.close();
		res.close();
	}

	
	//generates and displays a bill of purchased items
	void generateBill(Connection con) throws SQLException
	{
		Statement state = con.createStatement();
		System.out.print("\n------------------------------------------------------------------------------------------------");
		System.out.printf("\n SR.NO. \t\t\t ITEM NAME \t\t\t QTY \t\t AMT");
		System.out.print("\n------------------------------------------------------------------------------------------------");
		for(int i=0 ; i<cartItems.size() ; i++)
		{		
			ResultSet res = state.executeQuery("SELECT Product, Price FROM Products WHERE id='"+cartItems.get(i)+"';");
			System.out.printf("\n %10d %40s %15d %15s",(i+1), res.getString("Product"), quantity.get(i), quantity.get(i)*res.getInt("Price"));
			res.close();
		}
		System.out.print("\n------------------------------------------------------------------------------------------------");
		System.out.print("\n\t NET WEIGHT = "+totalWeight+" kgs");
		System.out.print("\t\t\t\t\t    NET AMOUNT = "+totalPrice);
		System.out.print("\n------------------------------------------------------------------------------------------------");
		state.close();
	}

}