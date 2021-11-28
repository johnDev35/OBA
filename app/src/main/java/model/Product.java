package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product  implements Parcelable {

    private String marca;
    private String nombre;
    private int precio;
    private String categoria;
    private String und_medida;
    private String ubicacion;
    private String codigo;
    private String formato;
    private String url_imagen;


    protected Product(Parcel in) {
        marca = in.readString();
        nombre = in.readString();
        precio = in.readInt();
        categoria = in.readString();
        und_medida = in.readString();
        ubicacion = in.readString();
        codigo = in.readString();
        formato = in.readString();
        url_imagen = in.readString();
    }

    public Product() {
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
        dest.writeString(und_medida);
        dest.writeString(ubicacion);
        dest.writeString(codigo);
        dest.writeString(formato);
        dest.writeString(url_imagen);
    }


    public Product(String marca, String nombre, int precio, String categoria, String undMedida, String ubicacion, String codigo, String formato, String imagen) {
        this.marca = marca;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.und_medida = undMedida;
        this.ubicacion = ubicacion;
        this.codigo = codigo;
        this.formato = formato;
        this.url_imagen = imagen;
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
        return und_medida;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getImagen() {
        return url_imagen;
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
                ", undMedida='" + und_medida + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", codigo='" + codigo + '\'' +
                ", formato='" + formato + '\'' +
                ", imagen=" + url_imagen +
                '}';
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setUnd_medida(String und_medida) {
        this.und_medida = und_medida;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
