package com.veganSitavi.domain;

public class Item extends Producto{
    
    private int cantidad; // Para registrar la info de cuantos unidades quiero de este producto

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public Item(){}
    
    public Item(Producto p){
        super.setCategoria(p.getCategoria());
        super.setDescripcion(p.getDescripcion());
        super.setNombre(p.getNombre());
        super.setExistencias(p.getExistencias());
        super.setIdProducto(p.getIdProducto());
        super.setPrecio(p.getPrecio());
        super.setImagen(p.getImagen());
        this.cantidad=0;
    }
    
    
    
}
