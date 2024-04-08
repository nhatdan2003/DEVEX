package com.Devex.Controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Devex.DTO.SellerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Devex.DTO.infoProductDTO;
import com.Devex.Entity.Category;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductBrand;
import com.Devex.Entity.Request;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.Seller;
import com.Devex.Entity.User;
import com.Devex.Sevice.CategoryDetailService;
import com.Devex.Sevice.CategoryService;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.FileManagerService;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.ProductBrandService;
import com.Devex.Sevice.RequestService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SellerService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;
import com.Devex.Sevice.ServiceImpl.CustomerServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.server.PathParam;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProductAdminRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CookieService cookieService;
	
	@Autowired
	private NotificationsService notificationsService;
	
	@Autowired
	private CategoryDetailService categoryDetailService;
	
	@Autowired 
	private CategoryService categoryService;
	
	@Autowired
	private NotiService notiService;
	
	@Autowired
	private ProductBrandService brandService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
    private FileManagerService fileManagerService;
	
	@Autowired
	private ImageProductService imageProductService;
	
	@Autowired
	private ProductVariantService productVariantService;
	
	@Value("${myapp.file-storage-path}")
	private String fileStoragePath;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping("/getcategory")
	public List<Category> getAllCategory(){
		return categoryService.findAllCategoryNotNameLikeUnknown();
	}
	
	@GetMapping("/getcategorydetails")
	public List<CategoryDetails> getAllCategoryDetailsByCategoryId(@RequestParam("id") int id){
		return categoryDetailService.findAllCategoryDetailsNotNameLikeUnknownAndCateId(id);
	}
	
	@PostMapping("/add/category")
	public Map<String, Object> insertCategory(@RequestParam("name") String name){
		categoryService.insertCategory(name);
		Map<String, Object> mapAllCategory = new HashMap<>();
		Category c = categoryService.getCategoryNew();
		List<Category> listCategory = categoryService.findAllCategoryNotNameLikeUnknown();
		mapAllCategory.put("id", c.getId());
		mapAllCategory.put("listCategory", listCategory);
		return mapAllCategory;
	}
	
	@PostMapping("/add/categorydetails")
	public List<CategoryDetails> insertCategoryDetails(@RequestParam("name") String name, @RequestParam("id") int id){
		categoryDetailService.insertCategoryDetails(name, id);
		return categoryDetailService.findAllCategoryDetailsNotNameLikeUnknownAndCateId(id);
	}
	
	@DeleteMapping("/delete/category")
	public List<Category> deleteCategory(@RequestParam("id") int id){
		categoryService.deleteById(id);
		return categoryService.findAllCategoryNotNameLikeUnknown();
	}
	
	@DeleteMapping("/delete/categorydetails")
	public List<CategoryDetails> deleteCategoryDetails(@RequestParam("iddelete") int iddelete, @RequestParam("idfill") int idfill){
		categoryDetailService.deleteById(iddelete);
		return categoryDetailService.findAllCategoryDetailsNotNameLikeUnknownAndCateId(idfill);
	}
	
	@PutMapping("/update/categorydetails")
	public List<CategoryDetails> updateCategoryDetails(@RequestParam("id") int id, @RequestParam("idfill") int idfill){
		List<Product> listProduct = productService.findProductsByCategoryDetailsId(id);
		boolean check = false;
		for (Product p : listProduct) {
			productService.updateProductCategoryByIdCategory(101, p.getId());
			check = true;
		}
		if(check == true) {
			categoryDetailService.deleteById(id);
		}
		return categoryDetailService.findAllCategoryDetailsNotNameLikeUnknownAndCateId(idfill);
	}
	
	@PutMapping("/update/category")
	public List<Category> updateCategory(@RequestParam("id") int id){
		List<CategoryDetails> listCategoryDetails = categoryDetailService.findAllCategoryDetailsById(id);
		for (CategoryDetails cad : listCategoryDetails) {
			List<Product> listProduct = productService.findProductsByCategoryDetailsId(cad.getId());
			for (Product p : listProduct) {
				productService.updateProductCategoryByIdCategory(101, p.getId());
			}
			categoryDetailService.deleteById(cad.getId());
		}
		categoryService.deleteById(id);
		return categoryService.findAllCategoryNotNameLikeUnknown();
	}
	
	@GetMapping("/getamountproduct")
	public int getCountProductByCategoryDetailsId(@RequestParam("id") int id) {
		return productService.getCountProductByCategoryId(id);
	}
	
	@GetMapping("getamountcategorydetails")
	public int getCountCategoryDetailsByCategoryId(@RequestParam("id") int id) {
		return categoryDetailService.getCountCategoryDetailsByCategoryId(id);
	}
	
	@GetMapping("/getproductbrand")
	public List<ProductBrand> getAllProductBrand(){
		return brandService.getProductBrandNotUnknown();
	}
	
	@GetMapping("/getamountproductbrand")
	public int getCountProductByProductBrandId(@RequestParam("id") int id) {
		return productService.getCountProductByProductBrandId(id);
	}
	
	@DeleteMapping("/delete/productbrand")
	public List<ProductBrand> deleteProductBrand(@RequestParam("id") int id){
		brandService.deleteById(id);
		return brandService.getProductBrandNotUnknown();
	}
	
	@PutMapping("/update/productbrand")
	public List<ProductBrand> updateProductBrand(@RequestParam("id") int id){
		List<Product> listProduct = productService.findAllProductByProductBrandId(id);
		for (Product p : listProduct) {
			productService.updateProductProductBrandByIdProductBrand(101, p.getId());
		}
		brandService.deleteById(id);
		return brandService.getProductBrandNotUnknown();
	}
	
	@PostMapping("/add/productbrand")
	public List<ProductBrand> insertProductBrand(@RequestParam("name") String name){
		brandService.insertProductBrand(name);
		return brandService.getProductBrandNotUnknown();
	}
	
	@PutMapping("/update/savecategory")
	public Map<String, Object> updateNameCategoryHaveData(@RequestParam("id") int id, @RequestParam("name") String name){
		categoryService.updateCategory(name, id);
		Map<String, Object> mapca = new HashMap<>();
		List<Category> listca = categoryService.findAllCategoryNotNameLikeUnknown();
		mapca.put("listca", listca);
		mapca.put("id", id);
		return mapca;
	}
	
	@PutMapping("/update/savecategorydetails")
	public int updateNameCategoryDetailsHaveData(@RequestParam("id") int id, @RequestParam("name") String name){
		categoryDetailService.updateCategoryDetails(name, id);
		CategoryDetails cad = categoryDetailService.findCategoryDetailsById(id);
		return cad.getCategory().getId();
	}
	
	@PutMapping("/update/saveproductbrand")
	public List<ProductBrand> updateNameProductBrandHaveData(@RequestParam("id") int id, @RequestParam("name") String name){
		brandService.updateProductBrand(name, id);
		return brandService.getProductBrandNotUnknown();
	}
	
	@GetMapping("/getallproductrequest")
	public Map<String, Object> getAllProductRequest(){
		Map<String, Object> mapProductRequest = new HashMap<>();
		List<infoProductDTO> listInfoProduct = new ArrayList<>();
		List<String> listNameCategory = new ArrayList<>();
		List<String> listNameProductBrand = new ArrayList<>();
		List<String> listShopName = new ArrayList<>();
		List<Request> listProductRequest = requestService.getAllRequestFalseDecreaseByCreatedDay();
		for (Request pr : listProductRequest) {
			Product p = productService.findByIdProduct(pr.getEntityId());
			if(p != null) {
				infoProductDTO pd = new infoProductDTO();
				pd.setId(p.getId());
				pd.setName(p.getName());
				pd.setActive(p.getActive());
				pd.setSoldCount(p.getSoldCount());
				pd.setQuantity(p.getProductVariants().get(0).getQuantity());
				pd.setPrice(p.getProductVariants().get(0).getPrice());
				pd.setPriceSale(p.getProductVariants().get(0).getPriceSale());
				pd.setNameImageProduct(p.getImageProducts().get(0).getName());
				pd.setCateId(p.getCategoryDetails().getId());
				listInfoProduct.add(pd);
				CategoryDetails cad = categoryDetailService.findCategoryDetailsById(p.getCategoryDetails().getId());
				listNameCategory.add(cad.getName());
				listNameProductBrand.add(brandService.findNameProductBrandById(p.getProductbrand().getId()));
				listShopName.add(p.getSellerProduct().getShopName());
			}
		}
		mapProductRequest.put("listProductRequest", listProductRequest);
		mapProductRequest.put("listInfoProduct", listInfoProduct);
		mapProductRequest.put("listNameCategory", listNameCategory);
		mapProductRequest.put("listNameProductBrand", listNameProductBrand);
		mapProductRequest.put("listShopName", listShopName);
		return mapProductRequest;
	}
	
	@DeleteMapping("/delete/productrequest")
	public Map<String, Object> deleteProductRequest(@RequestParam("id") int id){
		requestService.deleteById(id);
		Map<String, Object> mapProductRequest = new HashMap<>();
		List<infoProductDTO> listInfoProduct = new ArrayList<>();
		List<String> listNameCategory = new ArrayList<>();
		List<String> listNameProductBrand = new ArrayList<>();
		List<String> listShopName = new ArrayList<>();
		List<Request> listProductRequest = requestService.getAllRequestFalseDecreaseByCreatedDay();
		for (Request pr : listProductRequest) {
			Product p = productService.findByIdProduct(pr.getEntityId());
			if(p != null) {
				infoProductDTO pd = new infoProductDTO();
				pd.setId(p.getId());
				pd.setName(p.getName());
				pd.setActive(p.getActive());
				pd.setSoldCount(p.getSoldCount());
				pd.setQuantity(p.getProductVariants().get(0).getQuantity());
				pd.setPrice(p.getProductVariants().get(0).getPrice());
				pd.setPriceSale(p.getProductVariants().get(0).getPriceSale());
				pd.setNameImageProduct(p.getImageProducts().get(0).getName());
				pd.setCateId(p.getCategoryDetails().getId());
				listInfoProduct.add(pd);
				CategoryDetails cad = categoryDetailService.findCategoryDetailsById(p.getCategoryDetails().getId());
				listNameCategory.add(cad.getName());
				listNameProductBrand.add(brandService.findNameProductBrandById(p.getProductbrand().getId()));
				listShopName.add(p.getSellerProduct().getShopName());
			}
		}
		mapProductRequest.put("listProductRequest", listProductRequest);
		mapProductRequest.put("listInfoProduct", listInfoProduct);
		mapProductRequest.put("listNameCategory", listNameCategory);
		mapProductRequest.put("listNameProductBrand", listNameProductBrand);
		mapProductRequest.put("listShopName", listShopName);
		return mapProductRequest;
	}
	
	@PutMapping("/update/productrequest")
	public Map<String, Object> updateProductRequest(@RequestParam("id") int id){
		Request e = requestService.findRequestById(id);
		Product pro = productService.findByIdProduct(e.getEntityId());
		productService.updateProductActiveById(true, pro.getId());
		requestService.deleteById(id);
		Map<String, Object> mapProductRequest = new HashMap<>();
		List<infoProductDTO> listInfoProduct = new ArrayList<>();
		List<String> listNameCategory = new ArrayList<>();
		List<String> listNameProductBrand = new ArrayList<>();
		List<String> listShopName = new ArrayList<>();
		List<Request> listProductRequest = requestService.getAllRequestFalseDecreaseByCreatedDay();
		for (Request pr : listProductRequest) {
			Product p = productService.findByIdProduct(pr.getEntityId());
			if(p != null) {
				infoProductDTO pd = new infoProductDTO();
				pd.setId(p.getId());
				pd.setName(p.getName());
				pd.setActive(p.getActive());
				pd.setSoldCount(p.getSoldCount());
				pd.setQuantity(p.getProductVariants().get(0).getQuantity());
				pd.setPrice(p.getProductVariants().get(0).getPrice());
				pd.setPriceSale(p.getProductVariants().get(0).getPriceSale());
				pd.setNameImageProduct(p.getImageProducts().get(0).getName());
				pd.setCateId(p.getCategoryDetails().getId());
				listInfoProduct.add(pd);
				CategoryDetails cad = categoryDetailService.findCategoryDetailsById(p.getCategoryDetails().getId());
				listNameCategory.add(cad.getName());
				listNameProductBrand.add(brandService.findNameProductBrandById(p.getProductbrand().getId()));
				listShopName.add(p.getSellerProduct().getShopName());
			}
		}
		mapProductRequest.put("listProductRequest", listProductRequest);
		mapProductRequest.put("listInfoProduct", listInfoProduct);
		mapProductRequest.put("listNameCategory", listNameCategory);
		mapProductRequest.put("listNameProductBrand", listNameProductBrand);
		mapProductRequest.put("listShopName", listShopName);
		return mapProductRequest;
	}
	
	@GetMapping("/getlistseller")
	public List<SellerDTO> getListSeller(){
		return sellerService.findAllSeller();
	}
	
	@GetMapping("/search/seller")
	public List<Seller> searchSeller(@RequestParam("shopname") String shopname){
		return sellerService.findByShopNameContainingKeyword(shopname);
	}
	
	@GetMapping("/getlistproduct")
	public Map<String, Object> getListProductByUsername(@RequestParam("username") String username){
		Map<String, Object> mapInfoProduct = new HashMap<>();
		List<infoProductDTO> listInfoProduct = new ArrayList<>();
		List<String> listNameCategory = new ArrayList<>();
		List<String> listNameProductBrand = new ArrayList<>();
		List<Product> listProduct = productService.findProductByShopUsername(username);
		for (Product p : listProduct) {
			infoProductDTO ip = new infoProductDTO();
			ip.setId(p.getId());
			ip.setName(p.getName());
			ip.setActive(p.getActive());
			ip.setSoldCount(p.getSoldCount());
			ip.setQuantity(p.getProductVariants().get(0).getQuantity());
			ip.setPrice(p.getProductVariants().get(0).getPrice());
			ip.setPriceSale(p.getProductVariants().get(0).getPriceSale());
			ip.setNameImageProduct(p.getImageProducts().get(0).getName());
			ip.setCateId(p.getCategoryDetails().getId());
			listInfoProduct.add(ip);
			listNameCategory.add(p.getCategoryDetails().getName());
			listNameProductBrand.add(p.getProductbrand().getName());
		}
		mapInfoProduct.put("listInfoProduct", listInfoProduct);
		mapInfoProduct.put("listNameCategory", listNameCategory);
		mapInfoProduct.put("listNameProductBrand", listNameProductBrand);
		return mapInfoProduct;
	}
	
	@GetMapping("/img/product")
	public List<String> listImage() {
		String username = sessionService.get("usernameseller");
		String idproduct = sessionService.get("idproduct");
		List<String> imageUrls = fileManagerService.list(idproduct, username);
		return imageUrls;
	}
	
	@GetMapping("/ad/product")
	public Map<String, Object> getProduct() {
		String id = sessionService.get("idproduct");
		Map<String, Object> mapProduct = new HashMap<>();
		Product product = productService.findByIdProduct(id);
		Request productrequest = requestService.findRequestByEntityId(id);
		boolean check = false;
		if(productrequest != null) {
			check = true;
		}
		mapProduct.put("product", product);
		mapProduct.put("check", check);
		System.out.println(check+"aaaaaaaaaaa");
		if(check == true) {
			mapProduct.put("idproductrequest", productrequest.getId());
			mapProduct.put("content", productrequest.getContent());
		}
		return mapProduct;
	}
	
	@GetMapping("/ad/categoryDetails/{idca}")
	public List<CategoryDetails> listCategoryDetails(@PathVariable("idca") int idca) {
		int id = idca;
		List<CategoryDetails> listcategory = categoryDetailService.findAllCategoryDetailsById(id);
		return listcategory;
	}

	@GetMapping("/ad/idCategoryDetails")
	public int idCategorydetails() {
		String idp = sessionService.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getCategoryDetails().getId();
		return id;
	}

	@GetMapping("/ad/idcategory")
	public int idCategory() {
		String idp = sessionService.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getCategoryDetails().getCategory().getId();
		return id;
	}

	@GetMapping("/ad/category")
	public List<Category> idCategoryDetails() {
		List<Category> listCategory = categoryService.findAll();
		return listCategory;
	}

	@GetMapping("/ad/brand")
	public List<ProductBrand> listBrand() {
		return brandService.findAll();
	}

	@GetMapping("/ad/idbrand")
	public int idBrand() {
		String idp = sessionService.get("idproduct");
		Product p = productService.findByIdProduct(idp);
		int id = p.getProductbrand().getId();
		return id;
	}
	
	@GetMapping("/ad/productvariant")
	public List<ProductVariant> getProductVariant() {
		String idp = sessionService.get("idproduct");
		List<ProductVariant> listproductvariant = productVariantService.findAllProductVariantByProductId(idp);
		return listproductvariant;
	}
	
	@DeleteMapping("/ad/productvariant/delete/{id}")
	public void deleteProductVariant(@PathVariable("id") Integer id) {
		productVariantService.deleteById(id);
	}
	
	@SuppressWarnings("unused")
	@PutMapping("/ad/info/product")
	public void updateProduct(@RequestBody Object object) throws ParseException {
		String idp = sessionService.get("idproduct");
		Product ps= productService.findByIdProduct(idp);
		Seller u = ps.getSellerProduct();
		boolean checkInsert = false;
		// Chuyển object sang json sau đó đọc ra
		JsonNode jsonNode = objectMapper.valueToTree(object);
		JsonNode listNode = jsonNode.get("listvariant");
		List<ProductVariant> myList = objectMapper.convertValue(listNode, new TypeReference<List<ProductVariant>>() {});
		int idCategoryDetails = jsonNode.get("idCategoryDetails").asInt();
		Boolean active = jsonNode.get("active").asBoolean();
		int brand = jsonNode.get("brand").asInt();
		String createdDay = jsonNode.get("createdDay").asText();
		Date daycreated = dateFormat.parse(createdDay);
		String description = jsonNode.get("description").asText();
		String id = jsonNode.get("id").asText();
		String name = jsonNode.get("name").asText();
		CategoryDetails categoryDetails = categoryDetailService.findCategoryDetailsById(idCategoryDetails);
		Seller seller = sellerService.findFirstByUsername(u.getUsername());
		Product p = productService.findByIdProduct(id);
		// Update product
		productService.updateProduct(id, name, brand, description, daycreated, active, seller.getUsername(),
				categoryDetails.getId());
		// Duyệt list productVariant để thực hiện insert hoặc update
		for (ProductVariant productVariant : myList) {
			Integer idProductVariant = productVariant.getId();
			if (idProductVariant >= 100000) {
				productVariant.setPriceSale(productVariant.getPrice());
				productVariantService.updateProductVariant(idProductVariant, productVariant.getQuantity(),
						productVariant.getPrice(), productVariant.getPriceSale(), productVariant.getSize(),
						productVariant.getColor());
			} else if (idProductVariant < 100000) {
				productVariant.setPriceSale(productVariant.getPrice());
				productVariantService.addProductVariant(productVariant.getQuantity(), productVariant.getPrice(),
						productVariant.getPriceSale(), productVariant.getSize(), productVariant.getColor(), id);
			}
		}
	}
	
	@DeleteMapping("/ad/delete/product/{idproduct}")
	public void deleteproduct(@PathVariable("idproduct") String idproduct) {
		productService.deleteById(idproduct);
	}
	
	@GetMapping("/idproductrequest")
	public int getidProductRequest() {
		sessionService.set("request", true);
		return 0;
	}
	
	@PutMapping("/falserequest")
	public void updateFalseRequest() {
		sessionService.set("request", false);
	}
	
	@GetMapping("/getallproductrequesttrue")
	public Map<String, Object> getAllProductRequestTrue(){
		Map<String, Object> mapProductRequestTrue = new HashMap<>();
		List<infoProductDTO> listInfoProductTrue = new ArrayList<>();
		List<String> listNameCategoryTrue = new ArrayList<>();
		List<String> listNameProductBrandTrue = new ArrayList<>();
		List<String> listShopNameTrue = new ArrayList<>();
		List<Request> listProductRequestTrue = requestService.getAllRequestTrueDecreaseByCreatedDay();
		for (Request pr : listProductRequestTrue) {
			Product p = productService.findByIdProduct(pr.getEntityId());
			if(p != null) {
				infoProductDTO pd = new infoProductDTO();
				pd.setId(p.getId());
				pd.setName(p.getName());
				pd.setActive(p.getActive());
				pd.setSoldCount(p.getSoldCount());
				pd.setQuantity(p.getProductVariants().get(0).getQuantity());
				pd.setPrice(p.getProductVariants().get(0).getPrice());
				pd.setPriceSale(p.getProductVariants().get(0).getPriceSale());
				pd.setNameImageProduct(p.getImageProducts().get(0).getName());
				pd.setCateId(p.getCategoryDetails().getId());
				listInfoProductTrue.add(pd);
				CategoryDetails cad = categoryDetailService.findCategoryDetailsById(p.getCategoryDetails().getId());
				listNameCategoryTrue.add(cad.getName());
				listNameProductBrandTrue.add(brandService.findNameProductBrandById(p.getProductbrand().getId()));
				listShopNameTrue.add(p.getSellerProduct().getShopName());
			}
		}
		mapProductRequestTrue.put("listProductRequestTrue", listProductRequestTrue);
		mapProductRequestTrue.put("listInfoProductTrue", listInfoProductTrue);
		mapProductRequestTrue.put("listNameCategoryTrue", listNameCategoryTrue);
		mapProductRequestTrue.put("listNameProductBrandTrue", listNameProductBrandTrue);
		mapProductRequestTrue.put("listShopNameTrue", listShopNameTrue);
		return mapProductRequestTrue;
	}
	
	@PutMapping("/update/productrequesttrue")
	public Map<String, Object> updateProductRequestTrue(@RequestParam("id") int id){
		Request e = requestService.findRequestById(id);
		productService.updateProductActiveById(false, e.getEntityId());
		requestService.deleteById(id);
		Map<String, Object> mapProductRequestTrue = new HashMap<>();
		List<infoProductDTO> listInfoProductTrue = new ArrayList<>();
		List<String> listNameCategoryTrue = new ArrayList<>();
		List<String> listNameProductBrandTrue = new ArrayList<>();
		List<String> listShopNameTrue = new ArrayList<>();
		List<Request> listProductRequestTrue = requestService.getAllRequestTrueDecreaseByCreatedDay();
		for (Request pr : listProductRequestTrue) {
			Product p = productService.findByIdProduct(pr.getEntityId());
			if(p != null) {
				infoProductDTO pd = new infoProductDTO();
				pd.setId(p.getId());
				pd.setName(p.getName());
				pd.setActive(p.getActive());
				pd.setSoldCount(p.getSoldCount());
				pd.setQuantity(p.getProductVariants().get(0).getQuantity());
				pd.setPrice(p.getProductVariants().get(0).getPrice());
				pd.setPriceSale(p.getProductVariants().get(0).getPriceSale());
				pd.setNameImageProduct(p.getImageProducts().get(0).getName());
				pd.setCateId(p.getCategoryDetails().getId());
				listInfoProductTrue.add(pd);
				CategoryDetails cad = categoryDetailService.findCategoryDetailsById(p.getCategoryDetails().getId());
				listNameCategoryTrue.add(cad.getName());
				listNameProductBrandTrue.add(brandService.findNameProductBrandById(p.getProductbrand().getId()));
				listShopNameTrue.add(p.getSellerProduct().getShopName());
			}
		}
		mapProductRequestTrue.put("listProductRequestTrue", listProductRequestTrue);
		mapProductRequestTrue.put("listInfoProductTrue", listInfoProductTrue);
		mapProductRequestTrue.put("listNameCategoryTrue", listNameCategoryTrue);
		mapProductRequestTrue.put("listNameProductBrandTrue", listNameProductBrandTrue);
		mapProductRequestTrue.put("listShopNameTrue", listShopNameTrue);
		return mapProductRequestTrue;
	}
	
	@DeleteMapping("/delete/productrequesttrue")
	public Map<String, Object> deleteProductRequestTrue(@RequestParam("id") int id){
		requestService.deleteById(id);
		Map<String, Object> mapProductRequestTrue = new HashMap<>();
		List<infoProductDTO> listInfoProductTrue = new ArrayList<>();
		List<String> listNameCategoryTrue = new ArrayList<>();
		List<String> listNameProductBrandTrue = new ArrayList<>();
		List<String> listShopNameTrue = new ArrayList<>();
		List<Request> listProductRequestTrue = requestService.getAllRequestTrueDecreaseByCreatedDay();
		for (Request pr : listProductRequestTrue) {
			Product p = productService.findByIdProduct(pr.getEntityId());
			if(p != null) {
				infoProductDTO pd = new infoProductDTO();
				pd.setId(p.getId());
				pd.setName(p.getName());
				pd.setActive(p.getActive());
				pd.setSoldCount(p.getSoldCount());
				pd.setQuantity(p.getProductVariants().get(0).getQuantity());
				pd.setPrice(p.getProductVariants().get(0).getPrice());
				pd.setPriceSale(p.getProductVariants().get(0).getPriceSale());
				pd.setNameImageProduct(p.getImageProducts().get(0).getName());
				pd.setCateId(p.getCategoryDetails().getId());
				listInfoProductTrue.add(pd);
				CategoryDetails cad = categoryDetailService.findCategoryDetailsById(p.getCategoryDetails().getId());
				listNameCategoryTrue.add(cad.getName());
				listNameProductBrandTrue.add(brandService.findNameProductBrandById(p.getProductbrand().getId()));
				listShopNameTrue.add(p.getSellerProduct().getShopName());
			}
		}
		mapProductRequestTrue.put("listProductRequestTrue", listProductRequestTrue);
		mapProductRequestTrue.put("listInfoProductTrue", listInfoProductTrue);
		mapProductRequestTrue.put("listNameCategoryTrue", listNameCategoryTrue);
		mapProductRequestTrue.put("listNameProductBrandTrue", listNameProductBrandTrue);
		mapProductRequestTrue.put("listShopNameTrue", listShopNameTrue);
		return mapProductRequestTrue;
	}
}
