package ie.ul.cs4084project;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Item implements Parcelable {

    // item id, item name, item description, item category and itemImage Uri
    private String id;
    private String name;
    private String description;
    private String category;
    private String itemImage;
    // item price, item latitude, item longitude
    private double price;
    private double latitude;
    private double longitude;
    //item timeStamp from firebase
    @ServerTimestamp
    private Date timeStamp;
    // seller user name and seller email
    private String sellerName;
    private String sellerContact;

    /**
     * default constructor for items
     */
    public Item() {
    }

    /**
     * item constructor from parcel
     * @param in a parcel containing an item
     */
    protected Item(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        category = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        itemImage = in.readString();
        sellerName = in.readString();
        sellerContact = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /**
     * @return id String id of item
     */
    public String getId() {
        return id;
    }

    /**
     * @return name String name of item
     */
    public String getName() {
        return name;
    }

    /**
     * @return description String description of item
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return price Double price of item
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return category String category of item
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return latitude Double latitude of item location
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return longitude Double longitude of item Location
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return timeStamp Date timestamp of item creation from Firebase servers
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return sellerName String user name of item seller
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @return sellerContact String email of item seller
     */
    public String getSellerContact() {
        return sellerContact;
    }

    /**
     * @return itemImage String Uri of item image in String form
     */
    public String getItemImage() {
        return itemImage;
    }

    /**
     * method sets item id
     *
     * @param id String item id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * method sets item name
     *
     * @param name String item name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method sets item description
     *
     * @param description String item description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * method sets item price
     *
     * @param price Double item price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * method sets item category
     *
     * @param category String item category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * method sets item latitude
     *
     * @param latitude Double item latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * method sets item longitude
     *
     * @param longitude Double item latitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * method sets item timeStamp
     *
     * @param timeStamp Date item time stamp
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * method sets item seller user name
     *
     * @param sellerName String seller user name
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * method sets item image Uri
     *
     * @param itemImage String item image Uri
     */
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    /**
     * method sets item seller email
     *
     * @param sellerContact String seller email
     */
    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * method writes item attributes to parcel
     *
     * @param dest  Parcel to store item data
     * @param flags int
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(category);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(itemImage);
        dest.writeString(sellerName);
        dest.writeString(sellerContact);
    }
}
