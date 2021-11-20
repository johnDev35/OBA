package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {
    private Product producto;
    private int cantidad;
    private int valorItem;

    protected Cart(Parcel in) {
        producto = in.readParcelable(Product.class.getClassLoader());
        cantidad = in.readInt();
        valorItem = in.readInt();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(producto, flags);
        dest.writeInt(cantidad);
        dest.writeInt(valorItem);
    }

    public Cart(Product producto, int cantidad, int valorItem) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.valorItem = valorItem;
    }

    public Product getProducto() {
        return producto;
    }

    public void setProducto(Product producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getValorItem() {
        return valorItem;
    }

    public void setValorItem(int valorItem) {
        this.valorItem = valorItem;
    }
}
