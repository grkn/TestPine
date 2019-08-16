package com.friends.tanistan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.friends.tanistan.entity.TanistanBaseEntity;

@Repository
interface BaseTanistanJpaRepository<T extends TanistanBaseEntity<String>> extends CrudRepository<T, String>, PagingAndSortingRepository<T, String> {

}
