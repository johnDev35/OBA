package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product  implements Parcelable {

    private String marca;
    private String nombre;
    private int precio;
    private String categoria;
    private String undMedida;
    private String ubicacion;
    private String codigo;
    private String formato;
    private int imagen;


    protected Product(Parcel in) {
        marca = in.readString();
        nombre = in.readString();
        precio = in.readInt();
        categoria = in.readString();
        undMedida = in.readString();
        ubicacion = in.readString();
        codigo = in.readString();
        formato = in.readString();
        imagen = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(marca);
        dest.writeString(nombre);
        dest.writeInt(precio);
        dest.writeString(categoria);
        dest.writeString(undMedida);
        dest.writeString(ubicacion);
        dest.writeString(codigo);
        dest.writeString(formato);
        dest.writeInt(imagen);
    }


    public Product(String marca, String nombre, int precio, String categoria, String undMedida, String ubicacion, String codigo, String formato, int imagen) {
        this.marca = marca;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.undMedida = undMedida;
        this.ubicacion = ubicacion;
        this.codigo = codigo;
        this.formato = formato;
        this.imagen = imagen;
    }

    public String getMarca() {
        return marca;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getUndMedida() {
        return undMedida;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getImagen() {
        return imagen;
    }

    public String getFormato() {
        return formato;
    }

    @Override
    public String toString() {
        return "Product{" +
                "marca='" + marca + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                ", undMedida='" + undMedida + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", codigo='" + codigo + '\'' +
                ", formato='" + formato + '\'' +
                ", imagen=" + imagen +
                '}';
    }

}
