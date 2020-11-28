package ie.ul.cs4084project;


import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;


public class Item {

    private String name;
    private String description;
    private double price;
    private String category;
    @ServerTimestamp
    private Date timeStamp;

    public Item() { }

    public Item(String name, String description, double price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category;}
    public Date getTimeStamp() { return timeStamp; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price){ this.price = price; }
    public void setCategory(String catrgory){ this.category = category;}
    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }
}
