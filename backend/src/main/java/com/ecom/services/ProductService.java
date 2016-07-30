package com.ecom.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ecom.convertors.DtoConvertor;
import com.ecom.dto.ProductDto;
import com.ecom.exceptions.DuplicateEntryException;
import com.ecom.exceptions.InvalidDataException;
import com.ecom.exceptions.NotFoundException;
import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.exceptions.DataIntegrityException;

/**
 * Product class contains attributes of the Product.
 * 
 * @author raghunandangupta
 *
 */
@Service
public class ProductService {

	final static Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository productRepository;

	/**
	 * This method will be used to save the Product.
	 * 
	 * @param product
	 * @return
	 */
	public ProductDto save(ProductDto productDto) {
		Product savedProduct = null;
		Product product = null;
		try {
			savedProduct = productRepository.findOne(productDto.getCode());
			if (savedProduct != null) {
				return update(productDto);
			} else {
				product = DtoConvertor.buildProduct(productDto);
				product = productRepository.save(product);
			}
			return DtoConvertor.buildProductDto(product);
		} catch (DuplicateEntryException exception) {
			throw exception;
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.error("Exception occured {}", exception);
			throw new InvalidDataException();
		}
	}

	/**
	 * This method will get the Product Details.
	 * 
	 * @param productId
	 * @return
	 */
	public ProductDto get(String productId) {
		Product product = null;
		synchronized (productId.intern()) {
			try {
				product = productRepository.findOne(productId);
				if (product == null) {
					throw new NotFoundException();
				}
			} catch (NotFoundException exception) {
				throw exception;
			} catch (Exception exception) {
				logger.error("Exception occured {}", exception);
				throw new InvalidDataException();
			}
		}
		return DtoConvertor.buildProductDto(product);
	}

	/**
	 * This will delete product from Database
	 * 
	 * @param productId
	 * @return
	 */
	public ProductDto delete(String productId) {
		Product product = null;
		synchronized (productId.intern()) {
			try {
				product = productRepository.findOne(productId);
				if (product == null) {
					throw new NotFoundException();
				} else {
					productRepository.delete(productId);
				}
			} catch (NotFoundException exception) {
				throw exception;
			}catch (DataIntegrityViolationException exception) {
				logger.error("Exception occured {}", exception);
				throw new DataIntegrityException();
			} catch (Exception exception) {
				logger.error("Exception occured {}", exception);
				throw new InvalidDataException();
			}
		}
		return DtoConvertor.buildProductDto(product);
	}

	/**
	 * This method will update the product details.
	 * 
	 * @param product
	 * @return
	 */
	public ProductDto update(ProductDto productDto) {
		Product savedProduct = null;
		synchronized (productDto.getCode().intern()) {
			try {
				savedProduct = productRepository.findOne(productDto.getCode());
				if (savedProduct == null) {
					throw new NotFoundException();
				} else {
					savedProduct.setModification_ts(Calendar.getInstance());
					savedProduct.setDescription(productDto.getDescription());
					savedProduct.setPrice(productDto.getPrice());
					savedProduct.setQuantity(productDto.getQuantity());
					productRepository.save(savedProduct);
				}
			} catch (NotFoundException exception) {
				throw exception;
			} catch (Exception exception) {
				logger.error("Exception occured {}", exception);
				throw new InvalidDataException();
			}
		}
		return DtoConvertor.buildProductDto(savedProduct);
	}

	/**
	 * This method will return product by pagination.
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<ProductDto> getAll(Integer pageNumber, Integer pageSize) {
		List<ProductDto> productDtos = null;
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
		List<Product> productList = productRepository.findAll(pageRequest);
		if (productList == null || productList.size() == 0) {
			throw new NotFoundException();
		} else {
			productDtos = new ArrayList<ProductDto>();
			for (Product product : productList) {
				productDtos.add(DtoConvertor.buildProductDto(product));
			}
		}
		return productDtos;
	}

}
