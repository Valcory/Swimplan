package se.valcory.swimplan.to;

import android.os.Parcel;
import android.os.Parcelable;

public class SwimmingStyle implements Parcelable {

    private int id;
    private String name;

    public SwimmingStyle() {
        super();
    }

    public SwimmingStyle(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public SwimmingStyle(String name) {
        this.name = name;
    }

    private SwimmingStyle(Parcel in) {
        super();
        this.id = in.readInt();
        this.name = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public String toString() {
        return name;
       // return "id:" + id + ", name:" + name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
    }

    public static final Parcelable.Creator<SwimmingStyle> CREATOR = new Parcelable.Creator<SwimmingStyle>() {
        public SwimmingStyle createFromParcel(Parcel in) {
            return new SwimmingStyle(in);
        }

        public SwimmingStyle[] newArray(int size) {
            return new SwimmingStyle[size];
        }
    };

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SwimmingStyle other = (SwimmingStyle) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
