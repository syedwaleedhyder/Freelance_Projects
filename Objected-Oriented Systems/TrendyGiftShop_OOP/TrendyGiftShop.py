#######
# GIFT ITEMS
#######


class GiftItem:
    def __init__(self, item_id, price, quantity, description):
        self._id = item_id
        self._price = price
        self._quantity_available = quantity
        self._description = description

    def get_id(self):
        return self._id

    def set_id(self, item_id):
        self._id = item_id

    def get_description(self):
        return self._description

    def set_description(self, description):
        self._description = description

    def get_price(self):
        return self._price

    def set_price(self, price):
        self._price = price

    def set_quantity_available(self, quantity):
        self._quantity_available = quantity

    def get_quantity_available(self):
        return self._quantity_available


class GreetingCard(GiftItem):
    def __init__(self, item_id, price, quantity, description, size):
        super().__init__(id, price, quantity, description)
        self.__size = size

    def get_size(self):
        return self.__size

    def set_size(self, size):
        self.__size = size


class Souvenir(GiftItem):
    def __init__(self, item_id, price, quantity, description, souvenir_type):
        super().__init__(item_id, price, quantity, description)
        self.__souvenir_type = souvenir_type

    def get_souvenir_type(self):
        return self.__souvenir_type

    def set_souvenir_type(self, souvenir_type):
        self.souvenir_type = souvenir_type


class Toy(GiftItem):
    def __init__(self, item_id, price, quantity, description, toy_type):
        super().__init__(item_id, price, quantity, description)
        self.__toy_type = toy_type

    def get_toy_type(self):
        return self.__toy_type

    def set_toy_type(self, toy_type):
        self.__toy_type = toy_type


########
# CART
########
class Cart:
    def __init__(self):
        self.__GiftItems = []

    def addItem(self, gift_item):
        self.__GiftItems.append(gift_item)

    def checkout(self, card):
        total_price = 0

        for gift_item in self.__GiftItems:
            print(gift_item.get_ID, gift_item.get_price())
            total_price += gift_item.get_price()

        self.__GiftItems = []

        return card.charge(total_price)

#########
#CARD
#########

class Card:
    def __init__(self, cardnumber):
        self.__card_number = cardnumber

    def charge(self, amount):
        print(amount, " charged on card: ", self.__card_number)

    def get_card_details(self):
        return self.__card_number

    def set_card_details(self, cardnumber):
        self.__card_number = cardnumber

#########
# CUSTOMER
########
class Customer:
    def __init__(self):
        self.__user_ID = None
        self.__email = ""
        self.__cart = Cart()
        self.__order_history = []
        self.__card = None

    def sign_up(self):
        self.__user_ID = int(input('Enter User ID: '))
        self.__email = input('Enter Email: ')
        self.__username = input('Enter User Name: ')
        self.__password = input('Enter password: ')
        self.__card = Card(input('Enter card: '))

    def login(self, username, password):
        if self.__password == password and self.__username == username:
            return True
        else:
            False

    def get_user_ID(self):
        return self.__userID

    def set_user_ID(self, userid):
        self.__userID = userid

    def get_email(self):
        return self.__email

    def set_email(self, email):
        self.__email = email

    def get_username(self):
        return self.__username

    def set_username(self, uname):
        self.__username = uname

    def get_paswword(self):
        return self.__password

    def set_paswword(self, password):
        self.__password = password

    def checkout(self):
        self.__cart.checkout(self.__card)
        self.__order_history.append(Cart)
    
    def get_order_history(self):
        for order in self.__order_history:
            print(order)

###########
# WEBSITE
##########

class Website:
    def __init__(self):
        self.__customers = []
        self.__gift_items = []

    def register(self, customer):
        customer.sign_up()
        self.__customers.append(customer)

    def add_gift_item(self, gift_item):
        self.__gift_items.append(gift_item)

    def get_customers(self):
        for customer in self.__customers:
            print(customer.get_username())

    def get_items(self):
        for item in self.__gift_items:
            print(item.get_description())
            print(item.get_quantity_available())