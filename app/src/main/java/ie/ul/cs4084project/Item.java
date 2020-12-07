package ie.ul.cs4084project;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class Item implements Parcelable {

    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private double latitude;
    private double longitude;
    private String itemImage;
    @ServerTimestamp
    private Date timeStamp;
    private String sellerName;
    private String sellerContact;

    /**
     * default constructor for items
     */
    public Item() {
    }

    /**
     * item constructor from parcel
     *
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
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(category);
    }
}
