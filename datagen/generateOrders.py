import random
import json
from datetime import datetime, timedelta

# Helper function to generate random datetime in the desired format
def random_datetime(start_year=2025):
    start_date = datetime(start_year, 1, 1)
    end_date = datetime(start_year, 12, 31)
    delta = end_date - start_date
    random_days = random.randint(0, delta.days)
    random_time = start_date + timedelta(days=random_days, hours=random.randint(0, 23), minutes=random.randint(0, 59))
    return random_time.strftime('%Y-%m-%dT%H:%M:%S+0000')

# Helper function to generate a random purchase order
def generate_purchase_order(po_number):
    vendors = [
        ("Acme Industries", "VEN001", "John Smith", "123 Acme St"),
        ("Tech World", "VEN002", "Alice Brown", "456 Tech Ave"),
        ("Home Office Supplies", "VEN003", "Charlie Green", "789 Home Rd"),
        ("Office Essentials", "VEN004", "David Blue", "101 Office Pkwy"),
        ("Smart Supplies", "VEN005", "Emily White", "202 Smart Blvd"),
        ("Global Tech", "VEN006", "Frank Black", "303 Global Rd"),
        ("Workplace Goods", "VEN007", "Grace Green", "404 Workplace Blvd"),
        ("Tech Supplies", "VEN008", "Helen Yellow", "505 Tech Ave"),
        ("Creative Tools", "VEN009", "Ian Red", "606 Creative Rd"),
        ("Innovative Office", "VEN010", "Jane Purple", "707 Innovation St")
    ]

    vendor = random.choice(vendors)

    line_items = [
        {"itemId": 1001, "itemName": "Office Chair", "unitCost": 75.00, "quantity": 3, "totalCost": 225.00},
        {"itemId": 1002, "itemName": "Desk Lamp", "unitCost": 45.00, "quantity": 2, "totalCost": 90.00},
        {"itemId": 1003, "itemName": "Keyboard", "unitCost": 25.00, "quantity": 5, "totalCost": 125.00},
        {"itemId": 1004, "itemName": "Mouse", "unitCost": 15.00, "quantity": 10, "totalCost": 150.00},
        {"itemId": 1005, "itemName": "Desk Lamp", "unitCost": 45.00, "quantity": 4, "totalCost": 180.00},
        {"itemId": 1006, "itemName": "Monitor Stand", "unitCost": 30.00, "quantity": 3, "totalCost": 90.00},
        {"itemId": 1007, "itemName": "External Hard Drive", "unitCost": 120.00, "quantity": 2, "totalCost": 240.00},
        {"itemId": 1008, "itemName": "USB Cable", "unitCost": 5.00, "quantity": 10, "totalCost": 50.00},
        {"itemId": 1009, "itemName": "Printer Paper", "unitCost": 10.00, "quantity": 50, "totalCost": 500.00},
        {"itemId": 1010, "itemName": "File Folders", "unitCost": 2.00, "quantity": 100, "totalCost": 200.00}
    ]

    line_item_count = random.randint(1, 5)
    selected_items = random.sample(line_items, line_item_count)

    vendor_details = {
        "vendorName": vendor[0],
        "vendorId": vendor[1],
        "vendorContact": vendor[2],
        "vendorAddress": vendor[3]
    }

    return {
        "purchase_order_number": f"PO{po_number}",
        "order_date_time": random_datetime(),
        "vendor_details": json.dumps(vendor_details),
        "line_items": json.dumps(selected_items)
    }

# Generate 10,000 rows
rows = [generate_purchase_order(i) for i in range(10001, 20001)]

# Write to CSV file
import csv

with open("purchase_orders.csv", "w", newline='') as file:
    writer = csv.DictWriter(file, fieldnames=["purchase_order_number", "order_date_time", "vendor_details", "line_items"])
    writer.writeheader()
    for row in rows:
        writer.writerow(row)

print("CSV file with 10,000 rows has been generated.")