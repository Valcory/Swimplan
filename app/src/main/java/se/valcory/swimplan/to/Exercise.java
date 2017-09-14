package se.valcory.swimplan.to;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {

    private int id;
    private String name;
    private int distance;
    private int repetition;

    private SwimmingStyle swimmingStyle;

    public Exercise() {
        super();
    }

    private Exercise(Parcel in) {
        super();
        this.id = in.readInt();
        this.name = in.readString();
        this.distance = in.readInt();
        this.repetition = in.readInt();

        this.swimmingStyle = in.readParcelable(SwimmingStyle.class.getClassLoader());
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public SwimmingStyle getSwimmingStyle() {
        return swimmingStyle;
    }

    public void setSwimmingStyle(SwimmingStyle swimmingStyle) {
        this.swimmingStyle = swimmingStyle;
    }

    @Override
    public String toString() {
        return "Exercise [id=" + id + ", name=" + name + ", distance=" + distance + ", repetition=" + repetition + ", swimmingstyle="
                + swimmingStyle + "]";
    }

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
        Exercise other = (Exercise) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeInt(getDistance());
        parcel.writeInt(getRepetition());
        parcel.writeParcelable(getSwimmingStyle(), flags);
    }

    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

}
