package com.example.collection.mapper;


import java.util.Optional;

import com.example.collection.models.entities.Store;

public interface StoreMapper {

    public Optional<Store> findById(long id);
}

//@Component
//public class StoreMapper {
//	
//	@Autowired
//	private SqlSession sqlSessionTemplate;
//	
//	public Optional<Store> findById(long id) {
//		return Optional.ofNullable(sqlSessionTemplate.selectOne("com.example.collection.mapper.StoreMapper.findById", id));
//	}
//	
//}
