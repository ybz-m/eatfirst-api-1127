package jp.co.eatfirst.backendapi.app.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jp.co.eatfirst.backendapi.app.dao.entity.Category;
import jp.co.eatfirst.backendapi.app.dao.entity.Option;
import jp.co.eatfirst.backendapi.app.dao.entity.Product;
import jp.co.eatfirst.backendapi.app.domain.DomainFactory;
import jp.co.eatfirst.backendapi.app.domain.Store;
import jp.co.eatfirst.backendapi.app.dao.entity.dto.ProductOptionDto;
import jp.co.eatfirst.backendapi.app.dto.form.CategoryForm;
import jp.co.eatfirst.backendapi.app.dto.form.OrderForm;
import jp.co.eatfirst.backendapi.app.dto.form.ProductForm;
import jp.co.eatfirst.backendapi.app.dto.mapper.*;
import jp.co.eatfirst.backendapi.app.dto.vo.CategoryVO;
import jp.co.eatfirst.backendapi.app.dto.vo.OptionGroupVO;
import jp.co.eatfirst.backendapi.app.dto.vo.OptionVO;
import jp.co.eatfirst.backendapi.app.dto.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final String CATEGORY_CACHE = "Category_Cache";
    private static final String PRODUCT_CACHE = "Product_Cache";
    @Autowired
    DomainFactory domainFactory;

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OptionMapper optionMapper;

    @Autowired
    OptionGroupMapper optionGroupMapper;
    @Autowired
    ProductCategoryFormMapper productCategoryFormMapper;

    @Cacheable(cacheNames=CATEGORY_CACHE, key = "#storeId")
    public List<CategoryVO> getAllCategory(Long storeId){
        Store store = domainFactory.getStore(storeId);
        List<Category> categorys = store.getAllCategory();
        return categoryMapper.toVo(categorys);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CATEGORY_CACHE, key = "#storeId")
    public void saveCategory(Long storeId, CategoryForm form){
        Store store = domainFactory.getStore(storeId);
        store.saveCategory(
                form.getCategoryId(),
                form.getColor(),
                form.getName(),
                form.getNameEn(),
                form.getNameCn(),
                form.getCategoryDisplay(),
                form.getDisplayOrder());
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CATEGORY_CACHE, key = "#storeId")
    public void deleteCategory(Long storeId, Long categoryId){
        domainFactory.getStore(storeId).delCategory(categoryId);
    }

    @Cacheable(cacheNames=PRODUCT_CACHE, key = "#categoryId")
    public List<ProductVO> getAllProductByCategory(Long storeId, Long categoryId){
        Store store = domainFactory.getStore(storeId);
        List<Product> products = store.getAllProductByCategory(categoryId);
        List<Option> options = store.getAllOptionByCategory(categoryId);
        List<ProductOptionDto> productOptions = store.getAllOptionGroupByCategory(categoryId);

        Map<Long, List<Option>> optionMap = options.stream().collect(Collectors.groupingBy(
                Option::getOptionGroupId,
                LinkedHashMap::new,
                Collectors.mapping(
                        s->s, Collectors.toList())));

        Map<Long, List<ProductOptionDto>> productOptionMap = productOptions.stream().collect(Collectors.groupingBy(
                ProductOptionDto::getProductId,
                LinkedHashMap::new,
                Collectors.mapping(
                        s->s, Collectors.toList())));

        List<ProductVO> productVOs = Lists.newArrayList();
        List<OptionGroupVO> optionGroupVOList  = Lists.newArrayList();
        products.forEach(product -> {
            ProductVO productVO = productMapper.toVo(product);
            List<ProductOptionDto> productOptionLists = productOptionMap.get(product.getProductId());
            if(!CollectionUtils.isEmpty(productOptionLists)){
                productOptionLists.stream().forEach(productOption ->{
                    List<Option> optionLists = optionMap.get(productOption.getOptionGroupId());
                    if(!CollectionUtils.isEmpty(optionLists)){
                        OptionGroupVO optionGroupVO = OptionGroupVO.builder().name(productOption.getName()).nameCn(productOption.getNameCn()).nameEn(productOption.getNameEn())
                                .optionGroupId(productOption.getOptionGroupId()).build();
                        optionGroupVO.setOptions(optionMapper.toVo(optionLists));
                        optionGroupVOList.add(optionGroupVO);
                    }
                });
            }

            productVO.setOptionGroups(optionGroupVOList);
            productVOs.add(productVO);
        });
        return productVOs;
    }
    @Transactional(rollbackFor = Exception.class)
    public void order(String storeId, OrderForm form){

    }
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = PRODUCT_CACHE, key = "#form.categoryId")
    public void saveProduct(Long storeId, ProductForm form){
        domainFactory.getStore(storeId).saveProduct(
                form.getProductId(),
                form.getCategoryDisplayPosition(),
                form.getProductImage(),
                form.getProductName(),
                form.getProductNameEn(),
                form.getProductNameCn(),
                form.getProductNameReceipt(),
                form.getTaxType(),
                form.getReceiptPrinting(),
                form.getComment(),
                productCategoryFormMapper.toDto(form.getCategorys()),
                form.getDisplayFlg()
        );
    }
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = PRODUCT_CACHE, key = "#categoryId")
    public void deleteProduct(Long storeId, Long categoryId, Long productId){
        domainFactory.getStore(storeId).delProduct( productId);
    }
}
