package com.example.collection.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.collection.mapper.StoreMapper;
import com.example.collection.models.entities.Store;
import com.example.collection.models.values.StoreValue;
import com.example.collection.repositories.StoreRepository;

@Service
public class StoreService {

	private final StoreRepository storeRepository;
	private final StoreMapper storeMapper;

	@Autowired
	public StoreService(StoreRepository storeRepository, StoreMapper storeMapper) {
		this.storeRepository = storeRepository;
		this.storeMapper = storeMapper;
	}

	public Optional<Store> findById(Long id) {
		return storeRepository.findById(id);
	}

	public Optional<Store> findByIdUsingMapper(Long id) {
		return storeMapper.findById(id);
	}

	@Transactional
	public Store save(StoreValue value) {

		Store store = Store.builder()
				.name(value.getName())
				.storeBusiness(value.getStoreBusiness()).build();		// FIXME userId -> user

		return storeRepository.save(store);
	}

	@Transactional
	public void patch(Store store, StoreValue value) {
		if(StringUtils.isNotBlank(value.getName()))
			store.setName(value.getName());
		if(StringUtils.isNotBlank(value.getStoreBusiness()))
			store.setStoreBusiness(value.getStoreBusiness());
		// user
	}

	@Transactional
	public void delete(Store store) {
//		store.setDel(true);
		storeRepository.delete(store);
	}

	public List<Store> findAll(Pageable pageable) {
		return storeRepository.findAllByDelOrderByIdDesc(false, pageable);
	}
}
