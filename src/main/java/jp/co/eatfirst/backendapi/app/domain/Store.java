package jp.co.eatfirst.backendapi.app.domain;

import com.google.common.collect.Maps;
import jp.co.eatfirst.backendapi.app.dao.entity.*;
import jp.co.eatfirst.backendapi.app.dao.entity.dto.ProductOptionDto;
import jp.co.eatfirst.backendapi.app.dao.repository.db.*;
import jp.co.eatfirst.backendapi.app.dto.dto.ProductCategoryDto;
import jp.co.eatfirst.backendapi.middleware.IdGenerator;
import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import lombok.Builder;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
public class Store {
    Long storeId;

    CategoryRepository categoryRepository;

    ProductRepository productRepository;

    StoreInfomationRepository storeInfomationRepository;

    OptionRepository optionRepository;
    OptionGroupRepository optionGroupRepository;

    ProductOptionRepository productOptionRepository;

    ProductCategoryRepository productCategoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findByStoreId(storeId);
    }
    public void saveCategory(Long categoryId, String color, String name, String nameEn, String nameCn , int displayOrder, int categoryDisplay){
        Category category ;
        if(categoryId != null){
            Optional<Category> cate = categoryRepository.findById(categoryId);
            if(!cate.isPresent()){
                throw new BusinessException("categoryId is not exists");
            }
            category = cate.get();
        } else {
            category = new Category();
            category.setCategoryId(IdGenerator.IdType.CATEGORY.next());
        }
        category.setStoreId(storeId);
        category.setCategoryName(name);
        category.setCategoryNameCn(nameCn);
        category.setCategoryNameEn(nameEn);
        category.setColorCode(color);
        category.setCategoryDisplay(categoryDisplay);
        category.setDisplayOrder(displayOrder);
        categoryRepository.save(category);
    }

    public void delCategory(Long categoryId){
        Optional<Category> cate = categoryRepository.findById(categoryId);
        if(!cate.isPresent()){
            throw new BusinessException("categoryId is not exists");
        }
        categoryRepository.delete(cate.get());
    }

    public List<Option> getAllOptionByCategory(Long categoryId){
        return optionRepository.findByCategoryId(categoryId);
    }
    public List<ProductOptionDto> getAllOptionGroupByCategory(Long categoryId){
        return optionGroupRepository.findByCategoryId(categoryId);
    }
    public List<Product> getAllProductByCategory(Long categoryId){
        return productRepository.findByCategoryId(categoryId);
    }

    public void saveProduct(Long productId,
                            String categoryDisplayPosition,
                            String productImage,
                            String productName,
                            String productNameEn,
                            String productNameCn,
                            String productNameReceipt,
                            int taxType,
                            int receiptPrinting,
                            String comment,
                            List<ProductCategoryDto> categoryIds,
                            int displayFlg){
        Product product ;
        List<ProductCategory> oldCategoryList = null;
        if(productId != null){
            Optional<Product> hasProduct = productRepository.findById(productId);
            if(!hasProduct.isPresent()){
                throw new BusinessException("categoryId/productId is not exists");
            }
            product = hasProduct.get();
            oldCategoryList = productCategoryRepository.findByProductId(productId);
        } else {
            product = new Product();
            product.setProductId(IdGenerator.IdType.PRODUCT.next());
        }
        product.setProductImage(productImage);
        product.setProductName(productName);
        product.setProductNameEn(productNameEn);
        product.setProductNameCn(productNameCn);
        product.setCategoryDisplayPosition(categoryDisplayPosition);
        product.setProductNameReceipt(productNameReceipt);
        product.setDisplayFlg(displayFlg);
        product.setComment(comment);
        product.setTaxType(taxType);
        product.setReceiptPrinting(receiptPrinting);
        productRepository.save(product);


        final Map<Long, Integer> categoryMap = Maps.newHashMap();
        final Map<Long, Integer> oldCategoryMap = Maps.newHashMap();
        if(!CollectionUtils.isEmpty(oldCategoryList)){
            oldCategoryMap.putAll(oldCategoryList.stream().collect(Collectors.toMap(x->x.getCategoryId(), y->y.getSortOrder() )));
        }
        if(!CollectionUtils.isEmpty(categoryIds)){
            categoryMap.putAll(categoryIds.stream().collect(Collectors.toMap(x->x.getCategoryId(), y->y.getSortOrder() )));

            categoryIds.stream().filter(x-> !oldCategoryMap.containsKey(x.getCategoryId()))
                    .forEach(categoryId -> {
                        ProductCategory newProductCategory = new ProductCategory();
                        newProductCategory.setCategoryId(categoryId.getCategoryId());
                        newProductCategory.setProductId(categoryId.getProductId());
                        newProductCategory.setSortOrder(categoryId.getSortOrder());
                        productCategoryRepository.save(newProductCategory);
                    });

        }
        if(!CollectionUtils.isEmpty(oldCategoryList)){
            oldCategoryList.forEach(oldCategory->{
                if(categoryMap.containsKey(oldCategory.getCategoryId())){
                    if(!categoryMap.get(oldCategory.getCategoryId()).equals(oldCategory.getSortOrder())){
                        //update
                        oldCategory.setSortOrder(categoryMap.get(oldCategory.getCategoryId()));
                        productCategoryRepository.save(oldCategory);
                    }
                } else {
                    productCategoryRepository.deleteById(ProductCategoryPK.builder().categoryId(oldCategory.getCategoryId()).productId(oldCategory.getProductId()).build());
                }
            });
        }


    }

    public void delProductCategoryLink(Long productId, Long categoryId){
        productCategoryRepository.deleteById(ProductCategoryPK.builder().categoryId(categoryId).productId(productId).build());

    }
    public void addProductCategoryLink(Long productId, Long categoryId, Integer sortOrder){
        ProductCategory newProductCategory = new ProductCategory();
        newProductCategory.setCategoryId(categoryId);
        newProductCategory.setProductId(productId);
        newProductCategory.setSortOrder(sortOrder);
        productCategoryRepository.save(newProductCategory);

    }
    public void delProduct(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            throw new BusinessException("categoryId/productId is not exists");
        }
        productRepository.delete(product.get());

        productCategoryRepository.findByProductId(productId);
    }

    public Optional<StoreInformation> getStoteInfo(){
        return storeInfomationRepository.findById(storeId);
    }

    public void saveStoteInfo(String storeNo,
                              String storeName,
                              Integer tradeDetails,
                              String storeTel,
                              String storeZip,
                              Integer storeAddress1,
                              String storeAddress2,
                              String storeAddress3,
                              String storeAddress4,
                              String descreption,
                              String storeLogo,
                              String storeImageUrl){
        StoreInformation newStoreInformation;
        if(storeId != null){
            Optional<StoreInformation> hasInfo = storeInfomationRepository.findById(storeId);
            if(!hasInfo.isPresent()){
                throw new BusinessException("storeId is not exists");
            }
            newStoreInformation = hasInfo.get();
        } else {
            throw new BusinessException("storeId is null");
        }
        newStoreInformation.setStoreNo(storeNo);
        newStoreInformation.setStoreName(storeName);
        newStoreInformation.setTradeDetails(tradeDetails);
        newStoreInformation.setStoreTel(storeTel);
        newStoreInformation.setStoreZip(storeZip);
        newStoreInformation.setStoreAddress1(storeAddress1);
        newStoreInformation.setStoreAddress2(storeAddress2);
        newStoreInformation.setStoreAddress3(storeAddress3);
        newStoreInformation.setStoreAddress4(storeAddress4);
        newStoreInformation.setStoreLogo(storeLogo);
        newStoreInformation.setStoreImageUrl(storeImageUrl);
        newStoreInformation.setDescreption(descreption);
        storeInfomationRepository.save(newStoreInformation);
    }
}
