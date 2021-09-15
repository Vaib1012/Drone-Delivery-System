package DroneDelivery;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


public class Application {

	static Scanner ob=new Scanner(System.in);

	static int menu() // Application menu
	{
		int choice=0;

		System.out.print("\n\n\t**** MAIN MENU *****");
		System.out.print("\n\t 1. Place order"
				+ "\n\t 2. Track order"
				+ "\n\t 3. Display Database"
				+ "\n\t 4. Add new DRONE"
				+ "\n\t 5. Add new WAREHOUSE"
				+ "\n\t 6. Add PRODUCT/s");
		System.out.print("\n\n Enter '0' to close the Application");
		System.out.print("\n\n\t ENTER CHOICE = ");
		do
		{
			choice=ob.nextInt();

			if(choice<0 || choice>6)
				System.out.print("\n\t **Enter a valid choice = ");

		}while(choice<0 || choice>6);

		System.out.print("\n_____________________________________________________________________________________________\n");

		return choice;
	}
	
	
	public static void main(String[] args) {
		int choice=0;
		ControlRoom cr = new ControlRoom();	
		
		try {

			cr.connectToDatabase();		//to connect to a database
			System.out.print("\n--------------------------------------------------------------------");

			do
			{
				choice=menu();
				switch(choice)
				{
				//to place order;
				case 1: cr.placeOrder();
				break;

				//trackOrder
				case 2: cr.trackOrder();
					break;

				//To display Database
				case 3: int ch=0;
				System.out.print("\n\t\t ****DATABASE MENU****"
						+ "\n\t 1. DRONE"
						+ "\n\t 2. WAREHOUSE"
						+ "\n\t 3. PRODUCTS LIST"
						+ "\n\t 4. CUSTOMER LIST");
						System.out.print("\n\t ENTER CHOICE = ");
						do
						{
		
							ch=ob.nextInt();
		
							if(ch<1 || ch>4)
								System.out.print("\n\t **Enter a valid choice = ");
						}while(ch<1 || ch>4);
		
						switch(ch)
						{
							//Drone table
							case 1 : cr.displayDroneDB();
							break;
			
							//Warehouse table
							case 2 : cr.displayWarehouseDB();
							break;
			
							//Product table
							case 3: cr.displayProductDB();
							break;
							
							//Customer table
							case 4: cr.displayCustomerDB();
							break;
						}		
						break;

				//to Add Drone/s
				case 4: System.out.print("\n\tEnter Total Number of Drones To Be Added :  ");
				cr.addDrones(ob.nextInt());
				cr.displayDroneDB();
				break;

				//to Add Warehouse/s
				case 5: System.out.print("\n\tEnter Total Number of Warehouse To Be Added :  ");
				cr.addWarehouse(ob.nextInt());
				cr.displayWarehouseDB();
				break;


				//to Add Product/s
				case 6 :System.out.print("\n\tEnter Total Number of Products To Be Added :  ");
				cr.addProduct(ob.nextInt());
				cr.displayProductDB();
				break;

				//Exit case
				case 0: System.out.print("\n\t*****APPLICATION CLOSED*****");
				break;
				}
				System.out.print("\n_____________________________________________________________________________________________");

			}while(choice!=0);
			
			cr.closeDB(); // close database connection
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		ob.close();
	}
}





/*OUTPUT =>
****Drone Delivery System database opened successfully
--------------------------------------------------------------------

**** MAIN MENU *****
 1. Place order
 2. Track order
 3. Display Database
 4. Add new DRONE
 5. Add new WAREHOUSE
 6. Add PRODUCT/s

Enter '0' to close the Application

 ENTER CHOICE = 4

_____________________________________________________________________________________________

Enter Total Number of Drones To Be Added :  1

 Enter :
	 FOR DRONE : 1
	 Model Name :The Freefly Systems ALTA UAV

	 Capacity :7

Drone added to successfully to the database
-------------------------------------------------------------------------------------------------------------
 CODE			    MODEL NAME			X 	 Y 	 CAPACITY
-------------------------------------------------------------------------------------------------------------
      1                      DJI MATRICE 600 Pro            0        0     6.000000
      2                 DJI Spreading Wings S900            0        0     9.000000
      3                   Vulcan UAV Black Widow            0        0     8.000000
      4        Versadrones Heavy Lift Octocopter            0        0    12.000000
      6             The Freefly Systems ALTA UAV            0        0     7.000000
      7             The Freefly Systems ALTA UAV            0        0     7.000000
-------------------------------------------------------------------------------------------------------------
_____________________________________________________________________________________________

**** MAIN MENU *****
 1. Place order
 2. Track order
 3. Display Database
 4. Add new DRONE
 5. Add new WAREHOUSE
 6. Add PRODUCT/s

Enter '0' to close the Application

 ENTER CHOICE = 5

_____________________________________________________________________________________________

Enter Total Number of Warehouse To Be Added :  1

 Enter :
	 FOR Warehouse : 1
	 Warehouse Name :Warehouse 6

	 Location : Coordinate (X) : 6

	 Location : Coordinate (Y) : 10

Warehouse added to successfully to the database

Do you want to add products in Warehouse 6?
Press 1, if YES 
Press 2, if NO
 CHOICE = 1

Enter Total Number of Products To Be Added :  2

 Enter :
	 FOR Product : 1
	 Product Name :Pedigree meat 3kg dry dog food

	 Category :Pet supplies

	 Weight : 3

	 Product Price : 600

	 Stock Available : 200

Item added to successfully to the database
 Enter :
	 FOR Product : 2
	 Product Name :Whiskas kitten 1kg fish dry food

	 Category :Pet supplies

	 Weight : 1.1

	 Product Price : 377

	 Stock Available : 200

Item added to successfully to the database
-------------------------------------------------------------------------------------------------------------
 CODE 		 WAREHOUSE NAME 	   X 		 Y
-------------------------------------------------------------------------------------------------------------
      1                    Warehouse 1         6             6
      2                    Warehouse 2         1            -3
      3                    Warehouse 3        -3            -3
      4                    Warehouse 4        -6             4
      5                    Warehouse 5         0             4
      6                    Warehouse 6         6            10
-------------------------------------------------------------------------------------------------------------
_____________________________________________________________________________________________

**** MAIN MENU *****
 1. Place order
 2. Track order
 3. Display Database
 4. Add new DRONE
 5. Add new WAREHOUSE
 6. Add PRODUCT/s

Enter '0' to close the Application

 ENTER CHOICE = 6

_____________________________________________________________________________________________

Enter Total Number of Products To Be Added :  1

 Enter :
	 FOR Product : 1
	 Product Name :Whiskas adult Tuna 7kg dry food

	 Category :Pet supplies

	 Warehouse No. : 6

	 Weight : 7

	 Product Price : 1780

	 Stock Available : 250

Item added to successfully to the database
--------------------------------------------------------------------------------------------------------------------------------------------------------
 CODE 			 PRODUCT NAME 				 CATEGORY 	 WAREHOUSE NO. 		 WEIGHT 	 PRICE 		 STOCK
--------------------------------------------------------------------------------------------------------------------------------------------------------
      1                       BRU instant coffee                      Beverages               1                0.1             178             284
      2                          CocoCola bottle                      Beverages               1               1.25              65             295
      3                              Maaza mango                      Beverages               1                0.7              40             500
      4         Nescafe intense Cafe (Capucinno)                          Dairy               1                0.2              35             499
      5              Amul processed cheese cubes                          Dairy               1                0.2             114             500
      6                Sofit soya milk chocolate                          Dairy               1                1.2             123             300
      7                              Amul butter                          Dairy               1                0.1              48             300
      8                      Safe harvest peanut                        Staples               1               0.25              62             500
      9                            Fortune besan                        Staples               1               0.25              55             300
     10                Aashirvad multigrain atta                        Staples               1                5.0             305             500
     11                     Fortune basmati rice                        Staples               1                1.0             149             500
     12                       Origo fresh barley                        Staples               1                0.2              35             500
     13                          Similac neosure                      Baby food               2                0.4             525             500
     14                 Nestle rice fruit cereal                      Baby food               2                0.3             175             500
     15                                   Tomato            Fruits & Vegetables               2                1.0              30             495
     16                                   Garlic            Fruits & Vegetables               2                0.1              20             200
     17                                    Onion            Fruits & Vegetables               2                1.0              40             500
     18                                    Apple            Fruits & Vegetables               2                1.0             100             200
     19                               Strawberry            Fruits & Vegetables               2                0.2              50             150
     20                                Pineapple            Fruits & Vegetables               2                0.4              60             100
     21                          The daily stoic              Books: Philosophy               3               0.25             425             100
     22                      The idea of Justice              Books: Philosophy               3                0.3             386             100
     23                       Dan Brown : Origin                 Books: Fiction               3                0.4             238             100
     24                      Dan Brown : Inferno                 Books: Fiction               3                0.4             238             200
     25          Harry potter and deadly hallows                 Books: Fiction               3                0.5             419             200
     26                        Lord of the rings                 Books: Fiction               3                0.5             599             200
     27                          General Science               Books: Reference               3                0.3             166             150
     28                        Oxford Dictionary               Books: Reference               3                0.7             634             150
     29                                 Realme 5                        mobiles               4                0.2            9999             100
     30                              Vivo Z1 pro                        mobiles               4                0.2           13990             150
     31                             Oppo F11 pro                        mobiles               4               0.19           16990             150
     32                       Samsung galaxy s20                        mobiles               4               0.18           77900             100
     33                      Asus Zenfone pro M1                        mobiles               4                0.2            7499             100
     34                              Redmi 6 pro                        mobiles               4               0.21           11999             150
     35                                 Honor 9N                        mobiles               4                0.2            7999             150
     36                   Mi 20000 mAh powerbank             mobile accessories               4               0.43            1499             200
     37              Ambrane 10000 mAh powerbank             mobile accessories               4              0.187             699             100
     38              Mivi USB type C OTG adapter             mobile accessories               4               0.02             349             100
     39              Vodex micro USB OTG adapter             mobile accessories               4               0.02             399             200
     40               Philips cable selfie stick             mobile accessories               4                0.5             349             100
     41           Prestige Atlas electric kettle    kitchen and home appliances               5                1.2             825             200
     42                        Philips HR3700140    kitchen and home appliances               5                1.0            1749             200
     43                     Prestige PGMFB grill    kitchen and home appliances               5                1.0            1049             300
     44                       Pigeon 14239 Toast    kitchen and home appliances               5                0.8             750             300
     45                     Borosil coffee maker    kitchen and home appliances               5                2.0            3795             300
     46                  Bajaj GXI mixer grinder    kitchen and home appliances               5               3.85            1899             100
     47                       Bajaj DX7 dry iron    kitchen and home appliances               5                0.6             748             100
     48                 Pigeon induction cooktop    kitchen and home appliances               5                2.0            1499             100
     49             Kent ace plus water purifier    kitchen and home appliances               5                5.0           13999             200
     50             SmartBuy classic ceiling fan    kitchen and home appliances               5                2.0            1199             300
     51           Pedigree meat 3kg dry dog food                   Pet supplies               6                3.0             600             200
     52         Whiskas kitten 1kg fish dry food                   Pet supplies               6                1.1             377             200
     53          Whiskas adult Tuna 7kg dry food                   Pet supplies               6                7.0            1780             250
--------------------------------------------------------------------------------------------------------------------------------------------------------
_____________________________________________________________________________________________

**** MAIN MENU *****
 1. Place order
 2. Track order
 3. Display Database
 4. Add new DRONE
 5. Add new WAREHOUSE
 6. Add PRODUCT/s

Enter '0' to close the Application

 ENTER CHOICE = 1

_____________________________________________________________________________________________

------------------------------------------------------

	***** SHOP-BY-CATEGORY *****
1. Beverages
2. Dairy
3. Staples
4. Baby food
5. Fruits & Vegetables
6. Books: Philosophy
7. Books: Fiction
8. Books: Reference
9. mobiles
10. mobile accessories
11. kitchen and home appliances
12. Pet supplies
Enter '0' to go back to MAIN MENU.

CHOOSE CATEGORY : 1

------------------------------------------------------
-----------------------------------------------------------------------

	***** PRODUCTs LIST *****
-----------------------------------------------------------------------
SR. NO. 		 ITEM NAME 			 PRICE
-----------------------------------------------------------------------
1                      BRU instant coffee                178
2                         CocoCola bottle                 65
3                             Maaza mango                 40
Enter '0' to go back to CATEGORIES.
-----------------------------------------------------------------------

SELECT A PRODUCT TO ADD TO CART : 3


 Quantity to be purchased = 10

***Item added successfully to cart.
-----------------------------------------------------------------------

Enter '0' to go back to CATEGORIES.

SELECT A PRODUCT TO ADD TO CART : 0

------------------------------------------------------

	***** SHOP-BY-CATEGORY *****
1. Beverages
2. Dairy
3. Staples
4. Baby food
5. Fruits & Vegetables
6. Books: Philosophy
7. Books: Fiction
8. Books: Reference
9. mobiles
10. mobile accessories
11. kitchen and home appliances
12. Pet supplies
Enter '0' to go back to MAIN MENU.

CHOOSE CATEGORY : 6

------------------------------------------------------
-----------------------------------------------------------------------

	***** PRODUCTs LIST *****
-----------------------------------------------------------------------
SR. NO. 		 ITEM NAME 			 PRICE
-----------------------------------------------------------------------
1                         The daily stoic                425
2                     The idea of Justice                386
Enter '0' to go back to CATEGORIES.
-----------------------------------------------------------------------

SELECT A PRODUCT TO ADD TO CART : 1


 Quantity to be purchased = 1

***Item added successfully to cart.
-----------------------------------------------------------------------

Enter '0' to go back to CATEGORIES.

SELECT A PRODUCT TO ADD TO CART : 0

------------------------------------------------------

	***** SHOP-BY-CATEGORY *****
1. Beverages
2. Dairy
3. Staples
4. Baby food
5. Fruits & Vegetables
6. Books: Philosophy
7. Books: Fiction
8. Books: Reference
9. mobiles
10. mobile accessories
11. kitchen and home appliances
12. Pet supplies
Enter '0' to go back to MAIN MENU.

CHOOSE CATEGORY : 0

------------------------------------------------------
 ENTER DETAILS BELOW : 
	 Name :  Shivani Chandel

	Contact No. :  9898564745

	For Delivery Address, Enter => 
	Coordinate X :  11

	Coordinate Y :  15

------------------------------------------------------------------------------------------------
SR.NO. 			 			ITEM NAME       			      QTY 		     AMT
------------------------------------------------------------------------------------------------
      1                              Maaza mango              10             400
      2                          The daily stoic               1             425
------------------------------------------------------------------------------------------------
		 					NET WEIGHT = 7.25 kgs				NET AMOUNT = 825
------------------------------------------------------------------------------------------------
_____________________________________________________________________________________________

**** MAIN MENU *****
 1. Place order
 2. Track order
 3. Display Database
 4. Add new DRONE
 5. Add new WAREHOUSE
 6. Add PRODUCT/s

Enter '0' to close the Application

 ENTER CHOICE = 2

_____________________________________________________________________________________________

 ENTER CUSTOMER ID :  1

Path followed by drone : 
Node: Customer1(14,14)
Shortest Distance from Drone: 0.0
-> Node: Warehouse 22(1,-3)
Shortest Distance from Drone: 2.147483647E9
-> Node: Warehouse 33(-3,-3)
Shortest Distance from Drone: 2.147483647E9
-> Node: Warehouse 55(0,4)
Shortest Distance from Drone: 2.147483647E9
-> Node: Drone : DJI MATRICE 600 Pro1(0,0)
Shortest Distance from Drone: 2.147483647E9
-> 
Shortest Distance Travelled by drone is 8.589934588E9

_____________________________________________________________________________________________

**** MAIN MENU *****
 1. Place order
 2. Track order
 3. Display Database
 4. Add new DRONE
 5. Add new WAREHOUSE
 6. Add PRODUCT/s

Enter '0' to close the Application

 ENTER CHOICE = 2

_____________________________________________________________________________________________

 ENTER CUSTOMER ID :  4

Path followed by drone : 
Node: Customer4(1,6)
Shortest Distance from Drone: 0.0
-> Node: Warehouse 33(-3,-3)
Shortest Distance from Drone: 2.147483647E9
-> Node: Warehouse 44(-6,4)
Shortest Distance from Drone: 2.147483647E9
-> Node: Warehouse 55(0,4)
Shortest Distance from Drone: 2.147483647E9
-> Node: Drone : DJI MATRICE 600 Pro1(0,0)
Shortest Distance from Drone: 2.147483647E9
-> 
Shortest Distance Travelled by drone is 1.7179869176E10

_____________________________________________________________________________________________

**** MAIN MENU *****
 1. Place order
 2. Track order
 3. Display Database
 4. Add new DRONE
 5. Add new WAREHOUSE
 6. Add PRODUCT/s

Enter '0' to close the Application

 ENTER CHOICE = 3

_____________________________________________________________________________________________

	 ****DATABASE MENU****
 1. DRONE
 2. WAREHOUSE
 3. PRODUCTS LIST
 4. CUSTOMER LIST
 ENTER CHOICE = 4

-----------------------------------------------------------------------------------------------------------------------------
 CUST ID 	 CUSTOMER NAME 		 CONTACT 	 COOR X 	 COOR Y 	CART AMT 	 CART WEIGHT
-----------------------------------------------------------------------------------------------------------------------------
      1              Harsh Mishra           8865211341           14           14             1000                5.0
      2             Anand Agarwal           9974861312           10           20              350                2.0
      3              Sunita Bisht           7499690784            8           15             1855                6.0
      4              Shivam Kumar           9503384756            1            6              550                2.0
      5           Shivani Chandel           9898564745           11           15              825               7.25
-----------------------------------------------------------------------------------------------------------------------------
_____________________________________________________________________________________________

**** MAIN MENU *****
 1. Place order
 2. Track order
 3. Display Database
 4. Add new DRONE
 5. Add new WAREHOUSE
 6. Add PRODUCT/s

Enter '0' to close the Application

 ENTER CHOICE = 0

_____________________________________________________________________________________________

*****APPLICATION CLOSED*****
_____________________________________________________________________________________________*/