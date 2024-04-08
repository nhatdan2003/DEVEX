package com.Devex.Sevice.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.ShoppingCartDTO;
import com.Devex.Sevice.ShoppingCartService;


@Service
@SessionScope
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartDTO cart;
    
    @Autowired
    private ProductRepository dao;
//
//	@Override
//	public CartProdcut add(String id, int SoLuong, String Size, String Color) {
//		CartProdcut cartProduct = cart.getItems().get(id);
//		
//        if (cartProduct == null) {
//            Product product = dao.findByIdProduct(id);
//            if (product != null ) {
//				ProductVariant variant = product.getProductVariants().get(0);
//				String shopname = product.getSellerProduct().getShopName();
//                cartProduct = new CartProdcut();
//                cartProduct.setId(product.getId());
//                cartProduct.setName(product.getName());
//                cartProduct.setImg(product.getImageProducts().get(0).getName());
//                cartProduct.setColor(Size);
//                cartProduct.setSize(Color);
//                cartProduct.setSoluong(SoLuong);
//                cartProduct.setPrice(variant.getPriceSale());
//                cartProduct.setNameShop(shopname);
//                cart.getItems().put(id, cartProduct);
//            }
//           
//        }
//        else {
//        	System.out.println("đã tới");
//            cartProduct.setSoluong(cartProduct.getSoluong() + 1);
//            
//        }
//        
//        
//        return cartProduct;
		
	
	// Phương thức để in thông tin sản phẩm đã thêm vào giỏ hàng


//	@Override
//	public void remove(String id) {
//		cart.getItems().remove(id);
//	}
//		
//	
//	@Override
//	public CartProdcut update(String id, int qty) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void clear() {
//		cart.getItems().clear();
//	}
//
//	@Override
//	public Collection<CartProdcut> getItems() {
//		return cart.getItems().values();
//	}
//
//	@Override
//	public int getCount() {
//		
//		return cart.getItems().values().stream().mapToInt(CartProdcut::getSoluong).sum();
//	}
//
//	@Override
//	public double getAmount() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setItems(Map<String, CartProdcut> items) {
//		this.cart.setItems(items);
//		
//	}

   

}

