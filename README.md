# Ecommerce Website ( DamEcom )

A MVC modelled ecommerce site, using JSTL to template and display. Backed by JEE servlets and mongodb. 
* Shopping basket management and checkout functionality
* Order History view
* Authentication, 256bit hash stored passwords
* Shopping basket persistence accross different login sessions
* Product grouping submenu for quick navigation
 
![ecom_main](https://cloud.githubusercontent.com/assets/6975806/6029979/5b9f06aa-abf4-11e4-8de8-2b108e0dadd4.png)

## What I coded:

Firstly, it should be said that this was my very first attempt at an Ecom website after more than 10 years of not coding anything.

The database backend is MongoDB, with a collection for products and users. There is a product grouping mechanism but it is limited to one subgroup only. All pages are templated using the java tag library, and variables are passed via jstl to be processed for page output.

The website is designed using the MVC pattern, I have several database access objects that are called from controller servlets that apply logic to requests and fetch data, then renders the server side templated page and sends to the user.

## Where is it?:

You can access the demo site [here](https://damcode.duckdns.org/DamEcomWeb_v1/), and use login / pass : test / test to access the secured areas.

![ecom_basket](https://cloud.githubusercontent.com/assets/6975806/6029978/5b9de130-abf4-11e4-90d8-bc422ed16636.png)
![ecom_cust](https://cloud.githubusercontent.com/assets/6975806/6029981/5b9fde54-abf4-11e4-922f-aaa238002a2d.png)
![ecom_orderhist](https://cloud.githubusercontent.com/assets/6975806/6029980/5b9f2cde-abf4-11e4-92ae-f02c7cac0634.png)
![ecom_singleprod](https://cloud.githubusercontent.com/assets/6975806/6029982/5ba06568-abf4-11e4-804b-62b8adb8ac4b.png)
