package ie.ul.cs4084project;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class Item {

    private String name;
    private String description;
    private int price;
    @ServerTimestamp
    private Date timeStamp;

    public Item() { }

    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public Date getTimeStamp() { return timeStamp; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(int price){ this.price = price; }
    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }
}
