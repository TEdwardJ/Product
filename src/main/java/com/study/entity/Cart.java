package com.study.entity;

import java.util.*;

public class Cart implements Iterable<CartItem>{

    private Set<CartItem> cartSet = new HashSet<>();

    public void addProduct(Product product){
        CartItem cartItem = getCartItem(product).orElse(new CartItem(product,0));
        cartItem.incNumber();
        cartSet.add(cartItem);
    }

    private Optional<CartItem> getCartItem(Product product){
        return cartSet.stream()
                .filter(t->t.getProduct().equals(product))
                .findFirst();
    }

    public void deleteProduct(Product product){

    }

    @Override
    public Iterator<CartItem> iterator() {
        return cartSet.iterator();
    }

    public Collection<CartItem> getContent(){
        return cartSet;
    }
}
