from abc import ABCMeta, abstractmethod, abstractproperty
from datetime import datetime, date

class Item(metaclass=ABCMeta):
    def __init__(self, code, name, quantity, cost, offer):
        self.item_code=code
        self.item_name=name
        self.quantity_on_hand=quantity
        self.cost_price=cost
        self.on_offer=offer
        pass


    @property
    def quantity_on_hand(self): # implements the get - this name is *the* name
        return self._quantity_on_hand
    #
    @quantity_on_hand.setter
    def quantity_on_hand(self, value): # name must be the same
        self._quantity_on_hand = value

    @property
    def cost_price(self): # implements the get - this name is *the* name
        return self._cost_price
    #
    @cost_price.setter
    def cost_price(self, value): # name must be the same
        self._cost_price = value

    def changeOffer():
        if(self.on_offer == "Yes"):
            self.on_offer = "No"
        elif(self.on_offer == "No"):
            self.on_offer == "Yes"

    @abstractmethod
    def selling_price(self):
        pass
    @abstractmethod
    def offer_price(self):
        pass
    @abstractmethod
    def profit_margin(self):
        pass
    @abstractmethod
    def discount_rate(self):
        pass
    def to_string(self):
        if(self.on_offer == "Yes"):
            offer = "**Offer"
        else:
            offer = "(No Offer)"
        string = self.item_code + " " + self.item_name + " Availalbe= " + str(self.quantity_on_hand) + " " + offer
        return string

class Perishable(Item):
    def __init__(self, code, name, quantity, cost, offer, expiry):
        Item.__init__(self, code, name, quantity, cost, offer)
        self.expiry_date = expiry

    def profit_margin(self):
        return self.cost_price * 0.25

    def selling_price(self):
        return self.cost_price + self.profit_margin()
    
    def days_before_expiry(self):
        now = datetime.now().date()
        days = self.expiry_date- now
        return days.days
    
    def discount_rate(self):
        days = self.days_before_expiry()
        price = self.selling_price()
        if(days < 15):
            return price * 0.3
        elif(days < 30):
            return price * 0.2
        elif (days > 29):
            return price * 0.1

    def offer_price(self):
        if(self.on_offer == "No"):
            return selling_price()
        return self.selling_price() - self.discount_rate()

    def to_string(self):
        if(self.on_offer == "Yes"):
            offer = "**Offer**"
        else:
            offer = "(No Offer)"
        string = self.item_code + " " + self.item_name + " Available= " + str(self.quantity_on_hand) + " Price: $" + str(self.offer_price()) +" " + offer + " Expiry Date: " + self.expiry_date.strftime('%d %b %Y') + " Perishable Item"
        return string
    
class NonPerishable(Item):
    def __init__(self, code, name, quantity, cost, offer):
        Item.__init__(self, code, name, quantity, cost, offer)
    def profit_margin(self):
        return self.cost_price * 0.3

    def selling_price(self):
        return self.cost_price + self.profit_margin()
    
    def discount_rate(self):
        return self.selling_price() * 0.1

    def offer_price(self):
        if(self.on_offer == "No"):
            return self.selling_price()
        return self.selling_price() - self.discount_rate()

    def to_string(self):
        if(self.on_offer == "Yes"):
            offer = "**Offer**"
        else:
            offer = "(No Offer)"
        string = self.item_code + " " + self.item_name + " Available= " + str(self.quantity_on_hand) + " Price: $" + str(self.offer_price()) +" " + offer + " Non Perishable Item" 
        return string

class Grocer:
    def __init__(self):
        self.items_list = []
    
    def print_items(self):
        for item in self.items_list:
            print(item.to_string())
    
    def add_to_list(self, item_to_be_added):
        self.items_list.append(item_to_be_added)
        return

    def update_quantity_on_hand(self, item_code, new_quantity):
        if(new_quantity < 0):
            print("Quantity cannot be zero. Failed to update.")
            return False
        for item in self.items_list:
            if(item.item_code == item_code):
                item.quantity_on_hand = new_quantity
                return True


perishable = Perishable("P101", "Real Raisins", 10, 2, "Yes", date(2018,12, 10))
non_perishable = NonPerishable("NP210", "Tan Baking Paper", 25, 2, "No")
perishable2 = Perishable("P105", "Eggy Soup Tofu", 14, 1.85, "Yes", date(2018,11, 26))

grocer = Grocer()
grocer.add_to_list(perishable)
grocer.add_to_list(non_perishable)
grocer.add_to_list(perishable2)

grocer.print_items()
grocer.update_quantity_on_hand("P105", 10)
print()
grocer.print_items()


####################################################################
#DISCUSSION
"""
Single Responsibility Principle:
1) IN Perishable clas.
2) In NonPersishable class.
Open Closed Principle
1) Abstract class Item is open to be extended
2) Abstract class Item is closed for modification
Interface Segregation Principle
1) For using Perishable items, user don't have to know anything about Non-perishable items.
2) For using Non-perishable items, users don't have to know tha details of Perishable items.
Hence users are not forced to use methods they don't require.
"""
####################################################################