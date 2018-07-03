package java.dao;

import java.entity.Product;
import java.model.PaginationResult;
import java.model.ProductInfo;

public interface ProductDAO {

	public Product findProduct(String code);
	public ProductInfo findProductInfo(String code);
	
	public PaginationResult<ProductInfo> queryProducts(int page,int maxResult,int maxNavigationPage,String likeName);
	public void save(ProductInfo productInfo);
}
